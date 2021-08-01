package Davinci_Gui;

import javax.swing.JFrame;
//블랙카드 클래스
public class BlackCard_GUI extends GameObject_GUI {
    private static String back = "\\bb.png";
    
    public BlackCard_GUI(String num, String front_image, JFrame frame) {
        super(num, front_image, back , frame);
    }

}
