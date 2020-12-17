package gamsua;

import org.json.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Controller {
    private Client client;

    private JFrame frame = new JFrame();
    private final ImageIcon background = new ImageIcon("image/wasd.png");
    private char key = 'p', oldKey = 'p';

    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            oldKey = key;
            key = e.getKeyChar();

            if (key == 'a' || key == 'w' || key == 's' || key == 'd' || key == ' '){
                try {
                    send();
                } catch (IOException ioException) {ioException.printStackTrace();}
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    };

    public void send() throws IOException{
        if (key!= oldKey){
            JSONObject Obj = new JSONObject();
            Obj.put("Key", key);
            client.sendJSON(Obj);
        }
    }

    public Controller() throws IOException {
        client = new Client(935);

        frame.setTitle("Controller");
        frame.setSize(210,210);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new JLabel(background));
        frame.addKeyListener(keyListener);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException{
        Controller controller = new Controller();
        while (controller.client.getSocket().isConnected()){
            controller.send();
        }
    }
}
