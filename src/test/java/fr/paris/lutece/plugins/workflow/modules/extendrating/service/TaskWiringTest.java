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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.plugins.workflow.modules.extendrating.business.UpdateTaskStateResourceConfig;
import fr.paris.lutece.plugins.workflow.modules.extendrating.util.Constants;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfig;
import fr.paris.lutece.plugins.workflowcore.business.task.ITaskType;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.plugins.workflowcore.web.task.ITaskComponent;
import fr.paris.lutece.test.LuteceTestCase;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;

/**
 * Tests that the beans the workflow resolves by name are those of the update resource state task.
 */
public class TaskWiringTest extends LuteceTestCase
{
    private static final String TASK_TYPE_KEY = "taskTypeUpdateResourceState";

    /**
     * The task type carries the bean names and the flags of the task.
     */
    @Test
    public void testTaskType( )
    {
        ITaskType taskType = CDI.current( ).select( ITaskType.class, NamedLiteral.of( Constants.BEAN_TASK_TYPE ) ).get( );

        assertEquals( TASK_TYPE_KEY, taskType.getKey( ), "key" );
        assertEquals( Constants.BEAN_TASK, taskType.getBeanName( ), "task bean name" );
        assertEquals( Constants.BEAN_TASK_CONFIG, taskType.getConfigBeanName( ), "config bean name" );
        assertEquals( Constants.PROPERTY_TASK_TITLE, taskType.getTitleI18nKey( ), "title key" );
        assertTrue( taskType.isConfigRequired( ), "configuration required" );
        assertTrue( taskType.isTaskForAutomaticAction( ), "usable in an automatic action" );
    }

    /**
     * The task, its configuration and its component resolve under the names the task type gives.
     */
    @Test
    public void testNamedBeans( )
    {
        assertInstanceOf( UpdateStateResourceTask.class, CDI.current( ).select( ITask.class, NamedLiteral.of( Constants.BEAN_TASK ) ).get( ), "task" );
        assertInstanceOf( UpdateTaskStateResourceConfig.class,
                CDI.current( ).select( ITaskConfig.class, NamedLiteral.of( Constants.BEAN_TASK_CONFIG ) ).get( ), "configuration" );

        ITaskComponent component = CDI.current( ).select( ITaskComponent.class, NamedLiteral.of( Constants.BEAN_TASK_COMPONENT ) ).get( );
        assertTrue( component.isInvoked( TASK_TYPE_KEY ), "component bound to the task type" );
    }
}
