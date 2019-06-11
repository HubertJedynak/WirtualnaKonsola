package servicesOfPad;

public class Regulator {

    ///////////////
    //      a*z+b
    // Gz=-------------
    //    z^2 +c*z +d

    private double a, b, c, d;
    private double y0, y1, y2, u1, u2;// y(n), y(n-1), y(n-2), u(n-1), u(n-2)
    public int initialValue;

    public Regulator(double a, double b, double c, double d, int initialValue) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        y0 = initialValue;
        y1 = initialValue;
        y2 = initialValue;
        u1 = initialValue;
        u2 = initialValue;
        this.initialValue = initialValue;
    }
    double max = 0;

    public double getOutputSignal(int u0) {
        y0 = (-c * y1 - d * y2 + a * u1 + b * u2);

        y2 = y1;
        y1 = y0;
        u2 = u1;
        u1 = u0;
        return y0;
    }

}
