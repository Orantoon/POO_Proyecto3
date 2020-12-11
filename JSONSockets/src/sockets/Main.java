package sockets;

import java.io.IOException;

public class Main extends ServerConsole{
    public void receiveControl(Main principal) throws IOException {
        principal.receiveJSON();
    }

    public Main() throws IOException, InterruptedException {super();}

    public static void main(String[] args) throws IOException, InterruptedException {
        Main principal = new Main();
        while (principal.getClient().isConnected()){
            principal.receiveControl(principal);
        }
    }
}
