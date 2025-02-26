package entity;

import main.GamePannel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;



public class Player extends Entity{

    GamePannel gp;
    KeyHandler keyH;

    public final int screenX ;
    public final int screenY;
    public final int maxHealth = 50;


    public Player(GamePannel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize/2);
        screenY = gp.screenHeight / 2 - (gp.tileSize/2);

        setDefaultValues();
        getPlayerImage();
        solidArea = new Rectangle(12,20,24,26);
    }
    public  void setDefaultValues(){
        health = 50 ;
        worldX = 15 * gp.tileSize; // spawn x
        worldY = 17  * gp.tileSize; // spawn y
        speed = 4;
        direction = "down";
        keyNum = 0;
        blurRadius = 400;
    }
    public void getPlayerImage(){

        try{

            up1 = ImageIO.read(getClass().getResource("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResource("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResource("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResource("/player/boy_down_2.png"));
            right1 = ImageIO.read(getClass().getResource("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResource("/player/boy_right_2.png"));
            left1 = ImageIO.read(getClass().getResource("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResource("/player/boy_left_2.png"));


        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void update() {
        boolean keyPressed = false;

        if (keyH.upPressed) {
            direction = "up";
            keyPressed = true;
        } else if (keyH.downPressed) {
            direction = "down";
            keyPressed = true;
        } else if (keyH.leftPressed) {
            direction = "left";
            keyPressed = true;
        } else if (keyH.rightPressed) {
            direction = "right";
            keyPressed = true;
        }else if(keyH.f1Pressed){
            gp.stopMusic();
        }

        if (keyPressed) {
            collisionOn = false;
            isCollisionObjectOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObj(this);


            if (!collisionOn && !isCollisionObjectOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter >= 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }




    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                image = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2;
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        applyRadialBlur(g2);
    }





    public void applyRadialBlur(Graphics2D g2) {
        int width = gp.screenWidth;
        int height = gp.screenHeight;

        float centerX = width / 2.0f;
        float centerY = height / 2.0f;


        float[] fractions = { 0.0f, 0.7f, 1.0f };
        Color[] colors = { new Color(0, 0, 0, 0), new Color(0, 0, 0, 80), new Color(0, 0, 0, 180) };

        RadialGradientPaint radialPaint = new RadialGradientPaint(centerX, centerY, blurRadius, fractions, colors);

        g2.setPaint(radialPaint);
        g2.fillRect(0, 0, width, height);
    }




}
