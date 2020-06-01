import org.jdom2.Document;

import javax.swing.*;
import java.awt.*;

public class XMLChatClient extends JFrame {
    /**
     * Create GUI components for the application
     */
    public static JTextArea textArea = new JTextArea("");
    private JTextField jTextFieldName = new JTextField();
    private JTextField jTextFieldEmail = new JTextField();
    private JTextField jTextFieldHomePage = new JPasswordField();
    private JTextField jTextFieldMessage = new JTextField();
    private JButton sendButton = new JButton("Send");

    private ChatClient chatClient = null;
    private XmlChatMessageDocument xmlChatMessageDocument = new XmlChatMessageDocument();

    public XMLChatClient() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        /**
         * Setting up GUI space, by adding GUI components
         */
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
        jPanelSouth.add(this.sendButton);

        /**
         * creates a ActionListener for the submit button by using lambda expression
         * that use function submitButtonClicked()
         */
        sendButton.addActionListener(e -> {
            sendButtonClicked();
        });
        getContentPane().add("Center", new JScrollPane(this.textArea));
        getContentPane().add("South", jPanelSouth);
        setSize(640, 480);
        setVisible(true);
        chatClient = new ChatClient("atlas.dsv.su.se", 9494);


    }

    /**
     * Function to run when actionlistener is activated by the button pressed
     * The function starts a connection to the database and then tries to
     * insert a record in the database by suing the function insertDbPost with
     * information from the textfileds in the gui
     * and finnaly gets new comments from the database
     */
    private void sendButtonClicked() {
        Document doc = xmlChatMessageDocument.createXmlChatMessageDocument(jTextFieldName.getText(), jTextFieldEmail.getText(),jTextFieldHomePage.getText(),jTextFieldMessage.getText());
        String xmlString = xmlChatMessageDocument.convertToString(doc);
        chatClient.sendMessage(xmlString);
        
    }



    public static void main(String[] args) {
        new XMLChatClient();
    }
}
