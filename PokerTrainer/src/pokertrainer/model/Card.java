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
 * Clase que representa una carta de la baraja
 * @author usuario_local
 */
public class Card implements Comparable<Card> {
    
    private final Suit suit;
    private final Value val;
    private final int ord;
    
    /**
     * Constructor de la clase.
     * @param suit Palo de la carta
     * @param val Valor de la carta dentro del palo
     */
    public Card(Suit suit, Value val) {
        this.suit = suit;
        this.val = val;
        ord = val.ordinal();
    }
    
    /**
     * Devuelve el palo de la carta.
     * @see Suit
     */
    public Suit getSuit() {
        return this.suit;
    }
    
    /**
     * Devuelve el valor de la carta dentro del palo.
     * @see Value
     */
    public Value getVal() {
        return this.val;
    }
    
    /**
     * Devuelve el valor de la carta en ordinal dentro del orden del enumerado
     * @see Value
     */
    private int getOrd() {
        return this.val.ordinal();
    }
    
    @Override
    /**
     * Devuelve el nombre de la carta formateado
     */
    public String toString(){
        return (this.val.getVal() + this.suit.getName());
    }

    @Override
    /**
     * Comprar el valor de una carta con el valor de la carta actual.
     * @param c Carta de la cual se quiere comparar su valor.
     * @see Value
     */
    public int compareTo(Card c) {
       return this.val.compareTo(c.getVal());
    }
}

