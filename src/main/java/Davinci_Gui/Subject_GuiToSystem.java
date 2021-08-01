/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_Gui;

/**
 *
 * @author chung
 */
public interface Subject_GuiToSystem {
    void registerObserver(Observer_GuiToSystem observer);
    void removeObserver(Observer_GuiToSystem observer);
    void notifyObservers();
}
