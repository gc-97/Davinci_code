package Davinci_Gui;
import Davinci_System.Player;
import Davinci_Network.*;

//GamePlaying_GUI를 사용하는 클래스
public class PhaseThree_GUI{
    GamePlaying_GUI gui;
    Player player;
    Server server;
    Client client;
    
    public PhaseThree_GUI(Server server){
        this.server = server;
        gui = new GamePlaying_GUI();
        player = new Player(gui, server);
    }
    public PhaseThree_GUI(Client client){
        this.client = client;
        gui = new GamePlaying_GUI();
        player = new Player(gui, client);
    }
    
    public void go(){
        if(client == null){
            System.out.println("초기값 세팅");
            player.getTurn().Flipthecoin();
            player.getTurn().CheckTurnForGame();
            System.out.println("초기값 전송");
            player.synchronization();
        }
        else{
            player.linkObject();
        }
        player.setCardsLocation();
        player.start();
    }
}
