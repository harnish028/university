import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static final int PORT = 8001;
    public static final String HOST = "localhost";
    Dot dot;
    double r;
    List<Dot> dots;

    public Client(Dot dot, double r) {
        this.dot = dot;
        this.r = r;
    }

    public Client(List<Dot> dots, double r) {
        this.dots = dots;
        this.r = r;
    }

    public boolean start() throws UnknownHostException {
        boolean answer = false;
        try {
            byte[] ib = new byte[256];
            String message = new String(dot.getX() + " " + dot.getY() + " " + r);
            byte[] messageByte = message.getBytes();
            DatagramPacket clientPacket = new DatagramPacket(messageByte, messageByte.length, InetAddress.getByName("localhost"), PORT);
            DatagramSocket clientSocket = new DatagramSocket();
            if (clientSocket.isConnected()){

                clientSocket.send(clientPacket);
            } else{
                System.out.println("КАкая-то хуйня с сервером");
            }
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            clientSocket.receive(ip);
            Scanner sc = new Scanner(new String(ip.getData()).trim());
            answer = Boolean.parseBoolean(sc.next());
            System.out.println(answer);
            clientSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
