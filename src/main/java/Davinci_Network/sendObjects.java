package Davinci_Network;
import Davinci_System.TableDeck;
import Davinci_System.Cards;

import java.io.Serializable;

public class sendObjects implements Serializable{
    private boolean Player1Turn;
    private boolean Player2Turn;
    private TableDeck TD;
    private Cards pcard1;
    private Cards pcard2;
    private float p1LastPick;
    private float p2LastPick;
    private static final long serialVersionUID = 1L;
    
    public void setObjects(boolean Player1Turn, boolean Player2Turn, TableDeck TD, Cards pcard1, Cards pcard2, float p1LastPick, float p2LastPick){
        this.Player1Turn = Player1Turn;
        this.Player2Turn = Player2Turn;
        this.TD = TD;
        this.pcard1 = pcard1;
        this.pcard2 = pcard2;
        this.p1LastPick = p1LastPick;
        this.p2LastPick = p2LastPick;
    }

    public boolean isPlayer1Turn() {
        return Player1Turn;
    }

    public boolean isPlayer2Turn() {
        return Player2Turn;
    }
    
    public TableDeck getTD() {
        return TD;
    }

    public Cards getPcard1() {
        return pcard1;
    }

    public Cards getPcard2() {
        return pcard2;
    }
    public float getP1LastPick(){
        return p1LastPick;
    }
    public float getP2LastPick(){
        return p2LastPick;
    }
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("1Turn: " + Player1Turn).append(", 2Turn: " + Player2Turn)
                .append(", pcard1: " + pcard1).append(", pcard2: " + pcard2)
                .append(", p1lastPick: " + p1LastPick)
                .append(", p2lastPick: " + p2LastPick);;
        return str.toString();
    }
}
