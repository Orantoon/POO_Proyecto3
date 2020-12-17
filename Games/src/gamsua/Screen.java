package gamsua;

import org.json.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Iterator;

public class Screen {
    private final Server server;
    private final JFrame frame = new JFrame();
    private JSONObject received;
    private Panel panel = new Panel();

    public void gameJSON() throws IOException{
        server.receiveJSON();
        received = server.getReceived();
        System.out.println("Here");
    }

    public Screen() throws IOException {
        server = new Server(420);

        frame.setTitle("Screen");
        frame.setSize(600+10,600+10);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void start(){
        panel.paintComponent(frame.getGraphics(), new int[]{0,0,0},new int[]{100,0});
    }

    public void updateScreen(){
        Iterator<String> keys = received.keys();
        while (keys.hasNext()){
            String key = keys.next();
            JSONArray array = received.getJSONArray(key);
            int[] rgb = new int[]{0,0,0};

            switch (key) {
                case "Black" -> rgb = new int[]{0, 0, 0};
                case "Gray" -> rgb = new int[]{195, 195, 195};
                case "White" -> rgb = new int[]{255, 255, 255};
                case "Red" -> rgb = new int[]{255, 0, 0};
                case "Orange" -> rgb = new int[]{255, 128, 0};
                case "Yellow" -> rgb = new int[]{255, 255, 0};
                case "Purple" -> rgb = new int[]{153, 51, 255};
                case "Green" -> rgb = new int[]{0, 255, 0};
                case "Light Blue" -> rgb = new int[]{0, 255, 255};
                case "Dark Blue" -> rgb = new int[]{0, 0, 255};
            }

            for (int i = 0; i < array.length(); i++){
                JSONArray arr = array.getJSONArray(i); //{Red:[[10,20]]}
                int x = arr.getInt(0)*60, y = arr.getInt(1)*60;
                panel.paintComponent(frame.getGraphics(),rgb,new int[]{x,y});
                frame.add(panel);
            }
        }

    }

    public static void main(String[] args) throws IOException{
        Screen screen = new Screen();
        screen.start();
        while (screen.server.getClient().isConnected()){
            screen.gameJSON();
            screen.updateScreen();
        }
    }
}

class Panel extends JFrame{
    public void paintComponent(Graphics g, int[] rgb,int[] coord){
        super.paintComponents(g);

        if (coord[0] == 100){
            g.setColor(Color.BLACK);
            g.fillRect(0,0,610,610);
        } else {
            g.setColor(new Color(rgb[0],rgb[1],rgb[2]));
            g.fillRect(coord[0]*60,coord[1]*60,60,60);
        }
    }
}