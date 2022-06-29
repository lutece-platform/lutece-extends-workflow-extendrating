/*
 * Copyright (c) 2002-2022, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.extendrating.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import fr.paris.lutece.plugins.extend.modules.rating.business.Rating;
import fr.paris.lutece.plugins.extend.modules.rating.business.SimpleRating;
import fr.paris.lutece.plugins.extend.modules.rating.service.RatingService;
import fr.paris.lutece.plugins.workflow.modules.extendrating.business.IUpdateTaskStateResourceQueueDAO;
import fr.paris.lutece.plugins.workflow.modules.extendrating.business.UpdateTaskStateResourceQueue;
import fr.paris.lutece.plugins.workflow.modules.extendrating.business.UpdateTaskStateResourceConfig;
import fr.paris.lutece.plugins.workflow.modules.extendrating.util.Constants;
import fr.paris.lutece.plugins.workflow.utils.WorkflowUtils;
import fr.paris.lutece.plugins.workflowcore.business.action.Action;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceWorkflow;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.plugins.workflowcore.service.action.ActionService;
import fr.paris.lutece.plugins.workflowcore.service.action.IActionService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceWorkflowService;
import fr.paris.lutece.plugins.workflowcore.service.resource.ResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.resource.ResourceWorkflowService;
import fr.paris.lutece.plugins.workflowcore.service.state.IStateService;
import fr.paris.lutece.plugins.workflowcore.service.state.StateService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITaskService;
import fr.paris.lutece.plugins.workflowcore.service.task.TaskService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;

/**
 * 
 * UpdateTaskStateResourceQueueService
 *
 */
public class UpdateTaskStateResourceQueueService implements IUpdateTaskStateResourceQueueService
{
    @Inject
    @Named( ResourceWorkflowService.BEAN_SERVICE )
    private IResourceWorkflowService _resourceWorkflowService;
    @Inject
    @Named( ResourceHistoryService.BEAN_SERVICE )
    private IResourceHistoryService _resourceHistoryService;
    @Inject
    @Named( ActionService.BEAN_SERVICE )
    private IActionService _actionService;
    @Inject
    @Named( StateService.BEAN_SERVICE )
    private IStateService _stateService;
    @Inject
    @Named( TaskService.BEAN_SERVICE )
    private ITaskService _taskService;
    @Inject
    private IUpdateTaskStateResourceQueueDAO _updateResourceQueueDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create( UpdateTaskStateResourceQueue updateResourceQueue )
    {
        if ( updateResourceQueue != null )
        {
            _updateResourceQueueDAO.insert( updateResourceQueue );
        }
    }

    @Override
    public void update( UpdateTaskStateResourceQueue updateResourceQueue )
    {
        if ( updateResourceQueue != null )
        {
            _updateResourceQueueDAO.store( updateResourceQueue );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UpdateTaskStateResourceQueue> findAllActived( )
    {
        return _updateResourceQueueDAO.selectAllActived( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UpdateTaskStateResourceQueue> findAllDisabled( )
    {
        return _updateResourceQueueDAO.selectAllDisabled( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateTaskStateResourceQueue find( int nIdResourceHistory, int nIdTask )
    {
        return _updateResourceQueueDAO.find( nIdResourceHistory, nIdTask );
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public UpdateTaskStateResourceQueue find( int nIdResource, String strResourceType )
	{
		return _updateResourceQueueDAO.find( nIdResource, strResourceType );
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdResourceHistory, int nIdTask )
    {
        _updateResourceQueueDAO.delete( nIdResourceHistory, nIdTask );
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public void deleteByIdTask( int nIdTask ) 
	{
		_updateResourceQueueDAO.deleteByIdTask( nIdTask );	
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void delete( int nIdResource, String strResourceType )
	{
		_updateResourceQueueDAO.delete( nIdResource, strResourceType );
		
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVotingGoalValid( UpdateTaskStateResourceConfig config, UpdateTaskStateResourceQueue updateResourceQueue )
    {
        Optional<Rating> optionalRating = RatingService.INSTANCE.findAndbuildRatingResult( String.valueOf( updateResourceQueue.getIdResource( ) ), updateResourceQueue.getResourceType( ), SimpleRating.class.getName( ) );

        if ( optionalRating.isPresent( ) )
        {
        	Rating rating= optionalRating.get();
            Action action = _actionService.findByPrimaryKey( _taskService.findByPrimaryKey( config.getIdTask( ), I18nService.getDefaultLocale( ) ).getAction( ).getId( ) );

            // If initial state is change
            if ( action.getStateBefore( ).getId( ) != config.getStartState( ) )
            {
                updateResourceQueue.setInitialStateChange( true );
                update( updateResourceQueue );
                return false;
            }

            if ( config.getOperation( ) == Constants.SUPERIEUR_EGAL && rating.getRatingCount( ) >= config.getNbVote( ) )
            {
                return true;
            }
            else
            {
                return config.getOperation( ) == Constants.INFERIEUR_EGAL && rating.getRatingCount( ) <= config.getNbVote( );
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doChangeState( UpdateTaskStateResourceConfig config, UpdateTaskStateResourceQueue updateResourceQueue )
    {
        Locale locale = I18nService.getDefaultLocale( );
        Action action = _actionService.findByPrimaryKey( _taskService.findByPrimaryKey( config.getIdTask( ), locale ).getAction( ).getId( ) );
        State state = _stateService.findByPrimaryKey( config.getEndState( ) );

        if ( state != null && action != null )
        {
            // Create Resource History
            ResourceHistory resourceHistory = new ResourceHistory( );
            resourceHistory.setIdResource( updateResourceQueue.getIdResource( ) );
            resourceHistory.setResourceType( updateResourceQueue.getResourceType( ) );
            resourceHistory.setAction( action );
            resourceHistory.setWorkFlow( action.getWorkflow( ) );
            resourceHistory.setCreationDate( WorkflowUtils.getCurrentTimestamp( ) );
            resourceHistory.setUserAccessCode( Constants.USER_AUTO );
            _resourceHistoryService.create( resourceHistory );

            // Update Resource
            ResourceWorkflow resourceWorkflow = _resourceWorkflowService.findByPrimaryKey( updateResourceQueue.getIdResource( ), Constants.FORMS_FORM_RESPONSE,
                    action.getWorkflow( ).getId( ) );
            resourceWorkflow.setState( state );
            _resourceWorkflowService.update( resourceWorkflow );

            // Delete updateResourceQueue
            delete( updateResourceQueue.getIdResource( ), updateResourceQueue.getIdTask( ) );
            
            // If the new state has automatic reflexive actions
            WorkflowService.getInstance( ).doProcessAutomaticReflexiveActions( updateResourceQueue.getIdResource( ), updateResourceQueue.getResourceType( ),
                    state.getId( ), resourceWorkflow.getExternalParentId( ), locale, null );

            // if new state has action automatic
            WorkflowService.getInstance( ).executeActionAutomatic( updateResourceQueue.getIdResource( ), updateResourceQueue.getResourceType( ),
                    action.getWorkflow( ).getId( ), resourceWorkflow.getExternalParentId( ), null );
        }
    }

}
