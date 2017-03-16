/*
 * Copyright (c) 2017, Aarón Durán Sánchez,Javier López de Lerma, Mateo García Fuentes, Carlos López Martínez 
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package pokertrainer.model;

/**
 * Clase que define una acción del tipo Fold realizada por un jugador.
 * @author usuario_local
 */
public class ActionFold extends Action {

    /**
     * Constructor de la clase ActionFold
     */
    public ActionFold() {
        this.action = Actions.FOLD;
    }
    
    

    @Override
    /**
     * Ejecuta la acción Fold
     * @param b representa el modelo.
     * @param p representa el jugador que ejecuta la acción.
     */
    public GameState executeAction(Board b, Player p) {
         GameState state = GameState.CONTINUE;

        //Si tenemos 2 jugadores y uno se ha retirado
        b.removePlayerFromPots(p);
        int size = b.getHandPlayersSize();
        
        if(size <= 2 && b.isLastToSpeak())
            state = b.updateCurrentState();
        else if(size <= 2 && b.getNextPlayerTotalBet() >= b.getHighBet())
            state = b.updateCurrentState();
        return state;
    }
    
    @Override
    /**
     * Devuelve el nombre de la acción formateado
     */
    public String toString() {
        return action.toString();
    }
    
}
