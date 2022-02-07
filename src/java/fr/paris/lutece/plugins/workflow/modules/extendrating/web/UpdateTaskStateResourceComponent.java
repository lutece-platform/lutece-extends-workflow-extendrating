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
package fr.paris.lutece.plugins.workflow.modules.extendrating.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.api.user.User;
import fr.paris.lutece.plugins.workflow.modules.extendrating.util.Constants;
import fr.paris.lutece.plugins.workflow.web.task.NoFormTaskComponent;
import fr.paris.lutece.plugins.workflowcore.business.action.Action;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.plugins.workflowcore.service.action.ActionService;
import fr.paris.lutece.plugins.workflowcore.service.action.IActionService;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;

/**
 * 
 * UpdateTaskStateResourceComponent
 *
 */
public class UpdateTaskStateResourceComponent extends NoFormTaskComponent
{

    private static final String TEMPLATE_CONFIG = "admin/plugins/workflow/modules/extendrating/update_resource_state_task_form.html";

    @Inject
    @Named( Constants.BEAN_TASK_CONFIG_SERVICE )
    private ITaskConfigService _taskConfigService;

    @Inject
    @Named( ActionService.BEAN_SERVICE )
    private IActionService _actionService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayConfigForm( HttpServletRequest request, Locale locale, ITask task )
    {
        Map<String, Object> model = new HashMap<>( );

        Action action = _actionService.findByPrimaryKey( task.getAction( ).getId( ) );

        model.put( Constants.MARK_STATES, getStates( request, action.getWorkflow( ).getId( ) ) );
        model.put( Constants.MARK_INITIAL_STATE, action.getStateBefore( ) );
        model.put( Constants.MARK_CONFIG, _taskConfigService.findByPrimaryKey( task.getId( ) ) );
        model.put( Constants.MARK_SUPERIEUR_EGAL, Constants.SUPERIEUR_EGAL );
        model.put( Constants.MARK_INFERIEUR_EGAL, Constants.INFERIEUR_EGAL );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CONFIG, locale, model );

        return template.getHtml( );
    }

    /**
     * 
     * @param request
     * @return
     */
    private ReferenceList getStates( HttpServletRequest request, int nIdWorkflow )
    {
        User user = AdminUserService.getAdminUser( request );

        Collection<State> states = WorkflowService.getInstance( ).getAllStateByWorkflow( nIdWorkflow, user );
        ReferenceList referenceList = new ReferenceList( );
        if ( states != null )
        {
            referenceList.addItem( StringUtils.EMPTY, StringUtils.EMPTY );
            for ( State state : states )
            {
                if ( !state.isInitialState( ).booleanValue( ) )
                {
                    referenceList.addItem( String.valueOf( state.getId( ) ), state.getName( ) );
                }
            }
        }
        return referenceList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskInformation( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        return null;
    }
}
