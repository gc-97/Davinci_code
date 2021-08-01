/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_Network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private sendObjects ob;
    private SendThread s;
    private ReceiveThread recThread;
    
    public Server(){
        
    }
    public void connect(){
        try{
            serverSocket = new ServerSocket(5000);
            socket = serverSocket.accept();
            
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            recThread = new ReceiveThread(socket, ois);

            recThread.start();
        }catch(Exception e){}
        s = new SendThread(socket, oos);
    }

    public ReceiveThread getRecThread(){
        return this.recThread;
    }
    public void send(sendObjects ob){
        s.send(ob);
    }
    public static void main(String[] argv){
        Server s = new Server();
        sendObjects ob = new sendObjects();
        s.send(ob);
        s.send(ob);
    }
}