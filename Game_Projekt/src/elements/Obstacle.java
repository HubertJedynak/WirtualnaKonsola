package elements;

import graphics.Animation;
import java.util.ArrayList;

public class Obstacle extends Element {

    public Obstacle(Animation animation, int x, int y, int z, ArrayList<Element> allElements, ArrayList<Element> specialElements) {
        super(animation, x, y, z, allElements, specialElements);
    }

    public Obstacle(ArrayList<Animation> animations, int x, int y, int z, ArrayList<Element> allElements, ArrayList<Element> specialElements) {
        super(animations, x, y, z, allElements, specialElements);
    }

}
