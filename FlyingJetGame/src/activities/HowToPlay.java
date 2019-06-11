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

public class HowToPlay implements Activity {

    private UDPPad pad;
    private ArrayList<Element> elements;
    private LogicManager.Observer observer;
    private Element back;
    
    private Element howToPlay;
    private ArrayList<Element> cloudsInBackground;
    private ArrayList<Integer> targetsXOfCloudsInBackground;
    private ArrayList<Integer> targetsYOfCloudsInBackground;

    
    public HowToPlay(ArrayList<Element> elements, UDPPad pad, LogicManager.Observer observer) throws Exception {
        elements.clear();
        this.pad=pad;
        this.observer=observer;
        this.elements = elements;
        Animation a = new Animation(LogicManager.animationsLists.get("tutorial"), 10);
        howToPlay = new Element(a, 300, 150, 0, elements);
        howToPlay.create();
        a=new Animation(LogicManager.animationsLists.get("wroc"), 50);
        
        back=new Element((Animation) a.clone(), 50, 100, 0, elements);
        back.create();
        
        cloudsInBackground=new ArrayList<>();
        targetsXOfCloudsInBackground=new ArrayList<>();
        targetsYOfCloudsInBackground=new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            a = new Animation((ArrayList<BufferedImage>) LogicManager.animationsLists.get("chmurka").clone(), 50);
            Random r = new Random();
            Regulator rx = new Regulator(0.000005, 0.000005, -1.99998, 1, r.nextInt(600));
            Regulator ry = new Regulator(0.0000005, 0.0000005, -1.999998, 1, r.nextInt(300));
            Regulator rz = new Regulator(0.0000005, 0.0000005, -1.999998, 1, 30);
            Element cloud = new Element((Animation) a.clone(), elements, cloudsInBackground, rx, ry, rz);
            cloud.setIsTranslucent(true);
            cloud.create();
            cloud.setScale(3);
            targetsXOfCloudsInBackground.add(r.nextInt(600));
            targetsYOfCloudsInBackground.add(r.nextInt(300));
        }

    }

    @Override
    public void manageOfCollisions() {
    }

    @Override
    public void manageOfElements() {
    
        for (int i = 0; i < cloudsInBackground.size(); i++) {
            cloudsInBackground.get(i).goX(targetsXOfCloudsInBackground.get(i));
            cloudsInBackground.get(i).goY(targetsYOfCloudsInBackground.get(i));
        }

        if(pad.green){
            try {
                LogicManager.loadActivity(new Menu(elements, pad, observer));
            } catch (Exception ex) {
                Logger.getLogger(HowToPlay.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
