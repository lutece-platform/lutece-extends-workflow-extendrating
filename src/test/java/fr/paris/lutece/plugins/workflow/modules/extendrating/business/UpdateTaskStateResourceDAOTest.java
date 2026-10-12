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
package fr.paris.lutece.plugins.workflow.modules.extendrating.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.plugins.workflow.modules.extendrating.util.Constants;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO;
import fr.paris.lutece.test.LuteceTestCase;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Tests the DAOs of the update resource state task.
 */
public class UpdateTaskStateResourceDAOTest extends LuteceTestCase
{
    private static final int ID_TASK = 910001;
    private static final int ID_RESOURCE = 920001;
    private static final String RESOURCE_TYPE = "FORMS_FORM_RESPONSE_1";

    @Inject
    @Named( Constants.BEAN_TASK_CONFIG_DAO )
    private ITaskConfigDAO<UpdateTaskStateResourceConfig> _configDAO;

    @Inject
    private IUpdateTaskStateResourceQueueDAO _queueDAO;

    /**
     * Creates, reads, updates and deletes a task configuration.
     */
    @Test
    public void testConfigCRUD( )
    {
        UpdateTaskStateResourceConfig config = new UpdateTaskStateResourceConfig( );
        config.setIdTask( ID_TASK );
        config.setStartState( 1 );
        config.setEndState( 2 );
        config.setNbVote( 3 );
        config.setOperation( Constants.SUPERIEUR_EGAL );
        _configDAO.insert( config );

        UpdateTaskStateResourceConfig loaded = _configDAO.load( ID_TASK );
        assertNotNull( loaded, "inserted configuration" );
        assertEquals( 2, loaded.getEndState( ), "end state" );
        assertEquals( 3, loaded.getNbVote( ), "number of votes" );
        assertEquals( Constants.SUPERIEUR_EGAL, loaded.getOperation( ), "operation" );

        config.setNbVote( 5 );
        config.setOperation( Constants.INFERIEUR_EGAL );
        _configDAO.store( config );
        loaded = _configDAO.load( ID_TASK );
        assertEquals( 5, loaded.getNbVote( ), "updated number of votes" );
        assertEquals( Constants.INFERIEUR_EGAL, loaded.getOperation( ), "updated operation" );

        _configDAO.delete( ID_TASK );
        assertNull( _configDAO.load( ID_TASK ), "deleted configuration" );
    }

    /**
     * Queues a resource, finds it by task and by type, then removes it.
     */
    @Test
    public void testQueue( )
    {
        UpdateTaskStateResourceQueue queue = new UpdateTaskStateResourceQueue( );
        queue.setIdResource( ID_RESOURCE );
        queue.setIdTask( ID_TASK );
        queue.setResourceType( RESOURCE_TYPE );
        queue.setInitialStateChange( false );
        queue.setIdInitialState( 4 );
        queue.setIdExternalParent( 1 );
        queue.setIdWorkflow( 6 );
        _queueDAO.insert( queue );

        UpdateTaskStateResourceQueue loaded = _queueDAO.find( ID_RESOURCE, ID_TASK );
        assertNotNull( loaded, "queued resource" );
        assertEquals( RESOURCE_TYPE, loaded.getResourceType( ), "resource type" );
        assertEquals( 4, loaded.getIdInitialState( ), "initial state" );
        assertTrue( _queueDAO.selectAllActived( ).stream( ).anyMatch( q -> q.getIdResource( ) == ID_RESOURCE ), "active queue" );

        queue.setIdInitialState( 7 );
        _queueDAO.store( queue );
        assertEquals( 7, _queueDAO.find( ID_RESOURCE, RESOURCE_TYPE ).getIdInitialState( ), "updated initial state" );

        _queueDAO.delete( ID_RESOURCE, RESOURCE_TYPE );
        assertNull( _queueDAO.find( ID_RESOURCE, ID_TASK ), "removed resource" );
        assertFalse( _queueDAO.selectAllActived( ).stream( ).anyMatch( q -> q.getIdResource( ) == ID_RESOURCE ), "active queue after removal" );
    }
}
