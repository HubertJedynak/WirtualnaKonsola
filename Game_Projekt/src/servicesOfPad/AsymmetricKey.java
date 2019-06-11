package servicesOfPad;

import java.math.BigInteger;
import java.util.Random;

public class AsymmetricKey {

    protected BigInteger n;
    protected BigInteger e;
    protected BigInteger d;

    BigInteger counterOfD;
    BigInteger pr1;
    BigInteger pr2;
    BigInteger fin;
    BigInteger k;

    public AsymmetricKey() {

        BigInteger biMin = new BigInteger("40000000000000000000");
        BigInteger biMax = new BigInteger("60060011111111199999");
        BigInteger biMinEncrypted;
        BigInteger biMaxEncrypted;

        do {
            System.out.println("start");
            long timer = System.currentTimeMillis();
            pr1 = BigInteger.probablePrime(512, new Random());
            timer = System.currentTimeMillis() - timer;
            System.out.println("czas pr1 " + timer);
            System.out.println("wygenerowano pr1");
            pr2 = BigInteger.probablePrime(512, new Random());
            System.out.println("wygenerpwano pr2");
            fin = (pr1.add(new BigInteger("-1"))).multiply(pr2.add(new BigInteger("-1")));
            
            k = new BigInteger("3");

            e = new BigInteger("2");
            counterOfD = (k.multiply(fin)).add(BigInteger.ONE);
            for (int i = 0; i < 1000; i++) {
                if ((!e.gcd(fin).equals(BigInteger.ONE)) || (!(counterOfD.mod(e)).equals(BigInteger.ZERO))) {
                    e = e.add(BigInteger.ONE);
                }
            }

            d = ((k.multiply(fin)).add(BigInteger.ONE)).divide(e);

            System.out.println("pr1 " + pr1);
            System.out.println("pr2 " + pr2);
            System.out.println("fin " + fin);
            System.out.println("e " + e);
            System.out.println("k " + k);
            System.out.println("mianownikD " + counterOfD);
            System.out.println("d " + d);
            System.out.println("dlugosc d" + d.toString().length());
            System.out.println("modulo " + counterOfD.mod(e));

            n = pr1.multiply(pr2);
            System.out.println("n " + n);
            System.out.println("n length " + n.bitLength());

            biMinEncrypted = biMin.modPow(e, n);
            biMaxEncrypted = biMax.modPow(e, n);

        } while (!(counterOfD.mod(e)).equals(BigInteger.ZERO) || !(biMinEncrypted.toString().getBytes().length == biMaxEncrypted.toString().getBytes().length));
        System.out.println("biminzasz " + biMinEncrypted);
        System.out.println("bimaxzasz " + biMaxEncrypted);
        System.out.println("biminzasz length " + biMinEncrypted.toString().getBytes().length);
        System.out.println("bimaxzasz length " + biMaxEncrypted.toString().getBytes().length);

    }
}
