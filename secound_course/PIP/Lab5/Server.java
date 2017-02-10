import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

class AnalyzeThread implements Runnable{

    DatagramPacket in;
    AnalyzeThread(DatagramPacket in, DatagramSocket out){
        this.in = in;
    }

    public void run() {
        //
    }
}

public class Server {

    public static void main(String[] args) {
        try {
                while(true) {
                    DatagramSocket ds = new DatagramSocket(8001);
                    byte[] ib = new byte[256];
                    byte[] ob = new byte[256];
                    DatagramPacket ip = new DatagramPacket(ib, ib.length);
                    ds.receive(ip);
                    Scanner sc = new Scanner(new String(ip.getData()).trim());
                    System.out.println(new String(ip.getData()).trim());
                    double x = Double.parseDouble(sc.next());
                    double y = Double.parseDouble(sc.next());
                    double r = Double.parseDouble(sc.next());


                    boolean result = ((Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2)) && (x >= 0 && y >= 0)) ||
                            ((x >= -r && x <= 0) && (y <= r && y >= 0)) ||
                            ((x >= 0 && x <= r) && (y <= 0 && y >= -r));
                    String dataToClient = Boolean.toString(result);
                    ob = dataToClient.getBytes();
                    InetAddress addr = ip.getAddress();
                    int port = ip.getPort();
                    DatagramPacket op = new DatagramPacket(ob, ob.length, addr, port);
                    ds.send(op);
                    ds.close();
                }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}







