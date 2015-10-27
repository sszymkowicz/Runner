package starwarsrunner;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by slawek on 2015-10-21.
 */
public class Obstacle {
    float X, Y, cy;
    int Size = 60;
    Image Img;
    private float step = 0.1f;
    public Obstacle() {
        this.X = 224 + getRandomX();
        this.cy = -getRandomY();
        System.out.println(this.cy);
        try {
            this.Img = new Image("box/box.png");
        }
        catch (SlickException ex) {
            Logger.getLogger(StarWarsRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void stepDown() {
        this.Y = this.cy + this.step;
        this.step += 0.1f;
    }
    public Rectangle getBoundingBox() {
        return new Rectangle(X+Size/2, Y+Size/2, Size/2, Size/2);
    }
    public Image getImage() {
        return this.Img;
    }
    private static int getRandomX() {
        int [] array = {0, 66, 130};
        int rand = new Random().nextInt(array.length);
        return array[rand];
    }
    private static int getRandomY() {
        return new Random().nextInt(10) * 64;
    }
}
