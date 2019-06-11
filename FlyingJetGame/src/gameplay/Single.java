package gameplay;

import core.LogicManager;
import elements.Circle;
import graphics.Animation;
import java.util.Random;

public class Single implements Arrangement {

    private GameplayManagement gameplayManagement;
    private int cooldown = 0;

    public Single(GameplayManagement gameplayManagement) {
        this.gameplayManagement = gameplayManagement;
    }

    @Override
    public void generate(int x, int y, int z) {
        cooldown++;
        gameplayManagement.isAvaliableToGenerate = false;
        if (cooldown < 2) {
            Animation a = new Animation(LogicManager.animationsLists.get("babelek"), 10);
            Random r = new Random();
            Circle bubble = new Circle(a, x, y, z, gameplayManagement.allElements, gameplayManagement.circles, 1);
            bubble.create();
            bubble.setScale(2);
        }

        if (cooldown > 200) {
            cooldown = 0;
            gameplayManagement.isAvaliableToGenerate = true;
        }
    }
}
