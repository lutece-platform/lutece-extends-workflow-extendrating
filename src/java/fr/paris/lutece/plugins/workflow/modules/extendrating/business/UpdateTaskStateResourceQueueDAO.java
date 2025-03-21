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

import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for UpdateTaskStateResourceQueue objects
 */
public final class UpdateTaskStateResourceQueueDAO implements IUpdateTaskStateResourceQueueDAO
{

    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_resource, id_task, resource_type, initial_state_change, id_initial_state, id_external_parent, id_workflow  FROM extend_rating_update_resource_state_queue WHERE id_resource = ?";
    private static final String SQL_QUERY_SELECT_BY_ID_TASK_AND_ID_RESOURCE = "SELECT id_resource, id_task, resource_type, initial_state_change, id_initial_state, id_external_parent, id_workflow FROM extend_rating_update_resource_state_queue WHERE id_resource = ? AND id_task = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO extend_rating_update_resource_state_queue ( id_resource, id_task, resource_type, initial_state_change, id_initial_state, id_external_parent, id_workflow ) VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM extend_rating_update_resource_state_queue WHERE id_resource = ? AND id_task = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE extend_rating_update_resource_state_queue SET id_resource = ?, id_task = ?, resource_type = ?, initial_state_change = ?, id_initial_state = ?, id_external_parent = ?, id_workflow = ? WHERE id_resource = ? AND resource_type = ? AND id_workflow = ? ";
    private static final String SQL_QUERY_SELECTALL_ACTIVED = "SELECT id_resource, id_task, resource_type, initial_state_change, id_initial_state, id_external_parent, id_workflow FROM extend_rating_update_resource_state_queue WHERE initial_state_change = 0";
    private static final String SQL_QUERY_SELECTALL_DISABLED = "SELECT id_resource, id_task, resource_type, initial_state_change, id_initial_state, id_external_parent, id_workflow FROM extend_rating_update_resource_state_queue WHERE initial_state_change = 1";
    private static final String SQL_QUERY_DELETE_BY_ID_TASK = "DELETE FROM extend_rating_update_resource_state_queue WHERE id_task = ? ";
    private static final String SQL_QUERY_DELETE_BY_ID_RESOURCE_AND_TYPE = "DELETE FROM extend_rating_update_resource_state_queue WHERE id_resource = ? AND resource_type = ?";
    private static final String SQL_QUERY_SELECT_BY_ID_RESOURCE_AND_TYPE = "SELECT id_resource, id_task, resource_type, initial_state_change, id_initial_state, id_external_parent, id_workflow FROM extend_rating_update_resource_state_queue WHERE id_resource = ? AND resource_type = ?";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( UpdateTaskStateResourceQueue updateTaskStateResourceQueue )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, updateTaskStateResourceQueue.getIdResource( ) );
            daoUtil.setInt( ++nIndex, updateTaskStateResourceQueue.getIdTask( ) );
            daoUtil.setString( ++nIndex, updateTaskStateResourceQueue.getResourceType( ) );
            daoUtil.setBoolean( ++nIndex, updateTaskStateResourceQueue.isInitialStateChange( ) );
            daoUtil.setInt( ++nIndex, updateTaskStateResourceQueue.getIdInitialState( ) );
            daoUtil.setInt( ++nIndex, updateTaskStateResourceQueue.getIdExternalParent( ) );
            daoUtil.setInt( ++nIndex, updateTaskStateResourceQueue.getIdWorkflow( ) );
            
            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public UpdateTaskStateResourceQueue load( int nId )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT ) )
        {
            daoUtil.setInt( 1, nId );
            daoUtil.executeQuery( );

            UpdateTaskStateResourceQueue updateTaskStateResourceQueue = null;

            if ( daoUtil.next( ) )
            {
                int nIndex = 0;
                updateTaskStateResourceQueue = new UpdateTaskStateResourceQueue( );
                updateTaskStateResourceQueue.setIdResource( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdTask( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setResourceType( daoUtil.getString( ++nIndex ) );
                updateTaskStateResourceQueue.setInitialStateChange( daoUtil.getBoolean( ++nIndex ) );
                updateTaskStateResourceQueue.setIdInitialState( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdExternalParent( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdWorkflow( daoUtil.getInt( ++nIndex ) );
            }

            daoUtil.free( );

            return updateTaskStateResourceQueue;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nIdResource, int nIdTask )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE ) )
        {
            daoUtil.setInt( 1, nIdResource );
            daoUtil.setInt( 2, nIdTask );
            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

	@Override
	public void deleteByIdTask(int nIdTask)
	{
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_ID_TASK ) )
        {
            daoUtil.setInt( 1, nIdTask );
            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
	}
	
    /**
     * {@inheritDoc }
     */
    @Override
    public void store( UpdateTaskStateResourceQueue updateTaskStateResourceQueue )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, updateTaskStateResourceQueue.getIdResource( ) );
            daoUtil.setInt( ++nIndex, updateTaskStateResourceQueue.getIdTask( ) );
            daoUtil.setString( ++nIndex, updateTaskStateResourceQueue.getResourceType( ) );
            daoUtil.setBoolean( ++nIndex, updateTaskStateResourceQueue.isInitialStateChange( ) );
            daoUtil.setInt( ++nIndex, updateTaskStateResourceQueue.getIdInitialState( ) );
            daoUtil.setInt( ++nIndex, updateTaskStateResourceQueue.getIdExternalParent( ) );
            daoUtil.setInt( ++nIndex, updateTaskStateResourceQueue.getIdWorkflow( ) );
            
            daoUtil.setInt( ++nIndex, updateTaskStateResourceQueue.getIdResource( ) );
            daoUtil.setString( ++nIndex, updateTaskStateResourceQueue.getResourceType( ) );
            daoUtil.setInt( ++nIndex, updateTaskStateResourceQueue.getIdWorkflow( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UpdateTaskStateResourceQueue> selectAllActived( )
    {
        List<UpdateTaskStateResourceQueue> listUpdateResourceQueues = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ACTIVED ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
            	int nIndex = 0;
                UpdateTaskStateResourceQueue updateTaskStateResourceQueue = new UpdateTaskStateResourceQueue( );
                updateTaskStateResourceQueue.setIdResource( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdTask( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setResourceType( daoUtil.getString( ++nIndex ) );
                updateTaskStateResourceQueue.setInitialStateChange( daoUtil.getBoolean( ++nIndex ) );
                updateTaskStateResourceQueue.setIdInitialState( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdExternalParent( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdWorkflow( daoUtil.getInt( ++nIndex ) );
                
                listUpdateResourceQueues.add( updateTaskStateResourceQueue );
            }

            daoUtil.free( );

            return listUpdateResourceQueues;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UpdateTaskStateResourceQueue> selectAllDisabled( )
    {
        List<UpdateTaskStateResourceQueue> listUpdateResourceQueues = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_DISABLED ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
            	int nIndex = 0;
                UpdateTaskStateResourceQueue updateTaskStateResourceQueue = new UpdateTaskStateResourceQueue( );
                updateTaskStateResourceQueue.setIdResource( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdTask( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setResourceType( daoUtil.getString( ++nIndex ) );
                updateTaskStateResourceQueue.setInitialStateChange( daoUtil.getBoolean( ++nIndex ) );
                updateTaskStateResourceQueue.setIdInitialState( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdExternalParent( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdWorkflow( daoUtil.getInt( ++nIndex ) );
                
                listUpdateResourceQueues.add( updateTaskStateResourceQueue );
            }

            daoUtil.free( );

            return listUpdateResourceQueues;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public UpdateTaskStateResourceQueue find( int nIdResource, int nIdTask )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_TASK_AND_ID_RESOURCE ) )
        {
            daoUtil.setInt( 1, nIdResource );
            daoUtil.setInt( 2, nIdTask );
            daoUtil.executeQuery( );

            UpdateTaskStateResourceQueue updateTaskStateResourceQueue = null;

            if ( daoUtil.next( ) )
            {
                int nIndex = 0;
                updateTaskStateResourceQueue = new UpdateTaskStateResourceQueue( );
                updateTaskStateResourceQueue.setIdResource( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdTask( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setResourceType( daoUtil.getString( ++nIndex ) );
                updateTaskStateResourceQueue.setInitialStateChange( daoUtil.getBoolean( ++nIndex ) );
                updateTaskStateResourceQueue.setIdInitialState( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdExternalParent( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdWorkflow( daoUtil.getInt( ++nIndex ) );
            }

            daoUtil.free( );

            return updateTaskStateResourceQueue;
        }
    }

	@Override
	public void delete( int nIdResource, String strResourceType )
	{
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_ID_RESOURCE_AND_TYPE ) )
        {
            daoUtil.setInt( 1, nIdResource );
            daoUtil.setString( 2, strResourceType );
            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
	}
	
	@Override
	public UpdateTaskStateResourceQueue find(int nIdResource, String strResourceType)
	{
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_RESOURCE_AND_TYPE ) )
        {
            daoUtil.setInt( 1, nIdResource );
            daoUtil.setString( 2, strResourceType );
            daoUtil.executeQuery( );

            UpdateTaskStateResourceQueue updateTaskStateResourceQueue = null;

            if ( daoUtil.next( ) )
            {
                int nIndex = 0;
                updateTaskStateResourceQueue = new UpdateTaskStateResourceQueue( );
                updateTaskStateResourceQueue.setIdResource( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdTask( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setResourceType( daoUtil.getString( ++nIndex ) );
                updateTaskStateResourceQueue.setInitialStateChange( daoUtil.getBoolean( ++nIndex ) );
                updateTaskStateResourceQueue.setIdInitialState( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdExternalParent( daoUtil.getInt( ++nIndex ) );
                updateTaskStateResourceQueue.setIdWorkflow( daoUtil.getInt( ++nIndex ) );
            }

            daoUtil.free( );

            return updateTaskStateResourceQueue;
        }
	}

}
