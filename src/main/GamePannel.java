package main;

import entity.Entity;
import entity.Monsters;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePannel extends JPanel implements Runnable {
    final int orginalTileSize = 16; // 16 * 16 tiles
    final int scale = 3;

    public final int  tileSize = orginalTileSize * scale ; // what we see is this wich is 48 * 48
    public final int maxScreenCol = 16 ;
    public final int maxScreenRow = 12 ;
    public final int screenWidth = maxScreenCol * tileSize ; // 768 pixels
    public final int screenHeight = maxScreenRow * tileSize ; // 576 pixels

    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound sound = new Sound();
    Sound music = new Sound();
    UI ui = new UI(this);
    Thread gameThead ;
    public Player player = new Player(this,keyH);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public SuperObject[] obj = new SuperObject[10];
    public AssetSetter aSetter = new AssetSetter(this);

    public final int maxWorldCol = 32;
    public final int maxWorldRow = 64;


    public Monsters[] monsters = new Monsters[5];






    public GamePannel(){

        this.setPreferredSize(new Dimension(screenWidth , screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame(){
        aSetter.setObject();
        playMusic(0);
        for (int i = 0; i < monsters.length; i++) {
            monsters[i] = new Monsters(this);
            monsters[i].worldX = (i + 10) * tileSize;
            monsters[i].worldY = 10 * tileSize;
        }
        monsters[0].worldX = 20 * tileSize;
        monsters[0].worldY = 20 * tileSize;

        monsters[1].worldX = 15 * tileSize;
        monsters[1].worldY = 25 * tileSize;

        monsters[2].worldX = 25 * tileSize;
        monsters[2].worldY = 25 * tileSize;
//        monsters[0] = new Monsters(this);
//        monsters[0].worldX = ( 5) * tileSize;
//        monsters[0].worldY = 10 * tileSize;

    }

    public void stratGameThread(){
        gameThead = new Thread(this);
        gameThead.start();

    }

    public void run(){
        double drawInterval = 1000000000 /FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThead != null){
            // game loop here


            update();
            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if(remainingTime < 0 ){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update() {
        player.update();

        for (Monsters monster : monsters) {
            if (monster != null) {
                monster.update();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        for (SuperObject object : obj) {
            if (object != null) {
                object.draw(g2, this);
            }
        }

        player.draw(g2);

        for (Monsters monster : monsters) {
            if (monster != null) {
                monster.draw(g2);
            }
        }

        ui.drawUI(g2);
        g2.dispose();
    }

    public void playMusic(int i ){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSoundEffect(int i){
        sound.setFile(i);
        sound.play();
    }

}

