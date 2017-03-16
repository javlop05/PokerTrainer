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

import java.awt.Color;
import java.io.File;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import pokertrainer.controller.CustomPlayersController;
import pokertrainer.model.Board;
import pokertrainer.model.CustomPlayer;
import pokertrainer.model.PokerTrainer;


/**
 * Clase de la vista que define la ventana de customización de los jugadores completa.
 * @author Javi
 */
public class CustomPlayersWindow extends javax.swing.JDialog {
     
    private CustomPlayersController cpcntr;
    private int[] seats;
    private int numBotPanels = 0;
    private final ImageIcon capoff = new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/capoff.png"));
    private final ImageIcon capoff_normal = new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/capoff_normal.png"));
    private final ImageIcon capoff_brillo = new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/capoff_brillo.png"));
    private final ImageIcon capon_normal = new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/capon_normal.png"));
    private final ImageIcon capon = new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/capon.png"));
    private final ImageIcon capon_brillo = new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/capon_brillo.png"));
    private final ImageIcon botCardsOn_normal = new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/botcardsvisibleon_normal.png"));
    private final ImageIcon botCardsOn = new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/botcardsvisibleon.png"));
    private final ImageIcon botCardsOn_brillo = new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/botcardsvisibleon_brillo.png"));
    private final ImageIcon botCardsOff_normal = new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/botcardsvisibleoff_normal.png"));
    private final ImageIcon botCardsOff = new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/botcardsvisibleoff.png"));
    private final ImageIcon botCardsOff_brillo = new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/botcardsvisibleoff_brillo.png"));
    
    /**
     * Constructor que crea la ventana utilizando el constructor de la clase padre <code>JDialog</code>
     * @param parent Clase <code>Frame</code> para el constructor de la clase padre.
     * @param modal Permite que la ventana sea modal o no.
     */
    private CustomPlayersWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("PokerTrainer");
        initComponents();
        this.jToggleButtonBotCardsVisible.setVisible(false);
        this.jLabel1.setVisible(false);
        this.jSpinner1.setVisible(false);
        this.getContentPane().setBackground(Color.BLACK);
    }
    
    
    /**
     * Constructor que crea la ventana de customización según el número de jugadores establecido.
     * @param numPlayers Número de jugadores que se podrán customizar.
     * @param cpcntr Controlador para la ventana de customización.
     */
    public CustomPlayersWindow(int numPlayers, CustomPlayersController cpcntr) {
        this(null, true);
        this.cpcntr = cpcntr;
        this.seats = Board.getSeatsForPlayers(numPlayers);
        loadBotNamesInComboBoxes();
        hideCustomPanels();
        for(int i=0; i<numPlayers; i++){
            initializeCustomPanel(i);         
        }
        this.setResizable(false);
        this.pack();
    }
    
    

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gamePanel1 = new pokertrainer.view.GamePanel();
        boardCards = new pokertrainer.view.BoardCards();
        customPlayerPanel0 = new pokertrainer.view.CustomPlayerPanel();
        customPlayerPanel1 = new pokertrainer.view.CustomPlayerPanel();
        customPlayerPanel2 = new pokertrainer.view.CustomPlayerPanel();
        customPlayerPanel3 = new pokertrainer.view.CustomPlayerPanel();
        customPlayerPanel4 = new pokertrainer.view.CustomPlayerPanel();
        customPlayerPanel5 = new pokertrainer.view.CustomPlayerPanel();
        customPlayerPanel6 = new pokertrainer.view.CustomPlayerPanel();
        customPlayerPanel7 = new pokertrainer.view.CustomPlayerPanel();
        customPlayerPanel8 = new pokertrainer.view.CustomPlayerPanel();
        jLabel1 = new javax.swing.JLabel();
        jToggleButtonBotCardsVisible = new javax.swing.JToggleButton();
        jSpinner1 = new javax.swing.JSpinner();
        jToggleButtonCap = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        gamePanel1.setBackground(new java.awt.Color(0, 0, 0));
        gamePanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        gamePanel1.add(boardCards, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, -1, -1));
        gamePanel1.add(customPlayerPanel0, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 390, -1, -1));
        gamePanel1.add(customPlayerPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));
        gamePanel1.add(customPlayerPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));
        gamePanel1.add(customPlayerPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));
        gamePanel1.add(customPlayerPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, -1, -1));
        gamePanel1.add(customPlayerPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, -1, -1));
        gamePanel1.add(customPlayerPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 150, -1, -1));
        gamePanel1.add(customPlayerPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 310, -1, -1));
        gamePanel1.add(customPlayerPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 390, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CAP:");
        gamePanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 250, 80, 40));

        jToggleButtonBotCardsVisible.setBackground(new java.awt.Color(255, 0, 0));
        jToggleButtonBotCardsVisible.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jToggleButtonBotCardsVisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/botcardsvisibleoff_normal.png"))); // NOI18N
        jToggleButtonBotCardsVisible.setText("");
        jToggleButtonBotCardsVisible.setToolTipText("");
        jToggleButtonBotCardsVisible.setBorder(null);
        jToggleButtonBotCardsVisible.setBorderPainted(false);
        jToggleButtonBotCardsVisible.setContentAreaFilled(false);
        jToggleButtonBotCardsVisible.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/botcardsvisibleoff.png"))); // NOI18N
        jToggleButtonBotCardsVisible.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/botcardsvisibleoff_brillo.png"))); // NOI18N
        jToggleButtonBotCardsVisible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonBotCardsVisibleActionPerformed(evt);
            }
        });
        gamePanel1.add(jToggleButtonBotCardsVisible, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 330, 290, 40));

        jSpinner1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(100, 100, null, 1));
        gamePanel1.add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, 170, 40));

        jToggleButtonCap.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jToggleButtonCap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/capoff_normal.png"))); // NOI18N
        jToggleButtonCap.setBorder(null);
        jToggleButtonCap.setBorderPainted(false);
        jToggleButtonCap.setContentAreaFilled(false);
        jToggleButtonCap.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/capoff.png"))); // NOI18N
        jToggleButtonCap.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/capoff_brillo.png"))); // NOI18N
        jToggleButtonCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonCapActionPerformed(evt);
            }
        });
        gamePanel1.add(jToggleButtonCap, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 190, 290, 40));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/playIcon.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/playPressedIcon.png"))); // NOI18N
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/pokertrainer/view/img/button/playHoverIcon.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gamePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1013, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(357, 357, 357))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(gamePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método que se ejecutará tras pulsar en el botón CAP, que permite establecer la máxima apuesta en una mano por jugador.
     * @param evt Evento que provocará la llamada a este método.
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        
        try{
            int cap = (Integer) jSpinner1.getValue();
            int max = getMinStack();
            if(cap > max){
                JOptionPane.showMessageDialog(null, "Introduzca en el campo CAP un valor numérico entre [100, minStack(all Players)]");
            }else{
                this.cpcntr.start(readInfo(), cap, jToggleButtonBotCardsVisible.isSelected(), jToggleButtonCap.isSelected());
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error", "Introduzca en el campo CAP un valor numérico entre [100, minStack(all Players)]", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Método que se ejecutará al accionar el botón para seleccionar si las cartas de los bots serán visibles o no.
     * @param evt Evento que provocará la llamada a este método.
     */
    private void jToggleButtonBotCardsVisibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonBotCardsVisibleActionPerformed
        if(this.jToggleButtonBotCardsVisible.isSelected()){
            this.jToggleButtonBotCardsVisible.setIcon(botCardsOn_normal);
            this.jToggleButtonBotCardsVisible.setPressedIcon(botCardsOn);
            this.jToggleButtonBotCardsVisible.setRolloverIcon(botCardsOn_brillo);
        }else{
            this.jToggleButtonBotCardsVisible.setIcon(botCardsOff_normal);
            this.jToggleButtonBotCardsVisible.setPressedIcon(botCardsOff);
            this.jToggleButtonBotCardsVisible.setRolloverIcon(botCardsOff_brillo);
        }
    }//GEN-LAST:event_jToggleButtonBotCardsVisibleActionPerformed
    /**
     * Método que se ejecutará al accionar el botón para seleccionar si hay cap o no
     * @param evt Evento que provocará la llamada a este método.
     */
    private void jToggleButtonCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonCapActionPerformed
        if(this.jToggleButtonCap.isSelected()){
            jToggleButtonCap.setIcon(capon_normal);
            jToggleButtonCap.setPressedIcon(capon);
            jToggleButtonCap.setRolloverIcon(capon_brillo);
            this.jLabel1.setVisible(true);
            this.jSpinner1.setVisible(true);
            
        }else{
            jToggleButtonCap.setIcon(capoff_normal);
            jToggleButtonCap.setPressedIcon(capoff);
            jToggleButtonCap.setRolloverIcon(capoff_brillo);
            this.jLabel1.setVisible(false);
            this.jSpinner1.setVisible(false);
        }
    }//GEN-LAST:event_jToggleButtonCapActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pokertrainer.view.BoardCards boardCards;
    private pokertrainer.view.CustomPlayerPanel customPlayerPanel0;
    private pokertrainer.view.CustomPlayerPanel customPlayerPanel1;
    private pokertrainer.view.CustomPlayerPanel customPlayerPanel2;
    private pokertrainer.view.CustomPlayerPanel customPlayerPanel3;
    private pokertrainer.view.CustomPlayerPanel customPlayerPanel4;
    private pokertrainer.view.CustomPlayerPanel customPlayerPanel5;
    private pokertrainer.view.CustomPlayerPanel customPlayerPanel6;
    private pokertrainer.view.CustomPlayerPanel customPlayerPanel7;
    private pokertrainer.view.CustomPlayerPanel customPlayerPanel8;
    private pokertrainer.view.GamePanel gamePanel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JToggleButton jToggleButtonBotCardsVisible;
    private javax.swing.JToggleButton jToggleButtonCap;
    // End of variables declaration//GEN-END:variables

    /**
     * Asigna un <code>CustomPlayerPanel</code> a un jugador en función de su asiento.
     * @param asiento Asiento del jugador al que asignarle el JPanel de customización.
     * @return JPanel de customización asignado.
     */
    private CustomPlayerPanel getCustomPlayerInfo(int asiento) {
        CustomPlayerPanel cp;
        switch(asiento){
            case 0: cp = customPlayerPanel0;  break;
            case 1: cp = customPlayerPanel1;  break;
            case 2: cp = customPlayerPanel2;  break;
            case 3: cp = customPlayerPanel3;  break;
            case 4: cp = customPlayerPanel4;  break;
            case 5: cp = customPlayerPanel5;  break;
            case 6: cp = customPlayerPanel6;  break;
            case 7: cp = customPlayerPanel7;  break;
            case 8: cp = customPlayerPanel8;  break;
            default: cp = null;
        } 
        return cp;
    }

    
    /**
     * Método que esconde los paneles de customización de los jugadores.
     */
    private void hideCustomPanels() {
        for(int i = 0; i<9; i++){
            getCustomPlayerInfo(i).setVisible(false);
        }
    }

    /**
     * Método que lee la información del panel de cada jugador y la asocia a un objeto <code>CustomPlayer</code>
     * @return Lista de objetos <code>CustomPlayer</code> con la información de cada jugador.
     */
    private LinkedList<CustomPlayer> readInfo() {
        LinkedList<CustomPlayer> customPlayers = new LinkedList<>();
        for(int i=0; i<this.seats.length; i++){
            customPlayers.add(getCustomPlayerInfo(this.seats[i]).getCustomPlayer());
        }
        return customPlayers;
    }

    /**
     * Lee los nombres de los archivos del directorio de bots para mostrarlos en el comboBox del panel de customización.
     */
    private void loadBotNamesInComboBoxes() {
        String[] files = null;
        File directory = new File(PokerTrainer.BOTSDIRECTORY);  
        if (directory.exists()) {  
            // Get the list of the files contained in the package  
             files = directory.list(); 
        } 
        
        for (int i = 0; i < this.seats.length; i++) {
            getCustomPlayerInfo(seats[i]).setBotNames(files);
        }
    }

    /**
     * Inicializa el panel de customización de un jugador según su posición.
     * @param i Posición del jugador al que inicializar el panel de customización.
     */
    private void initializeCustomPanel(int i) {
        CustomPlayerPanel panel = getCustomPlayerInfo(seats[i]);
        panel.setView(this);
        panel.setDefaultStack(Board.MONEY);
        panel.setDefaultName(i);
        panel.setVisible(true);
    }

    /**
     * Método que hace visible el botón para seleccionar si se verán las cartas del bot durante la partida.
     * @param b <p>Si es True se hará visible el botón, ya que significará que alguno de los jugadores es un bot.</p> Si es False será el caso contrario.
     */
    void setVisibleBotCardsButton(boolean b) {
        if(b){
            this.numBotPanels ++;
            this.jToggleButtonBotCardsVisible.setVisible(true);
        }else{
            this.numBotPanels --;
            if(this.numBotPanels == 0){
                this.jToggleButtonBotCardsVisible.setVisible(false);
                this.jToggleButtonBotCardsVisible.setSelected(false);
            }
        }
    }
    
    /**
     * Devuelve el mínimo de los stacks de los jugadores.
     * @return  mínimo de los stacks
     */ 
    private int getMinStack() {
        int min;
        min = getCustomPlayerInfo(seats[0]).getStack();
        for(int i=1; i<this.seats.length; i++){
            int cant = getCustomPlayerInfo(seats[0]).getStack();
                if(cant < min) min = cant;
        }
        return min;
    }
    
}
