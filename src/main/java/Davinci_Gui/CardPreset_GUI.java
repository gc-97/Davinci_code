/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Davinci_Gui;

import java.awt.Point;

/**
 *
 * @author chung
 */
public class CardPreset_GUI {
    public static final Point[] MYCARD_POINT_PRESET = new Point[13];
    public static final Point[] OPPONENTCARD_POINT_PRESET = new Point[13];
    public static final Point[] DECK_POINT_PRESET = new Point[2];
    
    static{
        DECK_POINT_PRESET[0] = new Point(520, 300);
        DECK_POINT_PRESET[1] = new Point(680, 300);
        for(int i=0; i<13; i++){
            MYCARD_POINT_PRESET[i] = new Point((i*95)+30, 540);
            OPPONENTCARD_POINT_PRESET[i] = new Point((i*95)+30, 60);
        }
    }
    public CardPreset_GUI() {
    }
    
    static Point[] getMyCard_points_presets() {
        return MYCARD_POINT_PRESET;
    }

    static Point[] getOpponentCard_points_presets() {
        return OPPONENTCARD_POINT_PRESET;
    }

    static Point[] getDeck_points_presets() {
        return DECK_POINT_PRESET;
    }
    
}
