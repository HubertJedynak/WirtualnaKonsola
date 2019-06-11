package core;

import elements.Element;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class GraphicManager {

    private ArrayList<Element> toDraw;
    private LogicManager logicManager;
    private Main window;
    private Graphics pencil;
    private BufferStrategy bufferStrategy;

    private final int graphicPrecision;

    private BufferedImage iCanvas;
    private int[] pixelsOfICanvas;

    private BufferedImage graphicFormFile;
    private int[] pixelsOfGraphicFromFile;

    private double rescallingOfBitmap = 1;

    private Perspective perspective;

    public GraphicManager(Main window, LogicManager logicManager) {
        this.window = window;
        this.logicManager = logicManager;
        this.graphicPrecision = 40;
        this.perspective = new Perspective();
        this.toDraw = logicManager.toDraw;

        window.canvas.createBufferStrategy(2);
        bufferStrategy = window.canvas.getBufferStrategy();

        iCanvas = new BufferedImage(16 * graphicPrecision, 9 * graphicPrecision, BufferedImage.TYPE_INT_RGB);

        pixelsOfICanvas = ((DataBufferInt) iCanvas.getRaster().getDataBuffer()).getData();
        pencil = bufferStrategy.getDrawGraphics();
    }

    public void render() {

        for (int i = 0; i < 16 * graphicPrecision * 9 * graphicPrecision; i++) {

            pixelsOfICanvas[i] = -0x1000000 + logicManager.background;

        }
        for (int j = 0; j < toDraw.size(); j++) {
            if (toDraw.get(j).getZ() > -logicManager.heightOfEnvironment * 0.5) {
                graphicFormFile = toDraw.get(j).getImage();
                pixelsOfGraphicFromFile = new int[graphicFormFile.getWidth() * graphicFormFile.getHeight()];
                graphicFormFile.getRGB(0, 0, graphicFormFile.getWidth(), graphicFormFile.getHeight(), pixelsOfGraphicFromFile, 0, graphicFormFile.getWidth());

                perspective.getParametersOfElement(toDraw.get(j).getX(),
                        toDraw.get(j).getY(),
                        toDraw.get(j).getZ(),
                        toDraw.get(j).getScale(),
                        toDraw.get(j).getImage().getWidth(),
                        toDraw.get(j).getImage().getHeight());
                rescallingOfBitmap = perspective.scale();
                int widthOfCanvas = 16 * graphicPrecision;
                int heightOfCanvas = 9 * graphicPrecision;
                int heightOfBitmap = graphicFormFile.getHeight();
                int widthOfBitmap = graphicFormFile.getWidth();
                int firstPixelOfBitmap = pixelsOfGraphicFromFile[0];

                int xOfBitmap = perspective.xOfBitmap();
                int yOfBitmap = perspective.yOfBitmap();

                if (toDraw.get(j).isTranslucent() == false) {
                    for (int y = 0; y < heightOfBitmap * rescallingOfBitmap; y++) {
                        for (int x = 0; x < widthOfBitmap * rescallingOfBitmap; x++) {
                            if ((y + yOfBitmap > 0) && (x + xOfBitmap > 0) && (y + yOfBitmap < heightOfCanvas) && (x + xOfBitmap < widthOfCanvas)) {
                                try {
                                    if ((pixelsOfGraphicFromFile[widthOfBitmap * (int) (y / rescallingOfBitmap) + (int) (x / rescallingOfBitmap)] != firstPixelOfBitmap)) {
                                        pixelsOfICanvas[widthOfCanvas * (y + yOfBitmap) + x + xOfBitmap] = pixelsOfGraphicFromFile[widthOfBitmap * (int) (y / rescallingOfBitmap) + (int) (x / rescallingOfBitmap)];
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                } else if (toDraw.get(j).isTranslucent() == true) {

                    for (int y = 0; y < heightOfBitmap * rescallingOfBitmap; y++) {
                        for (int x = 0; x < widthOfBitmap * rescallingOfBitmap; x++) {
                            if ((y + yOfBitmap > 0) && (x + xOfBitmap > 0) && (y + yOfBitmap < heightOfCanvas) && (x + xOfBitmap < widthOfCanvas)) {
                                try {
                                    if ((pixelsOfGraphicFromFile[widthOfBitmap * (int) (y / rescallingOfBitmap) + (int) (x / rescallingOfBitmap)] != firstPixelOfBitmap)) {
                                        int colorOfBackground = pixelsOfICanvas[widthOfCanvas * (y + yOfBitmap) + x + xOfBitmap];
                                        int colorOfElement = pixelsOfGraphicFromFile[widthOfBitmap * (int) (y / rescallingOfBitmap) + (int) (x / rescallingOfBitmap)];
                                        pixelsOfICanvas[widthOfCanvas * (y + yOfBitmap) + x + xOfBitmap] = getTranslucent(colorOfBackground, colorOfElement);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }

                }
            }
        }
        pencil.drawImage(iCanvas, 0, 0, window.canvas.getWidth(), window.canvas.getHeight(), null);
        bufferStrategy.show();
    }

    private int getTranslucent(int colorOfBackground, int colorOfElement) {
        colorOfBackground += 0x1000000;
        colorOfElement += 0x1000000;
        int bBackground = (int) ((colorOfBackground % (256)) * 0.5);
        int gBackground = (int) ((colorOfBackground % (65536) - bBackground) * 0.5) - (int) ((colorOfBackground % (65536) - bBackground) * 0.5) % (256);
        int rBackground = (int) ((colorOfBackground - gBackground - bBackground) * 0.5) - (int) ((colorOfBackground - gBackground - bBackground) * 0.5) % (65536);
        int bElement = (int) ((colorOfElement % (256)) * 0.5);
        int gElement = (int) ((colorOfElement % (65536) - bElement) * 0.5) - (int) ((colorOfElement % (65536) - bElement) * 0.5) % 256;
        int rElement = (int) ((colorOfElement - gElement - bElement) * 0.5) - (int) ((colorOfElement - gElement - bElement) * 0.5) % 65536;
        return (rBackground + gBackground + bBackground + rElement + gElement + bElement) - 0x1000000;

    }

    private class Perspective {

        private int widthOfEnvironment, heightOfEnvironment, depthOfEnvironment;
        private double rescallingX_Logic_GraphicFromFile,
                rescallingY_Logic_GraphicFromFile;

        int x, y, z;
        int widthOfGraphic, heightOfGraphic;
        double scale;

        private Perspective() {
            this.widthOfEnvironment = logicManager.widthOfEnvironment;
            this.heightOfEnvironment = logicManager.heightOfEnvironment;
            this.depthOfEnvironment = logicManager.depthOfEnvironment;
            this.rescallingX_Logic_GraphicFromFile = 16.0 * graphicPrecision / widthOfEnvironment;
            this.rescallingY_Logic_GraphicFromFile = 9.0 * graphicPrecision / heightOfEnvironment;

        }

        private void getParametersOfElement(int x, int y, int z, double scale, int widthOfGraphic, int heightOfGraphic) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.widthOfGraphic = widthOfGraphic;
            this.heightOfGraphic = heightOfGraphic;
            this.scale = scale;
        }

        private double scale() {

            scale = widthOfEnvironment * 0.5 / (z + widthOfEnvironment * 0.5) * scale;
            return scale;
        }

        private int xOfBitmap() {
            double x = (0.5 * widthOfEnvironment * ((this.x - 0.5 * widthOfEnvironment) / (z + 0.5 * widthOfEnvironment) + 1) * rescallingX_Logic_GraphicFromFile);
            return (int) (x - widthOfGraphic * scale * 0.5);
        }

        private int yOfBitmap() {
            double y = (0.5 * heightOfEnvironment * ((this.y - 0.5 * heightOfEnvironment) / (z + 0.5 * heightOfEnvironment) + 1) * rescallingY_Logic_GraphicFromFile);
            return (int) (y - heightOfGraphic * scale * 0.5);

        }
    }
}
