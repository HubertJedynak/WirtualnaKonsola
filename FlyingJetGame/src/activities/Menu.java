package activities;

import core.LogicManager;
import elements.Element;

import graphics.Animation;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import servicesOfPad.Regulator;
import servicesOfPad.UDPPad;

public class Menu implements Activity {

    private UDPPad pad;
    private ArrayList<Element> elements;
    private LogicManager.Observer observer;
    private ArrayList<Element> fallingElements;
    private Element start, author, howToPlay, pointer, cursor;
    private boolean isPaused = false;

    private boolean[] options;
    private int indexOfOptions = 0;

    public Menu(ArrayList<Element> elements, UDPPad pad, LogicManager.Observer observer) throws Exception {
        elements.clear();
        LogicManager.background = 0xaaeeff;
        this.elements = elements;
        this.pad = pad;
        this.observer = observer;

        this.fallingElements = new ArrayList<Element>();
        options = new boolean[3];
        options[0] = true;
        options[1] = false;
        options[2] = false;

        Animation a;

        a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("start").clone(), 1000);
        start = new Element(a, 300, 150, 0, elements);
        start.create();

        a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("how to play").clone(), 1000);
        howToPlay = new Element(a, 300, 210, 0, elements);
        howToPlay.create();

        a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("kursor").clone(), 1000);
        cursor = new Element(a, 300, 150, 0, elements);
        cursor.create();

        a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("autor").clone(), 1000);

        author = new Element(a, 300, 180, 0, elements);
        author.create();

        a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("wskaznik").clone(), 30);

        pointer = new Element(a, 250, 150, 0, elements);
        pointer.create();

    }

    @Override
    public void manageOfCollisions() {

    }

    @Override
    public void manageOfElements() {

        Random r = new Random();
        if (r.nextInt(10) == 0) {
            int choice = r.nextInt(3);
            Animation a = new Animation(LogicManager.animationsLists.get("gwiazdka"), 5);
            if (choice == 0) {
                a = new Animation(LogicManager.animationsLists.get("gwiazdka"), 5);
            }
            if (choice == 1) {
                a = new Animation(LogicManager.animationsLists.get("tarcza"), 5);
            }
            if (choice == 2) {
                a = new Animation(LogicManager.animationsLists.get("babelek"), 5);
            }
            Regulator rx = new Regulator(0.02965, -0.02916, -1.96032, 0.9608, r.nextInt(600));
            Regulator ry = new Regulator(0.02965, -0.02916, -1.96032, 0.9608, 300);
            Regulator rz = new Regulator(0.02965, -0.02916, -1.96032, 0.9608, 10);

            Element e = new Element(a, elements, fallingElements, rx, ry, rz);
            e.create();
        }

        for (int i = 0; i < fallingElements.size(); i++) {
            fallingElements.get(i).goY(-50);
        }

        for (int i = 0; i < fallingElements.size(); i++) {
            if (fallingElements.get(i).getY() < -20) {
                fallingElements.get(i).delete();
            }
        }

        cursor.setX((int) pad.xPosition * 2);
        cursor.setY((int) pad.yPosition);

        options[0] = false;
        options[1] = false;
        options[2] = false;
        options[indexOfOptions] = true;
        if (indexOfOptions == 0) {
            pointer.setY(150);
        } else if (indexOfOptions == 1) {
            pointer.setY(180);
        } else if (indexOfOptions == 2) {
            pointer.setY(210);
        }

        if (pad.down) {
            down();
        }
        if (pad.up) {
            up();
        }
        if (pad.start) {
            try {
                selectOption();
            } catch (Exception ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void down() {
        while (pad.down) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (indexOfOptions < 2) {
            indexOfOptions++;
        }
    }

    private void up() {
        while (pad.up) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (indexOfOptions > 0) {
            indexOfOptions--;
        }
    }

    private void selectOption() throws Exception {
        while (pad.start) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (options[0] == true) {
            LogicManager.loadActivity(new Gameplay(elements, pad, observer));
        } else if (options[2] == true) {
            LogicManager.loadActivity(new HowToPlay(elements, pad, observer));
        } else if (options[1] == true) {
            LogicManager.loadActivity(new Author(elements, pad, observer));
        }
    }

}
