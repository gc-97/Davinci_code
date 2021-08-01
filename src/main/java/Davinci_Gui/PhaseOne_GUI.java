package Davinci_Gui;
import Davinci_Network.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//게임의 첫번째 기능을 하는 화면(서버와 클라이언트를 고르는 화면)
public class PhaseOne_GUI extends Sequence_GUI {
    private JPanel panel_north;
    private JPanel panel_center;
    private JButton butt_server;
    private JButton butt_client;
    private Server server;
    private Client client;
    
    public PhaseOne_GUI(Server server, Client client){
        this.server = server;
        this.client = client;
        
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);    //윈도우창이 정상적으로 종료하게 만듬
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);  //윈도우창의 이동불가
        frame.setVisible(true);             //윈도우창을 보이게 만듬
        frame.setLayout(new BorderLayout(0, 100));  //프레임의 배치를 구역별로 하게 함
        
        panel_north = new JPanel();
        panel_center = new JPanel();
        butt_server = new JButton("Server");
        butt_client = new JButton("Client");
        
        setInit();
    }
    //패널에 추가할 컴퍼넌트를 설정 및 추가
    public void addInit(){
        butt_server.setPreferredSize(new Dimension(200, 200));  //버튼 크기 설정
        butt_client.setPreferredSize(new Dimension(200, 200));
        panel_center.add(butt_server);
        panel_center.add(butt_client);
        
    }
    //이벤트 메소드
    public void event(){
        //서버 설정 버튼 이벤트
        butt_server.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                PhaseTwoServer_GUI next = new PhaseTwoServer_GUI(server);
                next.display();
            }
        });
        //클라이언트 설정 버튼 이벤트
        butt_client.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                PhaseTwoClient_GUI next = new PhaseTwoClient_GUI(client);
                next.display();
            }
        });
    }
    //보일 컴퍼넌트를 추가한 후 새로고침
    @Override
    public void display() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel_north, BorderLayout.NORTH);
        frame.getContentPane().add(panel_center, BorderLayout.CENTER);
        redisplay();
    }   
}
