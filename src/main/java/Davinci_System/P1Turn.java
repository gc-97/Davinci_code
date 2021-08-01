/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_System;

/**
 *
 * @author PSJ
 */
public class P1Turn implements  TurnState{
    private TableDeck TD = TableDeck.getinstance();
    private PlayerCard pcard1 = PlayerCard.getPlayer1instance();
    
    public void ShowCard()
    {
            System.out.println("\n플레이어1이 여태까지 뽑은 카드 카드 ");
            System.out.println("---------------------");
            for (int i = 0; i < pcard1.pickcard.size(); i++) {
                System.out.println(pcard1.pickcard.get(i));
            }                                                                      // 플레이어 카드 전부 출력

            System.out.println("플레이어1의 간파당한 카드  ");
            System.out.println("---------------------");

            for (int i = 0; i < pcard1.reversedcard.size(); i++) {
                System.out.println(pcard1.reversedcard.get(i));
            }                                                                      // 플레이어 카드 전부 출력

            System.out.println("플레이어1의 소유하고있는 카드 ");
            System.out.println("---------------------");
            for (int i = 0; i < pcard1.unreversedcard.size(); i++) {
                System.out.println(pcard1.unreversedcard.get(i));
            }                                                                      // 플레이어 카드 전부 출력

            System.out.println("--BlackDeckCard--");
            for (int i = 0; i < TD.BlackDeck.size(); i++) {
                System.out.println(TD.BlackDeck.get(i));
            }
            System.out.println("-WhiteDeckCard-");
            for (int i = 0; i < TD.WhiteDeck.size(); i++) {
                System.out.println(TD.WhiteDeck.get(i));
            }                                                                      //테이블 덱 전부 출력                               
    };
    
    
    public void CheckTurnForGame(){
        System.out.println("Player1의 차례입니다.");
    }
    
    
}
