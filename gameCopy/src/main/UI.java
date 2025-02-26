package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePannel gp;
    Font arial_20;
    BufferedImage keyImage;

    UI(GamePannel gp) {
        this.gp = gp;
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void drawUI(Graphics2D g2) {

        int healthWidth = 12;
        int healthHeight = 24;

        g2.setFont(arial_20);
        g2.setColor(Color.WHITE);


        if (gp.player != null && keyImage != null) {
            for (int i = 0; i < gp.player.keyNum; i++) {
                g2.drawImage(keyImage, 10 + (i * 24), 20, 24, 24, null);
            }
        }



        if (gp.player != null) {
            int playerHealth = gp.player.health;
            int maxHealth = gp.player.maxHealth;

            for (int i = 0; i < playerHealth / 5; i++) {
                int x = 20 + (i * (healthWidth));
                int y = 50;


                if (playerHealth > maxHealth / 2) {
                    g2.setColor(Color.GREEN);
                } else if (playerHealth > 10) {
                    g2.setColor(Color.ORANGE);
                } else {
                    g2.setColor(Color.RED);
                }

                g2.fillRect(x, y, healthWidth, healthHeight);

            }
        }
    }
}
