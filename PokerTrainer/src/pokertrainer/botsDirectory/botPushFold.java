/*
 * Copyright (c) 2017, Javier
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
package pokertrainer.botsDirectory;

import pokertrainer.model.Action;
import pokertrainer.model.ActionFold;
import pokertrainer.model.ActionRaise;
import pokertrainer.model.Actions;
import pokertrainer.model.BotInterface;
import pokertrainer.model.Card;
import pokertrainer.model.InfoBot;
import pokertrainer.model.Role;
import pokertrainer.model.Value;

/**
 *
 * @author Javier
 */
public class botPushFold implements BotInterface  {
    
    private final int size = 13;
    
    private boolean[][] chartbool = new boolean[size][size];
    
    private final String[][] chart = new String[][] { 
        {"AAo", "AKs", "AQs", "AJs", "ATs", "A9s", "A8s", "A7s", "A6s", "A5s", "A4s", "A3s", "A2s"},
        {"AKo", "KKo", "KQs", "KJs", "KTs", "K9s", "K8s", "K7s", "K6s", "K5s", "K4s", "K3s", "K2s"},
        {"AQo", "KQo", "QQo", "QJs", "QTs", "Q9s", "Q8s", "Q7s", "Q6s", "Q5s", "Q4s", "Q3s", "Q2s"},
        {"AJo", "KJo", "QJo", "JJo", "JTs", "J9s", "J8s", "J7s", "J6s", "J5s", "J4s", "J3s", "J2s"},
        {"ATo", "KTo", "QTo", "JTo", "TTo", "T9s", "T8s", "T7s", "T6s", "T5s", "T4s", "T3s", "T2s"},
        {"A9o", "K9o", "Q9o", "J9o", "T9o", "99o", "98s", "97s", "96s", "95s", "94s", "93s", "92s"},
        {"A8o", "K8o", "Q8o", "J8o", "T8o", "98o", "88o", "87s", "86s", "85s", "84s", "83s", "82s"},
        {"A7o", "K7o", "Q7o", "J7o", "T7o", "97o", "87o", "77o", "76s", "75s", "74s", "73s", "72s"},
        {"A6o", "K6o", "Q6o", "J6o", "T6o", "96o", "86o", "76o", "66o", "65s", "64s", "63s", "62s"},
        {"A5o", "K5o", "Q5o", "J5o", "T5o", "95o", "85o", "75o", "65o", "55o", "54s", "53s", "52s"},
        {"A4o", "K4o", "Q4o", "J4o", "T4o", "94o", "84o", "74o", "64o", "54o", "44o", "43s", "42s"},
        {"A3o", "K3o", "Q3o", "J3o", "T3o", "93o", "83o", "73o", "63o", "53o", "43o", "33o", "32s"},
        {"A2o", "K2o", "Q2o", "J2o", "T2o", "92o", "82o", "72o", "62o", "52o", "42o", "32o", "22o"}};
        
    private final String[][] allinRangeBB = new String[][] { 
        {"22+", "Kx+", "Q2s+", "Q8o+", "J4s+", "J8o+", "T5s+", "T8o+", "95s+", "98o", "85s+", "87o", "74s+", "64s+", "53s+"}, //11BB
        {"22+", "Kx+", "Q2s+", "Q7o+", "J3s+", "J8o+", "T5s+", "T8o+", "95s+", "97o+", "85s+", "87o", "74s+", "64s+", "53s+"}, //10BB
        {"22+", "Kx+", "Q2s+", "Q5o+", "J2s+", "J8o+", "T5s+", "T8o+", "95s+", "97o+", "85s+", "87o", "74s+", "64s+", "53s+"}, //9BB
        {"22+", "Kx+", "Q2s+", "Q4o+", "J2s+", "J7o+", "T4s+", "T7o+", "95s+", "97o+", "85s+", "87o", "74s+", "64s+", "53s+"}, //8BB
        {"22+", "Qx+", "J2s+", "J6o+", "T3s+", "T7o+", "95s+", "97o+", "84s+", "87o", "74s+", "76o", "64s+", "53s+", "43s"}, //7BB
        {"22+", "Qx+", "J2s+", "J4o+", "T2s+", "T6o+", "94s+", "97o+", "84s+", "86o+", "74s+", "76o", "64s+", "53s+", "43s"}, //6BB
        {"22+", "Jx+", "T2s+", "T5o+", "93s+", "96o+", "84s+", "86o+", "74s+", "76o", "64s+", "53s+"}, //5BB
        {"22+", "Jx+", "T2s+", "T3o+", "92s+", "95o+", "83s+", "85o+", "74s+", "76o", "64s+", "53s+"}, //4BB
        {"22+", "Tx+", "92s+", "93o+", "82s+", "84o+", "73s+", "75o+", "63s+", "65o", "53s+"}, //3BB
        {"22+", "8x+", "72s+", "73o+", "62s+", "63o+", "52s+", "53o+", "42s+", "32s"}};//2BB
    
    private final String[][] allinRangeD = new String[][] { 
        {"22+", "Ax+", "K2s+", "K6o+", "Q7s+", "Q9o+", "J8s+", "JTo", "T9s"}, //11BB
        {"22+", "Ax+", "K2s+", "K6o+", "Q6s+", "Q8o+", "J8s+", "J9o+", "T9s"}, //10BB
        {"22+", "Ax+", "K2s+", "K4o+", "Q4s+", "Q8o+", "J7s+", "J9o+", "T8s+", "T9o", "98s"}, //9BB
        {"22+", "Kx+", "Q2s+", "Q7o+", "J6s+", "J8o+", "T7s+", "T9o", "98s"}, //8BB
        {"22+", "Kx+", "Q2s+", "Q5o+", "J5s+", "J8o+", "T7s+", "T8o+", "97s+"}, //7BB
        {"22+", "Qx+", "J2s+", "J6o+", "T5s+", "T7o+", "96s+", "98o", "86s+"}, //6BB
        {"22+", "Qx+", "J2s+", "J3o+", "T2s+", "T6o+", "94s+", "96o+", "85s+", "87o", "75s+", "65s"}, //5BB
        {"22+", "Tx+", "92s+", "95o+", "82s+", "85o+", "73s+", "75o+", "63s+", "65o", "53s+", "43s"}, //4BB
        {"22+", "8x+", "72s+", "73o+", "62s+", "63o+", "5x+", "42s+", "43o", "32s"}, //3BB
        {"xx"}};//2BB
    
    @Override
    public Action createAction(InfoBot info) {
        
        initializeChartBool();
        
        String[][] ranges = whatsMyRole(info.getBot().getRole());
        
        int row = whatRow(calculateMyBB(info.getBot().getMoney(), info.getBb()));
       
        String[] myRange = ranges[row];
        
        setMyRange(myRange);
        
        /*
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                System.err.print(this.chartbool[i][j] + " ");
            }
            System.err.println();
        }*/
        
        Card c1 = info.getBotCards().get(0);
        Card c2 = info.getBotCards().get(1);
        
        String myCards = convertMyCardsToString(c1, c2);
        
        int[] position;
        String val1 = "" + myCards.charAt(0);
        String val2 = "" + myCards.charAt(1);
        
        if (myCards.contains("o")) {
            position = calculatePositionOffsuited(val1, val2);
        } else {
            position = calculatePositionSuited(val1, val2);
        }
        
        if (this.chartbool[position[0]][position[1]]) {
            return new ActionRaise(info.getBot().getMoney(), Actions.ALL_IN);
        } else {
            return new ActionFold();
        }
    }
    
    //Dado el role devuelve la estrategia a usar
    private String[][] whatsMyRole(Role role) {
        if (role == Role.BIG_BLIND) return this.allinRangeBB;
        else return this.allinRangeD;
    }
    
    //Dadas las BB que tenemos devuelve la fila de los rangos a usar
    private int whatRow(int bb) {
        switch(bb) {
            case 0:
            case 1:
            case 2:  return 9;
            case 3:  return 8;
            case 4:  return 7;
            case 5:  return 6;
            case 6:  return 5;
            case 7:  return 4;
            case 8:  return 3;
            case 9:  return 2;
            case 10: return 1;
            default: return 0;
        }
    }
    
    //Devuelve la cantidad de BB que nos queda
    private int calculateMyBB(int money, int bb) {
        return money / bb;
    }
    
    /*Dado el rango, lo aplica a chartboolean poniendo a true las combinaciones
     con las que debemos ir all-in */
    private void setMyRange(String[] range) {
        
        /*
        for (int i = 0; i < range.length; i++) {
            System.err.print(range[i]);
        }
        System.err.println();
        */
        
        for (int i = 0; i < range.length; i++) {
            
            if (range[i].equals("22+")) {
                setTrueTheDiagonal();
            
            } else if (range[i].contains("o")) {
               String c1 = "" + range[i].charAt(0);
               String c2 = "" + range[i].charAt(1);
               
               //Devuelve en position[0] la fila y en position[1] la columna
               int[] position = calculatePositionOffsuited(c1, c2);
               
               if (range[i].contains("+")) {
                   setTrueOffsuitedCol(position[0], position[1]);
               } else {
                   this.chartbool[position[0]][position[1]] = true;
               }
                
            } else if (range[i].contains("s")) {
               String c1 = "" + range[i].charAt(0);
               String c2 = "" + range[i].charAt(1);
               
               //Devuelve en position[0] la fila y en position[1] la columna
               int[] position = calculatePositionSuited(c1, c2);
               
               if (range[i].contains("+")) {
                   setTrueSuitedRow(position[0], position[1]);
               } else {
                   this.chartbool[position[0]][position[1]] = true;
               }
             
            } else if (range[i].contains("x+")) { //Ejemplo 5x+
                /* Pasamos la primera cadena del rango convertida a value, 
                   en el ejemplo 5 -> FIVE
                */
                Value v = Value.getValueByString("" + range[i].charAt(0));
                setTrueXp(v);
                
            } else { //Caso "xx"
                setTrueChart();
            }
        }
    }
    
    private void setTrueTheDiagonal() {
        for (int i = 0; i < this.size; i++) {
            this.chartbool[i][i] = true;
        }
    }
    
    private int[] calculatePositionOffsuited(String c1, String c2) {
        
        Value v1 = Value.getValueByString(c1);
        Value v2 = Value.getValueByString(c2);
        
        int x = calculatePosition(v1);
        int y = calculatePosition(v2);
        
        int row, col;
        
        //Row es la mayor y col la menor
        if (x > y) {
            row = x;
            col = y;
            
        } else {
            col = x;
            row = y;
        }
        
        int[] position = {row, col};
        
        return position;
    }
    
    private void setTrueOffsuitedCol(int row, int col) {
        //Decrementamos la fila hasta que se iguale con la columna
        for (int i = row; i != col; i--) {
            this.chartbool[i][col] = true;
        }
    }
    
    private int[] calculatePositionSuited(String c1, String c2) {
        
        Value v1 = Value.getValueByString(c1);
        Value v2 = Value.getValueByString(c2);
        
        int x = calculatePosition(v1);
        int y = calculatePosition(v2);
        
        int row, col;
        
        //Col es la mayor y row la menor
        if (x > y) {
            col = x;
            row = y;
            
        } else {
            row = x;
            col = y;
        }
        
        int[] position = {row, col};
        
        return position;
    }
    
    private void setTrueSuitedRow(int row, int col) {
        //Decrementamos la columna hasta que se iguale con la fila
        for (int i = col; i != row; i--) {
            this.chartbool[row][i] = true;
        }
    }
    
    //Pone a true el rango vx+ donde v es el valor de la carta más alta
    private void setTrueXp(Value v) {
        int x = calculatePosition(v);
        
        for (int i = x; i >= 0; i--) {
             for (int col = i; col < this.size; col++) {
                this.chartbool[i][col] = true;
            }
        
            for (int row = i; row < this.size; row++) {
                this.chartbool[row][i] = true;
            }
        }
       
        
    }
    
    //Dado el valor v calcula la posicion de la tabla donde está ese valor
    private int calculatePosition(Value v) {
        return this.size - v.ordinal() - 1;
    }
    
    //Pone chartbool a true 
    private void setTrueChart() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.chartbool[i][j] = true;
            }
        }
    }
    
    //Inicializa chartbool a false
    private void initializeChartBool() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.chartbool[i][j] = false;
            }
        }
    }
 
    private String convertMyCardsToString(Card c1, Card c2) {
        Card high;
        Card lower;
        
        if (c1.getVal().ordinal() > c2.getVal().ordinal()) {
            high = c1;
            lower = c2;
        } else {
            high = c2;
            lower = c1;
        }
        
        String myCards = "";
        
        myCards += high.getVal().getVal();
        myCards += lower.getVal().getVal();
        
        if (high.getSuit() == lower.getSuit()) myCards += "s";
        else myCards += "o";
        
        return myCards;
    }
    
}
