package Davinci_Gui;

public interface Subject_GUI {
    public void registerObserver(Observer_GUI observer);
    public void removeObserver(Observer_GUI observer);
    public void notifyObservers();
}
