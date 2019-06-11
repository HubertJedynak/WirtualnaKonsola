package elements;

import graphics.Animation;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import servicesOfPad.Regulator;

public class Element {

    protected ArrayList<Element> allElements;
    protected ArrayList<Element> specialElements;
    protected BufferedImage image;
    protected ArrayList<Animation> animations;
    protected Animation currentAnimation;
    protected int x, y, z;

    protected boolean isTranslucent, isToDraw, isInteractive, exists, isAnimated;
    protected double scale;
    protected Regulator regulaotrX, regulatorY, regulatorZ;

    //  public Obserwator o;
    public Element(ArrayList<Animation> animations, int x, int y, int z, ArrayList<Element> allElements) {
        isAnimated = true;
        this.animations = animations;
        if (!animations.isEmpty()) {
            currentAnimation = animations.get(0);
        }
        image = null;
        isTranslucent = false;
        isInteractive = true;
        isToDraw = true;
        this.x = x;
        this.y = y;
        this.z = z;
        scale = 1;
        regulaotrX = new Regulator(-1, 0, 0, 0, x);
        regulatorY = new Regulator(-1, 0, 0, 0, y);
        regulatorZ = new Regulator(-1, 0, 0, 0, z);

        this.allElements = allElements;
        this.specialElements = null;
        exists = true;
    }

    public Element(ArrayList<Animation> animations, int x, int y, int z, ArrayList<Element> allElements, ArrayList<Element> specialElements) {
        isAnimated = true;

        this.animations = animations;
        if (!animations.isEmpty()) {
            currentAnimation = animations.get(0);
        }
        image = null;
        isTranslucent = false;
        isInteractive = true;
        isToDraw = true;
        this.x = x;
        this.y = y;
        this.z = z;
        scale = 1;
        regulaotrX = new Regulator(-1, 0, 0, 0, x);
        regulatorY = new Regulator(-1, 0, 0, 0, y);
        regulatorZ = new Regulator(-1, 0, 0, 0, z);

        this.allElements = allElements;
        this.specialElements = specialElements;
        exists = true;
    }

    public Element(ArrayList<Animation> animations, ArrayList<Element> allElements, Regulator rx, Regulator ry, Regulator rz) {
        isAnimated = true;

        this.animations = animations;
        if (!animations.isEmpty()) {
            currentAnimation = animations.get(0);
        }
        image = null;
        this.x = rx.initialValue;
        this.y = ry.initialValue;
        this.z = rz.initialValue;
        isTranslucent = false;
        isInteractive = true;
        isToDraw = true;
        scale = 1;
        regulaotrX = rx;
        regulatorY = ry;
        regulatorZ = rz;
        exists = true;
        this.allElements = allElements;
        this.specialElements = null;
    }

    public Element(ArrayList<Animation> animations, ArrayList<Element> allElements, ArrayList<Element> specialElements, Regulator rx, Regulator ry, Regulator rz) {
        isAnimated = true;

        this.animations = animations;
        if (!animations.isEmpty()) {
            currentAnimation = animations.get(0);
        }
        image = null;
        this.x = rx.initialValue;
        this.y = ry.initialValue;
        this.z = rz.initialValue;
        isTranslucent = false;
        isInteractive = true;
        isToDraw = true;
        scale = 1;
        regulaotrX = rx;
        regulatorY = ry;
        regulatorZ = rz;
        exists = true;
        this.allElements = allElements;
        this.specialElements = specialElements;
    }

    public Element(Animation animation, int x, int y, int z, ArrayList<Element> allElements) {
        isAnimated = true;

        animations = new ArrayList<>();
        animations.add(animation);
        currentAnimation = animations.get(0);
        image = null;
        isTranslucent = false;
        isInteractive = true;
        isToDraw = true;
        this.x = x;
        this.y = y;
        this.z = z;
        scale = 1;
        regulaotrX = new Regulator(-1, 0, 0, 0, x);
        regulatorY = new Regulator(-1, 0, 0, 0, y);
        regulatorZ = new Regulator(-1, 0, 0, 0, z);

        this.allElements = allElements;
        this.specialElements = null;
        exists = true;
    }

    public Element(Animation animation, int x, int y, int z, ArrayList<Element> allElements, ArrayList<Element> specialElements) {
        isAnimated = true;

        animations = new ArrayList<>();
        animations.add(animation);
        currentAnimation = animations.get(0);
        image = null;
        isTranslucent = false;
        isInteractive = true;
        isToDraw = true;
        this.x = x;
        this.y = y;
        this.z = z;
        scale = 1;
        regulaotrX = new Regulator(-1, 0, 0, 0, x);
        regulatorY = new Regulator(-1, 0, 0, 0, y);
        regulatorZ = new Regulator(-1, 0, 0, 0, z);

        this.allElements = allElements;
        this.specialElements = specialElements;
        exists = true;
    }

    public Element(Animation animation, ArrayList<Element> allElements, Regulator rx, Regulator ry, Regulator rz) {
        isAnimated = true;

        animations = new ArrayList<>();
        animations.add(animation);
        currentAnimation = animations.get(0);
        image = null;
        this.x = rx.initialValue;
        this.y = ry.initialValue;
        this.z = rz.initialValue;
        isTranslucent = false;
        isInteractive = true;
        isToDraw = true;
        scale = 1;
        regulaotrX = rx;
        regulatorY = ry;
        regulatorZ = rz;
        exists = true;
        this.allElements = allElements;
        this.specialElements = null;
    }

    public Element(Animation animation, ArrayList<Element> allElements, ArrayList<Element> specialElements, Regulator rx, Regulator ry, Regulator rz) {
        isAnimated = true;

        animations = new ArrayList<>();
        animations.add(animation);
        currentAnimation = animations.get(0);
        image = null;
        this.x = rx.initialValue;
        this.y = ry.initialValue;
        this.z = rz.initialValue;
        isTranslucent = false;
        isInteractive = true;
        isToDraw = true;
        scale = 1;
        regulaotrX = rx;
        regulatorY = ry;
        regulatorZ = rz;
        exists = true;
        this.allElements = allElements;
        this.specialElements = specialElements;
    }

    public Element(BufferedImage image, int x, int y, int z, ArrayList<Element> allElements) {
        isAnimated = false;

        this.animations = null;
        this.currentAnimation = null;
        this.image = image;
        isTranslucent = false;
        isInteractive = true;
        isToDraw = true;
        this.x = x;
        this.y = y;
        this.z = z;
        scale = 1;
        regulaotrX = new Regulator(-1, 0, 0, 0, x);
        regulatorY = new Regulator(-1, 0, 0, 0, y);
        regulatorZ = new Regulator(-1, 0, 0, 0, z);

        this.allElements = allElements;
        this.specialElements = null;
        exists = true;
    }

    public Element(BufferedImage image, int x, int y, int z, ArrayList<Element> allElements, ArrayList<Element> specialElements) {
        isAnimated = false;
        this.animations = null;
        this.currentAnimation = null;
        this.image = image;
        isTranslucent = false;
        isInteractive = true;
        isToDraw = true;
        this.x = x;
        this.y = y;
        this.z = z;
        scale = 1;
        regulaotrX = new Regulator(-1, 0, 0, 0, x);
        regulatorY = new Regulator(-1, 0, 0, 0, y);
        regulatorZ = new Regulator(-1, 0, 0, 0, z);

        this.allElements = allElements;
        this.specialElements = specialElements;
        exists = true;
    }

    public Element(BufferedImage image, ArrayList<Element> allElements, Regulator rx, Regulator ry, Regulator rz) {
        isAnimated = false;
        this.animations = null;
        this.currentAnimation = null;
        this.image = image;
        this.x = rx.initialValue;
        this.y = ry.initialValue;
        this.z = rz.initialValue;
        isTranslucent = false;
        isInteractive = true;
        isToDraw = true;
        scale = 1;
        regulaotrX = rx;
        regulatorY = ry;
        regulatorZ = rz;
        exists = true;
        this.allElements = allElements;
        this.specialElements = null;
    }

    public Element(BufferedImage image, ArrayList<Element> allElements, ArrayList<Element> specialElements, Regulator rx, Regulator ry, Regulator rz) {
        isAnimated = false;
        this.animations = null;
        this.currentAnimation = null;
        this.image = image;
        this.x = rx.initialValue;
        this.y = ry.initialValue;
        this.z = rz.initialValue;
        isTranslucent = false;
        isInteractive = true;
        isToDraw = true;
        scale = 1;
        regulaotrX = rx;
        regulatorY = ry;
        regulatorZ = rz;
        exists = true;
        this.allElements = allElements;
        this.specialElements = specialElements;
    }

    public boolean isTranslucent() {
        return isTranslucent;
    }

    public void setIsTranslucent(boolean isTransparent) {
        this.isTranslucent = isTransparent;
    }

    public BufferedImage getImage() {
        if (currentAnimation != null) {
            return currentAnimation.getCurrentImage();
        } else {
            return image;
        }
    }

    public double getScale() {
        return scale;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setAnimation(int numberOfAnimation) {
        if (numberOfAnimation <= animations.size()) {
            this.currentAnimation = animations.get(numberOfAnimation);
        }
    }

    public void setScale(double skala) {
        this.scale = skala;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void goX(int x) {
        this.x = (int) regulaotrX.getOutputSignal(x);
    }

    public void goY(int y) {
        this.y = (int) regulatorY.getOutputSignal(y);
    }

    public void goZ(int z) {
        this.z = (int) regulatorZ.getOutputSignal(z);
    }

    public void goZWithConstantVelocity(int step) {
        this.z = this.z + step;
    }

    public void goYWithConstantVelocity(int step) {
        this.y = this.y + step;
    }

    public void goXWithConstantVelocity(int step) {
        this.x = this.x + step;
    }

    public Animation getAnimation() {
        return currentAnimation;
    }

    public boolean isInteractive() {
        return isInteractive;
    }

    public boolean isToDraw() {
        return isToDraw;
    }

    public void setIsInteractive(boolean isInteractive) {
        this.isInteractive = isInteractive;
    }

    public void setIsToDraw(boolean isToDraw) {
        this.isToDraw = isToDraw;
    }

    public void create() {
        exists = true;
        if (!allElements.contains(this)) {
            allElements.add(this);
        }

        if (specialElements != null) {
            specialElements.add(this);
        }
    }

    public void delete() {
        exists = false;
        allElements.remove(this);
        if (specialElements != null) {
            specialElements.remove(this);
        }
    }

    public boolean isAnimated() {
        return isAnimated;
    }

    public boolean exists() {
        return exists;
    }

}
