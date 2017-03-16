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
 * Clase que representa a un bot en la partida 
 * @author usuario_local
 */
public class PlayerBot extends Player {
    
    private BotInterface intelligence;
    
    /**
     * Constructor de la clase
     * @param name Nombre del bot
     * @param money Stack inicial del bot
     * @param seat Posición del asiento del bot
     * @param num Posición del bot en la mesa
     * @param intelligence Interfaz que implementa el bot
     */
    public PlayerBot(String name, int money, int seat, int num, BotInterface intelligence) {
        this.name = name;
        this.money = money;
        this.seat = seat;
        this.cards = null;
        this.hand = null;
        this.numPlayer = num;
        this.betLastPot = 0;
        this.playerMode = new BotMode();
        this.intelligence = intelligence;
        
    }

    @Override
    /**
     * Dice que el jugador es un Bot.
     * @return True siempre, ya que el jugador es bot.
     */
    public boolean isBot() {
       return true;
    }

    @Override
    /**
     * Método que crea una acción.
     * @param info Información necesaria para el bot.
     * @return Acción creada por el jugador.
     */
    public Action createAction(InfoBot info){
        return intelligence.createAction(info);
    }
}
 