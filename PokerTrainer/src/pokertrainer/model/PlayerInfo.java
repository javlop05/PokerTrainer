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
 * Clase que define la información relevante de un jugador para el bot
 * @author Aarón
 */
public class PlayerInfo {
    
    
    //Rol que ocupa en la mesa el jugador
    private Role role;
    
    //Stack actual del jugador;
    private int money;
    
    //Última acción realizada por el jugador.
    private Action action;
    
    //Apuesta total realizada por el jugador en esta mano.
    private int totalBet;
    
    //Jugada de este jugador en la anterior mano, si esta fue visible
    private HandPlayer winnerHand;

   
    /**
     * Constructor de la clase
     * @param role Rol del jugador en la mesa en esta mano
     * @param money Stack actual del jugador
     * @param action Última acción realizada por el jugador
     * @param totalBet Apuesta total realizada por el jugador en la mano
     * @param hand Jugada con la que ganó la anterior mano (si es el caso).
     */
    public PlayerInfo(Role role, int money, Action action, int totalBet,  HandPlayer hand) {
        this.role = role;
        this.money = money;
        this.action = action;
        this.totalBet = totalBet;
        this.winnerHand = hand;
    }

    /**
     * Devuelve la jugada con la que ganó la anterior mano (si es el caso).
     */
     public HandPlayer getWinnerHand() {
        return winnerHand;
    }

    
    /**
     * Devuelve la apuesta total realizada por el jugador
     */
    public int getTotalBet() {
        return totalBet;
    }
    /**
     * Devuelve el rol del jugudor
     */
    public Role getRole() {
        return role;
    }
    /**
     * Devuelve el stack del jugador
     */
    public int getMoney() {
        return money;
    }
    
    /**
     * Devuelve la última acción del jugador
     */
    public Action getAction() {
        return action;
    }
    
    
    
}
