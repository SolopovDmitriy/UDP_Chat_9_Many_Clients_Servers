package udp;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import static udp.Tool.out;

public class Client extends Thread {
    private String adr;
    private int port;
    private boolean cont = true;
    private DatagramSocket socket;

    public void close() {
        cont = false;
    }

    public void send(InetSocketAddress inetSocketAddress, String msg) {
        new Thread(() -> {

            try {
                socket = new DatagramSocket();
                DatagramPacket sndPack = new DatagramPacket(
                        msg.getBytes(), msg.getBytes().length,
                        inetSocketAddress
                );
                socket.send(sndPack);
                byte[] buf = new byte[1512];
                DatagramPacket rcvPack = new DatagramPacket(buf, buf.length);
                socket.setSoTimeout(30000);
                socket.receive(rcvPack);
                out(new String(rcvPack.getData()).trim());

            } catch (Exception e) {
                out(e.getMessage());
            }
        }).start();
    }
}
