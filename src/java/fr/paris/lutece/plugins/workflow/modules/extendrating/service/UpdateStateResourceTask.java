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

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;


import fr.paris.lutece.plugins.workflow.modules.extendrating.business.UpdateTaskStateResourceQueue;
import fr.paris.lutece.plugins.workflow.modules.extendrating.util.Constants;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceWorkflow;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceWorkflowService;
import fr.paris.lutece.plugins.workflowcore.service.resource.ResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.resource.ResourceWorkflowService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.portal.service.i18n.I18nService;

/**
 * 
 * UpdateResourceStateTask
 *
 */
public class UpdateStateResourceTask extends SimpleTask
{
    @Inject
    @Named( ResourceHistoryService.BEAN_SERVICE )
    private IResourceHistoryService _resourceHistoryService;
    @Inject
    @Named( Constants.BEAN_TASK_CONFIG_SERVICE )
    private ITaskConfigService _taskConfigService;
    @Inject
    @Named( ResourceWorkflowService.BEAN_SERVICE )
    private IResourceWorkflowService _resourceWorkflowService;
    @Inject
    @Named( Constants.BEAN_UPDATE_RESOURCE_QUEUE_SERVICE )
    private IUpdateTaskStateResourceQueueService _updateResourceQueueService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle( Locale locale )
    {
    	return I18nService.getLocalizedString( Constants.PROPERTY_TASK_TITLE, locale );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );

        ResourceWorkflow resourceWorkflow = _resourceWorkflowService.findByPrimaryKey( resourceHistory.getIdResource( ), resourceHistory.getResourceType( ),
                resourceHistory.getWorkflow( ).getId( ) );

        if ( _updateResourceQueueService.find( resourceHistory.getIdResource( ), getId( ) ) == null )
        {
            UpdateTaskStateResourceQueue updateResourceQueue = new UpdateTaskStateResourceQueue( );
            updateResourceQueue.setIdResource( resourceHistory.getIdResource( ) );
            updateResourceQueue.setIdTask( getId( ) );
            updateResourceQueue.setResourceType( getResourceType( resourceWorkflow ) );
            updateResourceQueue.setIdExternalParent( resourceWorkflow.getExternalParentId( ) );
            updateResourceQueue.setIdWorkflow( resourceWorkflow.getWorkflow( ).getId( ) );
            updateResourceQueue.setInitialStateChange( false );
            updateResourceQueue.setIdInitialState( resourceWorkflow.getState().getId( ) );
            
            _updateResourceQueueService.create( updateResourceQueue );
        }
    }
    
    @Override
    public void doRemoveConfig( )
    {
        _taskConfigService.remove( this.getId( ) );
        _updateResourceQueueService.deleteByIdTask( this.getId( ) );
    }
    
    @Override
    public void doRemoveTaskInformation( int nIdHistory )
    {
        ResourceHistory history = _resourceHistoryService.findByPrimaryKey( nIdHistory );
    	_updateResourceQueueService.delete( history.getIdResource( ), getId( ) );
    }
    /**
     * Gets resource type
     * 
     * @param resourceWorkflow
     * @return resource type
     */
     private String getResourceType( ResourceWorkflow resourceWorkflow )
     {
         if ( Constants.FORMS_FORM_RESPONSE.equals( resourceWorkflow.getResourceType( ) ) )
         {
               return Constants.FORMS_FORM_RESPONSE + "_" + resourceWorkflow.getExternalParentId( );
          }
          else
          {
              return resourceWorkflow.getResourceType( );
          }
     }
}
