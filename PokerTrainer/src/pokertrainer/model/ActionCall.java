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
 * Clase que define una acción del tipo Call o Check realizada por un jugador.
 * @author usuario_local
 */

//Acción usada para Call y Check

public class ActionCall extends Action {
    
    /**
     * Contructor de la clase ActionCall
     * @param action Nombre de la acción
     */
    public ActionCall(Actions action) {
        this.action = action;
    }
    
    @Override
    /**
     * Ejecuta la acción Call.
     * @param b representa el modelo.
     * @param p representa el jugador que ejecuta la acción.
     */
    public GameState executeAction(Board b, Player p) {
       
        //Calculamos la apuesta que tiene que realizar para hacer el call
        int actualBet = p.getBet();
        int betToCall = b.getHighBet() - actualBet;
        
        this.bet = betToCall;
        
        b.refreshBet(p, betToCall);
        
        GameState gs;
        if (b.getHandPlayersSize() == 1)  gs = GameState.OVER_ALLIN;
        else  gs = GameState.CONTINUE;
        
        return gs;
    }
    
    @Override
    /**
     * Devuelve el nombre de la acción formateado
     */
    public String toString() {
        if (this.action == Actions.CHECK)
            return action.toString();
        else return action + " " + bet;
    }
}
