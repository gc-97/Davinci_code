/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_System;

import java.io.Serializable;
import java.lang.Math;
import java.util.Random;

/**
 *
 * @author PSJ
 */
public class CheckTurn implements Serializable {

    boolean Player1Turn = false;
    boolean Player2Turn = false;
    boolean guess = false;
    private static final long serialVersionUID = 1L;
    public TurnState turnstate;
    
    private static CheckTurn checkturn = new CheckTurn(); //싱글톤 생성

    PlayerCard pcard1 = PlayerCard.getPlayer1instance();
    PlayerCard pcard2 = PlayerCard.getPlayer2instance();

    private CheckTurn() //싱글톤을 위한 private 생성자
    {
        //checkturn.Flipthecoin();
        System.out.println("싱글톤 생성");

    }

    public static CheckTurn getinstance() //싱글톤을 위한 객제 정보 얻기
    {
        return checkturn;
    }

    public void Flipthecoin() {
        float Coin = (float) (Math.random() * 2);
        if (Coin > 1) //동전이 1이상 즉 앞면 
        {
            System.out.println("동전은 앞면\n플레이어1선공" + Coin);
            this.Player1Turn = true;  //플레이어1 턴 활성화
            pcard1.playerTurn = true;
            turnstate = new P1Turn();
        } else //동전이 1미만  즉 뒷면
        {
            System.out.println("동전은 뒷면\n플레이어2선공" + Coin);
            this.Player2Turn = true; //플레이어2 턴 활성화
            pcard2.playerTurn = true;
            turnstate = new P2Turn();
        }

    }

    public void CheckTurnForGame() {

        turnstate.CheckTurnForGame();

    }

    public void isGuessTure() {
        if (Player1Turn == true) {
            if (guess == true) {
                guess = false;
                Player1Turn = true;
                Player2Turn = false;
                pcard1.playerTurn = true;
                pcard2.playerTurn = false;
                setState(new P1Turn());

            } else {
                Player1Turn = false;
                Player2Turn = true;
                pcard1.playerTurn = false;
                pcard2.playerTurn = true;
                setState(new P2Turn());
                

            }

        } else if (Player2Turn == true) {
            if (guess == true) {
                guess = false;
                Player2Turn = true;
                Player1Turn = false;
                pcard2.playerTurn = true;
                pcard1.playerTurn = false;
                setState(new P2Turn());

            } else {
                Player2Turn = false;
                Player1Turn = true;
                pcard2.playerTurn = false;
                pcard1.playerTurn = true;
                setState(new P1Turn());

            }

        }

    }

    public boolean isPlayer1Turn() {
        return Player1Turn;
    }

    public boolean isPlayer2Turn() {
        return Player2Turn;
    }

    public void setPlayer1Turn(boolean Player1Turn) {
        this.Player1Turn = Player1Turn;
    }

    public void setPlayer2Turn(boolean Player2Turn) {
        this.Player2Turn = Player2Turn;
    }
    
        public void setState(TurnState turnState)
    {
        this.turnstate= turnState;
    }             
    
}
