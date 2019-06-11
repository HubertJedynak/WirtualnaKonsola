package core;

import java.awt.Canvas;
import javax.swing.JFrame;

public class Main {

    public Canvas canvas;
    public JFrame jFrame;
    public LoopOfGame loopOfGame;

    public Main() {
        jFrame = new JFrame();
        jFrame.setVisible(true);

        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jFrame.setLayout(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("game");
        jFrame.setResizable(false);
        jFrame.repaint();

        canvas = new Canvas();
        canvas.setBounds(0, 0, jFrame.getWidth(), jFrame.getHeight());
        jFrame.add(canvas);
    }

    public static void main(String[] args) {
        Main window = new Main();
        LoopOfGame loopOfGame = new LoopOfGame(window);
        loopOfGame.startLoop();
    }

}
