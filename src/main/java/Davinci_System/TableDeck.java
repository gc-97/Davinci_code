/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_System;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author PSJ
 */
public class TableDeck implements Serializable{
    ArrayList<String> NumberBlackCard = new ArrayList<>();  
    ArrayList<String> NumberwhiteCard = new ArrayList<>();  
    String JokerWhite="JokerWhite";
    String JokerBlack="JokerBlack";
    ArrayList<String> BlackDeck = new ArrayList<>();
    ArrayList<String> WhiteDeck = new ArrayList<>();
    private static final long serialVersionUID = 1L;
    
    private  static  TableDeck TDeck = new TableDeck(); //싱글톤 생성

    
    private TableDeck()             //생성자를 private 한 이유는 싱글톤을 넣기 위해서이다.
    {   for(int i = 0 ; i <12 ; i++)
        {
        NumberBlackCard.add(Float.toString((float) (i+0.1)));     
        NumberwhiteCard.add(Float.toString((float) (i+0.2)));     //숫자카드 반복문을 통해 생성
         
        }
    
        BlackDeck.addAll(NumberBlackCard);
        BlackDeck.add(JokerBlack);
        WhiteDeck.addAll(NumberwhiteCard);
        WhiteDeck.add(JokerWhite);        //화이트,블랙덱에 숫자,조커카드 넣기

        Collections.shuffle(BlackDeck); 
        Collections.shuffle(WhiteDeck); //화이트,블랙덱 뒤섞기
    
    
    
    
         System.out.println("--BlackDeckCard--");
        for (int index = 0; index < BlackDeck.size(); index++) 
        {
           System.out.println(BlackDeck.get(index));
        }   
        System.out.println("--WhiteDeckCard--");
        for (int index = 0; index < WhiteDeck.size(); index++) 
        {
           System.out.println(WhiteDeck.get(index));
        }                                              // 화이트,블랙덱 생성이 제대로 확인하기위해 전부 출력하기
                                    

    
    } 
    
    public static TableDeck getinstance()  
    {
      return TDeck;
    }
    
   
    public ArrayList<String> getBlackDeck()
    {
        return BlackDeck;  
    }
    
    public ArrayList<String> getWhiteDeck()
    {
        return WhiteDeck;
    }
    
    public void setTD(TableDeck td){
        this.BlackDeck = td.BlackDeck;
        this.WhiteDeck = td.WhiteDeck;
        this.NumberBlackCard = td.NumberBlackCard;
        this.NumberwhiteCard = td.NumberwhiteCard;
        this.JokerBlack = td.JokerBlack;
        this.JokerWhite = td.JokerWhite;
    }
}
