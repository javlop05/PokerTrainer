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
 * <p>Enumerado con todas las posibles jugadas de una mano de Poker.</p>
 * <ul><li>HIGH_CARD: Carta alta</li><li>ONE_PAIR: Pareja</li>
 * <li>TWO_PAIR: Dobles parejas</li><li>THREE_KIND: Trío</li>
 * <li>STRAIGHT: Escalera</li><li>FLUSH: Color</li>
 * <li>FULL_HOUSE: Full</li><li>POKER: Poker</li><li>STRAIGHT_FLUSH: Escalera de color</li></ul>
 * @author usuario_local
 */
public enum HandCategories {
    HIGH_CARD("HIGH_CARD"),ONE_PAIR("ONE_PAIR"), TWO_PAIR("TWO_PAIR"),THREE_KIND("THREE_KIND"),
    STRAIGHT("STRAIGHT"), FLUSH("FLUSH"), FULL_HOUSE("FULL_HOUSE"), POKER("POKER"),
    STRAIGHT_FLUSH("STRAIGHT_FLUSH");
        
        private String name; 
        
        /**
        * Constructor del enumerado
        * @param name String que contiene el nombre de la jugada
        */
        private HandCategories(String name){
            this.name = name;
        }
        
        /**
         * Devuelve el nombre de la jugada.
         */
        public String getName() {
            return name;
        }
        
        /**
         * Establece el nombre de una jugada.
         * @param name Nombre de la jugada.
         */
        public void setName(String name) {
            this.name = name;
        }
}
