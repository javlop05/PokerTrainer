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
 * Clase que define una acción del tipo Bet, Raise o All-In realizada por un jugador.
 * @author usuario_local
 */

//Accion usada para Raise, Bet y All-in

public class ActionRaise extends Action {
    
    /**
     * Constructor de la clase ActionRaise
     * @param raiseBet Cantidad apostada
     * @param action Nombre de la acción
     */
    public ActionRaise(int raiseBet, Actions action) {
        this.action = action;
        this.bet = raiseBet;
    }

    
    @Override
    /**
     * <p>Ejecuta la acción correspondiente. Puede ser: </p>
     * <ul><li>Raise</li><li>Bet</li><li>All-in</li></ul>
     * @param b representa el modelo.
     * @param p representa el jugador que ejecuta la acción.
     */
    public GameState executeAction(Board b, Player p) {
        GameState allin = GameState.CONTINUE;
        
        if(this.bet > b.getHighBet()) {
            b.setHighBet(this.bet);
            b.updateLastToSpeak();
        }
        
        //Restamos la apuesta total que habia realizado 
        this.bet -= p.getBet();
        
        b.refreshBet(p, this.bet);
        
        
        //Si solo quedaba yo por hablar o si yo voy allin y yo era el último jugador en hablar
        if((b.getHandPlayersSize() == 1) || (b.getHandPlayersSize() == 2 && p.getMoney() == 0 && b.isLastToSpeak()))
            //Compruebo si se debe dar el dinero a un jugador o si se debe mostrar el showdown
            allin = b.updateCurrentState();
   
        return allin;
    }

    @Override
    /**
     * Devuelve el nombre de la acción formateado.
     */
    public String toString() {
        return action + " " + bet;
    }
    
}
