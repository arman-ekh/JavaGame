package object;

import main.GamePannel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int  worldY;
    public int worldX;
    public Rectangle solidArea = new Rectangle(0,0,48,48);



    public void draw(Graphics2D g2, GamePannel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY &&
                !collision
        ) {

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

//            g2.setColor(Color.RED); // draw hit box (collision box)
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

}
