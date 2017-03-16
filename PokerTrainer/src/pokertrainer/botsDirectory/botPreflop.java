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

import java.util.LinkedList;
import java.util.Random;
import pokertrainer.model.Action;
import pokertrainer.model.ActionFold;
import pokertrainer.model.ActionCall;
import pokertrainer.model.ActionRaise;
import pokertrainer.model.BotInterface;
import pokertrainer.model.Card;
import pokertrainer.model.InfoBot;
import pokertrainer.model.PlayerInfo;
import pokertrainer.model.Role;
import pokertrainer.model.Value;

/* TABLA PREFLOP IMPLEMENTADA 
https://www.pokerschoolonline.com/articles/NLHE-cash-pre-flop-essentials
*/

/**
 *
 * @author Javier
 */
public class botPreflop implements BotInterface {

    private enum History { EVERYBODY_FOLD,
                            SOMEBODY_CALL, 
                          SOMEBODY_RAISED, 
                 SOMEBODY_RAISES_AFTER_ME };
    
    private enum Position {EARLY, MID, LATE, BLIND};
    
    private enum Actions {CALL, CALL20, RAISE, FOLD}
    
    /* Que hacer con HighPairs AA, KK, QQ */
    private final Actions[] highPairsAAKK = {Actions.RAISE, Actions.RAISE, Actions.RAISE, Actions.RAISE};
    private final Actions[] highPairsQQ = {Actions.RAISE, Actions.RAISE, Actions.RAISE, Actions.CALL20};
    
    /* Que hacer con BigAces AK */
    private final Actions[] bigAcesAK = {Actions.RAISE, Actions.RAISE, Actions.RAISE, Actions.FOLD};
    
    /* Que hacer con MidPairs JJ, TT, 99 */
    private final Actions[] midPairsEP = {Actions.FOLD, Actions.RAISE, Actions.CALL20, Actions.CALL20};
    private final Actions[] midPairsMP = {Actions.RAISE, Actions.RAISE, Actions.CALL20, Actions.CALL20};
    private final Actions[] midPairsLP = {Actions.RAISE, Actions.RAISE, Actions.CALL20, Actions.CALL20};
    private final Actions[] midPairsBL = {Actions.RAISE, Actions.RAISE, Actions.CALL20, Actions.CALL20};
    
    /* Que hacer con SmallPairs 88 a 22*/
    private final Actions[] smallPairsEP = {Actions.FOLD, Actions.FOLD, Actions.CALL20, Actions.CALL20};
    private final Actions[] smallPairsMP = {Actions.CALL, Actions.CALL, Actions.CALL20, Actions.CALL20};
    private final Actions[] smallPairsLP = {Actions.RAISE, Actions.CALL, Actions.CALL20, Actions.CALL20};
    private final Actions[] smallPairsBL = {Actions.CALL, Actions.CALL, Actions.CALL20, Actions.CALL20};
    
    /* Que hacer con MidAces AQ, AJ, AT */
    private final Actions[] midAcesEP = {Actions.FOLD, Actions.FOLD, Actions.FOLD, Actions.FOLD};
    private final Actions[] midAcesMP = {Actions.RAISE, Actions.CALL, Actions.FOLD, Actions.FOLD};
    private final Actions[] midAcesLP = {Actions.RAISE, Actions.RAISE, Actions.FOLD, Actions.FOLD};
    private final Actions[] midAcesBL = {Actions.RAISE, Actions.CALL, Actions.FOLD, Actions.FOLD};
    
    /* Que hacer con SuitedAces A9s a A2s */
    private final Actions[] suitedAcesEP = {Actions.FOLD, Actions.FOLD, Actions.FOLD, Actions.FOLD};
    private final Actions[] suitedAcesMP = {Actions.RAISE, Actions.CALL, Actions.FOLD, Actions.FOLD};
    private final Actions[] suitedAcesLP = {Actions.RAISE, Actions.RAISE, Actions.FOLD, Actions.FOLD};
    private final Actions[] suitedAcesBL = {Actions.RAISE, Actions.CALL, Actions.FOLD, Actions.FOLD};
    
    /* Que hacer con Facecards KQ, KJ, KT, QJ, QT, JT */
    private final Actions[] facecardsEP = {Actions.FOLD, Actions.FOLD, Actions.FOLD, Actions.FOLD};
    private final Actions[] facecardsMP = {Actions.FOLD, Actions.FOLD, Actions.FOLD, Actions.FOLD};
    private final Actions[] facecardsLP = {Actions.RAISE, Actions.CALL, Actions.FOLD, Actions.FOLD};
    private final Actions[] facecardsBL = {Actions.RAISE, Actions.CALL, Actions.FOLD, Actions.FOLD};
    
    /* Que hacer con SuitedConnectors JTs a 54s */ 
    private final Actions[] suiteconnectorsEP = {Actions.FOLD, Actions.FOLD, Actions.FOLD, Actions.FOLD};
    private final Actions[] suiteconnectorsMP = {Actions.FOLD, Actions.FOLD, Actions.FOLD, Actions.FOLD};
    private final Actions[] suiteconnectorsLP = {Actions.RAISE, Actions.CALL, Actions.FOLD, Actions.FOLD};
    private final Actions[] suiteconnectorsBL = {Actions.FOLD, Actions.CALL, Actions.FOLD, Actions.FOLD}; 
    
    /* Que hacer con otras cartas */
    private final Actions[] others = {Actions.FOLD, Actions.FOLD, Actions.FOLD, Actions.FOLD};
    
    
    private final float minimumToRaise = 2.5f;
    private final float maximumToRaise = 3.0f;
           
    //Ultimo jugador distinto de nosotros que hizo el raise
    private PlayerInfo playerWhoRaised;
    
    private boolean iRaised;
    
    @Override
    public Action createAction(InfoBot info) {
        
        Action toDo = new ActionFold();
        
        switch (info.getState()) {
            case PREFLOP:
                toDo = getPreflopAction(info);
                break;
            case FLOP:
                toDo = getFlopAction();
                break;
            case TURN:
                toDo = getTurnAction();
                break;
            case RIVER:
                toDo = getRiverAction();
            default:
                break;
        }
        
        return toDo;
    }
    
    private Action getPreflopAction(InfoBot info) {
        Actions[] actions = rowOfStrategyPreflop(info.getBotCards(), info.getBot().getRole(), info.getInfoPlayers());
        
        History history = whatHappened(info);
        
        Actions a = actions[history.ordinal()];
        
        if (a == Actions.CALL) return new ActionCall(pokertrainer.model.Actions.CALL);
        else if (a == Actions.FOLD) return new ActionFold();
        else if (a == Actions.RAISE) {
            this.iRaised = true;
            Random r = new Random();
            float random = minimumToRaise + r.nextFloat() * (maximumToRaise - minimumToRaise);
            int raise = (int)(info.getBb() * random);
            return new ActionRaise(raise, pokertrainer.model.Actions.RAISE_TO);
        } else { //Call-20
            if (this.playerWhoRaised.getMoney() / 20 > this.playerWhoRaised.getTotalBet()) 
                return new ActionCall(pokertrainer.model.Actions.CALL);
            else 
                return new ActionFold();
        } 
    }
    
    private Action getFlopAction() {
        return new ActionCall(pokertrainer.model.Actions.CALL);
    }
    
    private Action getTurnAction() {
        return new ActionCall(pokertrainer.model.Actions.CALL);
    }
    
    private Action getRiverAction() {
       return new ActionCall(pokertrainer.model.Actions.CALL);
    }

    //Dada la lista de acciones de los jugadores calcula la Historia
    private History whatHappened(InfoBot info) {
        
        boolean everybodyFolds = true;
        int numRaises = 0;
               
        info.getInfoPlayers().remove(info.getBot());
        
        for (PlayerInfo p : info.getInfoPlayers()) {
            if (p.getAction() != null && p.getAction().getAction() == pokertrainer.model.Actions.CALL) {
                everybodyFolds = false;

             } else if (p.getAction() != null && p.getAction().getAction() == pokertrainer.model.Actions.RAISE_TO) {
                numRaises++;
                everybodyFolds = false;
                this.playerWhoRaised = p;
             }
         }

        if (everybodyFolds) return History.EVERYBODY_FOLD;
        else if (numRaises >= 1 && iRaised) return History.SOMEBODY_RAISES_AFTER_ME;
        else if (numRaises == 1) return History.SOMEBODY_RAISED;
        else return History.SOMEBODY_CALL;
        
    }
    
    //Dado el rol y la lista de la información de jugadores, devuelve la Posicion que ocupa el bot
    private Position whatsMyPosition(Role role, LinkedList<PlayerInfo> handPlayers) {
        int max = 0;
        
        for (int i = 1; i < handPlayers.size(); i++) {
            if (handPlayers.get(i).getRole().ordinal() > max) { 
                max = handPlayers.get(i).getRole().ordinal();
            }
        } 
        
        int numPlayers = max + 1;
        
        return calculatePositionWithNumPlayers(numPlayers, role.ordinal() + 1);
    }
    
    //Sub-método de "whatsMyPosition" que en base al numJugadores devuelve la posicion
    private Position calculatePositionWithNumPlayers(int numPlayer, int role) {
        //Implementado de momento solo para 9 jugadores (aproximación sin tener en cuenta numPlayers)
        switch (role) {
            case 0: case 1:         return Position.BLIND;
            case 2: case 3:         return Position.EARLY;
            case 4: case 5: case 6: return Position.MID;
            default:                return Position.LATE;
        }
    }
    
    private Actions[] rowOfStrategyPreflop(LinkedList<Card> myCards, Role role, LinkedList<PlayerInfo> handPlayers) {
        
        Value val1 = myCards.get(0).getVal();
        Value val2 = myCards.get(1).getVal();
        
        Position pos = whatsMyPosition(role, handPlayers);
        
        boolean suited = (myCards.get(0).getSuit() == myCards.get(1).getSuit());
 
        
        if ((val1 == Value.ACE || val1 == Value.KING) && (val1 == val2))
            return this.highPairsAAKK;
        
        else if (val1 == Value.QUEEN && val1 == val2) 
            return this.highPairsQQ;
        
        else if ((val1 == Value.ACE && val2 == Value.KING)
                  || (val1 == Value.KING && val2 == Value.ACE)) 
            return bigAcesAK;
        
        else if ((val1 == Value.JACK || val1 == Value.TEN || val1 == Value.NINE)
                && (val1 == val2)) {
        
            switch (pos) {
                case EARLY:
                    return midPairsEP;
                case MID:
                    return midPairsMP;
                case LATE:
                    return midPairsLP;
                default:
                    return midPairsBL;
            }
        }
        
        else if ((val1.ordinal()+2 <= 8) && (val1 == val2)) {
             switch (pos) {
                case EARLY:
                    return smallPairsEP;
                case MID:
                    return smallPairsMP;
                case LATE:
                    return smallPairsLP;
                default:
                    return smallPairsBL;
            }
        }
        
        else if ((val1 == Value.ACE && (val2 == Value.QUEEN || val2 == Value.JACK || val2 == Value.TEN))
                || (val2 == Value.ACE && (val1 == Value.QUEEN || val1 == Value.JACK || val1 == Value.TEN))) {
             switch (pos) {
                case EARLY:
                    return midAcesEP;
                case MID:
                    return midAcesMP;
                case LATE:
                    return midAcesLP;
                default:
                    return midAcesBL;
            }
        }
        
        else if (suited && ((val1 == Value.ACE && val2.ordinal()+1 <= 9) || (val2 == Value.ACE && val1.ordinal()+1 <= 9))) {
            switch (pos) {
                case EARLY:
                    return suitedAcesEP;
                case MID:
                    return suitedAcesMP;
                case LATE:
                    return suitedAcesLP;
                default:
                    return suitedAcesBL;
            }
        }
        
        else if (suited && (java.lang.Math.abs(val1.ordinal()-val2.ordinal()) == 1) 
                && (val1.ordinal()+2 >= 4) && (val1.ordinal()+2 <= 10) 
                && (val2.ordinal()+2 >= 4) && (val2.ordinal()+2 <= 10)) {
            switch (pos) {
                case EARLY:
                    return suiteconnectorsEP;
                case MID:
                    return suiteconnectorsMP;
                case LATE:
                    return suiteconnectorsLP;
                default:
                    return suiteconnectorsBL;
            }
        }
        
        //KQ, KJ, KT, QJ, QT, JT
        else if (val1.ordinal()+2 >= 10 && val2.ordinal()+2 >= 10) {
            switch (pos) {
                case EARLY:
                    return facecardsEP;
                case MID:
                    return facecardsMP;
                case LATE:
                    return facecardsLP;
                default:
                    return facecardsBL;
            }
        }
        
        else {
           return others;
        }
             
    }
    
}
