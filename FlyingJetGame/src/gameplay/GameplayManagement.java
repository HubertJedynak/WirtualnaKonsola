package gameplay;

import elements.Element;
import java.util.ArrayList;
import java.util.Random;

public class GameplayManagement {

    private Arrangement arrangement;
    protected ArrayList<Element> allElements;
    protected ArrayList<Element> circles;
    protected ArrayList<Element> obstacles;
    protected ArrayList<Element> dartboards;
    protected boolean isAvaliableToGenerate;

    private Single single;
    private Line line;
    private Spiral spiral;
    private Wall wall;
    private WallWithWindow wallWithWindow;

    private int x = 0, y = 0, z = 580;
    private int choice = 0;

    public GameplayManagement(ArrayList<Element> allElements, ArrayList<Element> circles, ArrayList<Element> obstancles, ArrayList<Element> dartboards) {
        this.allElements = allElements;
        this.circles = circles;
        this.obstacles = obstancles;
        this.dartboards = dartboards;
        isAvaliableToGenerate = true;

        single = new Single(this);
        line = new Line(this);
        spiral = new Spiral(this);
        wall = new Wall(this);
        wallWithWindow = new WallWithWindow(this);
    }

    public void manage() {

        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).goZWithConstantVelocity(-2);
        }
        for (int i = 0; i < circles.size(); i++) {
            circles.get(i).goZWithConstantVelocity(-5);
        }
        Random r = new Random();
        if (isAvaliableToGenerate) {
            choice = r.nextInt(5);
            x = r.nextInt(200) + 200;
            y = r.nextInt(100) + 100;
        }
        switch (choice) {
            case 0:
                arrangement = single;
                break;
            case 1:
                arrangement = line;
                break;
            case 2:
                arrangement = wall;
                break;
            case 3:
                arrangement = wallWithWindow;
                break;
            case 4:
                arrangement = spiral;
                break;
        }

        arrangement.generate(x, y, z);
    }

}
