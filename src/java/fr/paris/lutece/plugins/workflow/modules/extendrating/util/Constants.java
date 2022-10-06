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
package fr.paris.lutece.plugins.workflow.modules.extendrating.util;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;

/**
 * 
 * Constants
 *
 */
public final class Constants
{
    // BEAN
    public static final String PLUGIN_NAME = "workflow-extendrating";
    public static final String BEAN_TASK_CONFIG_SERVICE = PLUGIN_NAME + ".taskUpdateResourceStateConfigService";
    public static final String BEAN_UPDATE_RESOURCE_QUEUE_SERVICE = PLUGIN_NAME + ".updateResourceQueueService";

    // CONSTANTS
    public static final String FORMS_FORM_RESPONSE = "FORMS_FORM_RESPONSE";
    public static final String USER_AUTO = "auto";
    public static final int SUPERIEUR_EGAL = 1;
    public static final int INFERIEUR_EGAL = 2;

    // MARKS
    public static final String MARK_STATES = "list_states";
    public static final String MARK_INITIAL_STATE = "initial_state";
    public static final String MARK_CONFIG = "config";
    public static final String MARK_SUPERIEUR_EGAL = "superieur_egal";
    public static final String MARK_INFERIEUR_EGAL = "inferieur_egal";
    
    // PROPERTY
    public static final String PROPERTY_TASK_TITLE = "module.workflow.extendrating.updateResourceState.task_title";

    /**
     * Get the plugin
     * 
     * @return the plugin
     */
    public static Plugin getPlugin( )
    {
        return PluginService.getPlugin( PLUGIN_NAME );
    }

    /**
     * Private constructor
     */
    private Constants( )
    {
    }

}
