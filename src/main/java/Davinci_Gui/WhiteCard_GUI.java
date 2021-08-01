package Davinci_Gui;

import javax.swing.JFrame;

public class WhiteCard_GUI extends GameObject_GUI {
    private static String back = "\\wb.png";
    
    public WhiteCard_GUI(String num, String front_image, JFrame frame) {
        super(num, front_image, back , frame);
    }

}