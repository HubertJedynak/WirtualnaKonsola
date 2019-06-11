package core;

import activities.Activity;
import activities.Menu;
import elements.Element;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import servicesOfPad.UDPPad;

public class LogicManager {

    private static Activity currentActivity;
    public ArrayList<Element> toDraw = new ArrayList<>();
    public ArrayList<Element> elements = new ArrayList<>();
    public static int background = 0xaaeeff;
    public static final int widthOfEnvironment = 600, heightOfEnvironment = 300, depthOfEnvironment = 600;
    private UDPPad pad;
    private Observer observer;

    public static HashMap<String, ArrayList<BufferedImage>> animationsLists;
    public static HashMap<String, HashMap<String, BufferedImage>> FontsLists;

    public LogicManager() throws Exception {
        this.pad = new UDPPad();
        this.observer = new Observer();

        animationsLists = new HashMap<>();
        File animations = new File("res/animations");
        String[] animationList = animations.list();
        ArrayList<BufferedImage> lAnimation = new ArrayList<>();
        for (int i = 0; i < animationList.length; i++) {
            lAnimation.clear();
            File animation = new File("res/animations/" + animationList[i]);
            for (int j = 1; j < animation.list().length + 1; j++) {
                lAnimation.add(ImageIO.read(new File(animation.getPath() + "/" + String.valueOf(j) + ".bmp")));
            }
            animationsLists.putIfAbsent(animationList[i], (ArrayList<BufferedImage>) lAnimation.clone());
        }

        FontsLists = new HashMap<>();
        File fonts = new File("res/fonts");
        String[] fontList = fonts.list();
        HashMap<String, BufferedImage> hFont = new HashMap<>();
        for (int i = 0; i < fontList.length; i++) {
            hFont.clear();
            File font = new File("res/fonts/" + fontList[i]);
            for (int j = 0; j < 10; j++) {
                hFont.putIfAbsent(String.valueOf(j), ImageIO.read(new File(font.getPath() + "/" + String.valueOf(j) + ".bmp")));
            }
            FontsLists.putIfAbsent(fontList[i], (HashMap<String, BufferedImage>) hFont.clone());/////////////
        }

        currentActivity = new Menu(elements, pad, observer);

    }

    public void updateData() throws Exception {
        toDraw.clear();
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getZ() > depthOfEnvironment || elements.get(i).getZ() < -widthOfEnvironment * 0.5) {
                elements.get(i).delete();
            }
        }

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).isToDraw()) {
                toDraw.add(elements.get(i));
            }
        }

        observer.observe();

        currentActivity.manageOfCollisions();

        currentActivity.manageOfElements();

        for (int i = 0; i < toDraw.size(); i++) {
            if (toDraw.get(i).isAnimated()) {
                toDraw.get(i).getAnimation().checkStepOfFrame();
            }
        }

        toDraw.sort(new Comparator<Element>() {
            @Override
            public int compare(Element t, Element t1) {
                return t1.getZ() - t.getZ();
            }
        });

    }

    public static ArrayList<BufferedImage> getGraphicsList(String directoryName) {
        return animationsLists.get(directoryName);
    }

    public static void loadActivity(Activity loadedActivity) {
        currentActivity = loadedActivity;
    }

    public class Observer {

        private ArrayList<CollidingPair> collidingPairs = new ArrayList<>();

        private void observe() {
            collidingPairs.clear();
            for (int i = 0; i < toDraw.size(); i++) {
                if (toDraw.get(i).isInteractive()) {
                    for (int j = 0; j < toDraw.size(); j++) {
                        if (toDraw.get(j).isInteractive()) {
                            if (i != j) {
                                if (isColliding(toDraw.get(i), toDraw.get(j))) {
                                    collidingPairs.add(new CollidingPair(toDraw.get(i), toDraw.get(j)));
                                }
                            }
                        }
                    }
                }
            }
        }

        public boolean isColliding(Element e1, Element e2) {
            if (Math.abs(e1.getZ() - e2.getZ()) < 35) {
                int realDistanceX = Math.abs(e1.getX() - e2.getX());
                int distanceDuringCollisionX = (int) Math.abs(0.5 * e1.getImage().getWidth() * e1.getScale() + 0.5 * e2.getImage().getWidth() * e2.getScale());
                int realDistanceY = Math.abs(e1.getY() - e2.getY());
                int distanceDuringCollisionY = (int) Math.abs(0.5 * e1.getImage().getHeight() * e1.getScale() + 0.5 * e2.getImage().getHeight() * e2.getScale());
                if ((realDistanceX < distanceDuringCollisionX) && (realDistanceY < distanceDuringCollisionY)) {
                    return true;
                }
            }
            return false;
        }

        public ArrayList<CollidingPair> getCollidingPairs() {
            return collidingPairs;
        }

    }

    public class CollidingPair {

        public Element e1, e2;

        public CollidingPair(Element e1, Element e2) {
            this.e1 = e1;
            this.e2 = e2;
        }
    }

}
