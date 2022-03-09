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

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.workflow.modules.extendrating.business.UpdateTaskStateResourceQueue;
import fr.paris.lutece.plugins.workflow.modules.extendrating.util.Constants;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceWorkflow;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceWorkflowService;
import fr.paris.lutece.plugins.workflowcore.service.resource.ResourceWorkflowService;
import fr.paris.lutece.portal.business.event.EventRessourceListener;
import fr.paris.lutece.portal.business.event.ResourceEvent;
import fr.paris.lutece.portal.service.util.AppLogService;

/**
 * 
 * UpdateTaskStateResourceListener
 *
 */
public class UpdateTaskStateResourceListener implements EventRessourceListener
{
    @Inject
    @Named( Constants.BEAN_UPDATE_RESOURCE_QUEUE_SERVICE )
    private IUpdateTaskStateResourceQueueService _updateResourceQueueService;
    
    @Inject
    @Named( ResourceWorkflowService.BEAN_SERVICE )
    private IResourceWorkflowService _resourceWorkflowService;
    
    
    @Override
    public String getName( )
    {
        return Constants.PLUGIN_NAME;
    }

    @Override
    public void addedResource( ResourceEvent event )
    {
        // do nothing
    }

    @Override
    public void deletedResource( ResourceEvent event )
    {
        try
        {
            if ( StringUtils.isNumeric( event.getIdResource( ) ) )
            {
            	_updateResourceQueueService.delete( Integer.parseInt( event.getIdResource( ) ) , event.getTypeResource( ) );
            }
        }
        catch( Exception e )
        {
            AppLogService.error( e.getMessage( ), e );
        }
    }

    @Override
    public void updatedResource( ResourceEvent event )
    {
        try
        {
            if ( StringUtils.isNumeric( event.getIdResource( ) ) )
            {
            	UpdateTaskStateResourceQueue resourceInQueue =  _updateResourceQueueService
                        .find( Integer.parseInt( event.getIdResource( ) ), event.getTypeResource( ) );
            	
                if ( resourceInQueue != null )
                {
                    ResourceWorkflow resourceWorkflow = _resourceWorkflowService.findByPrimaryKey( resourceInQueue.getIdResource( ),
                            resourceInQueue.getResourceType( ), resourceInQueue.getIdWorkflow( ) );
                    
                    if ( resourceWorkflow == null || resourceInQueue.getIdInitialState( ) != resourceWorkflow.getState( ).getId( ) )
                    {
                    	_updateResourceQueueService.delete( Integer.parseInt( event.getIdResource( ) ) , event.getTypeResource( ) );
                    }
                }
            }

        }
        catch( Exception e )
        {
            AppLogService.error( e.getMessage( ), e );
        }
    }

}
