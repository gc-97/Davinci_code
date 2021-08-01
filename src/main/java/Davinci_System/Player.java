/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_System;

import Davinci_Gui.*;
import Davinci_Network.*;
/**
 *
 * @author PSJ
 */
import java.util.ArrayList;
import java.util.Collections;

public class Player extends Thread implements Observer_GuiToSystem, Observer_GUI, Observer_Net {
    private Cards cards;
    private String whoAreYou;
    private GamePlaying_GUI gui;
    private ArrayList<Subject_GUI> subjectsGui;
    private GameObject_GUI selectObject;
    private CheckTurn turn = CheckTurn.getinstance();
    private TableDeck TD = TableDeck.getinstance();
    private PlayerCard pcard1 = PlayerCard.getPlayer1instance();
    private PlayerCard pcard2 = PlayerCard.getPlayer2instance();
    private float player1lastpick = -1;
    private float player2lastpick = -1;
    private String color = "empty";
    private String msg = "empty";
    private float selectCardNum;
    private sendObjects sendObject;
    private Server server;
    private Client client;
    private boolean can_player_draw = true; // 플레이어가 자기턴때 카드를 뽑았는지 확인하는 제어변수 카드를 뽑았으면 false 안뽑았으면 true로 변경된다.
    private boolean isSync = false;
    private String isMsg = "^([0-9]\\sl)|([0-9]\\sm)|([0-9]\\sr)|" +
                        "([1][0-1]\\sl)|([1][0-1]\\sm)|([1][0-1]\\sr)$";

    public Player(GamePlaying_GUI gui, Server server) {
        this.whoAreYou = "server";
        this.server = server;
        this.gui = gui;
        pcard1.setGui(gui);
        pcard2.setGui(gui);
        subjectsGui = gui.getSubjectList();
        for (Subject_GUI e : subjectsGui) {
            e.registerObserver(this);
        }
        gui.registerObserver(this);
        server.getRecThread().addObserver(this);
        sendObject = new sendObjects();
        
    }

    public Player(GamePlaying_GUI gui, Client client) {
        this.whoAreYou = "client";
        this.client = client;
        this.gui = gui;
        pcard1.setGui(gui);
        pcard2.setGui(gui);
        subjectsGui = gui.getSubjectList();
        for (Subject_GUI e : subjectsGui) {
            e.registerObserver(this);
        }
        gui.registerObserver(this);
        client.getRecThread().addObserver(this);
        sendObject = new sendObjects();
    }


    public void PlayerDraw() {
        if (whoAreYou.equals("server") && turn.Player1Turn) // 플레이어1 
        {
            this.gui.setEnabled(true);
            if (can_player_draw == true) {
                if (!TD.BlackDeck.isEmpty() || !TD.WhiteDeck.isEmpty()) {
                    gui.showState("카드를 뽑으세요");
                    if (this.color.equals("empty")) {
                        return;
                    }
                    String BlackorWhite = color;                                                 // 블랙카드,화이트 카드 선택을 위한 키입력
                    if (BlackorWhite.equals("b")) //b를 눌러 블랙카드를 뽑으려고한다면
                    {
                        if (!TD.BlackDeck.isEmpty()) //블랙카드덱이 비어있지않고
                        {
                            if ("JokerBlack".equals(TD.BlackDeck.get(0))) //뽑을 카드가 블랙 조커카드 인지 확인하고 
                            {
                                gui.showState("검은색 조커를 뽑았습니다.");

                                if (msg.equals("empty")) {
                                    return;
                                }
                                if(!msg.matches(isMsg)){
                                    gui.showState("다시 입력하세요.");
                                    msg = "empty";
                                    return;
                                }
                                pcard1.Bjoker(msg);                                                    //  조커카드이면 조커 처리 메소드 호출
                                msg = "empty";
                                playerlastpick();
                                Collections.sort(pcard1.pickcard);
                                Collections.sort(pcard1.unreversedcard);// 카드 정렬한다.
                            } else //조커카드가 아니면
                            {
                                pcard1.unreversedcard.add(Float.parseFloat(TD.BlackDeck.get(0)));         //테이블덱에서 한장 가져온다.
                                pcard1.pickcard.add(Float.parseFloat(TD.BlackDeck.get(0)));
                                playerlastpick();
                                Collections.sort(pcard1.pickcard);
                                Collections.sort(pcard1.unreversedcard);// 카드 정렬한다.
                            }
                            TD.BlackDeck.remove(0);                                                //조커든, 숫자카드든 카드를 한장 가져왔으니 테이블덱에 배열 한개를 삭제한다.
                        } else //블랙 카드덱이 비어있다면
                        {
                            color = "empty";
                            gui.showState("검은색 카드가 없습니다.");
                            return;
                        }
                    } else if (BlackorWhite.equals("w")) {
                        if (!TD.WhiteDeck.isEmpty()) {
                            if ("JokerWhite".equals(TD.WhiteDeck.get(0))) {
                                gui.showState("흰색 조커를 뽑았습니다.");

                                if (msg.equals("empty")) {
                                    return;
                                }
                                if(!msg.matches(isMsg)){
                                    gui.showState("다시 입력하세요.");
                                    msg = "empty";
                                    return;
                                }
                                pcard1.Wjoker(msg);
                                msg = "empty";
                                playerlastpick();
                                Collections.sort(pcard1.pickcard);
                                Collections.sort(pcard1.unreversedcard);// 카드 정렬한다.
                            } else {
                                pcard1.unreversedcard.add(Float.parseFloat(TD.WhiteDeck.get(0)));
                                pcard1.pickcard.add(Float.parseFloat(TD.WhiteDeck.get(0)));
                                playerlastpick();
                                Collections.sort(pcard1.pickcard);
                                Collections.sort(pcard1.unreversedcard);// 카드 정렬한다.
                            }
                            TD.WhiteDeck.remove(0); //테이블덱에 배열 한개를 삭제한다.
                        } else {
                            this.gui.showState("흰색 카드가 없습니다.");
                            return;
                        }
                    } else //블랙카드,화이트 카드 선택을 위한 키입력할때 b또는w를 입력하지않았을때
                    {
                    }

                    //플레이어가 정상적으로 카드를 드로우를 마쳤을때
                    this.color = "empty";
                    this.msg = "empty";
                    ShowCard();                                                               //플레이어카드,덱카드리스트를 보여주는 메소드 호출
                    //=====================================================================================================================================네트워크 동기화
                    synchronization();
                    this.can_player_draw = !this.can_player_draw;
                } else //양쪽 카드가 완전히 고갈되었을때 알려준다.
                {
                    this.gui.showState("뽑을 카드가 더 이상 없습니다.");
                    this.can_player_draw = !this.can_player_draw;
                }

            }
        }
        else if(whoAreYou.equals("server") && !turn.Player1Turn){
            this.gui.showState("상대의 턴입니다.");
            this.gui.setEnabled(false);
        }
        
        if (whoAreYou.equals("client") && turn.Player2Turn == true) // 플레이어2 
        {
            this.gui.setEnabled(true);
            if (can_player_draw == true) {
                if (!TD.BlackDeck.isEmpty() || !TD.WhiteDeck.isEmpty()) {
                    gui.showState("카드를 뽑으세요");
                    if (this.color.equals("empty")) {
                        return;
                    }
                    String BlackorWhite = color;
                    if (BlackorWhite.equals("b")) {
                        if (!TD.BlackDeck.isEmpty()) //블랙카드덱이 비어있지않다면
                        {
                            if ("JokerBlack".equals(TD.BlackDeck.get(0))) {
                                gui.showState("검은색 조커를 뽑았습니다.");

                                if (msg.equals("empty")) {
                                    return;
                                }
                                if(!msg.matches(isMsg)){
                                    gui.showState("다시 입력하세요.");
                                    msg = "empty";
                                    return;
                                }
                                pcard2.Bjoker(msg);
                                msg = "empty";                                                //  조커카드인지 체크하고
                                playerlastpick();
                                Collections.sort(pcard2.pickcard);
                                Collections.sort(pcard2.unreversedcard);// 카드 정렬한다.
                            } else {
                                pcard2.unreversedcard.add(Float.parseFloat(TD.BlackDeck.get(0)));         //테이블덱에서 한장 가져온다.
                                pcard2.pickcard.add(Float.parseFloat(TD.BlackDeck.get(0)));

                                playerlastpick();
                                Collections.sort(pcard2.pickcard);
                                Collections.sort(pcard2.unreversedcard);// 카드 정렬한다.
                            }
                            TD.BlackDeck.remove(0); //테이블덱에 배열 한개를 삭제한다.
                        } else {
                            this.gui.showState("검은색 카드가 없습니다.");
                            return;
                        }

                    } else if (BlackorWhite.equals("w")) {
                        if (!TD.WhiteDeck.isEmpty()) {
                            if ("JokerWhite".equals(TD.WhiteDeck.get(0))) {
                                gui.showState("흰색 조커를 뽑았습니다.");

                                if (msg.equals("empty")) {
                                    return;
                                }
                                if(!msg.matches(isMsg)){
                                    gui.showState("다시 입력하세요.");
                                    msg = "empty";
                                    return;
                                }
                                pcard2.Wjoker(msg);//  조커카드인지 체크하고
                                msg = "empty";
                                playerlastpick();
                                Collections.sort(pcard2.pickcard);
                                Collections.sort(pcard2.unreversedcard);// 카드 정렬한다.
                            } else {
                                pcard2.unreversedcard.add(Float.parseFloat(TD.WhiteDeck.get(0)));         //테이블덱에서 한장 가져온다.
                                pcard2.pickcard.add(Float.parseFloat(TD.WhiteDeck.get(0)));

                                playerlastpick();
                                Collections.sort(pcard2.pickcard);
                                Collections.sort(pcard2.unreversedcard);// 카드 정렬한다.
                            }
                            TD.WhiteDeck.remove(0); //테이블덱에 배열 한개를 삭제한다.
                        } else {
                            this.gui.showState("흰색 카드가 없습니다.");
                            return;
                        }
                    } else {
                    }

                    Collections.sort(pcard1.unreversedcard);// 카드 정렬한다.

                    this.color = "empty";
                    this.msg = "empty";
                    //=====================================================================================================================================네트워크 동기화
                    synchronization();
                    this.can_player_draw = !this.can_player_draw;
                } else {
                    this.gui.showState("뽑을 카드가 더 이상 없습니다.");
                    this.can_player_draw = !this.can_player_draw;
                }

                //플레이어카드,덱카드리스트를 보여준다. 보여준다.
            }
        }
        else if(whoAreYou.equals("client") && !turn.Player2Turn){
            this.gui.showState("상대의 턴입니다.");
            this.gui.setEnabled(false);
        }
    }

    public void PlayerGuess() {
        String str;
        str = msg;
        if (whoAreYou.equals("server") && turn.Player1Turn && !can_player_draw) //플레이어 1의 턴일때
        {
            gui.showState("추리하세요.");
            if (str.equals("empty")) {
                return;
            }
            float guessnum = 0;                             // 추측한 카드숫자를 담을 변수
            float cardnum = 0;                             // 실제카드숫자를 담을 변수
            int cardorder = pcard2.unreversedcard.indexOf(selectCardNum);                             //지목할 카드를 고르기위한 변수  

            cardnum = this.selectCardNum;                          //지목한 카드의 번호를 cardnum에 저장
            this.gui.showState("선택한 카드의 번호를 적으세요");
            float cha = cardnum - (int) cardnum;
            cha = Math.round(cha * 100) / 100.0f;

            if (cha == 0.01f || cha == 0.02f || cha == 0.11f || cha == 0.12f || cha == 0.21f || cha == 0.22f) //조커일때
            {

                if ("joker".equals(str)) //사용자가 조커라고 입력하면 
                {
                    guessnum = cardnum;                                  //정답으로 처리

                }

            } else //조커가 아닐때 일반적으로 처리
            {
                try{
                    guessnum = Float.parseFloat(str);
                }catch(Exception e){
                    this.gui.showState("추리가 실패했습니다.");
                    System.out.println("실행");
                    can_player_draw = true;                                                   //턴을 넘기므로 상대방이 카드를 뽑을수 있도록하며 
                    turn.guess = false;                                                       //턴을 넘기기위해 guess를 false로 만들고
                    turn.isGuessTure();                                                     //턴을 갱신한다.
                    turn.CheckTurnForGame();                                                //확인을 위해 누구차례인지 출력한다.
                    synchronization();
                    this.msg = "empty";
                    this.color = "empty";
                    return;
                }
                cha = cardnum - (int) guessnum;
                cha = Math.round(cha * 100) / 100.0f;
                if (cha == 0.1f || cha == 0.2f) {
                    guessnum = cardnum;
                }
            }

            if (cardnum == guessnum) //추측한 숫자와 실제 숫자값이 같을때
            {
                this.gui.showState("추리가 성공했습니다.");
                this.msg = "empty";
                this.color = "empty";
                can_player_draw = false;                                                  //턴을 더 진행하는동안 카드 한장을 더못뽑도록 하고 
                try{
                    pcard2.reversedcard.add(pcard2.unreversedcard.get(cardorder));          //추리된 카드를 다른 reversedcard arraylist에 넣고
                    pcard2.unreversedcard.remove(cardorder);                                //추측한 카드를 제거하도록하고
                }catch(Exception e){}
                turn.guess = true;                                                        //턴유지를 위하여 Guess를 true 만들고
                turn.isGuessTure();                                                     //턴을 갱신한다.
                turn.CheckTurnForGame();                                                //확인을 위하여 누구차례인지 출력한다.
                //=====================================================================================================================================네트워크 동기화
                synchronization();
            } else {
                this.gui.showState("추리가 실패했습니다.");
                this.msg = "empty";
                this.color = "empty";
                can_player_draw = true;                                                   //턴을 넘기므로 상대방이 카드를 뽑을수 있도록하며 
                turn.guess = false;                                                       //턴을 넘기기위해 guess를 false로 만들고
                turn.isGuessTure();                                                     //턴을 갱신한다.
                turn.CheckTurnForGame();                                                //확인을 위해 누구차례인지 출력한다.
                synchronization();
            }
        } else if (whoAreYou.equals("client") && turn.Player2Turn && !can_player_draw) {
            gui.showState("추리하세요.");
            if (str.equals("empty")) {
                return;
            }
            float guessnum = 0;                             // 추측한 카드숫자를 담을 변수
            float cardnum = 0;                             // 실제카드숫자를 담을 변수
            int cardorder = pcard1.unreversedcard.indexOf(selectCardNum);                             //지목할 카드를 고르기위한 변수  

            cardnum = this.selectCardNum;                          //지목한 카드의 번호를 cardnum에 저장
            this.gui.showState("선택한 카드의 번호를 적으세요");
            float cha = cardnum - (int) cardnum;
            cha = Math.round(cha * 100) / 100.0f;

            if (cha == 0.01f || cha == 0.02f || cha == 0.11f || cha == 0.12f || cha == 0.21f || cha == 0.22f) //조커일때
            {
                if ("joker".equals(str)) //사용자가 조커라고 입력하면 
                {
                    guessnum = cardnum;                                  //정답으로 처리

                }

            } else //조커가 아닐때 일반적으로 처리
            {
                try{
                    guessnum = Float.parseFloat(str);
                }catch(Exception e){
                    this.gui.showState("추리가 실패했습니다.");
                    can_player_draw = true;                                                   //턴을 넘기므로 상대방이 카드를 뽑을수 있도록하며 
                    turn.guess = false;                                                       //턴을 넘기기위해 guess를 false로 만들고
                    turn.isGuessTure();                                                     //턴을 갱신한다.
                    turn.CheckTurnForGame();                                                //확인을 위해 누구차례인지 출력한다.
                    synchronization();
                    this.msg = "empty";
                    this.color = "empty";
                    return;
                }
                cha = cardnum - (int) guessnum;
                cha = Math.round(cha * 100) / 100.0f;
                if (cha == 0.1f || cha == 0.2f) {
                    guessnum = cardnum;
                }
            }

            if (cardnum == guessnum) {
                this.gui.showState("추리가 성공했습니다.");
                this.msg = "empty";
                this.color = "empty";
                can_player_draw = false;
                try{
                    pcard1.reversedcard.add(pcard1.unreversedcard.get(cardorder));
                    pcard1.unreversedcard.remove(cardorder);
                }catch(Exception e){}
                turn.guess = true;
                turn.isGuessTure();
                turn.CheckTurnForGame();
                //=====================================================================================================================================네트워크 동기화
                synchronization();

            } else {
                this.gui.showState("추리가 실패했습니다.");
                this.msg = "empty";
                this.color = "empty";
                can_player_draw = true;
                turn.guess = false;
                turn.isGuessTure();
                turn.CheckTurnForGame();
                synchronization();
            }

            this.msg = "empty";
        }

    }

    public void ShowCard() {
        if (turn.Player1Turn == true) {
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
        } else {
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
        }
    }

    public void playerlastpick() {
        if (turn.Player1Turn == true) {
            player1lastpick = pcard1.unreversedcard.get(pcard1.unreversedcard.size() - 1);   //방금 뽑은 카드를 변수에 저장한다.
        } else {
            player2lastpick = pcard2.unreversedcard.get(pcard2.unreversedcard.size() - 1);
        }

    }
    
    public void setCardsLocation(){
        if(TD.BlackDeck.isEmpty()){
            gui.BlackIsEmpty();
        }
        if(TD.WhiteDeck.isEmpty()){
            gui.WhiteIsEmpty();
        }
        if (whoAreYou.equals("server")) {
            this.gui.setMark(this.player2lastpick);
            this.gui.set_card_locations(pcard1.getPickCard(), pcard2.getPickCard(), pcard2.getReversedCard());
        } else if (whoAreYou.equals("client")) {
            this.gui.setMark(this.player1lastpick);
            this.gui.set_card_locations(pcard2.getPickCard(), pcard1.getPickCard(), pcard1.getReversedCard());
        }
        this.gui.display();
    }
    public void synchronization() {
        this.sendObject.setObjects(this.turn.isPlayer1Turn(), this.turn.isPlayer2Turn(), this.TD, this.pcard1.getCards(), this.pcard2.getCards(), this.player1lastpick, this.player2lastpick);
        if(whoAreYou.equals("server")){
            server.send(this.sendObject);
        }
        else if(whoAreYou.equals("client")){
            client.send(this.sendObject);
        }
    }

    public CheckTurn getTurn() {
        return turn;
    }

    public PlayerCard getPcard1() {
        return pcard1;
    }

    public PlayerCard getPcard2() {
        return pcard2;
    }

    public float getPlayer1lastpick() {
        return player1lastpick;
    }
    
    public float getPlayer2lastpick() {
        return player2lastpick;
    }
    
    public boolean isSync(){
        return this.isSync;
    }
    
    //카드 선택 옵저버
    @Override
    public void update(GameObject_GUI object) {
        System.out.println("Player에서 클릭 옵저버 실행");
        this.msg = "empty";
        if (object.equals(null)) {
            return;
        }
        this.selectObject = object;

        if (this.selectObject.getNum() == "bd") {
            this.color = "b";
        } else if (this.selectObject.getNum() == "wd") {
            this.color = "w";
        }
        if (!this.selectObject.getNum().equals("bd") && !this.selectObject.getNum().equals("wd")) {
            this.selectCardNum = Float.parseFloat(object.getNum());
        }
    }

    //메시지 옵저버
    @Override
    public void update(String message) {
        System.out.println("메시지 옵저버 실행");
        this.msg = message;
    }
    
    public void linkObject(){
        this.sendObject = client.getRecThread().getObject();
        this.turn.setPlayer1Turn(this.sendObject.isPlayer1Turn());
        this.turn.setPlayer2Turn(this.sendObject.isPlayer2Turn());
        this.TD.setTD(this.sendObject.getTD());
        this.pcard1.setCards(this.sendObject.getPcard1());
        this.pcard2.setCards(this.sendObject.getPcard2());
        this.player1lastpick = this.sendObject.getP1LastPick();
        this.player2lastpick = this.sendObject.getP2LastPick();
    }
    
    @Override
    public void update(sendObjects object) {
        System.out.println("리시브 옵저버 실행");
        this.sendObject.setObjects(object.isPlayer1Turn(), object.isPlayer2Turn(), object.getTD(), object.getPcard1(), object.getPcard2(), object.getP1LastPick(), object.getP2LastPick());
        this.turn.setPlayer1Turn(this.sendObject.isPlayer1Turn());
        this.turn.setPlayer2Turn(this.sendObject.isPlayer2Turn());
        this.TD.setTD(this.sendObject.getTD());
        this.pcard1.setCards(this.sendObject.getPcard1());
        this.pcard2.setCards(this.sendObject.getPcard2());
        this.player1lastpick = this.sendObject.getP1LastPick();
        this.player2lastpick = this.sendObject.getP2LastPick();
        this.isSync=true;
        this.gui.resetEnable();
    }
    
    @Override
    public void run() {
        System.out.println("player run");
        
        while (true) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            setCardsLocation();
            PlayerDraw();
            PlayerGuess();
            
            if (pcard2.unreversedcard.isEmpty()) //2번 플레이어 카드가 바닥나면
            {
                setCardsLocation();
                if(whoAreYou.equals("server")){
                    gui.showState("승리");
                }
                else{
                    gui.showState("패배");
                    this.gui.set_card_locations(pcard1.getPickCard(), pcard2.getPickCard(), pcard2.getPickCard());
                }
                System.out.println("플레이어1 승리");//1번 플레이어 승리
                break;
            } else if (pcard1.unreversedcard.isEmpty()) //1번 플레이어 카드가 바닥나면
            {
                setCardsLocation();
                if(whoAreYou.equals("client")){
                    gui.showState("승리");
                }
                else{
                    gui.showState("패배");
                    this.gui.set_card_locations(pcard2.getPickCard(), pcard1.getPickCard(), pcard1.getPickCard());
                }
                
                System.out.println("플레이어2 승리");// 2번 플레이어 승리
                break;
            }
        }
    }

}
