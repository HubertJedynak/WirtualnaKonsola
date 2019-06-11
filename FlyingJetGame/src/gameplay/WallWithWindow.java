package gameplay;

import core.LogicManager;
import elements.Circle;
import elements.Obstacle;
import elements.Dartboard;
import graphics.Animation;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WallWithWindow implements Arrangement {

    private GameplayManagement gameplayManagement;
    private int cooldown = 0;

    public WallWithWindow(GameplayManagement gameplayManagement) {
        this.gameplayManagement = gameplayManagement;
    }

    @Override
    public void generate(int x, int y, int z) {
        cooldown++;
        gameplayManagement.isAvaliableToGenerate = false;
        if (cooldown < 2) {
            Random r = new Random();
            int rx = r.nextInt(3);
            int ry = r.nextInt(3);
            for (int ix = 0; ix < 7; ix++) {
                for (int iy = 0; iy < 5; iy++) {
                    if (!((ix == 2 + rx) && (iy == 1 + ry))) {
                        Animation a = new Animation(LogicManager.animationsLists.get("gwiazdka"), 5);
                        Obstacle obstacle = new Obstacle(a, 300 + 70 * (ix - 3), 150 + 50 * (iy - 2), z, gameplayManagement.allElements, gameplayManagement.obstacles);
                        obstacle.setScale(2);
                        obstacle.create();
                    } else {
                        Animation a = new Animation(LogicManager.animationsLists.get("babelek"), 10);
                        Circle bubble = new Circle(a, 300 + 70 * (ix - 3), 150 + 50 * (iy - 2), z, gameplayManagement.allElements, gameplayManagement.circles, 50);
                        bubble.setScale(2);
                        bubble.create();
                    }
                }
            }
        }

        for (int i = 0; i < gameplayManagement.circles.size(); i++) {
            gameplayManagement.circles.get(i).goZWithConstantVelocity(2);
        }
        if (cooldown > 400) {
            int initialSize = gameplayManagement.dartboards.size();
            for (int i = 0; i < initialSize; i++) {
                gameplayManagement.dartboards.get(0).delete();
            }
            initialSize = gameplayManagement.circles.size();
            for (int i = 0; i < initialSize; i++) {
                gameplayManagement.circles.get(0).delete();
            }

            cooldown = 0;
            gameplayManagement.isAvaliableToGenerate = true;
        }

    }
}
