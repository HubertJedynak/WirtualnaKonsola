package servicesOfPad;

import static java.lang.Thread.sleep;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;

public class UDPPad implements Runnable {

    private Thread thread;

    public boolean start = false, select = false, up = false, down = false, left = false,
            right = false, circle = false, blue = false, green = false, red = false;

    public double xPosition, yPosition;

    private PIRegulation regPIX, regPIY;
    private int downloadedLeftRight, downloadedUpDown;

    private String code;
    
    public UDPPad() {
        code="00000";
        regPIX = new PIRegulation(50);
        regPIY = new PIRegulation(50);
        thread = new Thread(this);
        thread.start();
    }

    private void positionSupport() {
        //range 400 : 600
        xPosition = regPIX.getOutputSignal(downloadedLeftRight) - 350;
        yPosition = regPIY.getOutputSignal(downloadedUpDown) - 350;
    }

    private void catchFromPad(String stringFromPad) {

        //501570000000000     exemplary string received from pad
        if (stringFromPad.length() < 18+10) {

            if(code.equals("00000")){
                code=stringFromPad.substring(15);
                System.out.println("code="+code);
            }
            
            if(code.equals(stringFromPad.substring(15))){
            
            String leftRightS = stringFromPad.substring(0, 3);
            downloadedLeftRight = Integer.valueOf(leftRightS);

            String upDownS = stringFromPad.substring(3, 6);
            downloadedUpDown = Integer.valueOf(upDownS);

            if (stringFromPad.charAt(6) == '1') {
                start = true;
            } else {
                start = false;
            }
            if (stringFromPad.charAt(7) == '1') {
                select = true;
            } else {
                select = false;
            }
            if (stringFromPad.charAt(8) == '1') {
                up = true;
            } else {
                up = false;
            }
            if (stringFromPad.charAt(9) == '1') {
                down = true;
            } else {
                down = false;
            }
            if (stringFromPad.charAt(10) == '1') {
                left = true;
            } else {
                left = false;
            }
            if (stringFromPad.charAt(11) == '1') {
                right = true;
            } else {
                right = false;
            }
            if (stringFromPad.charAt(12) == '1') {
                blue = true;
            } else {
                blue = false;
            }
            if (stringFromPad.charAt(13) == '1') {
                green = true;
            } else {
                green = false;
            }
            if (stringFromPad.charAt(14) == '1') {
                red = true;
            } else {
                red = false;
            }
            System.err.println(stringFromPad);
        }//////////////////
        } else {
            System.err.println("zle zaszyfrowane " + stringFromPad.getBytes().length);
        }
    }

    @Override
    public void run() {

        try {
            long timer = System.currentTimeMillis();

            DatagramSocket ds = new DatagramSocket(6000);
            byte[] b = new byte[4000];
            ds.setReceiveBufferSize(1);
            DatagramPacket dpreceive = new DatagramPacket(b, b.length);
            String s;

            Encryption encryption = new Encryption();
            encryption.initialProcedure(ds, dpreceive);

            while (true) {
                timer = System.currentTimeMillis();
                s = "";
                ds.receive(dpreceive);
                s = new String(dpreceive.getData());
                String sRozszyfrowane=encryption.descrypt(s.trim());
                catchFromPad(sRozszyfrowane);
                //catchFromPad(encryption.descrypt(s.trim()));

                positionSupport();
            }
        } catch (Exception ex) {
            Logger.getLogger(UDPPad.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
