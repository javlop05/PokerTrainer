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

package pokertrainer.controller;

import java.util.LinkedList;
import pokertrainer.model.Board;
import pokertrainer.model.CustomPlayer;

/**
 * Clase que inicia el <code>Board</code> y <code>Controlador</code> de la aplicación.
 * @author usuario_local
 */
public class CustomPlayersController {
    
   
    /**
     * Método que crea e inicia el <code>Board</code> y <code>Controlador del juego</code> con los jugadores customizados previamente
     * @param customPlayers Lista de jugadores customizados
     * @param cap Límite superior que se puede apostar en una mano
     * @param botCardsVisible Si está a True las cartas de los bots se mostrarán
     * @param withCap Si está a True se ejecuta el juego con el cap establecido en el parámetro cap
     */
    public void start(LinkedList<CustomPlayer> customPlayers, int cap, boolean botCardsVisible ,boolean withCap) {
        Board board = new Board(customPlayers, withCap, cap);
        GameController gameController = new GameController(board, botCardsVisible, withCap, cap);
    }
}
