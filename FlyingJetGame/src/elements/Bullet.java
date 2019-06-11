package elements;

import graphics.Animation;
import java.util.ArrayList;

public class Bullet extends Element {

    public Bullet(ArrayList<Animation> animations, int x, int y, int z, ArrayList<Element> allElements, ArrayList<Element> specialElements) {
        super(animations, x, y, z, allElements, specialElements);
    }

}
