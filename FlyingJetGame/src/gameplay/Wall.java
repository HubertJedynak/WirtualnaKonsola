package gameplay;

import core.LogicManager;
import elements.Circle;
import elements.Dartboard;
import graphics.Animation;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import servicesOfPad.Regulator;

public class Wall implements Arrangement {

    private GameplayManagement gameplayManagement;
    private int cooldown = 0;

    public Wall(GameplayManagement gameplayManagement) {
        this.gameplayManagement = gameplayManagement;
    }

    @Override
    public void generate(int x, int y, int z) {
        cooldown++;
        gameplayManagement.isAvaliableToGenerate = false;
        if (cooldown < 2) {
            for (int ix = 0; ix < 7; ix++) {
                for (int iy = 0; iy < 7; iy++) {
                    Animation a = new Animation(LogicManager.animationsLists.get("tarcza"), 10);
                    Regulator rx = new Regulator(0.004829, 0.004671, -1.886, 0.9048, 300);
                    Regulator ry = new Regulator(0.004829, 0.004671, -1.886, 0.9048, 150);
                    Regulator rz = new Regulator(0.004829, 0.004671, -1.886, 0.9048, z - 300);
                    Dartboard dartboard = new Dartboard(a, gameplayManagement.allElements, gameplayManagement.dartboards, rx, ry, rz, 2 * (300 + 60 * (ix - 3)), 2 * (150 + 60 * (iy - 3)), z - 300);
                    dartboard.setScale(2);
                    dartboard.create();
                }
            }
        }
        for (int i = 0; i < gameplayManagement.dartboards.size(); i++) {
            gameplayManagement.dartboards.get(i).goX(((Dartboard) gameplayManagement.dartboards.get(i)).getXTarget());
            gameplayManagement.dartboards.get(i).goY(((Dartboard) gameplayManagement.dartboards.get(i)).getYTarget());
        }

        if (cooldown > 800) {
            int initialSize = gameplayManagement.dartboards.size();
            for (int i = 0; i < initialSize; i++) {
                gameplayManagement.dartboards.get(0).delete();
            }
            cooldown = 0;
            gameplayManagement.isAvaliableToGenerate = true;
        }

    }
}
