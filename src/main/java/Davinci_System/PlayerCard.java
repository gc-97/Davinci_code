/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_System;
import Davinci_Gui.GamePlaying_GUI;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.InputMismatchException;

/**
 *
 * @author PSJ
 */
public class PlayerCard implements Serializable{
    private static final long serialVersionUID = 1L;
    TableDeck TD = TableDeck.getinstance();//카드 객체를 받아온다.
    ArrayList<Float> unreversedcard = new ArrayList<>(); //플레이어가 뽑은 카드  Array list
    ArrayList<Float> reversedcard = new ArrayList<>();
    ArrayList<Float> pickcard = new ArrayList<>();
    boolean playerTurn = false;
    private static PlayerCard player1card = new PlayerCard();
    private static PlayerCard player2card = new PlayerCard();
    private Cards getCards;
    private GamePlaying_GUI gui;
    
    private PlayerCard() {
        getCards = new Cards();
        for (int i = 0, j = 0; i < 2; i++) {
            if ("JokerBlack".equals(TD.BlackDeck.get(j))) //카드를 뽑기전 조커 카드인지 확인하고 
            {                                            //조커카드가 맞다면   
                j++;
                i--;
            } else {                                    //조커카드가 아니라면 
                System.out.println("Black : " + j);
                unreversedcard.add(Float.parseFloat(TD.BlackDeck.get(j))); //String 타입인 덱카드를 Float형식으로 바꾼뒤 카드를 뽑는다.
                pickcard.add(Float.parseFloat(TD.BlackDeck.get(j)));
                TD.BlackDeck.remove(j);                     //그리고 덱카드를 제거한다.
            }
        }
        for (int i = 0, j = 0; i < 2; i++) {
            if ("JokerWhite".equals(TD.WhiteDeck.get(j))) {
                j++;
                i--;
            } else {
                System.out.println("White : " + j);
                unreversedcard.add(Float.parseFloat(TD.WhiteDeck.get(j)));
                pickcard.add(Float.parseFloat(TD.WhiteDeck.get(j)));
                TD.WhiteDeck.remove(j);
            }
        }
        //카드를 블랙덱2장 화이트덱 2장 뽑는다.
        Collections.sort(pickcard);
        Collections.sort(unreversedcard);// 카드 정렬

        System.out.println("Player first Pick");
        System.out.println("---------------------");
        for (int i = 0; i < unreversedcard.size(); i++) {
            System.out.println(unreversedcard.get(i));
        }                                              //플레이어가 처음 뽑은 카드 전부출력한다. 
        System.out.println("--BlackDeckCard--");
        for (int i = 0; i < TD.BlackDeck.size(); i++) {
            System.out.println(TD.BlackDeck.get(i));
        }
        System.out.println("--WhiteDeckCard--");
        for (int i = 0; i < TD.WhiteDeck.size(); i++) {
            System.out.println(TD.WhiteDeck.get(i));
        }                                             //남은 덱카드를 전부 출력한다.
    }
    
    public void setGui(GamePlaying_GUI gui){
        this.gui = gui;
    }
    
    public void Bjoker(String str) {
        String[] strSplit = str.split(" ");
        
        System.out.println("조커카드의 숫자를 임의로 정하시오");
        int jokerinput = Integer.parseInt(strSplit[0]);
        

        System.out.println("블랙 조커 배치");
        System.out.println("왼쪽이면 l  중앙이면 m 오른쪽이면 r을 입력해주세요");
        String LeftorRight = strSplit[1];
        
        if (LeftorRight.equals("l")) {
            switch (jokerinput) {
                case 0:
                    unreversedcard.add(0.01f);
                    pickcard.add(0.01f);
                    break;
                case 1:
                    unreversedcard.add(1.01f);
                    pickcard.add(1.01f);
                    break;
                case 2:
                    unreversedcard.add(2.01f);
                    pickcard.add(2.01f);
                    break;
                case 3:
                    unreversedcard.add(3.01f);
                    pickcard.add(3.01f);
                    break;
                case 4:
                    unreversedcard.add(4.01f);
                    pickcard.add(4.01f);
                    break;
                case 5:
                    unreversedcard.add(5.01f);
                    pickcard.add(5.01f);
                    break;
                case 6:
                    unreversedcard.add(6.01f);
                    pickcard.add(6.01f);
                    break;
                case 7:
                    unreversedcard.add(7.01f);
                    pickcard.add(7.01f);
                    break;
                case 8:
                    unreversedcard.add(8.01f);
                    pickcard.add(8.01f);
                    break;
                case 9:
                    unreversedcard.add(9.01f);
                    pickcard.add(9.01f);
                    break;
                case 10:
                    unreversedcard.add(10.01f);
                    pickcard.add(10.01f);
                    break;
                case 11:
                    unreversedcard.add(11.01f);
                    pickcard.add(11.01f);
                    break;
            }
        } else if (LeftorRight.equals("m")) {
            switch (jokerinput) {
                case 0:
                    unreversedcard.add(0.11f);
                    pickcard.add(0.11f);
                    break;
                case 1:
                    unreversedcard.add(1.11f);
                    pickcard.add(1.11f);
                    break;
                case 2:
                    unreversedcard.add(2.11f);
                    pickcard.add(2.11f);
                    break;
                case 3:
                    unreversedcard.add(3.11f);
                    pickcard.add(3.11f);
                    break;
                case 4:
                    unreversedcard.add(4.11f);
                    pickcard.add(4.11f);
                    break;
                case 5:
                    unreversedcard.add(5.11f);
                    pickcard.add(5.11f);
                    break;
                case 6:
                    unreversedcard.add(6.11f);
                    pickcard.add(6.11f);
                    break;
                case 7:
                    unreversedcard.add(7.11f);
                    pickcard.add(7.11f);
                    break;
                case 8:
                    unreversedcard.add(8.11f);
                    pickcard.add(8.11f);
                    break;
                case 9:
                    unreversedcard.add(9.11f);
                    pickcard.add(9.11f);
                    break;
                case 10:
                    unreversedcard.add(10.11f);
                    pickcard.add(10.11f);
                    break;
                case 11:
                    unreversedcard.add(11.11f);
                    pickcard.add(11.11f);
                    break;
            }
        } else if (LeftorRight.equals("r")) {
            switch (jokerinput) {
                case 0:
                    unreversedcard.add(0.21f);
                    pickcard.add(0.21f);
                    break;
                case 1:
                    unreversedcard.add(1.21f);
                    pickcard.add(1.21f);
                    break;
                case 2:
                    unreversedcard.add(2.21f);
                    pickcard.add(2.21f);
                    break;
                case 3:
                    unreversedcard.add(3.21f);
                    pickcard.add(3.21f);
                    break;
                case 4:
                    unreversedcard.add(4.21f);
                    pickcard.add(4.21f);
                    break;
                case 5:
                    unreversedcard.add(5.21f);
                    pickcard.add(5.21f);
                    break;
                case 6:
                    unreversedcard.add(6.21f);
                    pickcard.add(6.21f);
                    break;
                case 7:
                    unreversedcard.add(7.21f);
                    pickcard.add(7.21f);
                    break;
                case 8:
                    unreversedcard.add(8.21f);
                    pickcard.add(8.21f);
                    break;
                case 9:
                    unreversedcard.add(9.21f);
                    pickcard.add(9.21f);
                    break;
                case 10:
                    unreversedcard.add(10.21f);
                    pickcard.add(10.21f);
                    break;
                case 11:
                    unreversedcard.add(11.21f);
                    pickcard.add(11.21f);
                    break;
            }
        }
    }

    public void Wjoker(String str) {
        
        String[] strSplit = str.split(" ");
        
        gui.showState("흰색 조커를 뽑았습니다. 조커를 어디에 놓을까요?");
        System.out.println("조커카드의 숫자를 임의로 정하시오");
        int jokerinput = Integer.parseInt(strSplit[0]);
        

        System.out.println("흰색 조커 배치");
        System.out.println("왼쪽이면 l  중앙이면 m 오른쪽이면 r을 입력해주세요");
        String LeftorRight = strSplit[1];
        
        if (LeftorRight.equals("l")) {
            switch (jokerinput) {
                case 0:
                    unreversedcard.add(0.02f);
                    pickcard.add(0.02f);
                    break;
                case 1:
                    unreversedcard.add(1.02f);
                    pickcard.add(1.02f);
                    break;
                case 2:
                    unreversedcard.add(2.02f);
                    pickcard.add(2.02f);
                    break;
                case 3:
                    unreversedcard.add(3.02f);
                    pickcard.add(3.02f);
                    break;
                case 4:
                    unreversedcard.add(4.02f);
                    pickcard.add(4.02f);
                    break;
                case 5:
                    unreversedcard.add(5.02f);
                    pickcard.add(5.02f);
                    break;
                case 6:
                    unreversedcard.add(6.02f);
                    pickcard.add(6.02f);
                    break;
                case 7:
                    unreversedcard.add(7.02f);
                    pickcard.add(7.02f);
                    break;
                case 8:
                    unreversedcard.add(8.02f);
                    pickcard.add(8.02f);
                    break;
                case 9:
                    unreversedcard.add(9.02f);
                    pickcard.add(9.02f);
                    break;
                case 10:
                    unreversedcard.add(10.02f);
                    pickcard.add(10.02f);
                    break;
                case 11:
                    unreversedcard.add(11.02f);
                    pickcard.add(11.02f);
                    break;
            }
        } else if (LeftorRight.equals("m")) {
            switch (jokerinput) {
                case 0:
                    unreversedcard.add(0.12f);
                    pickcard.add(0.12f);
                    break;
                case 1:
                    unreversedcard.add(1.12f);
                    pickcard.add(1.12f);
                    break;
                case 2:
                    unreversedcard.add(2.12f);
                    pickcard.add(2.12f);
                    break;
                case 3:
                    unreversedcard.add(3.12f);
                    pickcard.add(3.12f);
                    break;
                case 4:
                    unreversedcard.add(4.12f);
                    pickcard.add(4.12f);
                    break;
                case 5:
                    unreversedcard.add(5.12f);
                    pickcard.add(5.12f);
                    break;
                case 6:
                    unreversedcard.add(6.12f);
                    pickcard.add(6.12f);
                    break;
                case 7:
                    unreversedcard.add(7.12f);
                    pickcard.add(7.12f);
                    break;
                case 8:
                    unreversedcard.add(8.12f);
                    pickcard.add(8.12f);
                    break;
                case 9:
                    unreversedcard.add(9.12f);
                    pickcard.add(9.12f);
                    break;
                case 10:
                    unreversedcard.add(10.12f);
                    pickcard.add(10.12f);
                    break;
                case 11:
                    unreversedcard.add(11.12f);
                    pickcard.add(11.12f);
                    break;
            }
        } else if (LeftorRight.equals("r")) {
            switch (jokerinput) {
                case 0:
                    unreversedcard.add(0.22f);
                    pickcard.add(0.22f);
                    break;
                case 1:
                    unreversedcard.add(1.22f);
                    pickcard.add(1.22f);
                    break;
                case 2:
                    unreversedcard.add(2.22f);
                    pickcard.add(2.22f);
                    break;
                case 3:
                    unreversedcard.add(3.22f);
                    pickcard.add(3.22f);
                    break;
                case 4:
                    unreversedcard.add(4.22f);
                    pickcard.add(4.22f);
                    break;
                case 5:
                    unreversedcard.add(5.22f);
                    pickcard.add(5.22f);
                    break;
                case 6:
                    unreversedcard.add(6.22f);
                    pickcard.add(6.22f);
                    break;
                case 7:
                    unreversedcard.add(7.22f);
                    pickcard.add(7.22f);
                    break;
                case 8:
                    unreversedcard.add(8.22f);
                    pickcard.add(8.22f);
                    break;
                case 9:
                    unreversedcard.add(9.22f);
                    pickcard.add(9.22f);
                    break;
                case 10:
                    unreversedcard.add(10.22f);
                    pickcard.add(10.22f);
                    break;
                case 11:
                    unreversedcard.add(11.22f);
                    pickcard.add(11.22f);
                    break;
            }
        }
    }
    
    public Cards getCards(){
        getCards.setCards(this.unreversedcard, this.reversedcard, this.pickcard);
        return getCards;
    }
    public void setCards(Cards cards){
        this.unreversedcard = cards.getUnreversedcard();
        this.reversedcard = cards.getReversedcard();
        this.pickcard = cards.getPickcard();
    }
    public static PlayerCard getPlayer1instance() {
        return player1card;
    }

    public static PlayerCard getPlayer2instance() {
        return player2card;
    }

    public ArrayList<Float> getUnReversedCard() {
        return unreversedcard;
    }

    public ArrayList<Float> getPickCard() {
        return pickcard;
    }

    public ArrayList<Float> getReversedCard() {
        return reversedcard;
    }

}
