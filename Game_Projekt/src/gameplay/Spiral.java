package gameplay;

import core.LogicManager;
import elements.Circle;
import graphics.Animation;
import java.util.Random;

public class Spiral implements Arrangement {

    private GameplayManagement gameplayManagement;
    private int cooldown = 0;
    private int stepCooldown = 50; // co tyle klatek tworzy sie nowy element
    private int countOfElements = 0;
    private int xOfElement = 0, yOfElement = 0;
    private double step = 0;

    public Spiral(GameplayManagement gameplayManagement) {
        this.gameplayManagement = gameplayManagement;
    }

    @Override
    public void generate(int x, int y, int z) {
        cooldown++;
        stepCooldown++;
        gameplayManagement.isAvaliableToGenerate = false;
        if (stepCooldown > 25 && countOfElements < 14) {
            step = step + 0.6;
            xOfElement = (int) (30 * Math.sin(step));
            yOfElement = (int) (30 * Math.cos(step));
            Animation a = new Animation(LogicManager.animationsLists.get("babelek"), 10);
            Random r = new Random();
            Circle bubble = new Circle(a, x + xOfElement, y + yOfElement, z, gameplayManagement.allElements, gameplayManagement.circles, 3);
            bubble.setScale(2);
            bubble.create();
            stepCooldown = 0;
            countOfElements++;
        }

        if (cooldown > 500) {
            countOfElements = 0;
            stepCooldown = 0;
            cooldown = 0;
            gameplayManagement.isAvaliableToGenerate = true;
        }

    }
}
