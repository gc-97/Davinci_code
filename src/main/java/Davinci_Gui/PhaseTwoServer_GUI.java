package Davinci_Gui;
import Davinci_Network.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//게임의 두번째 화면인 서버 화면을 담당
public class PhaseTwoServer_GUI extends Sequence_GUI {
    private DefaultListModel list = new DefaultListModel();
    private JPanel northPanel;
    private JPanel southPanel;
    private JLabel label_playerList;
    private JList list_playerList;
    private JButton butt_connect;
    private JButton butt_ready;
    private JLabel label_connected;
    private JButton butt_refresh;
    private Server server;
    
    public PhaseTwoServer_GUI(Server server){
       this.server = server;
       northPanel = new JPanel();
       southPanel = new JPanel();
       label_playerList = new JLabel("플레이어 목록");
       list_playerList = new JList();
       butt_connect = new JButton("연결");
       butt_ready = new JButton("준비");
       butt_refresh = new JButton("새로고침");
       label_connected = new JLabel("연결되었습니다.");
       
       //여기에 서버의 듣기 기능 추가
       
       setInit();
    }
    //컴퍼넌트를 설정하거나 패널에 추가하는 메소드
    public void addInit(){
       butt_ready.setEnabled(false);
       list_playerList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
       northPanel.add(label_playerList);
       //northPanel.add(list_playerList);
       northPanel.add(butt_refresh);
       southPanel.add(butt_connect);
       southPanel.add(butt_ready);
       
    }
    //이벤트 메소드
    public void event(){
        //리스트 클릭 이벤트(JListHandler 내부클래스를 불러옴)
        list_playerList.addListSelectionListener(new JListHandler());
        
        //연결 버튼 이벤트
        butt_connect.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                server.connect();
                connected();
                
                butt_ready.setEnabled(true);
            }
        });
        //다음 화면으로 넘어가는 버튼 이벤트
        butt_ready.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                PhaseThree_GUI last = new PhaseThree_GUI(server);
                last.go();
                frame.dispose();
            }
        });
        //새로고침 버튼 이벤트
        butt_refresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                list.addElement("야호");
                list.addElement("메롱");
                list_playerList.setModel(list);
                list_playerList.validate();
            }
        });
    }
    //리스트 클릭 이벤트에 사용되는 내부클래스
    private class JListHandler implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent event) {
            if(!event.getValueIsAdjusting())
                System.out.println("선택한 플레이어는 " + list_playerList.getSelectedValue() + "입니다.");
        }
        
    }
    public void connected(){
        southPanel.add(label_connected);
        redisplay();
    }
    @Override
    public void display() {
        frame.setSize(300, 300);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(northPanel, BorderLayout.NORTH);
        frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
        redisplay();
    }
}
