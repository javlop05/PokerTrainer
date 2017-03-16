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
 * Enumerado con los posibles valores de una carta dentro de un palo.
 * @author usuario_local
 */
public enum Value {
    
    TWO("2"), THREE("3"), FOUR("4"),FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), 
    NINE("9"), TEN("T"), JACK("J"), QUEEN("Q"), KING("K"), ACE("A");
    
    private String val;
    
    /**
    * Constructor del enumerado
    * @param val String que contiene el nombre del valor
    */
    private Value(String val) {
        this.val = val;
    }
    
    /**
     * Devuelve el nombre del valor
     */
    public String getVal() {
        return this.val;
    }
    
    /**
     * Dado un string devuelve el Value asociado
     * @param c string que queremos convertir a Value
     * @return Value asociado al string
     */
    public static Value getValueByString(String c) {
        switch(c) {
            case "2": return TWO;
            case "3": return THREE;
            case "4": return FOUR;
            case "5": return FIVE;
            case "6": return SIX;
            case "7": return SEVEN;
            case "8": return EIGHT;
            case "9": return NINE;
            case "T": return TEN;
            case "J": return JACK;
            case "Q": return QUEEN;
            case "K": return KING;
            case "A": return ACE;
            default : return null;
        }
    }
    
}
