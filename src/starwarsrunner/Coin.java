package starwarsrunner;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Animation;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by slawek on 2015-10-21.
 */
public class Coin {
    float X, Y, cy;
    int Size = 32;
    Image [] Img;
    int [] duration = {150, 150, 150, 150, 150, 150, 150, 150};
    boolean Taken = false;
    private float step = 0.1f;
    public Coin () {
        this.X = 224 + getRandomX();
        this.cy = -getRandomY();
        try {
            this.Img = new Image[]{
                    new Image("coin/coin_01.gif"),
                    new Image("coin/coin_02.gif"),
                    new Image("coin/coin_03.gif"),
                    new Image("coin/coin_04.gif"),
                    new Image("coin/coin_05.gif"),
                    new Image("coin/coin_06.gif"),
                    new Image("coin/coin_07.gif"),
                    new Image("coin/coin_08.gif")
            };
        }
        catch (SlickException ex) {
            Logger.getLogger(StarWarsRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void stepDown() {
        this.Y = this.cy + this.step;
        this.step += 0.1f;
    }
    public void collect() {
        this.X = 0f;
        this.Y = 0f;
        this.Taken = true;
    }
    public Rectangle getBoundingBox() {
        return new Rectangle(X, Y, Size, Size);
    }
    public Animation getAnimation() {
        return new Animation(this.Img, duration, true);
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
