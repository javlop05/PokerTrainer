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
package pokertrainer.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import pokertrainer.controller.GameController;

/**
 * Clase de la vista que define el menú con las posibles acciones de un jugador.
 * @author Carlos
 */
public class MenuPanel extends javax.swing.JPanel {

    private GameController controller;
    private boolean withCap = false;
    private int cap = 100;
    private int actualPlayerMoney;
    
    /**
     * Constructor de la clase.
     */
    public MenuPanel() {
        initComponents();
        QuantitySlider.setValue(0);
        QuantitySlider.setMinimum(2);
        jSpinner1.setEnabled(true);
        initEvents();
        bet = false;
    }
    
    
    /**
     * Método que actualiza el panel con las posibles acciones del jugador en función de su stack, las apuestas realizadas por él y por el resto de jugadores.
     * @param playerMoney Stack del jugador.
     * @param playerBet Apuesta realizado hasta el momento por el jugador en esta mano.
     * @param bigBlind Cuantía de la ciega grande.
     * @param highBet Mayor apuesta realizada por algún jugador en esta ronda.
     * @param bot Indica si el jugador es o no un bot.
     * @param totalBet Indica lo que lleva apostado el jugador que tiene el turno en la mano actual.
     */
    public void update(int playerMoney, int playerBet, int bigBlind, int highBet,
            boolean bot, int totalBet) {
        
        setVisibilityOfMenuPanel(bot);
        
        this.bet = false;
        this.CallButton.setEnabled(true);
        this.RaiseButton.setEnabled(true);
        this.CallButton.setText("Call");
        this.QuantitySlider.setEnabled(true);
        this.jSpinner1.setEnabled(true);
        this.actualPlayerMoney = playerMoney;
        
        int minimum;
        int maximum;
        
        if (highBet == playerBet) this.CallButton.setText("Check");
        //Si no hay ninguna apuesta anterior
        if (highBet == 0) {
            this.CallButton.setText("Check");
            //Caso de Bet
            if (playerMoney > bigBlind) {
                minimum = bigBlind;
                if(this.withCap) {
                    //BIEN
                    maximum = cap - totalBet;
                    if(playerMoney < maximum) maximum = playerMoney;
                }
                else maximum = playerMoney;
                
                this.RaiseButton.setText("Bet");
                this.bet = true;

            }
            else {
                changeRaiseForAllIn();
                minimum = maximum = playerMoney;

            }
        } else {
            //Sí que hay alguna apuesta anterior.
            if (playerMoney + playerBet <= highBet) {              
                    this.CallButton.setEnabled(false);
                    changeRaiseForAllIn();
                    minimum = maximum = playerMoney + playerBet;
            } 
            else {
                //Caso de Raise To
                if (playerMoney + playerBet > highBet + bigBlind) {
                    minimum = bigBlind + highBet;
                    if(this.withCap){
                        //Ahora bien
                        maximum = cap - totalBet + playerBet;
                        if(highBet + totalBet == cap) changeRaiseForCap();
                        else{
                            this.RaiseButton.setText("Raise to");
                        }
                        if(minimum > maximum) minimum = maximum;
                    }else{ 
                        maximum = playerMoney + playerBet;
                        this.RaiseButton.setText("Raise to");
                    }
                } else {       
                        minimum = maximum = playerMoney + playerBet;
                        changeRaiseForAllIn();
                }
            }
        }
        
        if((QuantitySlider.getValue() == cap )&& withCap) changeRaiseForCap();
        this.QuantitySlider.setMinimum(minimum);
        this.QuantitySlider.setMaximum(maximum);
        this.QuantitySlider.setValue(minimum);
        try{
            this.jSpinner1.setModel(new javax.swing.SpinnerNumberModel(minimum, minimum, maximum, 1));
        }catch(IllegalArgumentException e){
            
        }
        
    }
    
    
    
    /**
     * Método que modifica el texto del botón "Raise" por el de "All-In" cuando un jugador quiere apostar todo su stack y pone el Slider a no editable.
     */
    private void changeRaiseForAllIn() {
        this.RaiseButton.setText("All-in");
        this.QuantitySlider.setEnabled(false);
    }
    
    /**
     * Método que modifica el texto del botón "Raise" por el de "All-In" cuando un jugador quiere apostar todo su stack y pone el Slider a no editable.
     */
    private void changeRaiseForCap() {
        this.RaiseButton.setEnabled(false);
        this.jSpinner1.setEnabled(false);
        this.QuantitySlider.setEnabled(false);
    }
    
    /**
     * Establece el controlador de la partida.
     * @param controller Objeto controlador de la partida.
     */
    
    public void setController(GameController controller) {
        this.controller = controller;
    }
    
    /**
     * Modifica la visibilidad del menú de acciones de un jugador.
     * @param flag Si está a True hace visible el panel. Si está a False oculta el panel.
     */
    public void setVisibilityOfMenuPanel(boolean flag) {
        this.CallButton.setVisible(flag);
        this.FoldButton.setVisible(flag);
        this.RaiseButton.setVisible(flag);
        this.QuantitySlider.setVisible(flag);
        this.jSpinner1.setVisible(flag);
    }
    
    
    /**
     * Actualiza la parte gráfica de la interfaz.
     */
    public void updateGraphics(){
        if(this != null){
            try{
                this.update(this.getGraphics());
            }catch(NullPointerException e){
                this.repaint();
            }
        }
    }
    
    /**
     * Desactiva los botones del menú.
     */
    public void disableButtons() {
        this.CallButton.setEnabled(false);
        this.FoldButton.setEnabled(false);
        this.RaiseButton.setEnabled(false);
        this.QuantitySlider.setEnabled(false);
    }
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CallButton = new javax.swing.JButton();
        FoldButton = new javax.swing.JButton();
        RaiseButton = new javax.swing.JButton();
        QuantitySlider = new javax.swing.JSlider();
        jSpinner1 = new javax.swing.JSpinner();

        setBackground(new java.awt.Color(0, 0, 0));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        CallButton.setBackground(new java.awt.Color(0, 0, 0));
        CallButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        CallButton.setForeground(new java.awt.Color(255, 255, 255));
        CallButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/iconButton.png"))); // NOI18N
        CallButton.setText("Call");
        CallButton.setToolTipText("");
        CallButton.setBorder(null);
        CallButton.setBorderPainted(false);
        CallButton.setContentAreaFilled(false);
        CallButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CallButton.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/pressedIconButton.png"))); // NOI18N
        CallButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/hoverIconButton.png"))); // NOI18N
        add(CallButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 11, -1, -1));

        FoldButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        FoldButton.setForeground(new java.awt.Color(255, 255, 255));
        FoldButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/iconButton.png"))); // NOI18N
        FoldButton.setText("Fold");
        FoldButton.setBorder(null);
        FoldButton.setBorderPainted(false);
        FoldButton.setContentAreaFilled(false);
        FoldButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FoldButton.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/pressedIconButton.png"))); // NOI18N
        FoldButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/hoverIconButton.png"))); // NOI18N
        add(FoldButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(179, 11, -1, -1));

        RaiseButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        RaiseButton.setForeground(new java.awt.Color(255, 255, 255));
        RaiseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/iconButton.png"))); // NOI18N
        RaiseButton.setText("Raise to");
        RaiseButton.setBorder(null);
        RaiseButton.setBorderPainted(false);
        RaiseButton.setContentAreaFilled(false);
        RaiseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        RaiseButton.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/pressedIconButton.png"))); // NOI18N
        RaiseButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/hoverIconButton.png"))); // NOI18N
        add(RaiseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 11, -1, -1));

        QuantitySlider.setBackground(new java.awt.Color(0, 0, 0));
        QuantitySlider.setFont(new java.awt.Font("Agency FB", 0, 11)); // NOI18N
        add(QuantitySlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(529, 38, 151, -1));

        jSpinner1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(10, 10, 10, 1));
        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });
        add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 150, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        int bet = (Integer) jSpinner1.getValue();
        QuantitySlider.setValue(bet);
    }//GEN-LAST:event_jSpinner1StateChanged


    private boolean bet;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CallButton;
    private javax.swing.JButton FoldButton;
    private javax.swing.JSlider QuantitySlider;
    private javax.swing.JButton RaiseButton;
    private javax.swing.JSpinner jSpinner1;
    // End of variables declaration//GEN-END:variables

    
    /**
     * Método que inicializa los listeners de los botones del menú.
     */
    private void initEvents() {
        //Listener del botón "Call"
        CallButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.playerCalls(CallButton.getText());
            }
        });
        //Listener del botón "Fold"
        FoldButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.playerFolds();
                
            }
        });
        //Listener del botón "Raise"
        RaiseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.playerRaises(QuantitySlider.getValue(), RaiseButton.getText());
                
            }
        });
        //Listener del Slider de las apuestas.
        QuantitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = QuantitySlider.getValue();
                jSpinner1.setValue(value);
                
                if((value == QuantitySlider.getMaximum() && !withCap) || (actualPlayerMoney < cap && withCap)){
                    RaiseButton.setText("All-in");
                } else {
                    if (bet) RaiseButton.setText("Bet");
                    else RaiseButton.setText("Raise to");
                }
            }
        });
    }
    /**
     * Método que cambia los atributos de MenuPanel withCap y cap según los parámetros introducidos
     * @param withCap Si está a True se ejecuta el juego con el cap establecido en el parámetro cap
     * @param cap Límite superior que se puede apostar en una mano
     */
    void setCapMode(boolean withCap, int cap) {
        this.withCap = withCap;
        this.cap = cap;
    }
    
    /**
     * Método que repinta todos los componentes
     */
    void repaintAll() {
        this.CallButton.repaint();
        this.FoldButton.repaint();
        this.QuantitySlider.repaint();
        this.RaiseButton.repaint();
        this.jSpinner1.repaint();
    }
    
 
}
