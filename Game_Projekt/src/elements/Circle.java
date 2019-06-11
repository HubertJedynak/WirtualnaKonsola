package elements;

import graphics.Animation;
import java.util.ArrayList;

public class Circle extends Element {

    private int points;

    public Circle(Animation animation, int x, int y, int z, ArrayList<Element> allElements, ArrayList<Element> specialElements, int points) {
        super(animation, x, y, z, allElements, specialElements);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

}
