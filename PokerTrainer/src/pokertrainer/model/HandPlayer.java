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
 * Clase que representa la mano de un jugador.
 * @author Aarón
 */
public class HandPlayer {

    private HandCategories hand;
    private Value mainV;
    private Value secondV;
    private Suit suit;
    //Cartas que completan la mano
    private LinkedList<Value> cards;
    
    /**
     * Constructor de la clase
     * @param name Jugada que tiene el jugador en su mano
     * @see HandCategories
     */
    public HandPlayer(HandCategories name) {
        this.hand = name;
        this.mainV = null;
        this.secondV = null;
        this.suit = null;
        this.cards = null;
    }
    
    /**
     * Devuelve la jugada de la mano del jugador.
     * @see HandCategories
     */
    public HandCategories getHand() {
        return hand;
    }
    
    /**
     * Establece la jugada de la mano del jugador.
     * @param hand Jugada 
     * @see HandCategories
     */
    public void setHand(HandCategories hand) {
        this.hand = hand;
    }
    
    @Override
    /**
     * Devuelve el nombre de la jugada en formato cadena.
     */
    public String toString() {
        String str = hand.getName();
        if(this.mainV != null) {
            str += " " + this.mainV;        
            if(this.secondV != null)
                str += " - " + this.secondV;
        }
        if(this.suit != null)
            str += " " + this.suit;
        if(this.cards != null){
            for(int i = 0; i < this.cards.size(); i++){
                str += " " + cards.get(i);
            }          
        }           
        return str;
        
    }
    
    /**
     * Devuelve el palo de la jugada, si este fuese relevante.
     */
    public Suit getSuit() {
        return suit;
    }
    
    /**
     * Establece el palo de la jugada
     * @param suit Palo de la jugada
     */
    public void setSuit(Suit suit) {
        this.suit = suit;
    }
    
    /**
     * Establece las cartas que conforman la jugada de la mano del jugador.
     * @param cards Lista de valores de las cartas
     */
    public void setCards(LinkedList<Value> cards) {
        this.cards = cards;
    }
    
    /**
     * Añade una carta a la jugada al principio de la lista.
     * @param v Valor de la carta
     */
    public void pushCards(Value v) {
        if(this.cards == null)
            this.cards = new LinkedList<>();
        this.cards.addFirst(v);
    }
    
    /**
     * Añade una carta a la jugada al final de la lista.
     * @param v Valor de la carta
     */
    public void addLastCards(Value v) {
        if(this.cards == null)
            this.cards = new LinkedList<>();
        this.cards.addLast(v);
    }
    
    /**
     * Devuelve las cartas que conforman la jugada de la mano del jugador.
     */
    public LinkedList<Value> getCards() {
        return cards;
    }
    
    /**
     * <p>Establece el valor de la carta principal de la jugada.</p>
     * Ejemplo: Si la jugada es un trío de sietes, el valor de la carta principal será 7.
     * @param mainV Valor de la carta principal
     */
     public void setMainV(Value mainV) {
        this.mainV = mainV;
    }

     /**
      * <p>Establece el valor de la carta secundaria de la jugada.</p>
      * Ejemplo: Si la jugada es un full de Q-4, el valor de la carta secundaria será 4.
      * @param secondV Valor de la carta secundaria
      */
    public void setSecondV(Value secondV) {
        this.secondV = secondV;
    }

    /**
     * Devuelve el valor de la carta principal.
     */
    public Value getMainV() {
        return mainV;
    }
    
    /**
     * Devuelve el valor de la carta secundaria.
     */
    public Value getSecondV() {
        return secondV;
    }
 
}
