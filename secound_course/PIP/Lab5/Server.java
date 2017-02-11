import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;


public class Server {

    public static void main(String[] args) {

            while (true) {
                new Thread(new AnalyzeThread()).start();
            }
    }

    private static class AnalyzeThread implements Runnable{
        DatagramPacket in;
        DatagramSocket out;
        private byte[] ob = new byte[256];

        
        
        public void run() {
            try {
                DatagramSocket out = new DatagramSocket(8001);
                byte[] ib = new byte[256];

                DatagramPacket in = new DatagramPacket(ib, ib.length);
                out.receive(in);
                Scanner sc = new Scanner(new String(in.getData()).trim());
                System.out.println(new String(in.getData()).trim());
                double x = Double.parseDouble(sc.next());
                double y = Double.parseDouble(sc.next());
                double r = Double.parseDouble(sc.next());


                boolean result = ((Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2)) && (x >= 0 && y >= 0)) ||
                        ((x >= -r && x <= 0) && (y <= r && y >= 0)) ||
                        ((x >= 0 && x <= r) && (y <= 0 && y >= -r));
                String dataToClient = Boolean.toString(result);
                ob = dataToClient.getBytes();
                InetAddress addr = in.getAddress();
                int port = in.getPort();
                DatagramPacket op = new DatagramPacket(ob, ob.length, addr, port);
                out.send(op);
                out.close();
            } catch (Throwable t){}
        }
    }
}







