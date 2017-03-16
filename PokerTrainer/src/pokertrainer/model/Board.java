/*
 * Copyright (c) 2017, Aarón Durán,Javier López, Mateo García, Carlos López 
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import pokertrainer.controller.GameController;


/**
 * Esta clase define la lógica principal de una partida.
 * @author usuario_local
 */
public class Board {

    private LinkedList<Card> deck;
    static final int CARDS_HAND = 5;
    static final int NUMBER_CARDS = 52;
    static final int BIG_BLIND = 10;
    static final int SMALL_BLIND = 5;
	
    private int numCardsDeck;
    private int numPlayers;
    private LinkedList<Player> players;
    private LinkedList<Player> handPlayers;
  
    private LinkedList<Card> boardCards;
    ///
    private LinkedList<Player> playerLoses;
    ///
    private int[] seats;
    private int turn;
    private final int bigBlind;
    private final int smallBlind;
    private int hand;
    private State state;
    private LinkedList<Pot> potList;
    
    private LinkedList<Card> burntCards;

    private int posBigBlind;
    private int posSmallBlind;
    
    private int maxBet;
    //Indica el asiento del ultimo en hacer raise
    private int lastToSpeak;
    private GameController c;
    private boolean withCap;
    private int cap;
    private Timer timer;
    
    
    //Disposición de los asientos segun el numero de jugadores
    public static final int[] TWO = {0,4}; 
    public static final int[] THREE = {1,4,7}; 
    public static final int[] FOUR = {1,3,5,7}; 
    public static final int[] FIVE = {1,3,4,5,7}; 
    public static final int[] SIX = {0,1,3,4,5,7}; 
    public static final int[] SEVEN = {0,1,2,3,4,5,6}; 
    public static final int[] EIGHT = {0,1,2,3,4,5,6,7}; 
    public static final int[] NINE = {0,1,2,3,4,5,6,7,8}; 
	
	
    public static final int MONEY = 1000;
    private boolean finished = false;
    //Jugadores que en la anterior mano, sus cartas fueron visibles porque llegaron al showDown
    //Sirve para saber qué cartas decirles a los bots que fueron visibles
    private LinkedList<LastWinnerPlayers> playersWithVisibleHand;
    
    //Booleano que indica si al menos 2 jugadores llegaron al showdown y se deben mostrar sus cartas.
    private boolean showCards;

    
    
    /**
     * 
     * Este es el constructor de la clase Board.
     * @param customPlayers Lista de tipo y nombre de jugadores que jugarán la partida.
     *  @param withCap Si está a True se ejecuta el juego con el cap establecido en el parámetro cap
     * @param cap Límite superior que se puede apostar en una mano
     */
    public Board(LinkedList<CustomPlayer> customPlayers, boolean withCap, int cap){
        this.numCardsDeck = NUMBER_CARDS;
        this.bigBlind = BIG_BLIND;
        this.smallBlind = SMALL_BLIND;
        this.burntCards = new LinkedList<>();
        this.state = State.PREFLOP;
        this.turn = 0;
        this.hand = 0;
        this.posSmallBlind = 0;
        this.posBigBlind = 1;
        this.withCap = withCap;
        this.cap = cap;
        this.boardCards = new LinkedList<>();
        this.playerLoses = new LinkedList<>();
        this.playersWithVisibleHand = new LinkedList<>();
        generateDeck();
        initializePlayers(customPlayers);   
    }
    
    /**
     * Método que genera la baraja de poker como una lista de 52 cartas.
     */
    private void generateDeck() {
        this.deck = new LinkedList<>();
       
        for (Value val: Value.values()) 
            deck.push(new Card(Suit.HEARTS, val));
        
        for (Value val: Value.values()) 
            deck.push(new Card(Suit.DIAMONDS, val));
        
        for (Value val: Value.values()) 
            deck.push(new Card(Suit.CLUBS, val));
        
        for (Value val: Value.values()) 
            deck.push(new Card(Suit.SPADES, val)); 
        
    }

    /**
     * Establece el controlador de la partida
     * @param gameController Objeto <code>GameController</code>
     */
    public void setControl(GameController gameController) {
       this.c = gameController;
    }
    /**
     * Devuelve la cantidad de manos jugadas durante la partida.
     */
    public int getNumberOfHand() {
        return hand;
    }
    
    
    /**
     * Método que inicializa los atributos de los jugadores, les asigna su asiento, los añade a la lista de jugadores de la partida e inicia el preflop.
     * @param customPlayers Lista de jugadores con su información de customización.
     */
    private void initializePlayers(LinkedList<CustomPlayer> customPlayers) {
        this.players = new LinkedList<>();
        this.numPlayers = customPlayers.size();
        this.seats = getSeatsForPlayers(numPlayers);
        for (int i = 0; i < this.numPlayers; i++) {
            Player p = createPlayer(customPlayers.get(i), seats[i], i);
            this.players.add(p);
        }
        preflop();
    } 
    

    /**
     * Crea un objeto de tipo <code>InfoBot</code> con la información que el bot necesita para jugar.
     * @return Objeto <code>InfoBot</code> creado
     */
    public InfoBot createInfoBot() {
        //Información de los jugadores de la mano para el bot.
        LinkedList<PlayerInfo> infoPlayers = new LinkedList<>();
        
        Player pBot = this.getPlayerTurn();
        InfoBot info = null;
        if(pBot != null) {
            //Clase con la información concreta del bot.
            PlayerInfo bot = null;
            HandPlayer h = null;
            for(Player p: this.handPlayers) {

                //Si la jugada de este jugador fue visible en la anterior ronda se la paso al bot.

                int i = 0;
                boolean found = false;
                while(!found && i < this.playersWithVisibleHand.size()) {
                    if(this.playersWithVisibleHand.get(i).getPlayerName().equals(p.getName())) {
                        found = true;
                        h = p.getHand();
                    }

                    i++;
                }
                if(this.getPlayerTurn().equals(p)) 
                    pBot = p;
                infoPlayers.add(new PlayerInfo(p.role, p.money, p.action, p.totalBet, h));
            }

                bot = new PlayerInfo(pBot.role, pBot.money, pBot.action, pBot.totalBet, h);

            int max = 0;
            int totalPot = 0;
            for(Pot p: this.potList) {
                max += p.getBet();
                totalPot += p.getTotalPot();
            }

            info = new InfoBot(infoPlayers,bot , this.bigBlind, this.smallBlind, this.boardCards,
                                        pBot.getCards(), max, totalPot,this.state, this.cap);
        }
        else {
            info = new InfoBot();
        }
        
        return info;
    }
    
    /**
     * Método que devuelve la lista de jugadores que han llegado hasta el final de la mano
     * @return Lista de jugadores que han llegado hasta el final de la mano 
     */
    public LinkedList<Player> getPlayerHands() {
        this.playersWithVisibleHand.clear();
        for(Player p: this.potList.getFirst().getPlayers())
            this.playersWithVisibleHand.add(new LastWinnerPlayers(p.getName(), p.getHand()));
        return this.potList.getFirst().getPlayers();
    }
    
   
    
    /**
     * Método que crea un jugador del tipo correcto (Humano o Bot), le asigna un asiento y su posición en la lista de participantes en la partida.
     * @param p Información de customización a partir de la que se creará el jugador de la partida.
     * @param seat Asiento que se asignará al jugador.
     * @param n Posición en la lista de participantes de la partida.
     * @return Jugador creado.
     */
    private Player createPlayer(CustomPlayer p, int seat, int n){
        Player player = null;
        
        String name = p.getName();
        int stack = p.getStack();
        
        if (p.getMode().equals("HUMAN")) {
            player = new PlayerHuman(name, stack, seat, n);
            
        } else {
            try {
                //Reflection con p.NameBot
                Class bot = Class.forName(PokerTrainer.BOTSPACKAGE + p.getNameClass());
                BotInterface intelligence = (BotInterface) bot.newInstance();
                player = new PlayerBot(name, stack, seat, n, intelligence);        
            } catch (Exception ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
        return player;
    }
  
    
    /**
     * Método que inicializa los atributos de juego para una nueva mano (bote,cartas, roles de los jugadores y máxima apuesta realizada).
     */
    private void initializeGameForNewHand() {
        //Inicializa el array con el orden en el que hablan
        initializeHandPlayers();
        
        this.boardCards = new LinkedList<>();
        
        this.potList = new LinkedList<>();
        
        this.potList.add(new Pot(0, 0, new LinkedList<>(), new LinkedList<>()));
        if(this.players.size() > posSmallBlind)
            this.players.get(posSmallBlind).setRole(Role.SMALL_BLIND);
        if(this.players.size() > posBigBlind)
            this.players.get(posBigBlind).setRole(Role.BIG_BLIND);
        initializeRole();
        this.lastToSpeak = -1;
        this.maxBet = BIG_BLIND;
    }
    
    /**
     * Método que inicializa el rol para cada jugador
     */
    private void initializeRole(){
        for(int i = 1; i <this.numPlayers - 1; i++){
            this.players.get((posBigBlind + i) % numPlayers).
                    setRole(Role.values()[(Role.BIG_BLIND.ordinal() + i) % Role.values().length ]);
        }
        
    }
        
    //metodo que realiza el preflop
    /**
     * <p>Inicia el preflop de la mano</p>
     * <p>Reparte 2 cartas a cada jugador, y hace que se apuesten las ciegas.</p>
     * 
     */
    public void preflop(){
        
        initializeGameForNewHand();
        
        //Se reparten 2 cartas a cada jugador
        for (Player p: players) {
            LinkedList<Card> cards = deal(2);
            this.burntCards.addAll(cards);
            p.setCards(cards);
        }
        
        //Apuestan las ciegas
        blindsBet();
    }
    
    /**
     * <p>Inicia el flop de la mano.</p>
     * <p>Establece el orden en el que hablarán los jugadores, quema una carta y añade 3 cartas a la mesa</p>
     * @return Lista de cartas de la mesa
     */
    public LinkedList<Card> flop() {
        updateHandPlayers();
        //quemamos
        burntCards.addAll(deal(1));
        //realizamos el flop
        this.boardCards = deal(3);
        return this.boardCards;
    }

    
    
    /**
     * <p>Inicia el turn de la mano.</p>
     * <p>Quema una carta y añade la 4º carta de la mesa</p>
     * @return Última carta añadida a la mesa
     */
    public Card turn() {
        //quemamos
        burntCards.addAll(deal(1));
        //realizamos el turn
        LinkedList<Card> turnCard = deal(1);
        Card c = turnCard.getFirst();
        boardCards.add(3,c);
        return turnCard.peekFirst();
    }
    
    /**
     * <p>Inicia el river de la mano.</p>
     * <p>Quema una carta y añade la 5º carta de la mesa</p>
     * @return Última carta añadida a la mesa
     */
    public Card river() {
        //quemamos
        burntCards.addAll(deal(1));
        //realizamos el river
        LinkedList<Card> riverCard = deal(1);
        Card c = riverCard.getFirst();
        boardCards.add(4,c);
        return riverCard.peekFirst();
    }
    
    /**
     * Reparte el dinero de la mano a los ganadores y se finaliza esta
     * @return Lista de botes con una lista de los ganadores de cada bote
     */
    public LinkedList<LinkedList<Player>> showdown() {
        this.finished = true;
        
        return sharingPots();
    }
    
    /**
     * Método que hace a los dos jugadores correspondientes apostar las ciegas en cada mano.
     */
    private void blindsBet() {
        Player smPlayer = this.players.get(this.posSmallBlind);
        Player bbPlayer = this.players.get(this.posBigBlind);
        refreshBet(smPlayer, this.smallBlind);
        refreshBet(bbPlayer, this.bigBlind);
    }
    

    
    //Le paso el dinero que hay que poner y calcula el que debe poner este jugador en concreto
    /**
     * <p>Procesa la apuesta de un jugador.</p> 
     * Actualiza los botes correspondientes según la apuesta y la información del jugador que la realiza.
     * @param player Jugador que realiza la apuesta
     * @param bet Cantidad que apuesta el jugador
     */
    public void refreshBet(Player player, int bet){
        int b = player.bet(bet);
        
  
        int bAux = b;
        //Lo que un jugador ya ha apostado aunque le hagan Re-Raise no debe volver a meterlo en
        //el bote.
        int discount = player.getTotalBet();
        int i = 0;
        
        while(i < this.potList.size() && b > 0) {
            Pot pot = this.potList.get(i);
            
            int auxBet = pot.getBet();
       
            if(pot.getPlayers().contains(player)) {
                //Estas haciendo un raise en un bote que ya habías jugado
                if((b + discount) > auxBet && (this.potList.size() - 1 == i)) {
                    pot.removePlayersForRaise();
                    pot.addPlayers(player);
                    pot.setBet(b+discount);
                    pot.addTotalPot(b);
                    
                    player.setBetLastPot(b + discount);
                }
                discount -= pot.getBet();
            }
            else {
                //Estas haciendo un raise en un bote que aún no había jugado
                if((b + discount) > auxBet && (this.potList.size() - 1 == i)) {
                    
                    //Comprobamos si había algún jugador en allin
                    boolean plaAllIn = false;
                    //Si el jugador hace un raise y en ese bote había algún jugador/es que:
                    int j = 0; 
                    while(j < pot.getPlayers().size() && !plaAllIn){
                         //estaba allin hay que dividir el bote para este jugador/es
                        if(pot.getPlayers().get(j).getMoney() == 0)
                            plaAllIn = true;
                        
                        j++;
                    }
                    //Si había jugadores en allin dividimos el bote para estos
                    if(plaAllIn) {

                        int newP = (b + discount) - auxBet;
                        //Y ahora creamos un nuevo bote para el jugador del raise
                        //Este nuevo bote será de la apuesta del jugador menos lo que ha aportado al anterior bote
                        
                        LinkedList<Player> play = new LinkedList<>();
                        LinkedList<Player> outPla = new LinkedList<>();
                        play.add(player);
                        Pot p = new Pot(newP, newP, play,outPla);
                        this.potList.add(i+1, p);
                        
                        
                        //Lo que hay que hacer es añadir el dinero que aporta el jugador que ha hecho el raise
                        //al bote antiguo
                        //¿Y SI YA HABÍA APORTADO DINERO A ESE BOTE?
                        int betL = b;
                        
                        if(auxBet >= discount) {
                            pot.addTotalPot(auxBet - discount);
                            betL -= (auxBet - discount);
                            discount = 0;
                        }
                        else {
                            discount -= auxBet;
                            
                        }
                        
                        //Añadimos al jugador del raise en el anterior bote
                        pot.addPlayers(player);
                        player.setBetLastPot(betL);

                        b = 0;
                        
                    }
                    else {
                        pot.setBet(b+discount);
                        pot.addTotalPot(b); 
                        
                        //Si hace un raise esta será su apuesta en el último bote que jugó
                        player.setBetLastPot(b + discount);
                        pot.removePlayersForRaise();
                        pot.addPlayers(player);
                    }
                    
                    
                    
                }
                //Si el jugador tiene suficiente dinero para jugar este bote
                //Sumamos lo que el jugador ya había puesto(por si re-raise)
                else if((b + discount) >= auxBet) {
                    //Si la apuesta es igual a la bet de este bote será el últim bote que juegue
                    if(b + discount == auxBet)
                        player.setBetLastPot(b + discount);
                    if(discount == 0){    
                        pot.addTotalPot(auxBet);
                        pot.addPlayers(player);
                        b -= auxBet;
                    }else{
                        int currentD = auxBet - discount;
                       
                            pot.addTotalPot(currentD);
                            b = b - currentD;
                            if(auxBet >= discount)
                                discount = 0;
                        
                        pot.addPlayers(player);
                    }
                    
                }
                //En el caso de que no tengas dinero suficiente para el último de los botes no es necesario
                //calcular cuanto pusiste en el último bote ya que esto solo sirve para el caso en el que te hacen un raise
                //y los raise solo se pueden realizar al último bote.
                else {
                    
                    //Lo que hay que tratar con cuidado es como repartir el dinero de los botes teniendo en cuenta que puede haber dinero
                    //de un jugador en un bote el cual(EL JUGADOR) no esté ya en el bote.
                    
                    //DINERO PARA EL NUEVO BOTE
                    //Los jugadores que aparecían como participantes pondrán en el nuevo bote el dinero que tenga 
                    //este jugador que está haciendo que se parta el bote 
                    int newPot = (b + discount) * pot.getPlayers().size();
                    //Para calcular el dinero de otros jugadores debo comprobar cuanto pusieron en su último bote
                    //los jugadores que fueron eliminados de este bote por un raise
                    LinkedList<Player> playerForNewpot = new LinkedList<>();
                    //Lista de jugadores que aparecerán como participantes en el nuevo bote.
                    LinkedList<Player> pla = new LinkedList<>();
                    
                    //No puedo eliminar directamente del array outForRaise
                    LinkedList<Player> playerForDelete = new LinkedList<>();
                    for(Player py: pot.getPlayersOutForRaise()) {
                        //Si el dinero que puso el jugador es menor o igual que el que hay que poner en este nuevo bote
                        if(py.getBetLastPot() <= (b + discount)){
                            //Su dinero se meterá íntegramente en este nuevo bote y 
                            
                            newPot += py.getBetLastPot();
                            //ahora aparecerá en la lista de jugadores que han aportado dinero a este bote aunque no aparecen como participantes.
                            if(player != py)
                                playerForNewpot.add(py);
                            //Y desaparecerá de la lista del anterior bote(ya que ya no aporta dinero en ese bote).
                            
                            playerForDelete.add(py);
                        }
                        //El caso en el que el dinero del jugador se deba repartir entre el nuevo bote y el antiguo
                        else{
                            //Lo equivalente a la apuesta de este nuevo bote se meterá en este.
                            newPot += (b + discount);
                            //Reducimos el dinero que aporta al anterior bote.
                            py.setBetLastPot(py.getBetLastPot() - (b + discount));
                            //Podemos introducirlo como participante del bote (el algoritmo "arreglará" ya que el jugador tendrá que volver a hablar).
                            //Este jugador no puede estar allin porque si te hacen un raise a un allin se parte en dos botes.
                            pla.add(py);

                            
                        }
                    }
                    
                    if(!playerForDelete.isEmpty())
                        for(Player p: playerForDelete)
                            pot.delPlayerOut(p);

                    pla.addAll(pot.getPlayers());
                    pla.add(player);
                    //Número de jugadores que jugaban el anterior bote.
                    int numP = pot.getPlayers().size();
                    
                    Pot p = new Pot(newPot + b,(b + discount),pla,playerForNewpot);
                    this.potList.add(i, p);
                    //Ahora modifico el bote que antes ocupaba la posicion i.
                    this.potList.get(i+1).subTotalPot((b + discount) * numP);
                    this.potList.get(i+1).subBet(b + discount);
                    b = 0;
                    
                }
            }
            i++;
        }
        
        player.updatePlayer(bAux);
    }
    
    
    /**
     * Devuelve el siguiente estado de la partida
     * @see State
     * 
     */
    public State changeState() {
        int n = (this.state.ordinal() + 1 )%6; //% 6
        State t = State.values()[n];
        this.state = t;
       
        this.turn = 0;
        this.lastToSpeak = -1;
        this.maxBet = 0;
        
        
        for (Player p: this.handPlayers) {
            p.setBet(0);
        }
        
        return this.state;
    }
    
    /**
     * 
     * Devuelve la cantidad de jugadores que siguen jugando la mano 
     */
    public int getHandPlayersSize() {
        return this.handPlayers.size();
    }
    /**
     * Elimina a un jugador de todos los botes de la mano
     * @param p Jugador a eliminar
     */
    public void removePlayerFromPots(Player p){
        for (Pot pot: this.potList) {
            pot.getPlayers().remove(p);
        }
    }
    
    /**
     * Devuelve la apuesta total realizada por el próximo jugador en hablar
     * 
     */
    public int getNextPlayerTotalBet(){
        return handPlayers.get(((this.turn+1)%getHandPlayersSize())).getTotalBet();
    }
    /**
     * Actualiza la apuesta máxima de la mano
     * @param bet Nueva apuesta máxima
     */
    public void setHighBet(int bet) {
        this.maxBet = bet;
    }
    
    //Establece como último en hablar al jugador anterior al del turno actual
    /**
     * Actualiza quien será el último jugador en hablar
     */
    public void updateLastToSpeak() {
        int t = (this.turn - 1)%handPlayers.size();
        if(t == -1)
           t = this.handPlayers.size() - 1;
        this.lastToSpeak = this.handPlayers.get(t).getNumPlayer();
    }
           
    
    //Elimina un jugador de handPlayers, es decir, ese jugador no va a hablar mas
    //eso ocurre cuando hace all-in o hace fold
    /**
     * <p>Elimina un jugador de la lista de jugadores de la mano. Ocurre cuando un jugador hace:</p>
     * <ul><li>All-in (Se mantendrá como participante en los correspondientes botes)</li> <li>Fold</li></ul>
     * @param p Jugador a eliminar
     */
    public void removePlayer(Player p) {
        
        handPlayers.remove(p);
        
        if(this.handPlayers.size() >0 )
             this.turn %= this.handPlayers.size();
    }
    
    /**
     * Devuelve un <code>GameState</code> informando de la situación actual de la mano
     *  
     */
    public GameState updateCurrentState() {
        GameState state = GameState.CONTINUE;
        //Si solo quedaba yo por hablar y el único que juega algún bote soy yo
        if(this.potList.get(0).getPlayers().size() == 1)
           state = GameState.OVER;
        //Si solo quedaba yo por hablar y en el primer bote hay varios jugadores
        else if(this.potList.get(0).getPlayers().size() > 1)
            state = GameState.OVER_ALLIN;
        
        return state;
    }
    
    
    
    
    /**
     * Método que inicializa la lista de jugadores que participan en esta mano en el orden en que hablarán en Preflop.
     */
     private void initializeHandPlayers() {
        this.handPlayers = new LinkedList<>();
        int i = this.posBigBlind + 1;
        i %= this.numPlayers;
        int j = 0;
        while (j < this.numPlayers) {
            handPlayers.add(this.players.get(i));
            
            //El array de jugadores es circular
            i++;
            i %= this.numPlayers;
            
            j++;
        }
        
        //TotalBet a 0, betLastPot a 0, bet a 0
        for (Player p: handPlayers)
            p.initializePlayer();
    }

     
    
     /**
      * Método que introduce las cartas quemadas en la baraja de nuevo.
      */
    private void reinsertCardsToDeck(){
        this.deck.addAll(this.burntCards);
        this.deck.addAll(this.boardCards);
    }
    
    

    /**
     * Devuelve los asientos donde se sentarán los jugadores en función del número de jugadores
     * @param numPlayers Cantidad de jugadores que van a jugar la partida
     * @return Array con las posiciones de los jugadores
     */
    public static int[] getSeatsForPlayers(int numPlayers) {
        int[] seatsForPlayers = null;
        switch (numPlayers) {
            case 2: seatsForPlayers = TWO; break;
            case 3: seatsForPlayers = THREE; break;
            case 4: seatsForPlayers = FOUR; break;
            case 5: seatsForPlayers = FIVE; break;
            case 6: seatsForPlayers = SIX; break;
            case 7: seatsForPlayers = SEVEN; break;
            case 8: seatsForPlayers = EIGHT; break;
            case 9: seatsForPlayers = NINE; break;
            default: break;
        }
        return seatsForPlayers;
    }

    /**
     * 
     * Devuelve los jugadores de la partida 
     */
    public LinkedList<Player> getPlayers() {
        return this.players;
    }

    
    /**
     * Devuelve el jugador al que le toca hablar
     */
    public Player getPlayerTurn() {
        return this.handPlayers.get(turn);
    }
    
    
    /**
     * Devuelve la apuesta máxima de la mano
     */
    public int getHighBet() {
        return this.maxBet;
    }
    
    /**
     * Devuelve la ciega grande
     */
    public int getBigBlind() {
        return bigBlind;
    }
    /**
     * Devuelve la ciega pequeña
     */
    public int getSmallBlind() {
        return smallBlind;
    }

    
    /**
     * Devuelve la cantidad total acumulada en todos los botes de una mano
     */
    public int getSumOfPots(){
        int sumPots = 0;
        for (Pot p : this.potList) {
            sumPots += p.getTotalPot();
        }
        return sumPots;
    }
    
    /**
     * Devuelve los asientos de los jugadores
     */
    public int[] getSeats() {
        return this.seats;
    }
    
   
    /**
     * Método que reparte el número de cartas recibido por parámetro de manera aleatoria.
     * @param numCards Número de cartas a repartir.
     * @return Lista de cartas repartidas.
     */
    private LinkedList<Card> deal(int numCards) {
        LinkedList<Card> cards = new LinkedList<>();
        Random rndGenerated = new Random();
        for(int i = 0; i < numCards; i++){
            int rnd = rndGenerated.nextInt(numCardsDeck-1);
            cards.push(deck.get(rnd));
            numCardsDeck--;
            deck.remove(rnd);
        }
        return cards;
    }
   

    
    
    /**
     * Método que modifica el orden de los jugadores que participan en una mano para jugar el Flop.
     */
    private void updateHandPlayers() {
        LinkedList<Player> oldHandPlayers = this.handPlayers;
        LinkedList<Player> newHandPlayers = new LinkedList<>();
        
        int i = this.posSmallBlind;
        
        do {
            if (oldHandPlayers.contains(this.players.get(i))) {
                newHandPlayers.addLast(this.players.get(i));
            }
            i++;
            i %= this.players.size();
            
        } while (i != this.posSmallBlind);
        
        this.handPlayers = newHandPlayers;
        
    }
    
    /**
     * Método que devuelve si al menos 2 jugadores llegaron al showdown y por lo tanto se deben mostrar sus cartas.
     */
    public boolean isShowCards() {
        return showCards;
    }
    
    /**
     * Método que recorre la lista de botes de una mano, obtiene los ganadores de cada bote y reparte las fichas de los botes.
     * @return Lista de botes que cada posición mantiene una lista de jugadores que ganaron ese bote.
     */
    private LinkedList<LinkedList<Player>> sharingPots() {
        LinkedList<LinkedList<Player>> winnersPerPot = new LinkedList<>();
        
        if(this.potList.getFirst().getPlayers().size() > 1)
            this.showCards = true;
        else
            this.showCards = false;
        
        
        int i=0;
        for(Pot pot: this.potList){
            LinkedList<Player> potWinners = new LinkedList<Player>();
            //Si en un bote solo hay un jugador él será el ganador de ese bote.
            if(pot.getPlayers().size() == 1)
                potWinners.add(pot.getPlayers().getFirst());
            else
                potWinners = getWinners(pot.getPlayers(), i);
            
            
            
            int cashPerPlayer = pot.getTotalPot()/potWinners.size();
            for(Player p: potWinners){
                p.incrMoney(cashPerPlayer);
                
                
               
            }
            winnersPerPot.addLast(potWinners);
            i++;
        
        
        }
       return winnersPerPot;
    }
    
    
    
      
    /**
     * Método que obtiene los ganadores de un determinado bote.
     * @param playersPot Jugadores que participaron en ese bote.
     * @param numPot Posición del bote.
     * @return Lista de ganadores de este bote.
     */
    private LinkedList<Player> getWinners(LinkedList<Player> playersPot, int numPot){
       
       if(numPot == 0)
        for (Player p: playersPot) {
             LinkedList<Card> hand = p.getCards();   
             hand.addAll(this.boardCards);  
             //Obtenemos la jugada del jugador
             p.setHand(getHand(sortHand(hand))); 
         }
        
        
        return whoWin(playersPot);
    }
    
    
    /**
     * Método que incrementa el stack de un jugador.
     * @param incrMoney Cantidad de fichas que aumentar.
     * @param p Jugador al que aumentar el stack.
     */
    private void incrPlayerPot(int incrMoney, Player p) {
        p.incrMoney(incrMoney);
    }
    
    
    
    
    //Devuelve si la partida ha acabado
    /**
     * <p>Prepara una nueva mano si la partida no ha acabado</p>
     * 
     * @return Objeto State que indica si la partida ha terminado
     */
    public State tryNewHand() {
        
        reinsertCardsToDeck();
        this.state = State.GAME_FINISHED;
        this.finished = false;
        this.burntCards = new LinkedList<>();
        this.numCardsDeck = NUMBER_CARDS;
        
       ListIterator<Player> it = this.players.listIterator();
        while(it.hasNext()){
            Player p = it.next();
            if (p.getMoney() == 0) {
                this.playerLoses.add(p);
                it.remove();
                this.numPlayers--;
            }
        }

        
        //Si hay mas de 2 jugadores en el array
        if (this.numPlayers >= 2) {
            
            
            
            //Se incrementa la mano y se cambia el estado a preflop
            this.hand++;
            
            posSmallBlind = (posSmallBlind + 1 )%this.numPlayers;
            posBigBlind = (posBigBlind + 1)%this.numPlayers;
            this.state = State.PREFLOP;
            
            for (Player p: players) {
                p.setBet(0);
            }
            
        }
        
        return this.state;
        
    }
    
    
    /**
     * Cambia el turno del jugador al que le toca hablar
     */
    public void changeTurn() {
        this.turn++;
        this.turn %= this.handPlayers.size();
    }
    
    /**
     * Devuelve si un jugador es el útimo en hablar 
     */
    public boolean isLastToSpeak() {
        return ((lastToSpeak == -1 && handPlayers.getLast() == getPlayerTurn()) 
                || (lastToSpeak != -1 && handPlayers.get((this.turn)).getNumPlayer() == lastToSpeak));
    }
    
    /**
     * Devuelve si la partida ha terminado
     */
    public boolean isFinished() {
        return this.finished;
    }

    /**
     * Devuelve la lista de jugadores que ya no jugarán en la partida
     */
    public LinkedList<Player> getPlayerLoses() {
        return playerLoses;
    }
    
    /**
     * Devuelve la lista de jugadores que han participado en una mano para mostrar sus cartas.
     */
    public LinkedList<Player> getPlayersToShowCards(){
        return this.potList.get(0).getPlayers();
    }
    
    
    //Devuelve un LinkedList de Players para el caso que haya empate
    /**
     * Método que dada una lista de jugadores analiza sus jugadas y devuelve el ganador (o ganadores en caso de empate).
     * @param boardPlayers Jugadores que participan
     * @return Lista de ganadores.
     */
    private LinkedList<Player> whoWin(LinkedList<Player> boardPlayers) {
        LinkedList<Player> winners = new LinkedList<>();
        winners.push(boardPlayers.getFirst());
        
        for(int i = 1; i < boardPlayers.size(); i++) {
            HandComparator comp = compareTwoHands(winners.getFirst().getHand(),boardPlayers.get(i).getHand());
            if(comp == HandComparator.SECOND) {
                winners.clear();
                winners.push(boardPlayers.get(i));
                
            }
            else if(comp == HandComparator.DRAW) {
                winners.push(boardPlayers.get(i));
            }
            
            
        }
        
        
        return winners;
    }
    
    
    //Devuelve el ganador de estas dos manos (o empate).
    /**
     * Método que recibe 2 jugadas y devuelve cuál de las dos es mejor (o si son iguales).
     * @param h1 Jugada del primer jugador
     * @param h2 Jugada del segundo juagdor
     * @return Enumerado que indica si la primera jugada es mejor, si lo es la segunda o si han empatado.
     */
    private HandComparator compareTwoHands(HandPlayer h1, HandPlayer h2) {
        HandComparator winner = HandComparator.DRAW;
        
        
        if(h1.getHand().ordinal() > h2.getHand().ordinal())
            winner = HandComparator.FIRST;
        else if(h1.getHand().ordinal() < h2.getHand().ordinal())
            winner = HandComparator.SECOND;
        
        /////////////EMPATE EN LAS JUGADAS/////////////////////////////
        else {
            //Si la jugada tiene main Value
            if(h1.getMainV() != null && h2.getMainV() != null) {
                if(h1.getMainV().ordinal() > h2.getMainV().ordinal())
                    winner = HandComparator.FIRST;
                //Si el main Value del segundo es mejor se acaba
                else if(h1.getMainV().ordinal() < h2.getMainV().ordinal())
                    winner = HandComparator.SECOND;
                /////////////EMPATE EN EL MAIN VALUE/////////////////////////////
                
                else  { //2 casos: Si tiene secondValue y si no tiene secondValue
                    
                    //1 - Si tiene SecondValue
                    if(h1.getSecondV() != null && h2.getSecondV() != null) {
                        if(h1.getSecondV().ordinal() > h2.getSecondV().ordinal())
                            winner = HandComparator.FIRST;
                        else if(h1.getSecondV().ordinal() < h2.getSecondV().ordinal())
                            winner = HandComparator.SECOND;
                        //////////////EMPATE EN EL SECOND VALUE////////////////////////
                        //En el caso de que tenga single cards las comparamos
                        else if(h1.getCards() != null && h2.getCards() != null){
                            //Comparamos las singleCards y estas decidirán al ganador (o el empate).
                            winner = compareSingleCards(h1.getCards(), h2.getCards());    
                        }
                    }
                    //2 - NO tiene SecondValue
                    else {
                        //En el caso de que tenga single cards las comparamos
                        if(h1.getCards() != null && h2.getCards() != null){
                            //Comparamos las singleCards y estas decidirán al ganador (o el empate).
                            winner = compareSingleCards(h1.getCards(), h2.getCards()); 
                    
                        }
                    
                    }
                }
            }
            else {
                //Si la jugada no tiene mainValue
                //En el caso de que tenga single cards las comparamos
                if(h1.getCards() != null && h2.getCards() != null){
                    //Comparamos las singleCards y estas decidirán al ganador (o el empate).
                    winner = compareSingleCards(h1.getCards(), h2.getCards()); 
                    } 
                }
        }
        
        
        return winner;
    }
    
    /**
     * Método que compara el valor de dos listas de cartas y devuelve qué valor es mayor.
     * @param p1 Lista de cartas sueltas del primer jugador.
     * @param p2 lista de cartas sueltas del segundo jugador.
     * @return Enumerado que indica si la primera lista es mejor, si lo es la segunda o si han empatado.
     */
    private HandComparator compareSingleCards(LinkedList<Value> p1, LinkedList<Value> p2) {
        HandComparator winner = HandComparator.DRAW;
        int i = 0;
        
        while(winner == HandComparator.DRAW && i < p1.size()) {
            if(p1.get(i).ordinal() > p2.get(i).ordinal())
                winner = HandComparator.FIRST;
            else if(p1.get(i).ordinal() < p2.get(i).ordinal())
                winner = HandComparator.SECOND;
            i++;
        }
        
        return winner;
    }
    
    /**
     * Método que ordena las cartas de un jugador por el valor de estas.
     * @param cards Lista de cartas de un jugador.
     * @return Lista de cartas ordenadas.
     */
    private LinkedList<Card> sortHand(LinkedList<Card> cards) {
      Collections.sort(cards, Collections.reverseOrder());
      return cards;
    }

   
    /**
     * <p>Método que recibe una lista con las 7 cartas de un jugador ordenadas por valor de las cartas.</p>
     * Analiza las cartas buscando parejas, tríos, escaleras o colores. Y finalmente mezcla lo encontrado para determinar la mejor jugada de su mano.
     * @param cards Lista de cartas del jugador ordenadas. 
     * @return Objeto <code>HandPlayer</code> con la jugada del jugador.
     */
    private HandPlayer getHand(LinkedList<Card> cards) {
       
        int length = cards.size();
        HandPlayer bestHand = null;
        if(length > 0){            
            //Obtenemos la posición en el enumerado de la primera carta
            Card current = cards.get(0);
            LinkedList<Value> singleCards = new LinkedList<>();
            
            Value highStr = null;
            Value highStrFlushH = null, highStrFlushC = null, highStrFlushD = null, highStrFlushS = null;
            int straightCont = 1; 
            int strFlushContH = 0, strFlushContC = 0, strFlushContD = 0, strFlushContS = 0;
            int flushContH = 0, flushContC = 0, flushContD = 0, flushContS = 0;
            int repCont = 1;
            
            Value currentH = null, currentC = null, currentD = null, currentS = null;
            Value highFlushH = null, highFlushC = null, highFlushD = null, highFlushS = null;
            bestHand = new HandPlayer(HandCategories.HIGH_CARD);
            
            
            switch (cards.get(0).getSuit()) {
                case CLUBS:
                    flushContC = 1;
                    highFlushC = cards.get(0).getVal();
                    
                    break;
                case DIAMONDS:
                    flushContD = 1;
                    highFlushD= cards.get(0).getVal();
                    
                    break;
                case HEARTS:
                    flushContH = 1;
                    highFlushH = cards.get(0).getVal();
                 
                    break;
                case SPADES:
                    flushContS = 1;
                    highFlushS = cards.get(0).getVal();
                    
                    break;
                default:
                    break;
            }
            
            for(int i = 1; i < length; i++) {
                
                /////////////BUSCAR PAREJAS//////////////////////////////
                //Si encuentro varias cartas iguales incremento el contador
                //hasta que encuentre alguna diferente.
                if(cards.get(i).getVal() == current.getVal()) {
                    repCont++;
                }
                else {
                    //Como máximo necesitaremos 3 cartas adicionales en el caso que
                    //tengamos pareja
                    if(repCont == 1 && singleCards.size() < 5) {
                        singleCards.addLast(current.getVal());
                        //Las cartas que aparezcan una sola vez las insertamos 
                        //al final del array sinleHand y serán eliminadas
                        //si hay mejores jugadas
                    }
                    else if(repCont == 2 || repCont == 3 || repCont == 4) {              
                        bestHand = giveMeBestHand(current.getVal(), repCont, bestHand);
                        repCont = 1;
                    }
                }
                
                if(i == length-1 &&(repCont == 2 || repCont == 3 || repCont == 4)){
                    bestHand = giveMeBestHand(current.getVal(), repCont, bestHand);
                }      
                /////////////FIN BUSCAR PAREJAS////////////////////////////
                /////////////BUSCAR ESCALERAS//////////////////////////////
                
                if(cards.get(i).getVal().ordinal() == current.getVal().ordinal()-1 && straightCont<5){
                    if(straightCont == 1)
                        highStr = current.getVal();
                    straightCont++;
                    if(straightCont == 5){
                        HandCategories currentHand = HandCategories.STRAIGHT;
                        if(bestHand.getHand().ordinal() < currentHand.ordinal()){
                            bestHand.setHand(currentHand);
                            bestHand.setMainV(highStr);
                        }
                    }else{
                        //Caso de A a 5
                        if(straightCont == 4 && highStr == Value.FIVE && 
                                cards.get(0).getVal() == Value.ACE){
                            HandCategories currentHand = HandCategories.STRAIGHT;
                            if(bestHand.getHand().ordinal() < currentHand.ordinal()){
                                bestHand.setHand(currentHand);
                                bestHand.setMainV(highStr);
                            }
                        }
                    }
                }else{
                     if(cards.get(i).getVal().ordinal() < current.getVal().ordinal()-1){
                         straightCont = 1;
                     }
                }
                /////////////FIN BUSCAR ESCALERAS//////////////////////////////
                /////////////BUSCAR ESCALERA DE COLOR//////////////////////////
                
                
                //////HEARTS/////////////////
                if(strFlushContH == 0 && current.getSuit() == Suit.HEARTS) {
                    strFlushContH++;
                    currentH = current.getVal();
                    highStrFlushH = current.getVal();
                } 
                if(currentH != null){
                    if(cards.get(i).getVal().ordinal() == currentH.ordinal()-1 && 
                            cards.get(i).getSuit() == Suit.HEARTS && strFlushContH < 5){
                        
                        currentH = cards.get(i).getVal();
                        strFlushContH++;
                        if(strFlushContH == 5){                            
                            bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                            bestHand.setMainV(highStrFlushH);
                            bestHand.setSuit(Suit.HEARTS);                       
        
                        }else{
                        //Caso A a 5 corazones
                            if(strFlushContH == 4 && highStrFlushH == Value.FIVE){
                                int j = 0; 
                                boolean noAce = false;
                                while(j < 3 && !noAce){
                                    Card c = cards.get(j);
                                    if(c.getVal() == Value.ACE && c.getSuit() == Suit.HEARTS){
                                        noAce = true;
                                        bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                                        bestHand.setMainV(highStrFlushH);
                                        bestHand.setSuit(Suit.HEARTS); 
                                    }
                                    j++;
                                }
                            }
                        }
                    }else{
                        if(cards.get(i).getVal().ordinal() < currentH.ordinal()-1 && 
                            cards.get(i).getSuit() == Suit.HEARTS){
                            strFlushContH = 1;
                            currentH = cards.get(i).getVal();
                            highStrFlushH = cards.get(i).getVal();
                        }
                    }
                }
                //////////////////DIAMONDS//////////////////
                if(strFlushContD == 0 && current.getSuit() == Suit.DIAMONDS) {
                    strFlushContD++;
                    currentD = current.getVal();
                    highStrFlushD = current.getVal();
                } if(currentD != null)  {
                    if(cards.get(i).getVal().ordinal() == currentD.ordinal()-1 && 
                        cards.get(i).getSuit() == Suit.DIAMONDS && strFlushContD < 5){
                        currentD = cards.get(i).getVal();
                        strFlushContD++;
                        if(strFlushContD == 5){
                            bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                            bestHand.setMainV(highStrFlushD);
                            bestHand.setSuit(Suit.DIAMONDS); 
                        }else{
                        //Caso A a 5 diamantes
                            if(strFlushContD == 4 && highStrFlushD == Value.FIVE){
                                int j = 0; 
                                boolean noAce = false;
                                while(j < 3 && !noAce){
                                    Card c = cards.get(j);
                                    if(c.getVal() == Value.ACE && c.getSuit() == Suit.DIAMONDS){
                                        noAce = true;
                                        bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                                        bestHand.setMainV(highStrFlushD);
                                        bestHand.setSuit(Suit.DIAMONDS); 
                                    }
                                    j++;
                                }
                            }
                        }
                    }else{
                        if(cards.get(i).getVal().ordinal() < currentD.ordinal()-1 && 
                            cards.get(i).getSuit() == Suit.DIAMONDS){
                            strFlushContD = 1;
                            currentD = cards.get(i).getVal();
                            highStrFlushD = cards.get(i).getVal();
                        }
                    }
                }
                
                
                /////////////////////////CLUBS////////////////////////
                
                if(strFlushContC == 0 && current.getSuit() == Suit.CLUBS) {
                    strFlushContC++;
                    currentC = current.getVal();
                    highStrFlushC = current.getVal();
                } if(currentC != null) {
                    if(cards.get(i).getVal().ordinal() == currentC.ordinal()-1 && 
                            cards.get(i).getSuit() == Suit.CLUBS && strFlushContC < 5){
                        currentC = cards.get(i).getVal();
                        strFlushContC++;
                        if(strFlushContC == 5){
                            bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                            bestHand.setMainV(highStrFlushC);
                            bestHand.setSuit(Suit.CLUBS); 
                        }else{
                        //Caso A a 5 clubs
                            if(strFlushContC == 4 && highStrFlushC == Value.FIVE){
                                int j = 0; 
                                boolean noAce = false;
                                while(j < 3 && !noAce){
                                    Card c = cards.get(j);
                                    if(c.getVal() == Value.ACE && c.getSuit() == Suit.CLUBS){
                                        noAce = true;
                                        bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                                        bestHand.setMainV(highStrFlushC);
                                        bestHand.setSuit(Suit.CLUBS); 
                                    }
                                    j++;
                                }
                            }
                        }
                    }else{
                        if(cards.get(i).getVal().ordinal() < currentC.ordinal()-1 && 
                            cards.get(i).getSuit() == Suit.CLUBS){
                            strFlushContC = 1;
                            currentC = cards.get(i).getVal();
                            highStrFlushC = cards.get(i).getVal();
                        }
                    }
                }
                ////////////////////////////////SPADES////////////////////////////
                if(strFlushContS == 0 && current.getSuit() == Suit.SPADES) {
                    strFlushContS++;
                    currentS = current.getVal();
                    highStrFlushS = current.getVal();
                } if(currentS != null) {
                    if(cards.get(i).getVal().ordinal() == currentS.ordinal()-1 && 
                    cards.get(i).getSuit() == Suit.SPADES && strFlushContS < 5){
                        currentS = cards.get(i).getVal();
                        strFlushContS++;
                        if(strFlushContS == 5){
                            bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                            bestHand.setMainV(highStrFlushS);
                            bestHand.setSuit(Suit.SPADES); 
                        }else{
                        //Caso A a 5 clubs
                            if(strFlushContS == 4 && highStrFlushS == Value.FIVE){
                                int j = 0; 
                                boolean noAce = false;
                                while(j < 3 && !noAce){
                                    Card c = cards.get(j);
                                    if(c.getVal() == Value.ACE && c.getSuit() == Suit.SPADES){
                                        noAce = true;
                                        bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                                        bestHand.setMainV(highStrFlushS);
                                        bestHand.setSuit(Suit.SPADES); 
                                    }
                                    j++;
                                }
                            }
                        }
                    }else{
                        if(cards.get(i).getVal().ordinal() < currentS.ordinal()-1 && 
                            cards.get(i).getSuit() == Suit.SPADES){
                            strFlushContS = 1;
                            currentS = cards.get(i).getVal();
                            highStrFlushS = cards.get(i).getVal();
                        }
                    }
                  }
                /////////////FIN BUSCAR ESCALERAS DE COLOR/////////////////
                
                
       
                
                
                /////////////BUSCAR COLOR/////////////////////////////////
                switch (cards.get(i).getSuit()) {
                    case CLUBS:
                        if(flushContC == 0) 
                            highFlushC = cards.get(i).getVal();
                        flushContC++;
                        if(flushContC == 5) { 
                            HandCategories currentHand = HandCategories.FLUSH;
                            if(bestHand.getHand().ordinal() < currentHand.ordinal()) {
                                bestHand.setHand(currentHand);
                                bestHand.setSuit(Suit.CLUBS);
                            }
                        }
                        break;                
                    case DIAMONDS:
                        if(flushContD == 0) 
                            highFlushD = cards.get(i).getVal();
                        flushContD++;
                        if(flushContD == 5) {
                            HandCategories currentHand = HandCategories.FLUSH;
                            if(bestHand.getHand().ordinal() < currentHand.ordinal()) {
                                bestHand.setHand(currentHand);
                                bestHand.setSuit(Suit.DIAMONDS);
                            }
                        }
                        break;
                    case HEARTS:
                        if(flushContH == 0) 
                            highFlushH = cards.get(i).getVal();
                        flushContH++;
                        if(flushContH == 5) {
                            HandCategories currentHand = HandCategories.FLUSH;
                            if(bestHand.getHand().ordinal() < currentHand.ordinal()) {
                                bestHand.setHand(currentHand);
                                bestHand.setSuit(Suit.HEARTS);
                            }
                        }
                        break;
                    case SPADES:
                        if(flushContS == 0) 
                            highFlushS = cards.get(i).getVal();
                        flushContS++;
                        if(flushContS == 5) {HandCategories currentHand = HandCategories.FLUSH;
                            if(bestHand.getHand().ordinal() < currentHand.ordinal()) {
                                bestHand.setHand(currentHand);
                                bestHand.setSuit(Suit.SPADES);
                            }
                        }
                        break;
                    default:
                        break;
                }
                /////////////FIN BUSCAR COLOR/////////////////////////////////
                current = cards.get(i);
            }
            
        
            if(null != bestHand.getHand()) 
                switch (bestHand.getHand()) {
                case FLUSH:
                    bestHand.setCards(getFlushCards(cards, bestHand.getSuit()));
                    bestHand.setMainV(null);
                    break;
                case HIGH_CARD:
                    bestHand.setMainV(null);
                    bestHand.setCards(singleCards);
                    break;
                case POKER:
                    if(!singleCards.isEmpty())
                        bestHand.addLastCards(singleCards.getFirst());
                    else  //La carta kicker para el poker
                          //podría no estar en singleCards si formara una pareja o trío.
                        bestHand.addLastCards(findTheKicker(bestHand, cards));
                    break;
                case TWO_PAIR:
                    if(!singleCards.isEmpty())
                        bestHand.addLastCards(singleCards.getFirst());
                    else  //Puede haber 3 parejas y no estar en singleCards
                        bestHand.addLastCards(cards.get(4).getVal());
                    break;
                case THREE_KIND:
                    bestHand.addLastCards(singleCards.getFirst());
                    singleCards.removeFirst();
                    bestHand.addLastCards(singleCards.getFirst());
                    break;
                case ONE_PAIR:
                    bestHand.addLastCards(singleCards.getFirst());
                    singleCards.removeFirst();
                    bestHand.addLastCards(singleCards.getFirst());
                    singleCards.removeFirst();
                    bestHand.addLastCards(singleCards.getFirst());
                    break;
                default:
                    break;
            }
            
       
        }
        
        
        return bestHand;
    }
    
    /**
     * <p>Método que recibe el valor de la última carta analizada del jugador, la cantidad de veces que ha encontrado esa carta y la mejor jugada encontrada hasta ahora.</p>
     * Utilizando esta información devuelve la mejor jugada encontrada hasta ahora.
     * @param currentVal Valor de la última carta analizada de la mano del jugador.
     * @param repCont Número de veces que ha encontrado el valor de esa carta en la mano.
     * @param bestHand Mejor mano encontrada hasta ahora.
     * @return Mejor valor resultante tras analizar la información recibida por parámetro.
     */
    private HandPlayer giveMeBestHand(Value currentVal, int repCont, HandPlayer bestHand){
        HandCategories currentHand = getCurrentHand(repCont);
        bestHand = decideBetterHand(bestHand,currentHand, currentVal);
        return bestHand;
    }
    
    
    /**
     * Método que recibe dos jugadas encontradas en una mano y decide cuál de las dos es mejor, o si ambas se pueden mezclar para hacer una jugada mejor.
     * @param bestHand Mejor jugada encontrada hasta ahora
     * @param current Última jugada encontrada.
     * @param currentVal Valor de la carta que ha formado la última jugada encontrada.
     * @return Mejor jugada posible utilizando las recibidas por parámetro. 
     */ 
    private HandPlayer decideBetterHand(HandPlayer bestHand, HandCategories current
                                            ,Value currentVal) {
       
        //Si había encontrado una pareja y ahora encuentro un trio tengo full.
        if(bestHand.getHand() == HandCategories.ONE_PAIR && current == HandCategories.THREE_KIND) {
            bestHand.setHand(HandCategories.FULL_HOUSE);            
            bestHand.setSecondV(bestHand.getMainV());
            bestHand.setMainV(currentVal);
        } 
        //Si había encontrado un trio y ahora encuentro una pareja tengo full.
        else if(bestHand.getHand() == HandCategories.THREE_KIND && current == HandCategories.ONE_PAIR) {            
            bestHand.setHand(HandCategories.FULL_HOUSE);
            bestHand.setSecondV(currentVal);
            
        }
        //Si había encontrado una pareja y ahora encuentro una pareja tengo doble pareja.
        else if(bestHand.getHand() == HandCategories.ONE_PAIR && current == HandCategories.ONE_PAIR){
            bestHand.setHand(HandCategories.TWO_PAIR);
            bestHand.setSecondV(currentVal);
        }
        //Si encuentro dos trios tengo full con trio del mayor y pareja del menor
        else if(bestHand.getHand() == HandCategories.THREE_KIND && current == HandCategories.THREE_KIND){
            bestHand.setHand(HandCategories.FULL_HOUSE);
            bestHand.setSecondV(currentVal);
        }
        else if(bestHand.getHand().ordinal() < current.ordinal()) {
            bestHand.setHand(current);
            bestHand.setMainV(currentVal);
        }
      
        return bestHand;
    }   
    
    
    /**
     * Método que devuelve si hemos encontrado una pareja, trío o Poker por la cantidad de veces que aparece el valor de una carta en una mano.
     * @param cont Número de veces que aparece el valor de una carta en una mano.
     * @return Jugada que forma la repetición del valor de esa carta.
     */
   private HandCategories getCurrentHand(int cont){
        HandCategories hand = null;
        switch (cont) {
            case 2:
                hand = HandCategories.ONE_PAIR;
                /*
                else{
                    Value v = hand.getMainV();
                    hand = HandCategories.TWO_PAIR;
                    hand.setMainV(v);
                    hand.setSecondV(val);
                    HandCategories.ONE_PAIR.setMainV(null);
                }
                        */
                break;
            case 3:
                hand = HandCategories.THREE_KIND;
               // hand.setMainV(val);
                break;
            case 4:
                hand = HandCategories.POKER;
                //hand.setMainV(val);
                break;
            default:
                break;
        }
        return hand;
    }
   
   
   /**
    * Método que recorre las cartas de un jugador para encontrar los valores de las cartas que forman la jugada "Color".
    * @param cards Lista de cartas del jugador.
    * @param s Palo del que hay 5 o más cartas.
    * @return Lista de valores de cartas que forman la jugada "Color".
    */
   private LinkedList<Value> getFlushCards(LinkedList<Card> cards, Suit s) {
       
       int i = cards.size() - 1;
       LinkedList<Value> flush = new LinkedList<>();
       while (i >= 0 && flush.size() < 5) {           
           if(cards.get(i).getSuit() == s) 
               flush.push(cards.get(i).getVal());
           
           i--;
       }
       return flush;
   }  
   
   /**
    * Método que recorre las cartas de un jugador en busca del "Kicker".
    * @param hp Mejor jugada de la mano del jugador.
    * @param cards Lista de cartas del jugador.
    * @return Carta que es el Kicker de la jugada.
    */
   private Value findTheKicker(HandPlayer hp, LinkedList<Card> cards){
      int i = 0;
      Value v;
      while(cards.get(i).getVal() == hp.getMainV() && i < cards.size()) {
          i++;
      }
      v = cards.get(i).getVal();
      return v;
   }
   
   /**
    * Método que arranca el timer del jugador con el turno
    */
   public void initTimerBoard(){
        this.timer = new Timer(1000, new ActionListener() {
              
            private int cont = 30;
                @Override
                public void actionPerformed(ActionEvent e) {
                    c.updateCounter();
                    cont--;
                    if (cont == 0){
                        c.playerFolds();
                    }
                }
             });
        this.timer.start();
    }
    
   /**
    * Método que para el timer del jugador con el turno
    */
    public void stopTimerBoard(){
        this.timer.stop();
    }
   /**
    * Método que dice si hay timer o no
    */
    public boolean timer(){
        if(this.timer == null)
            return false;
        return true;
    }
    
    /**
    * Método que dice si el timer está corriendo
    */
    public boolean running(){
        if(timer()){
            if(this.timer.isRunning()) return true;
            return false;
        }
        return false;
    }
}
