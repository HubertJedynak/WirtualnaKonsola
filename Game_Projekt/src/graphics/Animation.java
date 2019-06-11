package graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation implements Cloneable {

    private ArrayList<BufferedImage> images;
    private int iteratorOfImages = 0;
    private int timeToChangeFrame;
    private int indexOfCurrentImage;

    public Animation(ArrayList<BufferedImage> images, int timeToChangeFrame) {
        this.images = images;
        this.timeToChangeFrame = timeToChangeFrame;
        if (images.isEmpty() == false) {
            indexOfCurrentImage = 0;
        }
    }

    public BufferedImage getCurrentImage() {
        if (iteratorOfImages > timeToChangeFrame) {
            indexOfCurrentImage++;
            if (indexOfCurrentImage > images.size() - 1) {
                indexOfCurrentImage = 0;
            }
            iteratorOfImages = 0;
        }
        return images.get(indexOfCurrentImage);
    }

    public void setTimeToChangeFrame(int timeToChangeFrame) {
        this.timeToChangeFrame = timeToChangeFrame;
    }

    public void setImageList(ArrayList<BufferedImage> image) {
        this.images = image;
    }

    public void checkStepOfFrame() {

        iteratorOfImages++;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
