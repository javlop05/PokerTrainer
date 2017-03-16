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
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import pokertrainer.model.Role;

/**
 * Clase de la vista que define el panel con la información de un jugador relativa a su nombre, rol y stack actual.
 * @author Javi
 */
public class PlayerInfo extends javax.swing.JPanel {
    
    private final Border turn = BorderFactory.createLineBorder(Color.yellow, 5, true);
    private final Border notTurn = BorderFactory.createLineBorder(new Color(240, 240, 240), 5, true);
    private final Border fold = BorderFactory.createLineBorder(Color.RED,5, true);
    private final Border win = BorderFactory.createLineBorder(Color.GREEN,5, true);

    /**
     * Constructor de la clase que crea el panel.
     */
    public PlayerInfo() {
        initComponents();
    }
    
    /**
     * Establece la información relativa a un jugador en el panel.
     * @param money Stack del jugador.
     * @param name Nombre del jugador.
     * @param role Rol del jugador.
     */
    public void setInfo(int money, String name, Role role) {
        setPlayerMoney(money);
        setPlayerName(name);
        setPlayerRole(role);
    }
    
    /**
     * Modifica el texto que indica el stack del jugador en el panel.
     * @param money Stack actual del jugador.
     */
    public void setPlayerMoney(int money) {
        playerMoney.setText(money + "");
    }
    
    /**
     * Modifica el texto que indica el nombre del jugador en el panel.
     * @param name Nombre del jugador.
     */
    private void setPlayerName(String name) {
        playerName.setText(name);
    }
    
    /**
     * Modifica el texto que indica el rol del jugador en el panel.
     * @param role Rol actual del jugador.
     */
    public void setPlayerRole(Role role) {
        playerRol.setText(role.toString());
    }
    
    /**
     * Cambia el color del borde del panel para resaltar qué jugador tiene el turno.
     */
    public void showPanelTurnPlayer(){
        this.jPanel1.setBorder(turn);
    }
    
    /**
     * Establece el color por defecto del borde del panel para mostrar que este jugador ya no tiene el turno. 
     */
    public void disableShowPanelTurnPlayer(){
        this.jPanel1.setBorder(notTurn);
        if(this != null){
            try{
                this.update(this.getGraphics());
            }catch(NullPointerException e){
                this.repaint();
            }
        }
    }
    
    /**
     * Modifica el color del borde del panel para resaltar que el jugador ha hecho Fold.
     */
    public void setFoldInfo() {
        this.jPanel1.setBorder(fold);
        if(this != null){
            try{
                this.update(this.getGraphics());
            }catch(NullPointerException e){
                this.repaint();
            }
        }
    }
    
    /**
     * Modifica el color del borde del panel para resaltar que el jugador ha ganado un bote.
     */
    public void setWinnerInfo(){
        this.jPanel1.setBorder(win);
        if(this != null)
            try{
                this.update(this.getGraphics());
            }catch(NullPointerException e){
                this.repaint();
            }
    }
    
    /**
     * Elimina la información relativa al stack del jugador.
     */
    public void setMoneyZero(){
        this.playerMoney.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        playerName = new javax.swing.JLabel();
        playerRol = new javax.swing.JLabel();
        playerMoney = new javax.swing.JLabel();

        setAutoscrolls(true);
        setOpaque(false);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 5, true));
        jPanel1.setForeground(new java.awt.Color(240, 240, 240));
        jPanel1.setOpaque(false);

        playerName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        playerName.setForeground(new java.awt.Color(204, 204, 204));
        playerName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playerName.setText("PlayerName");

        playerRol.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        playerRol.setForeground(new java.awt.Color(255, 255, 153));
        playerRol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playerRol.setText("Big Blind");

        playerMoney.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        playerMoney.setForeground(new java.awt.Color(255, 0, 0));
        playerMoney.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playerMoney.setText("113K");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(playerRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(playerMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(playerName, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(playerName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(playerRol)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playerMoney))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel playerMoney;
    private javax.swing.JLabel playerName;
    private javax.swing.JLabel playerRol;
    // End of variables declaration//GEN-END:variables

    /**
     * Método que oculta el panel de información de un jugador.
     */
    void disablePanel() {
        this.jPanel1.setVisible(false);
        this.playerMoney.setVisible(false);
        this.playerName.setVisible(false);
        this.playerRol.setVisible(false);
        this.setVisible(false);
    }
    
    /**
     * Método que repinta todos los componentes
     */
    void repaintAll() {
        this.jPanel1.repaint();
        this.playerMoney.repaint();
        this.playerName.repaint();
        this.playerRol.repaint();
    }

    

    
}
