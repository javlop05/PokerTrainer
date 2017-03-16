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

import java.util.LinkedList;

/**
 * Clase que almacena la información relativa a la partida necesaria para el bot.
 * @author Aarón
 */
public class InfoBot {
    
    /* En infoPlayers se encuentran los jugadores que siguen jugando la mano actual
    -Puede conocer todos sus datos (stack,última acción, rol).
    -Él es uno de los jugadores de infoPlayers (le sirve para conocer su posición en la mesa).
    */
    
    private LinkedList<PlayerInfo> infoPlayers;
    
    /*
    -En bot está toda la información relevante del bot.
    */
    private PlayerInfo bot;

    //Big blind y Small blind
    private int bb;
    private int sb;
    
    //Cantidad máxima de apuesta en una mano. 0 Si se juega sin CAP.
    private int cap;
    
    
    
    //Cartas del board
    private LinkedList<Card> boardCards;
    //Cartas del bot.
    private LinkedList<Card> botCards;
    
    //La apuesta del bote (cuantía del raise más alto)
    private int potBet;
    
    //Estado de la mano (preflop,flop,...)
    private State state;
    //Bote acumulado en la mesa.
    private int totalPot;

   
    
    /**
     * Constructor de la clase.
     * @param infoPlayers Lista de objetos <code>PlayerInfo</code> con información de los jugadores que participan en la mano.
     * @param bot Objeto <code>PlayerInfo</code> con información relativa al bot.
     * @param bb Cantidad que supone la ciega grande.
     * @param sb Cantidad que supone la ciega pequeña.
     * @param boardCards Lista de cartas que hay sobre la mesa.
     * @param botCards Lista de cartas del bot.
     * @param potBet Cuantía de la apuesta más alta realizada por algún jugador.
     * @param totalPot Bote acumulado en la mesa.
     * @param state Estado de la mano.
     * @param cap Cantidad máxima de fichas que se pueden apostar durante una mano.
     * @see PlayerInfo
     * @see State
     */
    public InfoBot(LinkedList<PlayerInfo> infoPlayers, PlayerInfo bot, int bb, int sb, LinkedList<Card> boardCards,
            LinkedList<Card> botCards, int potBet, int totalPot, State state, int cap) {
        this.infoPlayers = infoPlayers;
        this.bot = bot;
        this.bb = bb;
        this.sb = sb;
        this.boardCards = boardCards;
        this.botCards = botCards;
        this.potBet = potBet;
        this.totalPot = totalPot;
        this.state = state;
        this.cap = cap;
    }
    
    public InfoBot() {
        this.infoPlayers = null;
        this.bot = null;
        this.bb = 0;
        this.sb = 0;
        this.boardCards = null;
        this.botCards = null;
        this.potBet = 0;
        this.totalPot = 0;
        this.state = null;
        this.cap = 0;
    }
    
    /**
     * Devuelve la cantidad que supone la ciega grande
     */
     public int getBb() {
        return bb;
    }
     /**
      * Devuelve la cantidad que supone la ciega pequeña
      */
    public int getSb() {
        return sb;
    }
    /**
     * Devuelve el bote acumulado en la mesa.
     */
     public int getTotalPot() {
        return totalPot;
    }
    
    /**
     * Devuelve la cantidad máxima de fichas que se pueden apostar durante una mano.
     */
    public int getCap() {
        return this.cap;
    }
    
    /**
     * Devuelve una lista con las cartas de la mesa.
     */
     public LinkedList<Card> getBoardCards() {
        return boardCards;
    }
     
     /**
      * Devuelve una lista con las cartas del bot.
      */
    public LinkedList<Card> getBotCards() {
        return botCards;
    }

    /**
     * Devuelve la cuantía de la apuesta más alta. 
     */
    public int getPotBet() {
        return potBet;
    }
    
    /**
     * Devuelve el estado de la partida.
     */
    public State getState() {
        return state;
    }
    
    /**
     * Devuelve una lista de objetos <code>Player Info</code> con información de los jugadores que aún participan en la mano.
     */
    public LinkedList<PlayerInfo> getInfoPlayers() {
        return infoPlayers;
    }
    
    /**
     * Devuelve un objeto <code>Player Info</code> con información relativa al bot.
     */
    public PlayerInfo getBot() {
        return bot;
    }

   
 
    
}
