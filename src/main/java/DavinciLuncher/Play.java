/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DavinciLuncher;
import Davinci_Network.Client;
import Davinci_Network.Server;
import Davinci_Gui.PhaseOne_GUI;
/**
 *
 * @author chung
 */
public class Play {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Server server = new Server();
        Client client = new Client();
        PhaseOne_GUI game = new PhaseOne_GUI(server, client);
        game.display();
    }
    
}
