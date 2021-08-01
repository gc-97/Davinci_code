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
public class P2Turn implements TurnState {
    
    private TableDeck TD = TableDeck.getinstance();
    private PlayerCard pcard2 = PlayerCard.getPlayer2instance();
    
    public void ShowCard()
    {
            System.out.println("\n플레이어2가 여태까지 뽑은 카드 카드 ");
            System.out.println("---------------------");
            for (int i = 0; i < pcard2.pickcard.size(); i++) {
                System.out.println(pcard2.pickcard.get(i));
            }                                                                      // 플레이어 카드 전부 출력

            System.out.println("플레이어2가 간파당한 카드  ");
            System.out.println("---------------------");

            for (int i = 0; i < pcard2.reversedcard.size(); i++) {
                System.out.println(pcard2.reversedcard.get(i));
            }

            System.out.println("플레이어2가소유하고있는 카드 ");
            System.out.println("---------------------");
            for (int i = 0; i < pcard2.unreversedcard.size(); i++) {
                System.out.println(pcard2.unreversedcard.get(i));
            }                                                                      // 플레이어 카드 전부 출력

            System.out.println("--BlackDeckCard--");
            for (int i = 0; i < TD.BlackDeck.size(); i++) {
                System.out.println(TD.BlackDeck.get(i));
            }
            System.out.println("--WhiteDeckCard--");
            for (int i = 0; i < TD.WhiteDeck.size(); i++) {
                System.out.println(TD.WhiteDeck.get(i));
            }
        
    };
    
    public void CheckTurnForGame(){
        System.out.println("Player2의 차례입니다.");
    }
    
    
}
