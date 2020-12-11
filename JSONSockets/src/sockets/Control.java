package sockets;

import org.json.JSONObject;

import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;

public class Control extends Client {
    private JFrame frame = new JFrame();
    private char key = 'p', oldKey = 'p';

    KeyListener keyboard = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            oldKey = key;
            key = e.getKeyChar();
            try {
                send();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.out.println(key);
        }
    };

    public Control() throws IOException {
        super();
        frame.addKeyListener(keyboard);
        frame.setVisible(true);
    }

    public void send() throws IOException {
        if (key != oldKey){
            JSONObject Obj = new JSONObject();
            Obj.put("Control", Character.valueOf(key));

            this.sendJSON(Obj);
            //System.out.println(Obj.get("Control"));
        }
    }

    public static void main(String[] args) throws IOException {
        Control control = new Control();
        while (control.getClient().isConnected()){
            control.send();
        }
    }
}
