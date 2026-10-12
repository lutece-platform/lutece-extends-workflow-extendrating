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

import org.eclipse.microprofile.config.inject.ConfigProperty;

import fr.paris.lutece.plugins.workflow.modules.extendrating.util.Constants;
import fr.paris.lutece.plugins.workflowcore.business.task.ITaskType;
import fr.paris.lutece.plugins.workflowcore.business.task.TaskType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

/**
 * Produces the task type of the update resource state task.
 */
@ApplicationScoped
public class TaskTypeProducer
{
    /**
     * Produces the update resource state task type
     *
     * @param strKey
     *            the task type key
     * @param strTitleI18nKey
     *            the i18n key of the title
     * @param strBeanName
     *            the name of the task bean
     * @param strConfigBeanName
     *            the name of the task config bean
     * @param bConfigRequired
     *            true if the task needs a configuration
     * @param bFormTaskRequired
     *            true if the task needs a form
     * @param bTaskForAutomaticAction
     *            true if the task can be used in an automatic action
     * @return the task type
     */
    @Produces
    @ApplicationScoped
    @Named( Constants.BEAN_TASK_TYPE )
    public ITaskType produceTaskTypeUpdateResourceState(
            @ConfigProperty( name = "workflow-extendrating.taskTypeUpdateResourceState.key" ) String strKey,
            @ConfigProperty( name = "workflow-extendrating.taskTypeUpdateResourceState.titleI18nKey" ) String strTitleI18nKey,
            @ConfigProperty( name = "workflow-extendrating.taskTypeUpdateResourceState.beanName" ) String strBeanName,
            @ConfigProperty( name = "workflow-extendrating.taskTypeUpdateResourceState.configBeanName" ) String strConfigBeanName,
            @ConfigProperty( name = "workflow-extendrating.taskTypeUpdateResourceState.configRequired", defaultValue = "false" ) boolean bConfigRequired,
            @ConfigProperty( name = "workflow-extendrating.taskTypeUpdateResourceState.formTaskRequired", defaultValue = "false" ) boolean bFormTaskRequired,
            @ConfigProperty( name = "workflow-extendrating.taskTypeUpdateResourceState.taskForAutomaticAction", defaultValue = "false" ) boolean bTaskForAutomaticAction )
    {
        TaskType taskType = new TaskType( );
        taskType.setKey( strKey );
        taskType.setTitleI18nKey( strTitleI18nKey );
        taskType.setBeanName( strBeanName );
        taskType.setConfigBeanName( strConfigBeanName );
        taskType.setConfigRequired( bConfigRequired );
        taskType.setFormTaskRequired( bFormTaskRequired );
        taskType.setTaskForAutomaticAction( bTaskForAutomaticAction );

        return taskType;
    }
}
