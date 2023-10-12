import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/* Class MakeContactPopUp
 * Purpose of class is to serve as a dialog box for contact information that can either connect to a MainFrame or a Contact object
 * Author: Sandor Santos
 * MakeContactPopUp contains:
 * buttons for finishing dialog
 * 3 text fields: 1 Name field, 1 Number field, 1 Email field
**/

public class MakeContactPopUp extends JDialog {
    private JButton button;
    private JButton deleteContactButton = new JButton("Delete Contact");

    private JTextField name = new JTextField();;
    private JLabel nameText = new JLabel("Name: ");

    private JTextField number = new JTextField();;
    private JLabel numberText = new JLabel("Number: ");

    private JTextField email = new JTextField();;
    private JLabel emailText = new JLabel("Email: ");

    private JPanel imageHolder = new JPanel();
    private JLabel imageLabel = new JLabel();
    private JButton imageChangeButton = new JButton();
    private int imageIndex = 0;

    private JLabel missingFieldsText = new JLabel();

    public MakeContactPopUp(MainFrame parent, Contact parentContact) {
        super(parent, "Contact Info");
        parent.setDialogOpen(this.isVisible());

        this.addWindowListener(new WindowAdapter() { //reset all the fields when the dialog window is closed
            public void windowClosed(WindowEvent e) {
                System.out.println("Dialog Closed");
                parent.setDialogOpen(false);
                MakeContactPopUp.this.resetFields();
            }
        });

        int textFieldAlignmentsX = 200;
        
        missingFieldsText.setForeground(Color.RED);
        missingFieldsText.setBounds(135, 158, 200, 30);
        this.add(missingFieldsText);

        if (parentContact == null) {
            button = new JButton("Add Contact");
            button.setBounds(142, 130, 150, 30);
        }
        else {
            button = new JButton("Update Contact");
            button.setBounds(235, 130, 150, 30);

            name = new JTextField(parentContact.getName());
            number = new JTextField(parentContact.getNumber());
            email = new JTextField(parentContact.getEmail());
            imageHolder = new JPanel();
            imageIndex = parentContact.getImageIndex();

            this.add(deleteContactButton);
            deleteContactButton.setBounds(50, 130, 150, 30);
            deleteContactButton.setFocusPainted(false);
            deleteContactButton.addActionListener(e -> {
                parent.removeContact(parentContact);
                this.dispose();
            });
        }

        button.setFocusPainted(false);
        this.add(button);

        button.addActionListener(e -> { //check if any fields are empty and create new contact with info if not

            boolean proceedCreating = true;
            
            if(name.getText().equals("") && number.getText().equals("") && email.getText().equals("")) {
                proceedCreating = false;
                missingFieldsText.setText("One or more fields are empty");
                System.out.println("Empty Fields");
            }
            else {
                if (name.getText().equals("") ) {
                name.setText("Unknown Name");
                }
                if (number.getText().equals("") ) {
                number.setText("0000000000");
                }
                if (email.getText().equals("")) {
                email.setText(" ");
                }
            }
            
            if(proceedCreating) {
                if (parentContact == null) {
                    Contact newContact = new Contact(parent, name.getText(), number.getText(), email.getText());
                    System.out.println("Contact made in parent: " + parent + ", name: " + name.getText() + ", number: " + number.getText() + ", email: " + email.getText());
                    newContact.setData(this.getData());
                    try {
                        newContact.setIcon(this.imageIndex);
                    } catch (Exception e1) {}
                    parent.addContact(newContact);
                }
                else {
                    parentContact.setData(new String[]{name.getText(), number.getText(), email.getText()});
                    try {
                        parentContact.setIcon(imageIndex);
                    } catch (Exception e1) {}
                }
                parent.refreshScrollPane();
                this.setVisible(false);
                parent.setDialogOpen(false);
                MakeContactPopUp.this.resetFields();
            }
        });

        nameText.setBounds(textFieldAlignmentsX - 60, 10, 70, 25);
        this.add(nameText);
        
        name.setBounds(textFieldAlignmentsX, 10, 200, 25);
        this.add(name);
        
        numberText.setBounds(textFieldAlignmentsX - 60, 50, 70, 25);
        this.add(numberText);
        
        number.setBounds(textFieldAlignmentsX, 50, 200, 25);

        number.addFocusListener(new FocusListener() { //make number text field editable after losing focus
            public void focusGained(FocusEvent e) {}
            public void focusLost(FocusEvent e) {
                number.setEditable(true);
            }
        });

        number.addKeyListener(new KeyAdapter() { //Only allow numbers and backspace into number input
            public void keyPressed(KeyEvent ee) {
                String c = "" + ee.getKeyChar();

                try {
                    Integer.parseInt(c);
                    if(number.getText().length() <= 9) {
                        number.setEditable(true);
                    }
                    else {
                        number.setEditable(false);
                    }
                }
                catch (Exception a) {
                    if(c.equals("\b")) {
                        number.setEditable(true);
                    }
                    else {
                        number.setEditable(false);
                    }
                }
            }
        });
   
        this.add(number);
        
        emailText.setBounds(textFieldAlignmentsX - 60, 90, 70, 25);
        this.add(emailText);
        
        email.setBounds(textFieldAlignmentsX, 90, 200, 25);
        this.add(email);

        imageHolder.setBounds(27, 15, 100, 100);
        imageHolder.setLayout(new GridLayout(1, 1));
        imageHolder.setBackground(Color.GRAY);
        this.add(imageHolder);
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            imageLabel.setSize(100, 100);

            try {
                imageLabel.setIcon(ProfileImages.getImage(imageLabel, imageIndex)); //SETTING THE PROFILE PICTURE TO THE DEFAULT PROFILE PICTURE GIVEN BY ProfileImages.java
            } 
            catch (Exception e) { 
                System.out.println("An error has occured while trying to set a profile picture in " + this);
            }
        imageHolder.add(imageLabel);

        imageChangeButton.setBounds(27, 15, 100, 100);
        imageChangeButton.setOpaque(false);
        imageChangeButton.addActionListener(e -> {

            System.out.print("ImageChanger was clicked ");
            imageIndex++; //cycle through profile pics

            try {
                imageLabel.setIcon(ProfileImages.getImage(imageLabel, imageIndex));
            } catch (Exception e1) {}
            System.out.println(imageIndex);
        });

        this.add(imageChangeButton);

        this.setLayout(null); //regular stuff to make jframe work
        this.setSize(new Dimension(450, 225));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(parent);
        this.setAlwaysOnTop(true);

        this.setVisible(false);
    }

    public void resetFields() {
        missingFieldsText.setText("");
        name.setText("");
        number.setText("");
        email.setText("");
    }

    ///////////////////////////// Accessor and Mutator Methods ////////////////////////////////

    public String[] getData() {
        String[] data = {name.getText(), number.getText(), email.getText()};
        return data;
    }
}