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
 * Clase que representa a un jugador en la partida.
 * @author Mateo
 */
public abstract class Player {
    protected Role role;
    protected int seat;
    protected LinkedList<Card> cards;
    protected HandPlayer hand;
    protected PlayerMode playerMode;
    
    protected int bet;
    
    protected String name;

   
    protected int money;
    protected int numPlayer;
    protected int totalBet;
    
    //Atributo que indica cuanto dinero puso el jugador en el último bote.
    //Sirve para el caso en que haya que repartir su dinero entre 2 botes en los que el no está
    //porque le hicieron un raise.
    protected int betLastPot;
    protected Action action;
    
    
    /**
     * Devuelve la catidad apostada por el jugador en el último bote.
     */
    protected int getBetLastPot() {
        return betLastPot;
    }
    
    /**
     * Establece la cantidad apostada por el jugador en el último bote.
     * @param betLastPot Cantidad apostada
     */
    protected void setBetLastPot(int betLastPot) {
        this.betLastPot = betLastPot;
    }
    
    /**
     * Devuelve la cantidad total apostada por el jugador.
     */
    public int getTotalBet() {
        return totalBet;
    }
    
    /**
     * Devuelve la mano del jugador.
     */
    public HandPlayer getHand() {
        return hand;
    }
    
    /**
     * Establece la mano del jugador.
     * @param hand Mano del jugador
     */
    protected void setHand(HandPlayer hand) {
        this.hand = hand;
    }
    
    /**
     * Devuelve el asiento del jugador.
     */
    public int getSeat() {
        return seat;
    }
    
    /**
     * Establece el asiento del jugador
     * @param seat Número de asiento del jugador.
     */
    protected void setSeat(int seat) {
        this.seat = seat;
    }
    
    /**
     * Devuelve las cartas del jugador
     */
    public LinkedList<Card> getCards() {
        return cards;
    }
    
    /**
     * Establece las cartas del jugador
     * @param cards Lista de cartas
     */
    protected void setCards(LinkedList<Card> cards) {
        this.cards = cards;
    }
    
    /**
     * Establece la última acción realizada por el jugador
     * @param action Última acción realizada por el jugador
     */
     public void setAction(Action action) {
        this.action = action;
    }
     
    /**
     * Devuelve la apuesta realizada por el jugador en esta ronda
     */
    public int getBet() {
        return bet;
    }
    
    /**
     * Establece la apuesta realizada por el jugador en esta ronda
     * @param bet Cantidad de la apuesta
     */
    protected void setBet(int bet) {
        this.bet = bet;
    }
    
    /**
     * Devuelve el nombre del jugadors
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve la cantidad de fichas que le quedan al jugador.
     */
    public int getMoney() {
        return money;
    }
    
    /**
     * Establece la cantidad de fichas que le quedan al jugador.
     * @param money Cuantía de las fichas disponibles del jugador.
     */
    protected void setMoney(int money) {
        this.money = money;
    }
    
    /**
     * Aumenta la cantidad de fichas restantes del jugador
     * @param money Cantidad a aumentar
     */
    protected void addMoney(int money) {
        this.money += money;
    }
    
    /**
     * Devuelve el rol del jugador
     * @see Role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Establece el rol del jugador
     * @see Role
     * @param role Rol del jugador
     */
    protected void setRole(Role role) {
        this.role = role;
    }

    /**
     * Devuelve la posición del jugador en la mesa
     */
    protected int getNumPlayer() {
        return numPlayer;
    }
    
    //Devuelve el dinero qeu tiene que poner cada jugador( o el que puede)
    /**
     * Devuelve la cantidad que apuesta el jugador.
     * @param bet Cantidad a apostar. Si esta es mayor que el stack del jugador, entonces apostará todo el stack
     */
    public int bet(int bet) {
        int b;
        
        if (bet < this.money) 
            b = bet;    
        else 
            b = this.money;

        return b;
    }
    
    //Modifica el dinero y apuesta del jugador.
    /**
     * Modifica la cantidad apostada en esta ronda, la cantidad apostada en total en la mano y el stack del jugador tras realizar una apuesta
     * @param b Última apuesta realizada
     */
    protected void updatePlayer(int b) {
        this.bet += b;
        this.money -= b;
        this.totalBet += b;
    }
    
    /**
     * Aumenta la cantidad de fichas del jugador
     * @param money Cantidad de fichas a aumentar
     */
    protected void incrMoney(int money){
        this.money +=money;
    }
    
    /**
     * Inicializa los atributos de un jugador tras finalizar una mano
     */
    protected void initializePlayer() {
        this.bet = 0;
        this.betLastPot = 0;
        this.totalBet = 0;
    }
    
    /**
     * Devuelve True si es un bot
    */
    public abstract boolean isBot();

    /**
     * Devuelve el modo de un jugador
     * @see PlayerMode
     */
    public PlayerMode getPlayerMode() {
        return this.playerMode;
    }

    /**
     * Crea  y devuelve la acción de un jugador
     * @param b Información relativa a la partida necesaria para el bot
     */
    public abstract Action createAction(InfoBot b);
    

    /**
     * Método que pide la acción a un bot e inicia la cuenta atrás del tiempo disponible para realizarla
     * @param b Información relativa a la partida necesaria para el bot
     * @return Acción del jugador
     */
    public Action askAction(InfoBot b){
        return createAction(b);
    }     
    
    /**
     * Método que crea la acción relacionada a un Call
     * @param b Información relativa a la partida necesaria para el bot
     * @return Acción del jugador
     
    public Action callCreateAction(InfoBot b){
        
    }
    * */
}