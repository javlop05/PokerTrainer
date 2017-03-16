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
 * Esta clase mantiene la información definida antes de comenzar la partida de cada jugador. 
 * @author usuario_local
 */
public class CustomPlayer {
    private String name;
    private int stack;
    private String mode;
    private String nameClass;
    
    /**
     * Constructor de la clase.
     * @param name Nombre del jugador
     * @param stack Cantidad de fichas con las que comienza
     * @param mode Modo del jugador. Puede ser: <ul><li>Humano</li><li>Bot</li></ul>
     * @param nameClass En caso de ser un bot, nombre de la clase que lo implementa
     */
    public CustomPlayer(String name, int stack, String mode, String nameClass) {
        this.name = name;
        this.stack = stack;
        this.mode = mode;
        this.nameClass = nameClass;
    }

    /**
     * Devuelve el nombre del jugador.
     */
    public String getName() {
        return name;
    }
    /**
     * Devuelve la cantidad de fichas del jugador.
     */
    public int getStack() {
        return stack;
    }
    
    /**
     * Devuelve el modo del jugador.
     */
    public String getMode() {
        return mode;
    }
    /**
     * Devuelve el nombre de la clase que implementa al bot, si lo fuese.
     */
    public String getNameClass() {
        return nameClass;
    }
    
    
    
}
