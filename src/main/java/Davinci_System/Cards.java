/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_System;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author chung
 */
public class Cards implements Serializable{
    private ArrayList<Float> unreversedcard;
    private ArrayList<Float> reversedcard;
    private ArrayList<Float> pickcard;
    
    private static final long serialVersionUID = 1L;
    
    public void setCards(ArrayList<Float> unreversedcard, ArrayList<Float> reversedcard, ArrayList<Float> pickcard){
        this.unreversedcard = unreversedcard;
        this.reversedcard = reversedcard;
        this.pickcard = pickcard;
    }

    public ArrayList<Float> getUnreversedcard() {
        return unreversedcard;
    }

    public ArrayList<Float> getReversedcard() {
        return reversedcard;
    }

    public ArrayList<Float> getPickcard() {
        return pickcard;
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        
        str.append("PickCard : " + pickcard).append(", Reverse : " + reversedcard).append(", Unreverse : " + unreversedcard);
        return str.toString();
    }
    
}
