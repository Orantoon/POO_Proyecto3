package gamsua;

import org.json.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Iterator;


public class Screen {
    private final Server server;
    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();
    private JSONObject received;

    //Colors
    private final ImageIcon black = new ImageIcon("image/Black.png");
    private final ImageIcon blue = new ImageIcon("image/Blue.png");
    private final ImageIcon gray = new ImageIcon("image/Gray.png");
    private final ImageIcon green = new ImageIcon("image/Green.png");
    private final ImageIcon lightBlue = new ImageIcon("image/Light-Blue.png");
    private final ImageIcon orange = new ImageIcon("image/Orange.png");
    private final ImageIcon purple = new ImageIcon("image/Purple.png");
    private final ImageIcon red = new ImageIcon("image/Red.png");
    private final ImageIcon white = new ImageIcon("image/White.png");
    private final ImageIcon yellow = new ImageIcon("image/Yellow.png");

    public void gameJSON() throws IOException{
        server.receiveJSON();
        received = server.getReceived();
    }

    public Screen() throws IOException {
        server = new Server(420);

        frame.setTitle("Screen");
        frame.setSize(600+10,600+10);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        panel.setBounds(0,0,600+10,600+10);
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
    }

    public void updateScreen(){
        Iterator<String> keys = received.keys();
        while (keys.hasNext()){
            String key = keys.next();
            JSONArray array = received.getJSONArray(key);

            JLabel label = new JLabel();

            switch (key) {
                case "Black"  -> label.setIcon(black);
                case "Gray"   -> label.setIcon(gray);
                case "White"  -> label.setIcon(white);
                case "Red"    -> label.setIcon(red);
                case "Orange" -> label.setIcon(orange);
                case "Yellow" -> label.setIcon(yellow);
                case "Purple" -> label.setIcon(purple);
                case "Green"  -> label.setIcon(green);
                case "Blue"   -> label.setIcon(blue);
                case "Light Blue" -> label.setIcon(lightBlue);
            }

            for (int i = 0; i < array.length(); i++){
                JSONArray arr = array.getJSONArray(i);

                int x = arr.getInt(0)*12, y = arr.getInt(1)*12;
                label.setBounds(x,y,12,12);

                panel.add(label);
                frame.add(panel);

                //frame.revalidate();
                frame.repaint();
            }
        }
    }

    public static void main(String[] args) throws IOException{
        Screen screen = new Screen();
        //screen.start();
        while (screen.server.getClient().isConnected()){
            screen.gameJSON();
            screen.updateScreen();
        }
    }
}