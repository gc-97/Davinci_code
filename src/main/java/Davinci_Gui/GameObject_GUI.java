package Davinci_Gui;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//실질적으로 오브젝트를 생성하는 클래스
public class GameObject_GUI implements Subject_GUI, DisplayElement_GUI {
    private ArrayList<Observer_GUI> observers;
    protected JFrame frame;
    protected JPanel panel;
    protected JLabel label_front;
    protected JLabel label_back;
    protected String image_front_name;
    protected String image_back_name;
    protected Image image_front;
    protected Image image_back;
    protected Point point;
    protected String num;
    protected String frontOrBack = "back";
    
    private static final String image_location = "D:\\Team\\DavinciProject\\src\\main\\java\\image";
    
    public GameObject_GUI(String num, String image_front_name, String image_back_name, JFrame frame){
        observers = new ArrayList<>();
        this.frame = frame;
        this.image_front_name = image_front_name;
        this.image_back_name = image_back_name;
        this.num = num;
        
        point = new Point();
        try{
            image_front = ImageIO.read(new File(image_location + image_front_name));
            image_back = ImageIO.read(new File(image_location + image_back_name));
        }catch(IOException e){
            System.out.println("이미지 파일이 없습니다.");
        }
        label_front = new JLabel(new ImageIcon(image_front));
        label_back = new JLabel(new ImageIcon(image_back));
        setEnable(false);
        event();
    }
    public void event(){
        label_front.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent me){
                Object obj = me.getSource();
                if(obj instanceof JLabel){
                    notifyObservers();
                }
            }
        });
        label_back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent me){
                Object obj = me.getSource();
                if(obj instanceof JLabel){
                    notifyObservers();
                }
            }
        });
    }
    
    public void setFrontOrBack(String fob){
        this.frontOrBack = fob;
    }
    public String getFrontOrBack(){
        return this.frontOrBack;
    }
    public void setLocation(int x, int y){
        this.point.setLocation(x, y);
        label_front.setBounds(x, y, 80, 120);
        label_back.setBounds(x, y, 80, 120);
    }
    public void setLocation(Point point){
        this.point.setLocation(point);
        label_front.setBounds(point.x, point.y, 80, 120);
        label_back.setBounds(point.x, point.y, 80, 120);
    }
    public Point getLocation(){
        return point;
    }
    public void setNum(Float num){
        this.num = num.toString();
    }
    public void setNum(String num){
        this.num = num;
    }
    public String getNum(){
        return num;
    }
    public void setEnable(boolean set){
        this.label_back.setEnabled(set);
        this.label_front.setEnabled(set);
    }
    public void setVisiable(boolean set){
        this.label_back.setVisible(set);
        this.label_front.setVisible(set);
    }
    @Override
    public void display() {
        setEnable(true);
        if(frontOrBack.equals("front")){
            frame.remove(label_back);
            frame.add(label_front);
    }
        else if(frontOrBack.equals("back")){
            frame.remove(label_front);
            frame.add(label_back);
        }
        redisplay();
    }
    @Override
    public void redisplay() {
        frame.revalidate();
        frame.repaint();
    }
    
    @Override
    public void registerObserver(Observer_GUI observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer_GUI observer) {
        int index = observers.indexOf(observer);
        observers.remove(index);
    }

    @Override
    public void notifyObservers() {
        System.out.println("notifyObservers");
        for(Observer_GUI observer : observers){
            observer.update(this);
        }
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("num : " + this.num).append(" isFront : " + this.frontOrBack).append(" front image: " + this.image_front_name);
        return str.toString();
    }
}
