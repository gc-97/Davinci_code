/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_Network;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author chung
 */
public class ReceiveThread extends Thread implements Subject_Net{
    private Socket socket;
    private ObjectInputStream ois;
    private sendObjects ob;
    private ArrayList<Observer_Net> observers;
    
    public ReceiveThread(Socket socket, ObjectInputStream ois){
        this.socket = socket;
        this.ois = ois;
        observers = new ArrayList<>();
    }
   
    @Override
    public void run(){
        super.run();
        try{
            while(true){
                System.out.println("readObject 실행됨");
                this.ob = (sendObjects)ois.readObject();
                System.out.println("받은 ob는 : " + this.ob.toString());
                notifyObservers();
            }
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    @Override
    public void addObserver(Observer_Net o){
        observers.add(o);
    }
    @Override
    public void removeObserver(Observer_Net o){
        int i = observers.indexOf(o);
        if(i>=0){
            observers.remove(i);
        }
    }
    @Override
    public void notifyObservers(){
        System.out.println("리시브 옵저버 호출");
        for(Observer_Net observer : observers){
            observer.update(ob);
        }
    }
    public void setSocket(Socket socket){
        this.socket = socket;
    }
    
    public sendObjects getObject(){
        return this.ob;
    }
}
