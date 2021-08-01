/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_Gui;

import java.awt.Point;
import javax.swing.JFrame;

/**
 *
 * @author chung
 */
public class NewCardMark extends GameObject_GUI{
    
    public NewCardMark(String num, String front_image, JFrame frame){
        super(num, front_image, front_image, frame);
    }
    
    @Override
    public void setLocation(Point point){
        this.point = point;
        this.point.translate(25, -45);
        label_front.setBounds(this.point.x, this.point.y, 30, 30);
        label_back.setBounds(this.point.x, this.point.y, 30, 30);
    }

}
