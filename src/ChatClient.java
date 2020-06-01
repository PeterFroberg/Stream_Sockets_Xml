import java.io.*;
import java.net.Socket;

public class ChatClient {


    private Socket socket;
    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private BufferedReader socketReader;
    private PrintWriter socketWriter;

    public ChatClient(String host, int port){
        try {
            socket = new Socket(host, port);
            System.out.println("Connected!");

            socketWriter = new PrintWriter(socket.getOutputStream(), true);
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

            startReceiver();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReceiver(){
        new Thread(() -> {
            try {
                /**
                 * Keeps the receiver alive for the client
                 */

                    while (true) {
                        if (socketReader.ready()) {
                            String line = socketReader.readLine();
                            System.out.println(line);
                            XMLChatClient.textArea.setText(XMLChatClient.textArea.getText() + "\n" + line);
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void sendMessage(String message){
            socketWriter.println(message);
    }

    public void closeConnection(){
        try {
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
