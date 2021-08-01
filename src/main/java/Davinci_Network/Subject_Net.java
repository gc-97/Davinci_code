/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_Network;


/**
 *
 * @author chung
 */
public interface Subject_Net {
    
    public void addObserver(Observer_Net o);
    public void removeObserver(Observer_Net o);
    public void notifyObservers();
    
}
