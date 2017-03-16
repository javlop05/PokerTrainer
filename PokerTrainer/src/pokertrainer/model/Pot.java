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
 * Esta clase contiene la información relativa a un bote de la partida.
 * @author usuario_local
 */
public class Pot {
    
    
    private int totalPot;
    private int bet;
    private LinkedList<Player> players;
    private LinkedList<Player> outForRaise;
    
    /**
     * Constructor de la clase <code>Pot</code>.
     * @param totalPot  Dinero total del bote
     * @param bet       Apuesta necesaria para participar en este bote 
     * @param players   Jugadores que actualmente participan en el bote
     * @param outRaise  Jugadores que participaron en el bote pero fueron eliminados por un raise posterior
     */
    public Pot(int totalPot, int bet, LinkedList<Player> players, LinkedList<Player> outRaise) {
        this.totalPot = totalPot;
        this.bet = bet;
        this.players = players;
        this.outForRaise = outRaise;
    }
    
    /**
     * Añade un jugador a la lista <code>outForRaise</code>
     * @param player Jugador a añadir
     */
    public void addPlayersOut(Player player) {
        this.outForRaise.add(player);
    }
    
    /**
     * Añade una lista de jugadores a la lista <code>outForRaise</code>
     * @param players Lista de jugadores a añadir
     */
    public void addListPlayersOut(LinkedList<Player> players) {
        this.outForRaise.addAll(players);
    }
    
    /**
     * Elimina a un jugador de la lista <code>outForRaise</code>. Además resta del dinero total del bote lo que el jugador había puesto
     * @param player Jugador que se va a eliminar
     */
    public void delPlayerOut(Player player) {
        this.outForRaise.remove(player);
        this.totalPot -= player.getBetLastPot();
    }
    
    /**
     * 
     * Devuelve la lista <code>outForRaise</code> 
     */
    public LinkedList<Player> getPlayersOutForRaise() {
        return this.outForRaise;
    }
    
    /**
     * 
     * Devuelve el dinero total del bote 
     */
    public int getTotalPot() {
        return totalPot;
    }
    
    /**
     * Añade dinero al bote
     * @param totalPot Cantidad de dinero a añadir al bote 
     */
    public void addTotalPot(int totalPot) {
        this.totalPot += totalPot;
    }
    
    /**
     * Elimina dinero del bote
     * @param totalPot Cantidad de dinero a eliminar del bote 
     */
    public void subTotalPot(int totalPot) {
        this.totalPot -= totalPot;
    }

    /**
     * 
     * Devuelve la apuesta mínima necesaria para participar en el bote 
     */
    public int getBet() {
        return bet;
    }
    /**
     * Establece la apuesta mínima necesaria para participar en el bote 
     * @param bet Cantidad de la apuesta
     */
    public void setBet(int bet) {
        this.bet = bet;
    }
    
    /**
     * Resta una cierta cantidad a la apuesta mínima para participar en el bote
     * @param bet Cantidad a restar
     */
     public void subBet(int bet) {
        this.bet -= bet;
    }

     /**
      * 
      * Devuelve los jugadores que participan en el bote
      */
    public LinkedList<Player> getPlayers() {
        return players;
    }
    /**
     * Añade un participante al bote. Además lo elimina de <code>outForRaise</code> si perteneciese a esta lista
     * @param player Jugador que participará en el bote
     */
    public void addPlayers(Player player) {
        this.players.add(player);
        if(this.outForRaise.contains(player))
            this.outForRaise.remove(player);
    }
   
    
    
    /**
     * Elimina a todos los actuales participantes del bote.
     * Antes de hacerlo los añade a la lista <code>outForRaise</code> para poder saber posteriormente cuanta 
     * gente participó en el bote. 
     */
    public void removePlayersForRaise() {
        for(Player p: this.players) {
            if(!this.outForRaise.contains(p))
                this.outForRaise.add(p);
        }
        
        
        this.players.clear();
    }
    
    
    
    
    
    
   
}
