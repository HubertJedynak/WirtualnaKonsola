package activities;

import core.LogicManager;
import elements.Element;

import elements.*;
import elements.Player;
import graphics.Animation;
import graphics.Number;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import gameplay.GameplayManagement;
import servicesOfPad.Regulator;
import servicesOfPad.UDPPad;

public class Gameplay implements Activity {

    private GameplayManagement gameplayManagement;
    private UDPPad pad;
    private ArrayList<Element> elements;
    private Player plane;
    private Element cursor, pause, points, chances;
    private Element back;
    private Number numberOfPoints;
    private Number numberOfChances;
    private ArrayList<Element> stars, bubbles, dartboards, bullets;
    private ArrayList<Element> cloudsInBackground;
    private ArrayList<Integer> targetsXOfCloudsInBackGround;
    private ArrayList<Integer> targetsYOfCloudsInBackground;
    private ArrayList<Element> flyingClouds;
    
    private LogicManager.Observer observer;
    private boolean isPaused = false;

    public Gameplay(ArrayList<Element> elements, UDPPad pad, LogicManager.Observer observer) throws Exception {
        elements.clear();
        this.elements = elements;
        this.pad = pad;
        this.observer = observer;

        dartboards = new ArrayList<>();
        bubbles = new ArrayList<>();
        stars = new ArrayList<>();
        bullets = new ArrayList<>();
        
        cloudsInBackground = new ArrayList<>();
        targetsXOfCloudsInBackGround=new ArrayList<>();
        targetsYOfCloudsInBackground=new ArrayList<>();

        flyingClouds=new ArrayList<>();
        
        gameplayManagement = new GameplayManagement(elements, bubbles, stars, dartboards);

         Animation a;
        ArrayList<Animation> al = new ArrayList<>();

        a=new Animation(LogicManager.animationsLists.get("wroc"), 50);
        back=new Element((Animation) a.clone(), 50, 100, 0, elements);
        
        for (int i = 0; i < 10; i++) {
            a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("chmurka").clone(), 50);
            al.clear();
            al.add(a);
            Random r=new Random();
            Regulator rx = new Regulator(0.000005, 0.000005, -1.99998, 1, r.nextInt(600));
            Regulator ry = new Regulator(0.0000005, 0.0000005, -1.999998, 1, r.nextInt(300));
            Regulator rz = new Regulator(0.0000005, 0.0000005, -1.999998, 1, 580);
            Element cloud=new Element((Animation) a.clone(), elements, cloudsInBackground, rx, ry, rz);
            cloud.setIsTranslucent(true);
            cloud.create();
            cloud.setScale(3);
            targetsXOfCloudsInBackGround.add(r.nextInt(600));
            targetsYOfCloudsInBackground.add(r.nextInt(300));
        }

        
        a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("kursor").clone(), 50);
        al.clear();
        al.add(a);
        cursor = new Element((ArrayList<Animation>) al.clone(), 300, 150, -10, elements);
        cursor.create();

        a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("samolot").clone(), 5);
        al.clear();
        al.add((Animation) a.clone());
        a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("samolot prawo").clone(), 5);
        al.add((Animation) a.clone());
        a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("samolot lewo").clone(), 5);
        al.add((Animation) a.clone());
        Regulator rx = new Regulator(0.02965, -0.02916, -1.96032, 0.9608, 300);
        Regulator ry = new Regulator(0.02965, -0.02916, -1.96032, 0.9608, 290);
        Regulator rz = new Regulator(0.02965, -0.02916, -1.96032, 0.9608, 0);
        plane = new Player((ArrayList<Animation>) al.clone(), elements, rx, ry, rz, bullets);
        plane.create();
        plane.setScale(1.5);
        a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("pauza").clone(), 50);
        pause = new Element((Animation) a.clone(), 300, 150, 0, elements);

        a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("punkty").clone(), 50);
        points = new Element((Animation) a.clone(), 100, 50, 0, elements);
        points.create();

        a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("zycia").clone(), 50);
        chances = new Element((Animation) a.clone(), 100, 70, 0, elements);
        chances.create();

        numberOfPoints = new Number(LogicManager.FontsLists.get("zwykla"), 100, 50, elements);

        numberOfChances = new Number(LogicManager.FontsLists.get("zwykla"), 100, 100, elements);

    }

    @Override
    public void manageOfCollisions() {
        for (int i = 0; i < observer.getCollidingPairs().size(); i++) {
            if ((observer.getCollidingPairs().get(i).e1 instanceof Dartboard) && (observer.getCollidingPairs().get(i).e2 instanceof Bullet)) {
                observer.getCollidingPairs().get(i).e2.delete();
                observer.getCollidingPairs().get(i).e1.delete();
                plane.setPoints(plane.getPoints() + 1);
            }
            if ((observer.getCollidingPairs().get(i).e1 instanceof Circle) && (observer.getCollidingPairs().get(i).e2 instanceof Player)) {
                plane.setPoints(plane.getPoints() + ((Circle) observer.getCollidingPairs().get(i).e1).getPoints());
                observer.getCollidingPairs().get(i).e1.delete();
                //System.err.println("kolizja");
            }
            if ((observer.getCollidingPairs().get(i).e1 instanceof Obstacle) && (observer.getCollidingPairs().get(i).e2 instanceof Player)) {
                plane.delete();
                observer.getCollidingPairs().get(i).e1.delete();
            }

        }
    }

    @Override
    public void manageOfElements() {
        if (pad.start && !isPaused) {
            pauseOn();
        } else if (pad.start && isPaused) {
            pauseOff();
        }

        if (!isPaused) {

            Random r=new Random();
            if(r.nextInt(10)==0){
                Animation a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("chmurka").clone(), 50);
                Random r1=new Random();
                try {
                    Element chmura=new Element((Animation) a.clone(), r1.nextInt(600), r1.nextInt(300), 580, elements, flyingClouds);
                    chmura.create();
                    chmura.setIsTranslucent(true);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            for(int i=0;i<flyingClouds.size();i++){
                flyingClouds.get(i).goZWithConstantVelocity(-7);
            }
            
            for(int i=0;i<cloudsInBackground.size();i++){
                cloudsInBackground.get(i).goX(targetsXOfCloudsInBackGround.get(i));
                cloudsInBackground.get(i).goY(targetsYOfCloudsInBackground.get(i));
            }
            gameplayManagement.manage();

            numberOfPoints.setNumber(plane.getPoints());
            numberOfPoints.show(150, 50, 10);


            numberOfChances.setNumber(plane.getChances());
            numberOfChances.show(150, 70, 10);

            cursor.setX((int) pad.xPosition * 2);
            cursor.setY((int) pad.yPosition);

            if (pad.blue) {
                plane.shoot();
            }

            if (cursor.getX() - plane.getX() > 50) {
                plane.setAnimation(plane.PLANE_RIGHT);
            } else if (cursor.getX() - plane.getX() < -50) {
                plane.setAnimation(plane.PLANE_LEFT);
            } else {
                plane.setAnimation(plane.PLANE);
            }

            if (pad.red) {
                if (!plane.exists()) {
                    plane.create();
                    plane.setChances(plane.getChances() - 1);
                }
            }

            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).goZWithConstantVelocity(5);
            }

            plane.goX(cursor.getX());
            plane.goY(cursor.getY());
            plane.goZ(cursor.getZ());

        }else{
            if(pad.green){
                try {
                    LogicManager.loadActivity(new Menu(elements, pad, observer));
                } catch (Exception ex) {
                    Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (plane.getChances() == 0 && !plane.exists()) {
            try {
                LogicManager.loadActivity(new Menu(elements, pad, observer));
            } catch (Exception ex) {
                Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void pauseOn() {
        while (pad.start) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        pause.create();
        back.create();

        LogicManager.background = 0x0;
        isPaused = true;
    }

    private void pauseOff() {
        while (pad.start) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        pause.delete();
        back.delete();
        LogicManager.background = 0xaaeeff;
        isPaused = false;
    }

}
