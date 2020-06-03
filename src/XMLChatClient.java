import org.jdom2.Document;

import javax.swing.*;
import java.awt.*;

public class XMLChatClient extends JFrame {
    // Create GUI components for the application
    private   JTextArea textArea = new JTextArea("");
    private JTextField jTextFieldName = new JTextField();
    private JTextField jTextFieldEmail = new JTextField();
    private JTextField jTextFieldHomePage = new JPasswordField();
    private JTextField jTextFieldMessage = new JTextField();

    private ChatClient chatClient;
    private XmlDocumentHandler xmlDocumentHandler = new XmlDocumentHandler();

    public XMLChatClient() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Setting up GUI space, by adding GUI components
        JPanel jPanelSouth = new JPanel();
        jPanelSouth.setLayout(new GridLayout(7, 2));
        jPanelSouth.add(new JLabel("Name:"));
        jPanelSouth.add(this.jTextFieldName);
        jPanelSouth.add(new JLabel("E-Mail:"));
        jPanelSouth.add(this.jTextFieldEmail);
        jPanelSouth.add(new JLabel("HomePage:"));
        jPanelSouth.add(this.jTextFieldHomePage);
        jPanelSouth.add(new JLabel("Message:"));
        jPanelSouth.add(this.jTextFieldMessage);
        jPanelSouth.add(new JLabel("Send"));
        JButton sendButton = new JButton("Send");
        jPanelSouth.add(sendButton);

        // creates a ActionListener for the submit button by using lambda expression
        // that use function submitButtonClicked()
        sendButton.addActionListener(e -> {
            sendButtonClicked();
        });
        getContentPane().add("Center", new JScrollPane(textArea));
        getContentPane().add("South", jPanelSouth);
        setSize(640, 480);
        setVisible(true);
        chatClient = new ChatClient("atlas.dsv.su.se", 9494, this);
    }

    /**
     * Function to run when actionlistener is activated by the button pressed
     * The function starts a connection to the database and then tries to
     * insert a record in the database by suing the function insertDbPost with
     * information from the textfileds in the gui
     * and finnaly gets new comments from the database
     */
    private void sendButtonClicked() {
        Document doc = xmlDocumentHandler.createXmlChatMessageDocument(jTextFieldName.getText(), jTextFieldEmail.getText(), jTextFieldHomePage.getText(), jTextFieldMessage.getText());
        //Print XML document to the console
        xmlDocumentHandler.printXmlDoc(doc);
        //Convert XML document to string
        String xmlString = xmlDocumentHandler.convertToString(doc);

        //Send XML String to server
        chatClient.sendMessage(xmlString);
    }

    /**
     * Add chat message to textarea(GUI)
     * @param newChatMessage - message to add
     */
    public void addChatMessage(String newChatMessage){
        textArea.append("\n" + newChatMessage);
    }

    public static void main(String[] args) {
        new XMLChatClient();
    }
}
