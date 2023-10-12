import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.*;

/* Purpose of Contact class is serve as a definitive object for contacts contained within a MainFrame object's mainPanel 
 * Author: Sandor Santos
 * Contact has: 
 * edit button clickable anywhere on the Contact object
 * 4 JLabel objects: 1 Name label, 2 Number labels - 1 visible containing fancy representational number data, 1 invisible containing original data, 1 Email label
**/

public class Contact extends JPanel {
    private JLabel name; //will later be used to name the label for the contact's name
    private JLabel number; //phone number
    private JLabel displayNumber;
    private JLabel email;

    private JButton editButton;

    private JPanel imagePanel;
    private JLabel imageLabel;
    private int imageIndex;

    public Contact(MainFrame parent, String name, String number, String email) {
        super();
        this.setLayout(null);
        parent.getMainPanel().add(this);
        
        this.setPreferredSize(new Dimension(250, 70));
        this.setBackground(Color.LIGHT_GRAY);
// Image Pane not used, don't know how to use
        imagePanel = new JPanel(new GridLayout(1, 1));
            imagePanel.setBounds(12, 12, 46, 46);
            this.add(imagePanel);

            imageLabel = new JLabel();
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            imageLabel.setSize(46, 46);
            try {
                imageLabel.setIcon(ProfileImages.getImage(imageLabel, 0)); //SETTING THE PROFILE PICTURE TO THE DEFAULT PROFILE PICTURE GIVEN BY ProfileImages.java
            } catch (Exception e) { System.out.println("An error has occured while trying to set a profile picture in " + this);}
            imagePanel.add(imageLabel);

        this.name = new JLabel(name);
            this.name.setBounds(70, 10, 120, 15);
            
        this.add(this.name);

        this.number = new JLabel(number);
            this.number.setBounds(70, 29, 120, 15);
            this.number.setVisible(false);

        this.add(this.number);

        this.displayNumber = new JLabel();
            this.displayNumber.setText(getDisplayNumber(number));
            this.displayNumber.setBounds(70, 29, 120, 15);

        this.add(this.displayNumber);

        this.email = new JLabel(email);
            this.email.setBounds(70, 48, 170, 15);

        this.add(this.email);
        
        this.editButton = new JButton();
            editButton.setOpaque(false);
            editButton.setFocusPainted(false);
            editButton.setContentAreaFilled(false);
            editButton.setBorder(new LineBorder(Color.BLACK, 1));
            editButton.setBounds(0, 0, 250, 70);

        this.add(editButton);

        editButton.addMouseListener(new MouseAdapter() { //Darken border of contact when hovered over
            public void mouseEntered(MouseEvent evt) {
               editButton.setBorder(new LineBorder(Color.GRAY, 4));
            }
            public void mouseExited(MouseEvent evt) {
               editButton.setBorder(new LineBorder(Color.BLACK, 1));
            }
        });

        editButton.addActionListener(e -> { //When contact is clicked in order to edit its info
            System.out.println("Edit button pressed");
            if(!(parent.dialogBoxVisible()) && !(parent.isDialogOpen())) {
                MakeContactPopUp temp = new MakeContactPopUp(parent, this);
                parent.setDialogOpen(true);
                temp.setVisible(true);
            }
        });

        parent.refreshScrollPane();
    }

    ///////////////////////////// Accessor and Mutator Methods ////////////////////////////////

    public void setData(String[] data) {
        this.setName(data[0]);
        this.setNumber(data[1]);
        this.setEmail(data[2]);
    }
    
    public String getName() {
        return name.getText();
    }
    
    public String getNumber() {
        return number.getText();
    }
    
    public String getEmail() {
        return email.getText();
    }

    public int getImageIndex() {
        return this.imageIndex;
    }

    public void setName(String name) {
        this.name.setText(name);
    }
    
    public void setIcon(int index) throws IOException {
        this.imageLabel.setIcon(ProfileImages.getImage(imageLabel, index));
        imageIndex = index;
    }

    public void setNumber(String number) {
        this.number.setText(number);
        this.displayNumber.setText(getDisplayNumber(number));
    }

    public static String getDisplayNumber(String originalNumber) {
        if(originalNumber.length() == 10) {
            return "+1 (" + originalNumber.substring(0, 3) + ") " + originalNumber.substring(3, 6)+ "-" + originalNumber.substring(6);
        }
        else {
            return originalNumber;
        }
    }
    
    public void setEmail(String email) {
        this.email.setText(email);
    }

    public String toString() {
        return "N:" + this.getName() + "\n#:" + this.getNumber() + "\n@:" + this.getEmail() + "\nP:" + this.getImageIndex() + "\n";
    }
}