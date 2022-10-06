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

import fr.paris.lutece.plugins.workflowcore.business.config.TaskConfig;

/**
 * 
 * UpdateTaskStateResourceConfig
 *
 */
public class UpdateTaskStateResourceConfig extends TaskConfig
{

    private int _strStartState;
    private int _strEndState;
    private int _nNbVote;
    private int _nOperation;

    /**
     * @return the _strStartState
     */
    public int getStartState( )
    {
        return _strStartState;
    }

    /**
     * @param strStartState
     *            the _strStartState to set
     */
    public void setStartState( int strStartState )
    {
        this._strStartState = strStartState;
    }

    /**
     * @return the _strEndState
     */
    public int getEndState( )
    {
        return _strEndState;
    }

    /**
     * @param strEndState
     *            the _strEndState to set
     */
    public void setEndState( int strEndState )
    {
        this._strEndState = strEndState;
    }

    /**
     * @return the _nNbVote
     */
    public int getNbVote( )
    {
        return _nNbVote;
    }

    /**
     * @param nNbVote
     *            the _nNbVote to set
     */
    public void setNbVote( int nNbVote )
    {
        this._nNbVote = nNbVote;
    }

    /**
     * @return the _nOperation
     */
    public int getOperation( )
    {
        return _nOperation;
    }

    /**
     * @param nOperation
     *            the _nOperation to set
     */
    public void setOperation( int nOperation )
    {
        this._nOperation = nOperation;
    }

}
