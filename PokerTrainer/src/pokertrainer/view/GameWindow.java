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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import pokertrainer.model.Card;
import pokertrainer.model.Player;
import pokertrainer.controller.GameController;
import pokertrainer.model.Action;
import pokertrainer.model.Role;

/**
 * Clase de la vista que define la ventana donde se desarrollarán las partidas.
 * @author Javi
 */
public class GameWindow extends javax.swing.JDialog {
     
    
    private DebugDialog debugView;
 
    /**
     * Constructor que crea la ventana utilizando el constructor de la clase padre <code>JDialog</code>
     * @param parent Clase <code>Frame</code> para el constructor de la clase padre.
     * @param modal Permite que la ventana sea modal o no.
     */
    public GameWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("PokerTrainer");
        initComponents();
        this.debugView = new DebugDialog(null, false);
        debugView.setVisible(true);
        this.getContentPane().setBackground(Color.BLACK);
    }
    
    /**
     * Constructor que inicializa la ventana de juego.
     * @param controller Controlador de la aplicación.
     * @param players Jugadores que participarán en esta partida.
     * @param seats Lista de posiciones donde colocar a los jugadores de la partida.
     * @param pot Cantidad de fichas en el bote de la mesa.
     * @param playerTurn Jugador que dispone del turno actual.
     * @param bigBlind Cuantía de fichas de la ciega grande.
     * @param withCap Si está a True se ejecuta el juego con el cap establecido en el parámetro cap
     * @param cap Límite superior que se puede apostar en una mano
     */
    public GameWindow(GameController controller, LinkedList<Player> players, 
            int[] seats, int pot, Player playerTurn, int bigBlind, boolean withCap, int cap) {
        this(null, false);
        this.menuPanel1.setCapMode(withCap, cap);
        this.menuPanel1.setController(controller);
        setTextToTextArea("******** HAND 1 ******** \n");
        loadPlayersInfo(seats, players, pot, playerTurn, bigBlind, controller.getBotCardsVisible());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.pack();
    }
    
    
    /**
     * Método que carga y muestra la información relativa a cada jugador.
     * @param seats Lista de posiciones donde colocar a los jugadores de la partida.
     * @param players Jugadores que participarán en esta partida.
     * @param pot Cantidad de fichas en el bote de la mesa.
     * @param playerTurn Jugador que dispone del turno actual.
     * @param bigBlind Cuantía de fichas de la ciega grande.
     * @param botCardsVisible Si es True, durante la partida se mostrarán las cartas de los bots.
     */
    private void loadPlayersInfo(int[] seats, LinkedList<Player> players, 
            int pot, Player playerTurn, int bigBlind, boolean botCardsVisible) {
        showPlayers(seats, false);
        setPlayersInfo(players);
        updatePot(pot);
        boolean cardsVisible = true;
        if(playerTurn.isBot()){
            cardsVisible = botCardsVisible;
        }
        updateViewForPlayerTurn(playerTurn, bigBlind, bigBlind, cardsVisible);
    }
    
    
    /**
     * Recorre la lista de jugadores para hacer visible su información sobre la ventana.
     * @param players Lista de jugadores de la partida.
     */
    private void setPlayersInfo(List<Player> players) {
        int numPlayers = players.size();
        for(int i=0; i<numPlayers; i++){
            Player player = players.get(i);
            putPlayerOnSeat(player);
        }
    }
    
    
    //Pinta toda la informacion del jugador en el asiento correspondiente
    /**
     * Hace visible la información de un jugador en el asiento correspondiente.
     * @param player Jugador del que hacer visible la información.
     */
    private void putPlayerOnSeat(Player player){
        Card card1 = player.getCards().get(0);
        Card card2 = player.getCards().get(1);
        ImageIcon IconCard1 = getCardImageIcon(card1);
        ImageIcon IconCard2 =  getCardImageIcon(card2);
        
        int seat = player.getSeat();
        
        getBetPanel(seat).setBet(player.getBet());
        getCardsPanel(seat).setCardsImages(IconCard1, IconCard2);
        getInfoPanel(seat).setInfo(player.getMoney(), player.getName(), player.getRole());
    }
    
    
    /* Colorea el marco para que sepa que jugador tiene el turno
        y edita los botones conforme al dinero del que disponga y el estado de 
        la partida
    */
    /**
     * <p>Actualiza la ventana en función del jugador que tiene el turno.</p>
     * Colorea el marco del jugador para notificar que tiene el turno, y edita los botones de las posibles acciones del jugador según su stack y el estado de la partida.
     * @param playerTurn Jugador que dispone del turno.
     * @param highBet Mayor apuesta realizada hasta el momento.
     * @param bigBlind Cuantía de la ciega grande.
     * @param botCardsVisible Si es True y el jugador es un bot mostrará sus cartas. Si es False no las mostrará.
     */
    public void updateViewForPlayerTurn(Player playerTurn, int highBet, int bigBlind, boolean botCardsVisible) {
        //Llama a playerInfo par apintar de amarillo el panel del jugador al que le toca
        getInfoPanel(playerTurn.getSeat()).showPanelTurnPlayer();
        if(botCardsVisible)
            getCardsPanel(playerTurn.getSeat()).showCards();
        //Llama a una funcion de menuPanel encargada de editar los botones
        this.menuPanel1.update(playerTurn.getMoney(), playerTurn.getBet(), 
                bigBlind, highBet, !playerTurn.isBot(), playerTurn.getTotalBet());
    }
    
    
    /**
     * Método que esconde la información de un jugador. Para cuando el turno de este ha finalizado.
     * @param playerTurn Jugador del que esconder la información.
     */
    public void disableViewForPlayerTurn(Player playerTurn){
        getInfoPanel(playerTurn.getSeat()).disableShowPanelTurnPlayer();
        getCardsPanel(playerTurn.getSeat()).hideCards();
    }
    
    //Se usa para el call, raise y all-in
    //Pinta la apuesta y actualiza el dinero del jugador
    /**
     * Método que recibe la información relativa a la apuesta de un jugador, y actualiza la interfaz en consecuencia.
     * @param seat Asiento del jugador que realiza la acción.
     * @param money Stack del jugador.
     * @param bet Cuantía de la apuesta.
     */
    public void setPlayerBetAndMoney(int seat, int money, int bet) {
        getBetPanel(seat).setBet(bet);
        getInfoPanel(seat).setPlayerMoney(money);        
    }
    
    /**
     * Actualiza la interfaz tras el fold de un jugador.
     * @param playerTurn Jugador que ha realizado el fold.
     */
    public void setFoldState(Player playerTurn) {
        getInfoPanel(playerTurn.getSeat()).setFoldInfo();
        getBetPanel(playerTurn.getSeat()).setFoldBet();
        getCardsPanel(playerTurn.getSeat()).setVisible(false);
    }
    
    /**
     * Actualiza el bote de la mesa.
     * @param moneyPot Cuantía actual del bote.
     */
    public void updatePot(int moneyPot){
        this.potPanel1.setPot(moneyPot);
    }
    

    //Dada una carta obtiene la ruta con el ImageIcon y lo devuelve
    /**
     * Método que dada una carta, crea la ruta donde se encuentra la imagen de la misma y devuelve un objeto <code>ImageIcon</code> de la carta.
     * @param c Carta de la que se creará la ruta.
     * @return Objeto <code>ImageIcon</code> de la carta recibida por parámetro.
     */
    private ImageIcon getCardImageIcon(Card c) {
        String cardRoute = "img/cards/" + c.getVal().getVal() + c.getSuit().getName() + ".png";
        return new ImageIcon(CardPanel.class.getResource(cardRoute));
    }
    
    
    /**
     * Método que devuelve el panel de apuesta correspondiente al jugador en el asiento recibido por parámetro
     * @param seat Asiento del jugador.
     * @return Panel de apuesta del jugador.
     */
    private PlayerBet getBetPanel(int seat) {
        PlayerBet bet;
        switch(seat) {
            case 0: bet = this.playerBet0; break;
            case 1: bet = this.playerBet1; break;
            case 2: bet = this.playerBet2; break;
            case 3: bet = this.playerBet3; break;
            case 4: bet = this.playerBet4; break;
            case 5: bet = this.playerBet5; break;
            case 6: bet = this.playerBet6; break;
            case 7: bet = this.playerBet7; break;
            case 8: bet = this.playerBet8; break;
            default: bet = null; 
        }
        return bet;
    }
    
    
    
    /**
     * Método que devuelve el panel de cartas correspondiente al jugador en el asiento recibido por parámetro
     * @param seat Asiento del jugador.
     * @return Panel de cartas del jugador.
     */
    private PlayerCards getCardsPanel(int seat) {
        PlayerCards cards;
        switch(seat) {
            case 0: cards = this.playerCards0; break;
            case 1: cards = this.playerCards1; break;
            case 2: cards = this.playerCards2; break;
            case 3: cards = this.playerCards3; break;
            case 4: cards = this.playerCards4; break;
            case 5: cards = this.playerCards5; break;
            case 6: cards = this.playerCards6; break;
            case 7: cards = this.playerCards7; break;
            case 8: cards = this.playerCards8; break;
            default: cards = null; 
        }
        return cards;
    }

    
    /**
     * Método que devuelve el panel de información correspondiente al jugador en el asiento recibido por parámetro
     * @param seat Asiento del jugador.
     * @return Panel de información del jugador.
     */
    private PlayerInfo getInfoPanel(int seat) {
        PlayerInfo info;
        switch(seat) {
            case 0: info = this.playerInfo0; break;
            case 1: info = this.playerInfo1; break;
            case 2: info = this.playerInfo2; break;
            case 3: info = this.playerInfo3; break;
            case 4: info = this.playerInfo4; break;
            case 5: info = this.playerInfo5; break;
            case 6: info = this.playerInfo6; break;
            case 7: info = this.playerInfo7; break;
            case 8: info = this.playerInfo8; break;
            default: info = null; 
        }
        return info;
    }
    
    
    /**
     * Método que permite hacer visible o esconder los paneles de un jugador.
     * @param num Jugador del que modificar la visibilidad de sus paneles.
     * @param flag Si está a True hace visible los paneles. Si está a False los esconde.
     */
    private void showSeat(int num, boolean flag){
        getBetPanel(num).setVisible(flag);
        getCardsPanel(num).setVisible(flag);
        getInfoPanel(num).setVisible(flag);    
    }
    
    
    /**
     * Método que pone la propiedad visible de todos los asientos al valor del flag excepto los recibidos por parametro.
     * @param seats Asientos cuya visiblidad se modificará al contrario del valor del flag.
     * @param flag Si está a True hace visible los paneles. Si está a False los esconde.
     */
    private void showPlayers(int[] seats, boolean flag) {
        int cont = 0;
        
        //8 el numero maximo de jugadores
        for (int i = 0; i < 9; i++) {
            if (cont < seats.length && i == seats[cont]) {
                showSeat(seats[cont], !flag);
                cont++;
            }
            else {
                showSeat(i, flag);
            }
        }
    }
    
 
    //
    /**
     * Método que pinta la carta recibida en la posición recibida (de 1 a 5) en la mesa.
     * @param card Carta que hacer visible
     * @param pos Posición de la carta.
     */
    private void setCardOnBoard(Card card, int pos) {
        this.boardCards.setCardImage(getCardImageIcon(card), pos);
    }
    
    
    /**
     * Método que hace visible las 3 cartas del Flop.
     * @param flopCards Lista de cartas que conforman el Flop.
     */
    public void paintFlop(LinkedList<Card> flopCards){
        for (int i=0; i<flopCards.size(); i++){
            setCardOnBoard(flopCards.get(i), i+1);
        }
    }
    
    
    /**
     * Método que hace visible la carta del Turn.
     * @param turnCard Carta que se mostrará la 4º de la mesa.
     */
    public void paintTurn(Card turnCard){
        setCardOnBoard(turnCard, 4);
    }
    
    /**
     * Método que hace visible la carta del River.
     * @param riverCard Carta que se mostrará la 5º de la mesa.
     */
    public void paintRiver(Card riverCard){
        setCardOnBoard(riverCard, 5);
    }
    
    /**
     * Método que muestra en la ventana de debug las manos de cada jugador que opta a bote en cada ronda
     * @param playersInHand Jugadores que llegan al final de la ronda
     */
    public void paintPlayerHands(LinkedList<Player> playersInHand){
        boolean painted = false;
        for(Player p: playersInHand) {
            if(p.getHand() != null) {
                painted = true;
                setTextToTextArea("\n");
                setTextToTextArea("////////     " + p.getName() + "     ///////// \n");
                setTextToTextArea(" " +p.getHand().toString() +  " \n");
                setTextToTextArea(" ");
                for(Card c: p.getCards()) {
                    setTextToTextArea(c.toString() + "  ");
                }
                setTextToTextArea("\n\n");
            }
        }
        if(painted)
            setTextToTextArea("\n");
        
    }
    
    /**
     * Método que muestra los ganadores de cada bote de una mano.
     * @param roundWinners Lista de botes donde cada posición contiene una lista de los jugadores que ganaron ese bote.
     */
    public void paintWinners(LinkedList<LinkedList<Player>> roundWinners) {
        hideMenuPanel(true);
        hideCounterPanel();
        setTextToTextArea("The winners are: \n");
        for(int i=0; i<roundWinners.size(); i++){
            disableBordersPlayers(roundWinners.get(i));
            setTextToTextArea("Pot " + (i + 1) + " : \n");
            for(int j =0; j< roundWinners.get(i).size(); j++){
                setTextToTextArea(" - " + roundWinners.get(i).get(j).getName() + "\n");
                getInfoPanel(roundWinners.get(i).get(j).getSeat()).setWinnerInfo();
            }
            setTextToTextArea("\n");
            
            /*try {
                TimeUnit.SECONDS.sleep(2);
                
            } catch (InterruptedException ex) {

            }*/
            disableBordersPlayers(roundWinners.get(i));
        }
    }
     
    
    /**
     * Método que añade la información de la acción de un jugador a la ventana de depuración.
     * @param playerName Nombre del jugador que realizó la acción.
     * @param action Acción que realizó.
     */
     public void addActionToPlayerList(String playerName, Action action) {
         String texto = playerName + " - " + action+ "\n";
         this.debugView.appendText(texto);
     }
     
     /**
      * Método que añade una cadena de texto a la ventana de depuración. 
      * @param text Cadena de texto que se añadirá a la ventana de depuración.
      */
     public void setTextToTextArea(String text) {
         this.debugView.appendText(text);
     }
     
   
     /**
      * Método que inicializa las apuestas de los jugadores a 0.
      * @param seats Lista de asientos de los jugadores.
      */
     public void paintAllInitialBets(int[] seats){
         for(int i=0; i< seats.length; i++){
             getBetPanel(seats[i]).setBet(0);
         }
     }
     
     /**
      * Método que actualiza el stack de los jugadores.
      * @param players Lista de jugadores de los que actualizar el stack.
      */
     public void paintAllBets(LinkedList<Player> players){
         for(int i=0; i< players.size(); i++){
             getInfoPanel(players.get(i).getSeat()).setPlayerMoney(players.get(i).getMoney());
         }
     }
     
     
     /**
      * <p>Método que actualiza la interfaz para una nueva mano.</p>
      * Actualiza la ventana de depuración, el rol, asiento, y cartas de los jugadores, la cuantía de la ciega grande y el bote de la mesa.
      * @param players Jugadores que participarán en la nueva mano.
      * @param seats Asientos de los jugadores.
      * @param pot Cantidad de fichas en el bote.
      * @param playerTurn Jugador con el turno actual.
      * @param bigBlind Cuantía de la ciega grande.
      * @param hand Número de manos jugadas.
      * @param getCardsVisible Booleano que indica si las cartas de los bots deberán ser visibles en la siguiente mano.
      */
    public void prepareViewForNextHand(LinkedList<Player> players,
            int[] seats, int pot, Player playerTurn, int bigBlind, int hand, boolean getCardsVisible) {
        
        setTextToTextArea("******** HAND " + (hand + 1) + " ******** \n");
        //System.out.println("******** HAND " + (hand + 1) + " ******** \n");
        
        for (Player p: players) {
             getInfoPanel(p.getSeat()).disableShowPanelTurnPlayer();
             
             int add=0;
             if(p.getRole() == Role.SMALL_BLIND){
                 add = 5;
                 
             }else{
                if(p.getRole() == Role.BIG_BLIND){
                   add = 10;
                   
                }
             }
             
             //System.out.println(p.getName() + " money: "+ (p.getMoney() + add));
             loadPlayersInfo(seats, players, pot, playerTurn, bigBlind, getCardsVisible);
        }
        
        this.boardCards.hideBoardCards();
    }
    
    
    /**
     * Método que elimina a un jugador de la interfaz.
     * @param seat Asiento del jugador a eliminar.
     */
    public void disablePlayer(int seat) {
        getBetPanel(seat).disablePanel();
        getInfoPanel(seat).disablePanel();
        getCardsPanel(seat).disablePanel();
    }
    
    /**
     * Método que desactiva el borde de los cuadros de los jugadores y elimina la información sobre sus stacks.
     * @param players Jugadores a los que modificar la información.
     */
    private void disableBordersPlayers(LinkedList<Player> players){
        for(Player p : players){
          getInfoPanel(p.getSeat()).setMoneyZero();
          getInfoPanel(p.getSeat()).disableShowPanelTurnPlayer();
        }
    }
    
    /**
     * Método que modifica la información (stack,rol y apuesta) de los jugadores que ganaron algún bote en la anterior mano.
     * @param last Lista de jugadores ganadores en la anterior mano.
     */
     public void paintLastWinners(LinkedList<Player> last) {
        this.potPanel1.setPot(0);
        for(Player p: last){
            int seat = p.getSeat();
            getBetPanel(seat).setBet(p.getBet());
            getInfoPanel(seat).setPlayerMoney(p.getMoney());
            getInfoPanel(seat).setPlayerRole(p.getRole());
        }
        hideMenuPanel(true);
        hideCounterPanel();
    }
     
     
     /**
      * Método que oculta el panel donde se muestran las posibles acciones de un jugador.
      * @param end Si está a True la mano habrá finalizado.
      */
    public void hideMenuPanel(boolean end) {
        this.menuPanel1.setVisibilityOfMenuPanel(false);
        this.counterPanel1.setVisibilityOfCounterPanel(false);
        if(end) this.menuPanel1.updateGraphics();
    }
    
     /**
      * Método que oculta el panel del contador
      */
    public void hideCounterPanel() {
        this.counterPanel1.setVisibilityOfCounterPanel(false);
    }
    
    /**
     * Método que hace visible el panel donde se muestran las posibles acciones de un jugador.
     */
    public void showMenuPanel() {
        this.menuPanel1.setVisibilityOfMenuPanel(true);
    }
    
    /**
     * Método que hace visible el panel del contador
     */
    public void showCounterPanel() {
        this.counterPanel1.setVisibilityOfCounterPanel(true);
    }
    
    /**
     * Método que muestra las cartas de los jugadores recibidos por parámetro.
     * @param players Lista de jugadores de los que se mostrarán las cartas.
     */
    public void showAllCards(LinkedList<Player> players) {
        for(Player p: players){
            getCardsPanel(p.getSeat()).showCards();
        }
    }
    
    /**
     * Método que repinta todos los componentes incluidos en esta clase (panel).
     */
    public void repaintAll(){
        this.boardCards.repaint();
        this.boardCards.repaintAll();
        this.counterPanel1.repaint();
        this.counterPanel1.repaintAll();
        this.gamePanel1.repaint();
        this.menuPanel1.repaint();
        this.menuPanel1.repaintAll();
        this.playerBet0.repaint();
        this.playerBet0.repaintAll();
        this.playerBet1.repaint();
        this.playerBet1.repaintAll();
        this.playerBet2.repaint();
        this.playerBet2.repaintAll();
        this.playerBet3.repaint();
        this.playerBet3.repaintAll();
        this.playerBet4.repaint();
        this.playerBet4.repaintAll();
        this.playerBet5.repaint();
        this.playerBet5.repaintAll();
        this.playerBet6.repaint();
        this.playerBet6.repaintAll();
        this.playerBet7.repaint();
        this.playerBet7.repaintAll();
        this.playerBet8.repaint();
        this.playerBet8.repaintAll();
        this.playerCards0.repaint();
        this.playerCards0.repaintAll();
        this.playerCards1.repaint();
        this.playerCards1.repaintAll();
        this.playerCards2.repaint();
        this.playerCards2.repaintAll();
        this.playerCards3.repaint();
        this.playerCards3.repaintAll();
        this.playerCards4.repaint();
        this.playerCards4.repaintAll();
        this.playerCards5.repaint();
        this.playerCards5.repaintAll();
        this.playerCards6.repaint();
        this.playerCards6.repaintAll();
        this.playerCards7.repaint();
        this.playerCards7.repaintAll();
        this.playerCards8.repaint();
        this.playerCards8.repaintAll();
        this.playerInfo0.repaint();
        this.playerInfo0.repaintAll();
        this.playerInfo1.repaint();
        this.playerInfo1.repaintAll();
        this.playerInfo2.repaint();
        this.playerInfo2.repaintAll();
        this.playerInfo3.repaint();
        this.playerInfo3.repaintAll();
        this.playerInfo4.repaint();
        this.playerInfo4.repaintAll();
        this.playerInfo5.repaint();
        this.playerInfo5.repaintAll();
        this.playerInfo6.repaint();
        this.playerInfo6.repaintAll();
        this.playerInfo7.repaint();
        this.playerInfo7.repaintAll();
        this.playerInfo8.repaint();
        this.playerInfo8.repaintAll();
        this.potPanel1.repaint();
        this.potPanel1.repaintAll();
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
        playerInfo0 = new pokertrainer.view.PlayerInfo();
        playerInfo1 = new pokertrainer.view.PlayerInfo();
        playerInfo2 = new pokertrainer.view.PlayerInfo();
        playerInfo3 = new pokertrainer.view.PlayerInfo();
        playerInfo4 = new pokertrainer.view.PlayerInfo();
        playerInfo5 = new pokertrainer.view.PlayerInfo();
        playerInfo6 = new pokertrainer.view.PlayerInfo();
        playerInfo7 = new pokertrainer.view.PlayerInfo();
        playerInfo8 = new pokertrainer.view.PlayerInfo();
        playerBet0 = new pokertrainer.view.PlayerBet();
        playerBet1 = new pokertrainer.view.PlayerBet();
        playerBet2 = new pokertrainer.view.PlayerBet();
        playerBet3 = new pokertrainer.view.PlayerBet();
        playerBet4 = new pokertrainer.view.PlayerBet();
        playerBet5 = new pokertrainer.view.PlayerBet();
        playerBet6 = new pokertrainer.view.PlayerBet();
        playerBet7 = new pokertrainer.view.PlayerBet();
        playerBet8 = new pokertrainer.view.PlayerBet();
        playerCards0 = new pokertrainer.view.PlayerCards();
        playerCards1 = new pokertrainer.view.PlayerCards();
        playerCards2 = new pokertrainer.view.PlayerCards();
        playerCards3 = new pokertrainer.view.PlayerCards();
        playerCards4 = new pokertrainer.view.PlayerCards();
        playerCards5 = new pokertrainer.view.PlayerCards();
        playerCards6 = new pokertrainer.view.PlayerCards();
        playerCards7 = new pokertrainer.view.PlayerCards();
        playerCards8 = new pokertrainer.view.PlayerCards();
        boardCards = new pokertrainer.view.BoardCards();
        potPanel1 = new pokertrainer.view.PotPanel();
        menuPanel1 = new pokertrainer.view.MenuPanel();
        counterPanel1 = new pokertrainer.view.CounterPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        gamePanel1.setBackground(new java.awt.Color(0, 0, 0));
        gamePanel1.setMaximumSize(new java.awt.Dimension(1015, 577));
        gamePanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        gamePanel1.add(playerInfo0, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 500, -1, -1));
        gamePanel1.add(playerInfo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));
        gamePanel1.add(playerInfo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));
        gamePanel1.add(playerInfo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));
        gamePanel1.add(playerInfo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, -1, -1));
        gamePanel1.add(playerInfo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, -1, -1));
        gamePanel1.add(playerInfo6, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 210, -1, -1));
        gamePanel1.add(playerInfo7, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 330, -1, -1));
        gamePanel1.add(playerInfo8, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 500, -1, -1));
        gamePanel1.add(playerBet0, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 510, -1, -1));
        gamePanel1.add(playerBet1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, -1, -1));
        gamePanel1.add(playerBet2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));
        gamePanel1.add(playerBet3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));
        gamePanel1.add(playerBet4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, -1, -1));
        gamePanel1.add(playerBet5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, -1, -1));
        gamePanel1.add(playerBet6, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 180, -1, -1));
        gamePanel1.add(playerBet7, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 400, -1, -1));
        gamePanel1.add(playerBet8, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 510, -1, -1));
        gamePanel1.add(playerCards0, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 400, -1, -1));
        gamePanel1.add(playerCards1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 330, -1, -1));
        gamePanel1.add(playerCards2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, -1, -1));
        gamePanel1.add(playerCards3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, -1, -1));
        gamePanel1.add(playerCards4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, -1, -1));
        gamePanel1.add(playerCards5, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, -1, -1));
        gamePanel1.add(playerCards6, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 210, -1, -1));
        gamePanel1.add(playerCards7, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 330, -1, -1));
        gamePanel1.add(playerCards8, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 400, -1, -1));
        gamePanel1.add(boardCards, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, -1, -1));
        gamePanel1.add(potPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 120, 50));

        menuPanel1.setPreferredSize(new java.awt.Dimension(161, 53));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gamePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1013, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(counterPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(menuPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 709, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(gamePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(counterPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(menuPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pokertrainer.view.BoardCards boardCards;
    private pokertrainer.view.CounterPanel counterPanel1;
    private pokertrainer.view.GamePanel gamePanel1;
    private pokertrainer.view.MenuPanel menuPanel1;
    private pokertrainer.view.PlayerBet playerBet0;
    private pokertrainer.view.PlayerBet playerBet1;
    private pokertrainer.view.PlayerBet playerBet2;
    private pokertrainer.view.PlayerBet playerBet3;
    private pokertrainer.view.PlayerBet playerBet4;
    private pokertrainer.view.PlayerBet playerBet5;
    private pokertrainer.view.PlayerBet playerBet6;
    private pokertrainer.view.PlayerBet playerBet7;
    private pokertrainer.view.PlayerBet playerBet8;
    private pokertrainer.view.PlayerCards playerCards0;
    private pokertrainer.view.PlayerCards playerCards1;
    private pokertrainer.view.PlayerCards playerCards2;
    private pokertrainer.view.PlayerCards playerCards3;
    private pokertrainer.view.PlayerCards playerCards4;
    private pokertrainer.view.PlayerCards playerCards5;
    private pokertrainer.view.PlayerCards playerCards6;
    private pokertrainer.view.PlayerCards playerCards7;
    private pokertrainer.view.PlayerCards playerCards8;
    private pokertrainer.view.PlayerInfo playerInfo0;
    private pokertrainer.view.PlayerInfo playerInfo1;
    private pokertrainer.view.PlayerInfo playerInfo2;
    private pokertrainer.view.PlayerInfo playerInfo3;
    private pokertrainer.view.PlayerInfo playerInfo4;
    private pokertrainer.view.PlayerInfo playerInfo5;
    private pokertrainer.view.PlayerInfo playerInfo6;
    private pokertrainer.view.PlayerInfo playerInfo7;
    private pokertrainer.view.PlayerInfo playerInfo8;
    private pokertrainer.view.PotPanel potPanel1;
    // End of variables declaration//GEN-END:variables

    
}
