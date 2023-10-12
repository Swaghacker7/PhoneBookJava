import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

import java.io.*;  // Import the File class

/* Class MainFrame
 * Purpose is to serve as the primary JFrame of a PhoneBook, does not relu upon the class PhoneBook and is the main bulk of the code
 * Author: Sandor Santos
 * MainFrame contains:
 * 1 Dialog box to display info for creating a new contact
 * 1 mainPanel to hold contacts and JScrollPane
 * 1 secondaryPanel to hold action buttons such as create new contact 
**/

public class MainFrame extends JFrame implements ActionListener {

    private File contactsFile;

    private JPanel mainPanel;
    private JPanel secondaryPanel;
    private JScrollPane scrollPane;

    private OwnMenuBar menuBar;

    private MakeContactPopUp dialogBox;
    private boolean anyDialogOpen;

    private Dimension mainPanelDims;

    private ArrayList<Contact> contactList = new ArrayList<Contact>();

    public MainFrame() {
        this.setTitle("Phone Book");
        this.setBackground(Color.RED);

        try {
            contactsFile = new File("contacts.txt");
            if (contactsFile.createNewFile()) {
              System.out.println("File created: " + contactsFile.getName());
            } 
            else {
              System.out.println("File already exists.");
            }
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        addContent();

        createContactsFromFile(contactsFile);
        refreshScrollPane();

        defaultJFrame();

        this.setVisible(true);
    }
    
    ///////////////////////////// Accessor and Mutator Methods ////////////////////////////////

    public JPanel getMainPanel() {
        return mainPanel;
    }
    
    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public boolean dialogBoxVisible() {
        try {
            return this.dialogBox.isVisible();
        }
        catch (Exception e) { System.out.println(e); }
        return false;
    }
    
    /* General Methods that still interact with private instance variables */

    public void actionPerformed(ActionEvent e) {
        try {
            System.out.println("actionPerformed triggered | MainFrame");
            menuBar.performAction(e);
        }
        catch (Exception E) {
            System.out.println("Error has occured in actionPerformed");
        }
    }
    private void defaultJFrame() {

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                writeToContactsFile();
                refreshScrollPane();
                System.out.println("MainFrame closed");
            }
        });

        this.setLayout(new FlowLayout()); //regular stuff to make jframe work
        this.setSize(new Dimension(320, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void addContent() {
        this.mainPanelDims = new Dimension(280, 400);

        this.menuBar = new OwnMenuBar(this);
        menuBar.addContact.addActionListener(this);
        this.setJMenuBar(menuBar);

        this.mainPanel = new JPanel(new FlowLayout());
            mainPanel.setPreferredSize(mainPanelDims);
            mainPanel.setBackground(Color.WHITE);
            mainPanel.setBorder(new EmptyBorder(5, 10, 10, 30)); //making an inset for the main panel so that the scroll bar does not cover contacts ?

        this.scrollPane = new JScrollPane(mainPanel, 
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(new Dimension(280, 520));
            scrollPane.getVerticalScrollBar().setUnitIncrement(16); //Stuff for adding the main panel to the Scrollpane's view (so that the panbel is visible and can scroll)

        this.secondaryPanel = new JPanel(new GridBagLayout());
            secondaryPanel.setPreferredSize(new Dimension(280, 50));

        this.dialogBox = new MakeContactPopUp(this, null);

        //this.addContact = new JButton("New Contact"); //text for adding a contact button

        //secondaryPanel.add(addContact);

        this.add(scrollPane); //adds the scroll view instead of the main panel so that it's still scrollable
        this.add(secondaryPanel); //panel is for the buttons on bottom to add a new contact
    }

    public void refreshScrollPane() {
        if(contactList.size() * 80 > mainPanel.getHeight()) {
            mainPanelDims.setSize(280, contactList.size() * 80);
            System.out.println(mainPanelDims);
            mainPanel.setPreferredSize(mainPanelDims);
        }
        else if(contactList.size() * 80 < mainPanel.getHeight()) {
            mainPanelDims.setSize(280, contactList.size() * 80);
            mainPanel.setPreferredSize(mainPanelDims);
        }
        scrollPane.revalidate();
    }
    
    public void createContactsFromFile(File file) { //Will read Contacts.txt and create contacts before frame loads in
        System.out.println("Attempting to read from file");
        String block1 = "", block2 = "", block3 = "";
        int imageIndex = 0;
        try {
            System.out.println("Reading...");

            Scanner reader = new Scanner(new File("contacts.txt"));
            while(reader.hasNextLine()) {
                block1 = reader.nextLine();
                block2 = reader.nextLine();
                block3 = reader.nextLine();
                imageIndex = Integer.parseInt(reader.nextLine().substring(2));
                Contact temp = new Contact(this, block1.substring(2), block2.substring(2), block3.substring(2));
                temp.setIcon(imageIndex);
                this.addContact(temp);
            }
            reader.close();

            System.out.println("Finished Reading!");
        } 
        catch(Exception e) { System.out.println("Error has occured"); }
        refreshScrollPane();
        //System.out.println(contactList);
    }
    
    public void writeToContactsFile() { //Will be used for saving contact info into contacts.txt
        String buffer = "";
        for(Contact c: contactList) {
            System.out.print(c);
            buffer += c;
        }

        try {
            FileWriter myWriter = new FileWriter("contacts.txt");
            myWriter.write(buffer);
            myWriter.close();
            System.out.println("-------- Successfully wrote --------\n" + buffer + "-------- to file: contacts.txt --------");
        } 
        catch (IOException e) {
            System.out.println("An error occurred while writing to file in writeToContactsFile");
            e.printStackTrace();
        }
        buffer = null;
    }

    public void enableContactDialogue() {
        if(!dialogBox.isVisible() && !(this.anyDialogOpen)) {
            dialogBox.setLocationRelativeTo(this);
            dialogBox.setVisible(true);
        }
        else {
            dialogBox.setLocationRelativeTo(this);
        }

        refreshScrollPane();
    }

    public void addContact(Contact contact) {
        this.contactList.add(contact);
    }

    public void removeContact(Contact contact) {
        this.contactList.remove(contact);
        this.mainPanel.remove(contact);
        refreshScrollPane();
    }

    public void setDialogOpen(boolean b) {
        this.anyDialogOpen = b;
    }

    public boolean isDialogOpen() {
        return this.anyDialogOpen;
    }
    
    public String toString() {
        return this.getTitle();
    }    
}