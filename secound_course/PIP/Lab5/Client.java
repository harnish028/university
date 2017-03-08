import java.io.IOException;
import java.net.*;
import java.util.List;

public class Clientt implements Runnable{
    public static final int PORT = 8001;
    public static final String HOST = "localhost";
     public static boolean serverIsAvailable;
    public static boolean isBusy = false;
    DatagramPacket clientPacket;
    DatagramSocket clientSocket;
    Dot dot;
    double r;
    private boolean answer;  
    List<Dot> dots;
    DrawPanel drawPanel;
   
    public Clientt(List<Dot> dots, double r, DrawPanel drawPanel) {
        this.dots = dots;
        this.r = r;
        this.drawPanel = drawPanel;
    }

    public boolean isAnswer() {
        return answer;
    }
    
    public void runClient() throws SocketException {
        System.out.println("UDPClient: Started");
        clientSocket = new DatagramSocket();
    }

    public void sendData(byte[] data) throws IOException {
        try {
            clientPacket = new DatagramPacket(data, data.length, InetAddress.getByName("localhost"), PORT);
            clientSocket.send(clientPacket);
        }catch(UnknownHostException ex) {
            System.out.println("Error! Unknown host!");
        }
    }

    public byte[] receiveDat() throws IOException {
        byte[] ib = new byte[256];
        DatagramPacket ip = new DatagramPacket(ib, ib.length);
        clientSocket.setSoTimeout(50);
        clientSocket.receive(ip);
        ib = ip.getData();
        return ib;
    }

    @Override
    public void run() {
        serverIsAvailable = false;
        isBusy = true;
        String test = "1.0 1.0 1.0";
        while(!serverIsAvailable){
            try {
                sendData(test.getBytes());
                answer = Boolean.parseBoolean(receiveDat().toString());
            } catch (IOException e) {
                serverIsAvailable = false;
                continue;
            }
            serverIsAvailable = true;
            if(!dots.isEmpty()){
                for(int i = 0; i < dots.size(); i++){
                    String message = new String(dots.get(i).getX() + " " + dots.get(i).getY() + " " + r);
                    try {
                        sendData(message.getBytes());
                        dots.get(i).setHit(Boolean.parseBoolean(new String(receiveDat()).trim()));
                        if(dots.get(i).getHit() == true) {
                            drawPanel.repaint();
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}
