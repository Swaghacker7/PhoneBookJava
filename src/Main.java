import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(
            UIManager.getCrossPlatformLookAndFeelClassName());
        } 
        catch (Exception e) { }
        
        JFrame.setDefaultLookAndFeelDecorated(true);

        new MainFrame();
    }
}
