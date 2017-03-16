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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import pokertrainer.model.Action;
import pokertrainer.model.ActionCall;
import pokertrainer.model.ActionFold;
import pokertrainer.model.ActionRaise;
import pokertrainer.model.Actions;
import pokertrainer.model.Board;
import pokertrainer.model.GameState;
import pokertrainer.model.InfoBot;
import pokertrainer.model.Player;
import pokertrainer.model.State;
import pokertrainer.view.CounterPanel;
import pokertrainer.view.GameWindow;


/**
 * <p>Clase Adaptador del Framework. </p>
 * EL Framework sigue el patrón Modelo-Vista-Adaptador, por lo que tiene instancias del modelo y la vista.
 * @author usuario_local
 */
public class GameController {
    
    private Board model;
    private GameWindow view;
    private Timer timer;
    private boolean botCardsVisible = false;
    private boolean withCap;
    private int cap;
    
    
    /**
     * <p>Constructor de la clase</p>
     * Crea y hace visible la vista, e inicia una partida
     * @param board Objeto board que representa el modelo de la aplicación
     * @param botCardsVisible <code>Booleano</code> que si está a True indica que se mostrarán las cartas de los bots
     * @param withCap Si está a True se ejecuta el juego con el cap establecido en el parámetro cap
     * @param cap Límite superior que se puede apostar en una mano
     */
    public GameController(Board board, boolean botCardsVisible, boolean withCap, int cap) {
        this.model = board;
        this.withCap = withCap;
        this.cap = cap;
        this.view = new GameWindow(this, model.getPlayers(), this.model.getSeats(),
                this.model.getSumOfPots(), model.getPlayerTurn(), model.getBigBlind(), withCap, cap);
        this.botCardsVisible = botCardsVisible;
        this.view.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.view.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we){
                int valor = JOptionPane.showConfirmDialog(null, "¿Quieres cerrar PokerTrainer?", "Advertencia", JOptionPane.YES_NO_OPTION);
                if(valor == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        }); 
        continueGame();
        this.view.setVisible(true);
        this.model.setControl(this);
        
    }
    
   
    /**
     * <p>Método que modifica el modelo según la acción de un jugador.</p>
     * <p>Si era el último en hablar cambia el estado de la mano.</p>
     * Si no lo era: Modifica el <code>Board</code> según la acción realizada.
     * @see State
     * @param p Último jugador que realizó una acción
     * @param playerFoldsOrAllin 
     */
    private void isLastToSpeak(Player p, boolean playerFoldsOrAllin) {
        //si es el ultimo en hablar
        if (model.isLastToSpeak()){
            State t = model.changeState();
            view.paintAllInitialBets(model.getSeats());
            //si ha hecho fold o allin
            if (playerFoldsOrAllin) model.removePlayer(p);
            
            executeState(t);
           
        } 
        //si no es el ultimo en hablar
        else {
            //si ha hecho fold o allin
            if (playerFoldsOrAllin)
                model.removePlayer(p);
                
            else
                model.changeTurn();
        }
    }
    
    
    /**
     * Ejecuta el siguiente estado de la mano (que le entra por parámetro).
     * @param t Siguiente estado.
     * @see State
     */
    private void executeState(State t) {
        
         switch(t) {
            case PREFLOP:
                preflop();
                break;
            case FLOP:
                flop();
                break;
            case TURN:
                turn();
                break;
            case RIVER:
                river();
                break;
            case SHOWDOWN:
                showDown();
                break;
            case GAME_FINISHED:
                System.out.println("Partida acabada.");
            default:
                break;
            }
    }
    
    
    
    /**
     * Inicia el Preflop de la mano llamando al método del <code>Board</code>.
     * @see Board
     */
    public void preflop(){
        //stopGame();
        this.model.preflop();
        this.view.showMenuPanel();
        this.view.showCounterPanel();
        continueGame();       
    }
    
    
    /**
     * Inicia el Flop llamando al método del Board y muestra las 3 primeras cartas de la mesa.
     * @see Board
     */
    public void flop() {
        view.paintFlop(model.flop());      
    }
    

    /**
     * Inicia el Turn llamando al método del Board y muestra la cuarta carta de la mesa.
     * @see Board
     */
    public void turn() {
        view.paintTurn(model.turn());     
    }
    
    /**
     * Inicia el River llamando al método del Board y muestra la quinta carta de la mesa.
     * @see Board
     */
    public void river() {
        view.paintRiver(model.river());   
    }
    
    /**
     * <p>Inicia el ShowDown </p>
     * Muestra los ganadores de la mano e inicia la siguiente (si la partida no ha finalizado). 
     * @see State
     */
    private void showDown() {
        LinkedList<LinkedList<Player>> winners = model.showdown();
        
        view.paintAllBets(model.getPlayers());
        //Solo se muestran las cartas si al menos 2 jugadores llegaron al showdown.
        if(this.model.isShowCards())
            view.paintPlayerHands(this.model.getPlayerHands());
        view.paintWinners(winners);
        
        State t = model.tryNewHand();
        if (t == State.PREFLOP) {
            
            this.preflop();
            for(Player p: this.model.getPlayerLoses())
                view.disablePlayer(p.getSeat());
                view.prepareViewForNextHand(model.getPlayers(), model.getSeats(),
                model.getSumOfPots(), model.getPlayerTurn(), model.getBigBlind(),
                model.getNumberOfHand(), this.botCardsVisible);
            
        } else {
            view.paintLastWinners(winners.getLast());
            view.setTextToTextArea("The game has finished.\n");
        }
    }

    //********************** PLAYER ACTIONS ************************************
    
    
    
    /**
     * Método que realiza el Call o Check de un jugador.
     * @param action Nombre de la acción. Puede ser Call o Check
     */
    public void playerCalls(String action) {
		if(!this.model.getPlayerTurn().isBot())
            this.model.stopTimerBoard();
        Action a = new ActionCall(stringToActions(action));
        Player actualPlayer = model.getPlayerTurn();
        actualPlayer.setAction(a);
        GameState gs = a.executeAction(model, actualPlayer);
        
        
        view.addActionToPlayerList(actualPlayer.getName(), a);
        view.setPlayerBetAndMoney(actualPlayer.getSeat(), actualPlayer.getMoney(), actualPlayer.getBet());
        view.updatePot(model.getSumOfPots());
        view.disableViewForPlayerTurn(actualPlayer);
        if(this.withCap && this.cap == actualPlayer.getTotalBet() && model.isLastToSpeak()){
            gs = GameState.OVER_ALLIN;
        }
        if(gs == GameState.OVER_ALLIN) {
            view.showAllCards(this.model.getPlayersToShowCards());
            untilTheEnd();
        }
        else {
            //stopGame();
            boolean playerTurnAllIn;
            if(this.withCap && this.cap == actualPlayer.getBet()){
                playerTurnAllIn = true;
            }else{
                playerTurnAllIn = false;
            }
            isLastToSpeak(actualPlayer, playerTurnAllIn);
            continueGame();
        }
    }
    
    /**
     * Método que convierte el parámetro de tipo <code>String</code> en un objeto de tipo <code>Actions</code>
     * @param act String que representa la acción.
     */
    private Actions stringToActions(String act) {
        Actions action = null;
        switch(act) {
            case "Call":
                action = Actions.CALL;
                break;
            case "Check":
                action = Actions.CHECK;
                break;
            case "Fold":
                action = Actions.FOLD;
                break;
            case "All-in":
                action = Actions.ALL_IN;
                break;
            case "Bet":
                action = Actions.BET;
                break;
            case "Raise to":
            case "Raise":
                action = Actions.RAISE_TO;
                
        }
        
        return action;
        
        
    }
    
    
    /**
     * <p>Método que realiza el Fold de un jugador.</p>
     * Elimina al jugador de la mano y si era el último en hablar ejecuta el ShowDown.
     */
    public void playerFolds() {
	if(!this.model.getPlayerTurn().isBot())
            this.model.stopTimerBoard();
        Action a = new ActionFold();
        Player actualPlayer = model.getPlayerTurn();
        actualPlayer.setAction(a);
        GameState gs = a.executeAction(model, actualPlayer);
       
        view.addActionToPlayerList(actualPlayer.getName(), a);
        view.setFoldState(actualPlayer); 
         
        switch (gs) {
            case OVER:
                model.removePlayer(actualPlayer);
                executeState(State.SHOWDOWN);
    
                break;
            case OVER_ALLIN:
                view.showAllCards(this.model.getPlayersToShowCards());
                untilTheEnd();  
                break;
            default:            
                isLastToSpeak(actualPlayer, true);
                continueGame();
                break;
        }
    }

    
    
   
    /**
     * <p>Método que realiza el open o raise de un jugador</p>
     * @param raiseBet Cuantía de la apuesta.
     * @param action Nombre de la acción.
     */
    public void playerRaises(int raiseBet, String action) {
        if(!this.model.getPlayerTurn().isBot())
            this.model.stopTimerBoard();
        Action a = new ActionRaise(raiseBet, stringToActions(action));
        Player actualPlayer = model.getPlayerTurn();
        actualPlayer.setAction(a);
        GameState gs = a.executeAction(model, actualPlayer);
       
        view.addActionToPlayerList(actualPlayer.getName(), a);
        view.setPlayerBetAndMoney(actualPlayer.getSeat(), actualPlayer.getMoney(), actualPlayer.getBet());
        view.updatePot(model.getSumOfPots());
        view.disableViewForPlayerTurn(actualPlayer);
        if(this.withCap && this.cap == actualPlayer.getTotalBet() && model.isLastToSpeak()){
            gs = GameState.OVER_ALLIN;
        }
        if(gs == GameState.OVER_ALLIN) {
           view.showAllCards(this.model.getPlayersToShowCards());
           untilTheEnd();
        } else {
            boolean playerTurnAllIn;
            if(this.withCap && this.cap == actualPlayer.getBet()){
                playerTurnAllIn = true;
            }else{
                playerTurnAllIn = (actualPlayer.getMoney() == 0);
            }
            isLastToSpeak(actualPlayer, playerTurnAllIn);
            continueGame();
            
        }
    }
    
    
    /**
     * Método que utiliza un Timer para ir mostrando los restantes estados de una mano hasta el ShowDown.
     * @see State
     */
    private void untilTheEnd(){
        view.hideMenuPanel(true);
        view.hideCounterPanel();
        /***Descomentar para ejecutar con bots sin interfaz gráfica
        State t;
        do{
            t = model.changeState();
            executeState(t);
        }while(t != State.SHOWDOWN);
        /***Comentar para ejecutar con bots sin interfaz gráfica*/
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State t = model.changeState();
                executeState(t);
                if (t == State.SHOWDOWN) timer.stop();
            }
        });
         timer.start();
         /***/
        
    }
    
    /**
     * FILTRAR LA RESPUESTA DEL BOT.
     * Pide la acción al bot y la lleva a cabo.
     * @param bot Información relativa a la partida necesaria para el bot.
     */
    public synchronized void executeBotAction(InfoBot bot) {
        Player p = model.getPlayerTurn();
        Action a;
        try{
            a = checkActionBot(p.askAction(bot), bot);
        }
        catch(Exception e) {
            a = new ActionFold();
            System.out.println("Exception " + e.toString() + " in bot\n");
        }
        
        if(p == model.getPlayerTurn()){
            this.model.stopTimerBoard();
            if(a.getAction() == Actions.CALL || a.getAction() == Actions.CHECK){
                playerCalls(a.getAction().toString());
            }else{
                 if(a.getAction() == Actions.FOLD){
                    playerFolds();
                 }else{
                    playerRaises(a.getBetAction(), a.getAction().toString());
                 }
            }
        }
    }

    /**
     * Método que comprueba si la acción devuelta por el bot se puede llevar acabo dado el estado actual de la partida.
     * @param act Acción que quiere realizar el bot
     * @param info Información relativa a la partida necesaria para el bot.
     * @return Acción que realizará el bot.
     */
    private Action checkActionBot(Action act, InfoBot info) {
        Action a = new ActionFold();
        //El bot podría devolver null como acción
        if(act != null && act.getAction() != null) {
            Player bot = this.model.getPlayerTurn();
            int botBet = act.getBetAction();

            int realBet = bot.bet(botBet);

            //realCap será: El cap - las apuestas del bot en las rondas anteriores.
            int realCap = this.cap - bot.getTotalBet();
            //La apuesta mínima que puede realizar el bot.
            int minimumBet = this.model.getHighBet() + this.model.getBigBlind();
            //Ya que es RaiseTo se suma lo que ya ha apostado en esta ronda.
            int minimumRaise = this.model.getHighBet() + this.model.getBigBlind();
            if(this.withCap) {
                if(minimumBet > realCap) {
                    minimumBet = realCap;
                    minimumRaise = realCap + bot.getBet();
                }
                if(botBet > realCap)
                        botBet = realCap;
                    if(realBet > realCap)
                        realBet = realCap;
            }


            //Si no se había realizado ninguna apuesta
            if(this.model.getHighBet() == 0) {

            //Si la acción es un Call lo transformamos en un check.
                if(act.getAction() == Actions.CALL || act.getAction() == Actions.CHECK)
                    a = new ActionCall(Actions.CHECK);
                else if(act.getAction() == Actions.RAISE_TO || act.getAction() == Actions.BET) {

                        if(botBet < minimumBet)
                            botBet = minimumBet;
                        if(botBet >= info.getBot().getMoney())
                            a = new ActionRaise(realBet, Actions.ALL_IN);
                        else 
                            a = new ActionRaise(botBet, Actions.BET);

                }   
                //Si el nombre de la acción es All-in apostará todo el dinero
                else if(act.getAction() == Actions.ALL_IN) 
                    a = new ActionRaise(bot.getMoney(), Actions.ALL_IN);
            }
            //Si el nombre de la acción es All-in hará un Raise To de la suma de su dinero y lo que ya había apostado en la ronda.
            else if(act.getAction() == Actions.ALL_IN) {
                    int allin = bot.getMoney() + bot.getBet();
                    //Si hay cap y quiere hacer allin, lo que hará será un raise to CAP
                    if(this.withCap && allin > realCap)
                        a = new ActionRaise(realCap + bot.getBet(), Actions.RAISE_TO);
                    else
                        a = new ActionRaise(allin, Actions.ALL_IN);

                }
            //Si la apuesta del bot no llega al mínimo entonces hará Call
            else if(act.getAction() != Actions.FOLD && act.getBetAction() < minimumRaise){
                a = new ActionCall(Actions.CALL);
            }
            //Si ya había alguna apuesta
            else {

                //Si la acción es Call, este será de las mismas fichas que la apuesta más alta
                //Si ya había puesto dinero en esta ronda se resta.
                if(act.getAction() == Actions.CALL){
                    a = act;
                }
                //Si la acción del bot es un raise
                else if(act.getAction() == Actions.RAISE_TO || act.getAction() == Actions.BET) {

                    if(botBet < minimumRaise)
                        botBet = minimumRaise;
                    if(botBet >= info.getBot().getMoney() + bot.getBet())
                        a = new ActionRaise(realBet + bot.getBet(), Actions.ALL_IN);
                    else 
                        a = new ActionRaise(botBet , Actions.RAISE_TO);
                }


            }
        }

        return a;
    } 

        
    
    /**
     * Se encarga de continuar el juego.
     * Crea la información necesaria para un bot y se la manda para que realice su acción.
     */
    public void continueGame() {
        if (!this.model.isFinished()){
            updateViewForNextTurn();
            view.repaintAll();
            InfoBot currentInfo = null;
        if(this.model.getPlayerTurn().isBot())
            currentInfo = this.model.createInfoBot();        
        CounterPanel.initalizeCounter();
        if(!this.model.running())
            this.model.initTimerBoard(); 
        this.model.getPlayerTurn().getPlayerMode().start(this, currentInfo);
        }
        
    }
    
    /**
     * Método que llama a la vista para que se actualice en consecuencia del cambio de turno del jugador.
     */
    private void updateViewForNextTurn() {
        boolean cardsVisible = true;
        if(model.getPlayerTurn().isBot()){
            cardsVisible = this.botCardsVisible;
        }
        view.updateViewForPlayerTurn(model.getPlayerTurn(), model.getHighBet(),
            model.getBigBlind(), cardsVisible);
    }
    
    /**
     * Método que devuelve True si las cartas de los bots serán visibles durante la partida.
     */
    public boolean getBotCardsVisible() {
        return this.botCardsVisible;
    }
    
    /**
     * Método que actualiza el contador en la vista.
     */
    public void updateCounter(){
        CounterPanel.decrCounter();
    }
}