package gameplay;

import core.LogicManager;
import elements.Circle;
import graphics.Animation;
import java.util.Random;

public class Line implements Arrangement {

    private GameplayManagement gameplayManagement;
    private int cooldown = 0;
    private int stepCooldown = 50; // co tyle klatek tworzy sie nowy element
    private int countOfElements = 0;

    public Line(GameplayManagement gameplayManagement) {
        this.gameplayManagement = gameplayManagement;
    }

    @Override
    public void generate(int x, int y, int z) {
        cooldown++;
        stepCooldown++;
        gameplayManagement.isAvaliableToGenerate = false;
        if (stepCooldown > 25 && countOfElements < 7) {
            Animation a = new Animation(LogicManager.animationsLists.get("babelek"), 10);
            Random r = new Random();
            Circle bubble = new Circle(a, x, y, z, gameplayManagement.allElements, gameplayManagement.circles, 2);
            bubble.setScale(2);
            bubble.create();
            stepCooldown = 0;
            countOfElements++;
        }

        if (cooldown > 400) {
            countOfElements = 0;
            stepCooldown = 0;
            cooldown = 0;
            gameplayManagement.isAvaliableToGenerate = true;
        }

    }
}
