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
 * Enumerado con los posibles roles de un jugador
 * <ul><li>BIG_BLIND: Debe poner la ciega grande</li>
 * <li>SMALL_BLIND: Debe poner la ciega pequeña</li>
 * <li>BB_MASX: No es ciega, pero ocupa la posición X (1...7) a partir de la ciega grande</li></ul>
 * @author Mateo
 */
public enum Role {
     SMALL_BLIND("sb"), BIG_BLIND("bb"), BB_MAS1(""), BB_MAS2(""),BB_MAS3(""),
     BB_MAS4(""),BB_MAS5(""),BB_MAS6(""),BB_MAS7("");
     
    private final String role;
    
    /**
    * Constructor del enumerado
    * @param role String que contiene el nombre del rol
    */
    private Role(String role){
        this.role = role;
    }
    
    @Override
    /**
     * Devuelve el nombre del rol formateado
     */
    public String toString() {
        return role;
    }
    
    
}
