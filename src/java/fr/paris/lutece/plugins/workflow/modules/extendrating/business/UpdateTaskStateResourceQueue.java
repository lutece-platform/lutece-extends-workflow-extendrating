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

/**
 * This is the business class for the object UpdateTaskStateResourceQueue
 */
public class UpdateTaskStateResourceQueue
{
    // Variables declarations
    private int _nIdResource;
    private int _nIdTask;
    private String _strResourceType;
    private int _nIdExternalParent;
    private boolean _bInitialStateChange;
    private int _nIdInitialState;
    private int _nIdWorkflow;

    /**
     * Returns the IdResourceHistory
     * 
     * @return The IdResourceHistory
     */
    public int getIdResource( )
    {
        return _nIdResource;
    }

    /**
     * Sets the IdResource
     * 
     * @param nIdResource
     *            The IdResource
     */
    public void setIdResource( int nIdResource )
    {
        _nIdResource = nIdResource;
    }

    /**
     * Returns the IdTask
     * 
     * @return The IdTask
     */
    public int getIdTask( )
    {
        return _nIdTask;
    }

    /**
     * Sets the IdTask
     * 
     * @param nIdTask
     *            The IdTask
     */
    public void setIdTask( int nIdTask )
    {
        _nIdTask = nIdTask;
    }

    /**
     * Returns the ResourceType
     * 
     * @return The ResourceType
     */
    public String getResourceType( )
    {
        return _strResourceType;
    }

    /**
     * Sets the ResourceType
     * 
     * @param strResourceType
     *            The ResourceType
     */
    public void setResourceType( String strResourceType )
    {
        _strResourceType = strResourceType;
    }

    /**
     * Returns the InitialStateChange
     * 
     * @return The InitialStateChange
     */
    public boolean isInitialStateChange( )
    {
        return _bInitialStateChange;
    }

    /**
     * Sets the InitialStateChange
     * 
     * @param bInitialStateChange
     *            The InitialStateChange
     */
    public void setInitialStateChange( boolean bInitialStateChange )
    {
        _bInitialStateChange = bInitialStateChange;
    }

	/**
	 * @return the _nIdInitialState
	 */
	public int getIdInitialState( )
	{
		return _nIdInitialState;
	}

	/**
	 * @param nIdInitialState the _nIdInitialState to set
	 */
	public void setIdInitialState( int nIdInitialState )
	{
		this._nIdInitialState = nIdInitialState;
	}

	/**
	 * @return the _nIdExternalParent
	 */
	public int getIdExternalParent( )
	{
		return _nIdExternalParent;
	}

	/**
	 * @param nIdExternalParent the _nIdExternalParent to set
	 */
	public void setIdExternalParent( int nIdExternalParent )
	{
		this._nIdExternalParent = nIdExternalParent;
	}

	/**
	 * @return the _nIdWorkflow
	 */
	public int getIdWorkflow( )
	{
		return _nIdWorkflow;
	}

	/**
	 * @param nIdWorkflow the _nIdWorkflow to set
	 */
	public void setIdWorkflow( int nIdWorkflow )
	{
		this._nIdWorkflow = nIdWorkflow;
	}
}
