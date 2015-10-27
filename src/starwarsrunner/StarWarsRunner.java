package starwarsrunner;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.geom.Rectangle;

public class StarWarsRunner extends BasicGame
{
    private TiledMap testMap, testMap2;
    private Animation sprite, up, left, right, coinSprite;
    private List<Obstacle> boxList = new ArrayList<>();
    private List<Coin> coinList = new ArrayList<>();
    private int x = 288, y = 500;
    private float step = 0f;
    private int line = 1;
    private int tileSize;
    private int mapHeight;
    private int points = 0;

    public StarWarsRunner(String gamename) { super(gamename); }

    @Override
    public void init(GameContainer gc) throws SlickException {
        testMap = new TiledMap("/stoneMap.tmx");
        testMap2 = testMap;
        tileSize = testMap.getTileHeight();
        mapHeight = tileSize * testMap.getHeight();

        Image [] movementUp = {
                new Image("yoda/yoda_fr1.gif"),
                new Image("yoda/yoda_fr2.gif")
        };
        Image [] movementLeft = {
                new Image("yoda/yoda_lt1.gif"),
                new Image("yoda/yoda_lt2.gif")
        };
        Image [] movementRight = {
                new Image("yoda/yoda_rt1.gif"),
                new Image("yoda/yoda_rt2.gif")
        };

        int [] duration = {150, 150};

        // Adding Coins and Boxes (Obstacles)
        coinList.add(new Coin());
        for (int i=0; i<10; i++) {
            boxList.add(new Obstacle());
        }

        // Animation declarations
        up = new Animation(movementUp, duration, false);
        left = new Animation(movementLeft, duration, false);
        right = new Animation(movementRight, duration, false);
        coinSprite = coinList.get(0).getAnimation();

        // Initial setting of player (looking forward)
        sprite = up;

        Input i = gc.getInput();
        KeyListener k = new KeyListener() {
            @Override
            public void keyPressed(int i, char c) {
                if(i == Input.KEY_LEFT && line > 0) {
                    sprite = left;
                    sprite.update(1);
                    x -= 64f;
                    line--;
                }
                else if(i == Input.KEY_RIGHT && line < 2) {
                    sprite = right;
                    sprite.update(1);
                    x += 64f;
                    line++;
                }
            }

            @Override
            public void keyReleased(int i, char c) {
                sprite = up;
                sprite.update(1);
            }

            @Override
            public boolean isAcceptingInput() {
                return true;
            }

            @Override
            public void setInput(Input input) {}

            @Override
            public void inputEnded() {}

            @Override
            public void inputStarted() {}
        };
        i.addKeyListener(k);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {}

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        g.drawString(Integer.toString(points), 10, 100);

        int mapPos1 = (int)step,
                mapPos2 = -mapHeight + (int)step;

        Rectangle p = new Rectangle(x, y, tileSize, tileSize);
        for(Obstacle o : boxList) {
            o.stepDown();
            if (p.intersects(o.getBoundingBox())) {
                System.out.println("KOLIZJA");
            }
        }
        for(Coin c : coinList) {
            if(!c.Taken) {
                c.stepDown();
            }
            if (p.intersects(c.getBoundingBox()) && !c.Taken) {
                c.collect();
                points += 100;
            }
        }

        // Player fancy move (moving feet)
        sprite.update(1);

        testMap.render(160, mapPos1);
        testMap2.render(160, mapPos2);
        sprite.draw(x, y);
        for(Coin c : coinList) {
            coinSprite.draw(c.X, c.Y);
        }
        for(Obstacle b : boxList) {
            b.getImage().draw(b.X, b.Y);
        }

        // Timer reset when texture out of screen
        if(mapPos1 == 640) {
            step = 0;
        }
        // Timers (used for moving map and objects)
        step += 0.1f;
    }

    public static void main(String[] args)
    {
        Menu myMenu = new Menu();

        while(!myMenu.start) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {

            }
        }

        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new StarWarsRunner("Star Wars Runners"));
            appgc.setDisplayMode(640, 640, false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(StarWarsRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}