package elements;

import graphics.Animation;
import java.util.ArrayList;
import servicesOfPad.Regulator;

public class Dartboard extends Element {

    private int xTarget, yTarget, zTarget;

    public Dartboard(Animation animation, int x, int y, int z, ArrayList<Element> allElements, ArrayList<Element> specialElements) {
        super(animation, x, y, z, allElements, specialElements);
    }

    public Dartboard(Animation animation, ArrayList<Element> allElements, ArrayList<Element> specialElements, Regulator rx, Regulator ry, Regulator rz, int xTarget, int yTarget, int zTarget) {
        super(animation, allElements, specialElements, rx, ry, rz);
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.zTarget = zTarget;
    }

    public int getXTarget() {
        return xTarget;
    }

    public int getYTarget() {
        return yTarget;
    }

    public int getZTarget() {
        return zTarget;
    }

}
