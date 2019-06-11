package elements;

import core.LogicManager;
import graphics.Animation;
import java.util.ArrayList;
import servicesOfPad.Regulator;

public class Player extends Element {

    private ArrayList<Element> bullets;
    public final int PLANE = 0, PLANE_RIGHT = 1, PLANE_LEFT = 2;

    private int currentCooldown = 0;
    private int maxCooldown = 20;

    private int points = 0;
    private int chances = 3;

    public Player(ArrayList<Animation> animations, ArrayList<Element> elements, Regulator rx, Regulator ry, Regulator rz, ArrayList<Element> bullets) {
        super(animations, elements, rx, ry, rz);
        this.bullets = bullets;
    }

    public Player(ArrayList<Animation> animations, ArrayList<Element> allElements, Regulator rx, Regulator ry, Regulator rz) {
        super(animations, allElements, rx, ry, rz);
    }

    public void shoot() {
        if (this.exists) {
            currentCooldown++;
            if (currentCooldown > maxCooldown) {
                currentCooldown = 0;
                ArrayList<Animation> al = new ArrayList<>();
                al.add(new Animation(LogicManager.getGraphicsList("atak gracza"), 50));

                Bullet bullet = new Bullet(al, this.getX(), this.getY(), this.getZ(), this.allElements, bullets);
                bullet.create();
            }
        }
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setChances(int chances) {
        this.chances = chances;
    }

    public int getChances() {
        return chances;
    }

    @Override
    public void create() {
        super.create();
    }

}
