import java.io.*;
import java.net.Socket;

public class ChatClient {

    private Socket socket;
    private BufferedReader socketReader;
    private PrintWriter socketWriter;

    private XmlDocumentHandler xmlDocumentHandler = new XmlDocumentHandler();
    private XMLChatClient xmlChatClient;

    /**
     * The constructor sets uo the connection to the chat server
     * @param host - Host name or IP-adress to the server
     * @param port - Communication port to the server
     */
    public ChatClient(String host, int port, XMLChatClient xmlChatClient) {
        try {
            this.xmlChatClient = xmlChatClient;
            //Connect to the server on host and port
            socket = new Socket(host, port);
            System.out.println("Connected!");

            //Prepares the streams for communication
            socketWriter = new PrintWriter(socket.getOutputStream(), true);
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Starts a receiver for messages from the server
            startReceiver();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start a reciver Thread for the Chat client that listens for new messages from the server
     */
    private void startReceiver() {
        new Thread(() -> {
            try {
                // Keeps the receiver alive for the client
                while (true) {
                    if (socketReader.ready()) {
                        String line = socketReader.readLine();

                        //Add new message to chatWindow
                        String newChatMEssage = xmlDocumentHandler.parseXML(line, "body");
                        xmlChatClient.addChatMessage(newChatMEssage);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }

        }).start();
    }

    /**
     * Sends message to the server
     * @param message - messge to send
     */
    public void sendMessage(String message) {
        socketWriter.println(message);
    }

    /**
     * Closes the connection to the server
     */
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
