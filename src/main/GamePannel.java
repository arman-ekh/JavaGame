package main;

import entity.Monsters;
import entity.Player;
import entity.Skeleton;
import object.SuperObject;
import tile.TileManager;
import java.util.List;
import java.util.ArrayList;


import javax.swing.*;
import java.awt.*;
import java.io.*;


public class GamePannel extends JPanel implements Runnable {
    final int orginalTileSize = 16; // 16 * 16 tiles
    final int scale = 3;

    public final int  tileSize = orginalTileSize * scale ; // what we see is this wich is 48 * 48
    public final int maxScreenCol = 24 ;
    public final int maxScreenRow = 18 ;
    public final int screenWidth = maxScreenCol * tileSize ;
    public final int screenHeight = maxScreenRow * tileSize ;

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
    public final int maxWorldRow = 32;


    public Monsters[] monsters = new Monsters[20];






    public GamePannel(){

        this.setPreferredSize(new Dimension(screenWidth , screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame() {
        aSetter.setObject();
        playMusic(0);

        String filePath = "/maps/map1.txt";
        List<String> newMapData = new ArrayList<>();

        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);

            if (inputStream == null) {
                throw new FileNotFoundException("File not found: " + filePath);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            int row = 0;

            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (!line.isEmpty()) {
                    String[] tiles = line.split("\\s+");

                    for (int col = 0; col < tiles.length; col++) {

                        String tile = tiles[col].trim();

                        if (!tile.isEmpty()) {
                            try {
                                int tileType = Integer.parseInt(tile);

                                if (tileType == 6) { // Example for monster
                                    spawnMonster(new Monsters(this), col, row);
                                    tiles[col] = "0"; // replace monster spawn with an empty tile
                                } else if (tileType == 5) { // Example for Skeleton
                                    spawnMonster(new Skeleton(this), col, row);
                                    tiles[col] = "0"; // replace skeleton spawn with an empty tile
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid number format: " + tile);
                            }
                        }
                    }

                    newMapData.add(String.join(" ", tiles));
                    row++;
                }
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void spawnMonster(Monsters monster, int col, int row) {
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] == null) {
                monsters[i] = monster;
                monsters[i].worldX = col * tileSize;
                monsters[i].worldY = row * tileSize;
                break;
            }
        }
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



        for (Monsters monster : monsters) {
            if (monster != null) {
                monster.draw(g2);
            }
        }
        player.draw(g2);
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

