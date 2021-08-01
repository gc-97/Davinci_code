package Davinci_Gui;
import Davinci_Network.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//게임의 두번째 화면인 클라이언트 화면을 담당
public class PhaseTwoClient_GUI extends Sequence_GUI {
    private JPanel panel;
    private JTextField text;
    private JButton butt_search;
    private JLabel label_waiting;
    private JLabel label_connected;
    private JLabel label_connectError;
    private JButton butt_ready;
    private String isIP = "^([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\."
                        + "([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\."
                        + "([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\."
                        + "([01]?\\d?\\d|2[0-4]\\d|25[0-5])$";  //IP주소 확인하는 표현식
    private String ip;
    private Client client;
    
    public PhaseTwoClient_GUI(Client client){
        this.client = client;
        panel = new JPanel();
        text = new JTextField(20);
        butt_search = new JButton("찾기");
        label_waiting = new JLabel("기다리는중...");
        label_connected = new JLabel("연결됨!");
        label_connectError = new JLabel("연결오류!");
        butt_ready = new JButton("준비");
        
        setInit();
    }
    //컴퍼넌트를 설정하거나 패널에 추가하는 메소드
    public void addInit(){
        butt_ready.setEnabled(false);
        
        panel.add(text);
        panel.add(butt_search);
        panel.add(butt_ready);
    }
    //이벤트 메소드
    public void event(){
        //IP를 검색하는 버튼 이벤트
        butt_search.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ip = text.getText();
                if(ip.matches(isIP)){               //아이피 표현 확인
                    waiting();
                    client.setIp(ip);
                    client.connect();
                    if(client.isConnect())  connected();
                    else    connect_error();
                    butt_ready.setEnabled(true);
                }
                else    JOptionPane.showMessageDialog(null, "IP주소를 잘못 입력하였습니다.");   //메시지 박스 출력
            }
        });
        //다음 화면으로 넘어가는 버튼 이벤트
        butt_ready.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                PhaseThree_GUI last = new PhaseThree_GUI(client);
                last.go();
                frame.dispose();
            }
        });
    }
    //연결되었다는 메소드
    public void connected(){
        panel.remove(label_waiting);
        panel.add(label_connected);
        redisplay();
    }
    //기다리는 중인 메소드
    public void waiting(){
        panel.remove(label_connected);
        panel.add(label_waiting);
        redisplay();
    }
    //연결 오류라는 메소드
    public void connect_error(){
        panel.remove(label_waiting);
        panel.remove(label_connected);
        panel.add(label_connectError);
        redisplay();
    }
    @Override
    public void display() {
        frame.setSize(300, 300);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        redisplay();
    }
}
