package udp;

import javafx.beans.property.SimpleStringProperty;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

import static udp.Tool.out;

public class Server extends Thread{
    private int port;
    private boolean cont = true;
    private DatagramSocket socket;
    private SimpleStringProperty property;

    public Server(int port){
        this.port = port;
        property = new SimpleStringProperty();
    }

    public SimpleStringProperty property(){
        return property;
    }

    public void close(){
        cont = false;
    }

    @Override
    public void run() {
        try{
            socket = new DatagramSocket(port);
            out("Server -> " + socket.getLocalAddress() + ":" + port);
            property.set("Server -> " + socket.getLocalAddress() + ":" + port);
            byte[] buffer;
            while(cont){
                buffer = new byte[1512];
                DatagramPacket rcvPack = new DatagramPacket(buffer, buffer.length);
                socket.receive(rcvPack);
                String s = new String(rcvPack.getData()).trim();
                out(s);
                property.set(s);
                DatagramPacket sndPack = new DatagramPacket(
                        "OK".getBytes(), "OK".getBytes().length, rcvPack.getAddress(), rcvPack.getPort()
                );
                socket.send(sndPack);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
