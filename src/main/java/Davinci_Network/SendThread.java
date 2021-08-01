/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_Network;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author chung
 */
public class SendThread{
    private Socket socket;
    private sendObjects ob;
    private ObjectOutputStream oos;
    
    public SendThread(Socket socket, ObjectOutputStream oos){
        this.socket = socket;
        this.oos = oos;
    }
    
    public void send(sendObjects ob){
        System.out.println("send실행");
        this.ob = ob;
        System.out.println("보낼 ob는 :" + ob.toString());
        try{
            oos.reset();
            oos.writeObject(this.ob);
            oos.flush();
        }catch(Exception e){e.printStackTrace();}
    }
    public void setSocket(Socket socket){
        this.socket = socket;
    }
    
}
