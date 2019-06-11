package core;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import servicesOfPad.UDPPad;

public class LoopOfGame implements Runnable {

    private boolean isLoopRunning = false;
    private Thread loop;
    private Main window;

    private UDPPad pad;
    private LogicManager logicManager;
    private GraphicManager graphicManager;

    public LoopOfGame(Main window) {
        this.window = window;
    }

    public void startLoop() {
        if (isLoopRunning) {
            return;
        }

        isLoopRunning = true;
        loop = new Thread(this);
        loop.start();
    }

    public void stopLoop() {
        isLoopRunning = false;
        System.exit(0);
    }

    @Override
    public void run() {
        try {
            logicManager = new LogicManager();
            graphicManager = new GraphicManager(window, logicManager);

            int FRAMERATE = 60;
            double timer;
            int FPS = 0;
            timer = System.currentTimeMillis();
            long timeNow = System.nanoTime();
            long timeLast = System.nanoTime();
            double frametime = 1000000000 / FRAMERATE;
            double delta = 0;
            while (isLoopRunning) {
                timeNow = System.nanoTime();
                delta += (timeNow - timeLast) / frametime;
                timeLast = timeNow;

                if (delta > 1) {
                    logicManager.updateData();
                    graphicManager.render();
                    FPS++;
                    delta -= 1;
                }

                if (System.currentTimeMillis() - timer > 1000) {
                    timer = System.currentTimeMillis();
                    window.jFrame.setTitle("FPS: " + FPS);
                    FPS = 0;
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(LoopOfGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}