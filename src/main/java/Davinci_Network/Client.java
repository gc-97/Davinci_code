/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_Network;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client{
    private Socket socket;
    private sendObjects ob;
    private SendThread s;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String ip;
    private ReceiveThread recThread;
    public Client(){
    }
    public void connect(){
        try{
            socket = new Socket(this.ip, 5000);
            
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            
            recThread = new ReceiveThread(socket, ois);

            recThread.start();
            
        }catch(Exception e){}
        s = new SendThread(socket, oos);
    }
    public void setIp(String ip){
        this.ip = ip;
    }
    public void send(sendObjects ob){
        s.send(ob);
    }
    public ReceiveThread getRecThread(){
        return this.recThread;
    }
    public boolean isConnect(){
        return socket.isConnected();
    }
    public static void main(String[] argv){
        Client c = new Client();
        sendObjects ob = new sendObjects();
        c.send(ob);
        c.send(ob);
    }
}