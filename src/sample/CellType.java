package sample;

import javafx.scene.paint.Color;
import java.util.LinkedList;
import java.util.Random;

public class CellType {

    int value;
    Color color;

    CellType(int val){
        value = val;
    }

    public void setColor(LinkedList<Color> colors) {

        Random colorGenerator = new Random(255);
        boolean isOnList = true;
        Color c = null;

        int R, G, B;

        while(isOnList)
        {
            R = colorGenerator.nextInt(256);
            G = colorGenerator.nextInt(256);
            B = colorGenerator.nextInt(256);

            c = Color.rgb(R,G,B);

            if(!colors.contains(c))
            {
                isOnList = false;
            }
        }

        colors.add(c);
        this.color = c;
    }

    public void setColor(Color color){
        this.color = color;
    }
}
