package Davinci_Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePlaying_GUI implements DisplayElement_GUI, Observer_GUI, Subject_GuiToSystem{
    public static final int cardNum = 13;
    protected static JFrame frame = new JFrame("Davinci");
    private ArrayList<Subject_GUI> subjects;
    private NewCardMark cardMark;
    private ArrayList<BlackCard_GUI> blackCards;
    private ArrayList<WhiteCard_GUI> whiteCards;
    private ArrayList<GameObject_GUI> allCards;
    private BlackCard_GUI deckBlackCard;
    private WhiteCard_GUI deckWhiteCard;
    private String[] blackCard_locations = {"\\b0.png", "\\b1.png", "\\b2.png", "\\b3.png", "\\b4.png", "\\b5.png", "\\b6.png", "\\b7.png", "\\b8.png", "\\b9.png", "\\b10.png", "\\b11.png", "\\bj.png"};
    private String[] whiteCard_locations = {"\\w0.png", "\\w1.png", "\\w2.png", "\\w3.png", "\\w4.png", "\\w5.png", "\\w6.png", "\\w7.png", "\\w8.png", "\\w9.png", "\\w10.png", "\\w11.png", "\\wj.png"};
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private String input_text;
    private JLabel labelState;
    private GameObject_GUI selectObject;
    private ArrayList<Observer_GuiToSystem> observers;
    private String message;
    //private CardBunch_GUI cardBunch;
    
    
    public GamePlaying_GUI() {
        //프레임의 setSize로는 가용 영역의 크기가 맞지 않아서 이렇게 프레임 영역을 늘림
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(1280, 720));
        frame.add(p);
        frame.pack();
        frame.remove(p);
        //끝
        subjects = new ArrayList<>();
        observers = new ArrayList<>();
        cardMark = new NewCardMark("mark", "\\mark.png", frame);
        blackCards = new ArrayList<>();
        whiteCards = new ArrayList<>();
        allCards = new ArrayList<>();
        deckBlackCard = new BlackCard_GUI("bd", "\\bb.png", frame);
        deckWhiteCard = new WhiteCard_GUI("wd", "\\wb.png", frame);
        selectObject = null;
        
        textField = new JTextField(input_text);
        button = new JButton("확인");
        label = new JLabel("<html>*추리할 카드를 선택하고 아래에 카드 숫자를 입력하세요<br/>조커는 \"joker\"라고 입력합니다.<br/>*조커를 뽑았을 경우, 위치를 입력해주세요.<br/>위치는 기준이 될 카드, 오른쪽(r)or왼쪽(l)or가운데(m)를 입력하세요.<br/>ex) \"1 l\" 또는 \"10 m\"</html>");
        labelState = new JLabel();
        
        event();
        add_init();
        //카드 생성
        for(int i=0; i<cardNum; i++){
            if(i==cardNum-1){
                blackCards.add(new BlackCard_GUI("JokerBlack", blackCard_locations[i], frame));
                whiteCards.add(new WhiteCard_GUI("JokerWhite", whiteCard_locations[i], frame));
                break;
            }
            blackCards.add(new BlackCard_GUI(i + ".1", blackCard_locations[i], frame));
            whiteCards.add(new WhiteCard_GUI(i + ".2", whiteCard_locations[i], frame));
        }
        
        
        //각 카드를 옵저버에 연결 및 카드뭉치에 포함
        for(BlackCard_GUI e : blackCards){
            allCards.add(e);
            subjects.add(e);
        }
        for(WhiteCard_GUI e : whiteCards){
            allCards.add(e);
            subjects.add(e);
        }
        subjects.add(deckWhiteCard);
        subjects.add(deckBlackCard);
        for(Subject_GUI e : subjects){
            e.registerObserver(this);
        }
    }
    
    public void add_init(){
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        
        label.setBounds(50, 250, 350, 160);
        textField.setBounds(50, 400, 100, 30);
        button.setBounds(160, 400, 60, 30);
        labelState.setBounds(950, 300, 280, 100);
        labelState.setFont(new Font("Serif", Font.BOLD, 20));
        labelState.setForeground(Color.red);
        
        frame.add(textField);
        frame.add(button);
        frame.add(label);
        frame.add(labelState);
    }
    public void event(){
        //입력창 입력버튼 이벤트
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                input_text = textField.getText();
                message = input_text;
                notifyObservers();
            }
        });
    }
    //손카드의 놓는 위치를 정렬하는 메소드
    public int card_index_check(ArrayList<Float> playerCards) {
        int index = 0;
        int n = playerCards.size();
        if(n == 1){
            index = 6;
        }
        else if(n%2 == 0){
            index = 6-(n/2-1);
        }
        else if(n%2 == 1){
            index = 6-(n/2);
        }
        return index;
    }
    //카드번호로 카드 찾는 메소드
    public GameObject_GUI findObject(String str){
        GameObject_GUI obj = null;
        for(GameObject_GUI e : allCards){
            if(str.compareTo(e.getNum())==0){
                obj = e;
            }
        }
        if(obj!=null){
            return obj;
        }
        else{
            return null;
        }
    }
    //카드의 놓는 위치를 지정하는 메소드
    public void set_card_locations(ArrayList<Float> myCard, ArrayList<Float> opponentCard, ArrayList<Float> reverseCard){
        int myIndex = card_index_check(myCard);
        int opponentIndex = card_index_check(opponentCard);
        GameObject_GUI temp;
        for(Float f : myCard){
            if(myIndex > 12){
                System.out.println("myIndex overflow");
            }
            String str = f.toString();
            temp = findObject(str);
            if(temp==null){
                String lastStr = str.substring(str.length()-1);
                if(lastStr.equals("1")){         //검은색
                    setJoker("Black", f);
                }
                else if(lastStr.equals("2")){    //흰색
                    setJoker("White", f);
                }
                temp = findObject(str);
            }
            if(temp == null)    System.out.println("널체크");
            temp.setEnable(true);
            temp.setVisiable(true);
            temp.setFrontOrBack("front");
            temp.setLocation(CardPreset_GUI.MYCARD_POINT_PRESET[myIndex++]);
        }
        for(Float f : opponentCard){
            if(myIndex > 12){
                System.out.println("opponentIndex overflow");
            }
            String str = f.toString();
            temp = findObject(str);
            if(temp==null){
                String lastStr = str.substring(str.length()-1);
                if(lastStr.equals("1")){         //검은색
                    setJoker("Black", f);
                }
                else if(lastStr.equals("2")){    //흰색
                    setJoker("White", f);
                }
                temp = findObject(str);
            }
            if(temp == null)    System.out.println("널체크");
            temp.setEnable(true);
            temp.setVisiable(true);
            temp.setFrontOrBack("back");
            temp.setLocation(CardPreset_GUI.OPPONENTCARD_POINT_PRESET[opponentIndex++]);
        }
        if(deckBlackCard != null)
            deckBlackCard.setLocation(CardPreset_GUI.DECK_POINT_PRESET[0]);
        if(deckWhiteCard != null)
            deckWhiteCard.setLocation(CardPreset_GUI.DECK_POINT_PRESET[1]);
        
        setFront(reverseCard);
        display();
    }
    public void resetEnable(){
        for(GameObject_GUI e : allCards){
            e.setEnable(false);
            e.setVisiable(false);
        }
    }
    public void BlackIsEmpty(){
        if(deckBlackCard != null){
            deckBlackCard.setEnable(false);
            deckBlackCard.setVisiable(false);
            deckBlackCard = null;
        }
    }
    public void WhiteIsEmpty(){
        if(deckWhiteCard != null){
            deckWhiteCard.setEnable(false);
            deckWhiteCard.setVisiable(false);
            deckWhiteCard = null;
        }
    }
    public void setFront(ArrayList<Float> opponentCard){
        GameObject_GUI temp;
        
        for(Float f : opponentCard){
            temp = findObject(f.toString());
            temp.setFrontOrBack("front");
        }
    }
    //마크 위치 세팅하는 메소드
    public void setMark(Float num){
        GameObject_GUI temp = findObject(num.toString());
        if(temp != null){
            cardMark.setLocation(temp.getLocation());
        }
        display();
    }
    
    public void setEnabled(boolean state){
        button.setEnabled(state);
        textField.setEnabled(state);
    }
    
    public void showState(String state){
        labelState.setText(state);
        redisplay();
    }
    
    public void setJoker(String color, float num){
        GameObject_GUI tmp;
        tmp = findObject("Joker" + color);
        tmp.setNum(num);
    }
    
    //필드에 카드를 보이게 하는 메소드
    @Override
    public void display() {
        for(GameObject_GUI e : allCards){
            e.display();
        }
        
        if(deckBlackCard != null)
            deckBlackCard.display();
        
        if(deckWhiteCard != null)
            deckWhiteCard.display();
        cardMark.display();
        redisplay();
    }
    @Override
    public void update(GameObject_GUI object) {
        System.out.println("업데이트");
        System.out.println(object.toString());
        selectObject = object;
    }
    
    public ArrayList<Subject_GUI> getSubjectList(){
        return this.subjects;
    }
    @Override
    public void redisplay() {
        frame.revalidate();
        frame.repaint();
    }
    
    public static void main(String args[]){
        ArrayList<Float> player = new ArrayList<>();
        ArrayList<Float> opponent = new ArrayList<>();
        ArrayList<Float> deck = new ArrayList<>();
        for(float i=0; i<13; i++){
            deck.add(i + 0.1f);
            deck.add(i + 0.2f);
        }
        
        player.add(3.1f);
        deck.remove(3.1f);
        player.add(1.2f);
        deck.remove(1.2f);
        player.add(10.1f);
        deck.remove(10.1f);
        player.add(4.2f);
        deck.remove(4.2f);
        opponent.add(0.2f);
        deck.remove(0.2f);
        opponent.add(1.1f);
        deck.remove(1.1f);
        opponent.add(6.1f);
        deck.remove(6.1f);
        opponent.add(7.1f);
        deck.remove(7.1f);
        
        GamePlaying_GUI go = new GamePlaying_GUI();
        
        ArrayList<Float> list1 = new ArrayList<>();
        ArrayList<Float> list2 = new ArrayList<>();
        list1.add(1.1f);
        
        go.set_card_locations(player, opponent, list1);
        go.setMark(10.1f);
        go.setEnabled(false);
        go.display();
        
        opponent.add(3.2f);
        go.set_card_locations(player, opponent, list1);
        go.redisplay();
        
        go.setFront(list1);
        go.display();
        
        go.showState("wrong");
        
        player.add(1.01f);
        go.setJoker("Black", 1.01f);
        go.set_card_locations(player, opponent, list1);
        go.redisplay();
    }

    @Override
    public void registerObserver(Observer_GuiToSystem observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer_GuiToSystem observer) {
        int index = observers.indexOf(observer);
        observers.remove(index);
    }

    @Override
    public void notifyObservers() {
        System.out.println("메시지 옵저버 호출");
        for(Observer_GuiToSystem observer : observers){
            observer.update(message);
        }
    }

}
