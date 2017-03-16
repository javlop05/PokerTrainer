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

import pokertrainer.controller.MainWindowController;
import pokertrainer.view.MainWindow;

/**
 * Main principal de la aplicación
 * @author Javi
 */
public class PokerTrainer {
    
    public static final String BOTSDIRECTORY = "build/classes/pokertrainer/botsDirectory";
    public static final String BOTSPACKAGE = "pokertrainer.botsDirectory.";
    
    /**
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
       
        MainWindowController mwc = new MainWindowController();
        MainWindow login = new MainWindow(true, mwc);
            
        login.setVisible(true);
     
    }
    
}
