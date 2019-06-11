package com.example.kiryu.udppad;

import android.util.Log;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Encryption {
    BigInteger bIE;
    BigInteger bIN;

    public void initialProcedure(DatagramSocket ds, DatagramPacket dpSend, InetAddress ia) throws Exception {
        String string ="";

        byte[] bytes;

        bytes = string.getBytes();
        dpSend = new DatagramPacket(bytes, bytes.length, ia, 6000);
        Thread.sleep(100);
        ds.send(dpSend);


        byte[] b=new byte[4000];
        DatagramPacket dpReceive = new DatagramPacket(b, b.length);
        ds.receive(dpReceive);
        String e=new String(dpReceive.getData());


        b=new byte[4000];
        dpReceive = new DatagramPacket(b, b.length);
        ds.receive(dpReceive);
        String n=new String(dpReceive.getData());

        bIE=new BigInteger(e.trim());
        bIN=new BigInteger(n.trim());



    }

    public String encrypt(String string){
        BigInteger bi=new BigInteger(string);

        return (bi.modPow(bIE,bIN)).toString();
    }

}
