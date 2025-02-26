package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX , worldY;
    public int speed;
    public int keyNum ;
    public int health ;
    public float blurRadius ;

    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public boolean collisionOn;
    public boolean isCollisionObjectOn;
}
