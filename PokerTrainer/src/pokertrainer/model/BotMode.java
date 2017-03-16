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

import static java.lang.Thread.sleep;
import pokertrainer.controller.GameController;

/**
 * Clase que implementa la interfaz <code> PlayerMode </code> y define la lógica necesaria para iniciar y finalizar
 * las acciones de un bot
 * @author usuario_local
 */
public class BotMode implements PlayerMode {
    
    private Thread hebra;

    private static int numThreads = 0;
    
    private int numThread;
    
    /**
     * Crea una hebra para el bot, le pide la acción y la ejecuta
     * @param control Objeto <code>Controlador</code> de la partida
     * @param currentInfo Información necesaria para el bot relativa a la partida
     */
    public void start(GameController control, InfoBot currentInfo) {
        
        this.numThread = BotMode.numThreads+1;
        BotMode.numThreads++;
        
        this.hebra = new Thread() {
            public void run() {
                /***Descomentar para ejecutar bots sin interfaz gráfica
                control.executeBotAction(currentInfo);
                System.out.println(Thread.currentThread().getName());
                interrupt();
                ***/
                /***Comentar para ejecutar bots sin interfaz gráfica*/
                try {
                    sleep(2000);                    
                    control.executeBotAction(currentInfo);
                    System.out.println(Thread.currentThread().getName());
                    interrupt();
                } catch (InterruptedException e) {
                    System.out.println("Ha habido un error");
                }
                /***/
            }
        };
        
        while(this.numThread != BotMode.numThreads) {}
        this.hebra.start();
    }

    
    
    
}
