package object;

import main.GamePannel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Door extends SuperObject{
     public boolean open = false;
    public BufferedImage image2;
    public OBJ_Door(){
        name = "door";
        boolean open = false;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/door.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/object/doorOpen.png"));
        }catch (IOException e){
            e.printStackTrace();

        }
    }
    public void draw(Graphics2D g2, GamePannel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY &&
                !collision && !open
        ) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

//            g2.setColor(Color.RED); // draw hit box (collision box)
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }else if(
                worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY &&
                        !collision && open
        ){
            g2.drawImage(image2, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
