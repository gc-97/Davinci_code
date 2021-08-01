package Davinci_Gui;
import javax.swing.*;

public abstract class Sequence_GUI implements DisplayElement_GUI {
    protected static JFrame frame = new JFrame("Davinci");
    
    protected abstract void addInit();
    protected abstract void event();
    
    public void setInit(){
        addInit();
        event();
    }
    
    @Override
    public abstract void display();

    @Override
    public void redisplay(){
        frame.revalidate();
        frame.repaint();
    }

}
