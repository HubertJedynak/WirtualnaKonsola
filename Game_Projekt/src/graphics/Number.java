package graphics;

import core.LogicManager;
import elements.Element;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Number {

    private ArrayList<Element> allElements;
    private int number = 0;
    private HashMap<String, BufferedImage> figures;
    private String stringOfNumber = "0";
    private ArrayList<Element> figuresList;

    private int x, y;

    public Number(HashMap<String, BufferedImage> figures, int x, int y, ArrayList<Element> allElements) {
        this.allElements = allElements;
        this.figures = figures;
        figuresList = new ArrayList<>();
    }

    public void setNumber(int number) {
        if (number >= 0) {
            this.number = number;
            stringOfNumber = String.valueOf(number);
        } else {
            this.number = 0;
            stringOfNumber = "0";
        }
    }

    public void show(int x, int y, int distanceBetweenFigures) {
        for (int i = 0; i < figuresList.size(); i++) {
            figuresList.get(i).delete();
        }
        figuresList.clear();
        for (int i = 0; i < stringOfNumber.length(); i++) {
            Element e = new Element(LogicManager.FontsLists.get("zwykla").get(stringOfNumber.substring(i, i + 1)), x + i * distanceBetweenFigures, y, 0, allElements);
            e.create();
            figuresList.add(e);
        }
    }

    public void delete() {
        for (int i = 0; i < figuresList.size(); i++) {
            figuresList.get(i).delete();
        }
        figuresList.clear();
    }

}
