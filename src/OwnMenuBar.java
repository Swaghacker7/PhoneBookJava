import java.awt.event.*;

import javax.swing.*;

public class OwnMenuBar extends JMenuBar {
    private MainFrame parentFrame;

    private JMenu toolbar;

    public JMenuItem addContact;

    public OwnMenuBar(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        
        this.toolbar = new JMenu("Edit");

        this.addContact = new JMenuItem("New Contact");

        toolbar.add(addContact);
        this.add(toolbar);
    }

    public void performAction(ActionEvent e) {
        if( e.getSource() == addContact) {
            System.out.println("addContact button was pressed | JMenuBar");
            parentFrame.enableContactDialogue();
        }
    }
}
