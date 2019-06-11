package servicesOfPad;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Encryption {

    protected AsymmetricKey key;
    private InetAddress IPOfMobileDevice;
    private DatagramPacket dpSend;

    public void initialProcedure(DatagramSocket ds, DatagramPacket dpreceive) throws Exception {

        ds.receive(dpreceive);
        IPOfMobileDevice = dpreceive.getAddress();
        System.err.println(IPOfMobileDevice);
        key = new AsymmetricKey();

        String sE = key.e.toString();
        byte[] bytes;

        bytes = sE.getBytes();
        dpSend = new DatagramPacket(bytes, bytes.length, IPOfMobileDevice, 6000);

        Thread.sleep(300);
        ds.send(dpSend);

        String sN = key.n.toString();

        bytes = sN.getBytes();
        dpSend = new DatagramPacket(bytes, bytes.length, IPOfMobileDevice, 6000);

        Thread.sleep(300);
        ds.send(dpSend);

    }

    public String descrypt(String string) {
        BigInteger bi = new BigInteger(string);
        return (bi.modPow(key.d, key.n)).toString();
    }
}
