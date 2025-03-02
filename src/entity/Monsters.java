package entity;

import main.GamePannel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Monsters extends Entity {
    GamePannel gp;
    int targetX, targetY;
    boolean isMoving = false;
    int stepX, stepY;
    Random rand = new Random();




    int[][] moves = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
    };

    public Monsters(GamePannel gp) {
        this.gp = gp;
        health = 30;
        getImage();
        setDefaultValues();
        solidArea = new Rectangle(0, 0, 48, 48);
        direction = "up";
        this.damage = 10;
    }

    public void getImage() {
        try {
            up1 = ImageIO.read(getClass().getResource("/monster/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResource("/monster/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResource("/monster/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResource("/monster/boy_down_2.png"));
            right1 = ImageIO.read(getClass().getResource("/monster/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResource("/monster/boy_right_2.png"));
            left1 = ImageIO.read(getClass().getResource("/monster/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResource("/monster/boy_left_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        speed = 1;
    }

    public void update() {
        if (isMoving) {
            moveStep();
        } else {
            findNewTarget();
        }

        if (checkPlayerCollision(this, gp.player)) {
            long currentTime = System.currentTimeMillis();

            if (currentTime - gp.player.lastDamageTime > gp.player.damageCooldown) {
                gp.player.health = gp.player.health - damage ;
                gp.player.lastDamageTime = currentTime;
                System.out.println("Player-Monster collision detected! Health: " + gp.player.health);
            }
        }
    }

    private void findNewTarget() {
        int[] shuffledIndexes = shuffleIndexes();

        for (int i = 0; i < moves.length; i++) {
            int moveIndex = shuffledIndexes[i];
            int newX = worldX + moves[moveIndex][0] * gp.tileSize;
            int newY = worldY + moves[moveIndex][1] * gp.tileSize;

            if (!checkWallCollision(newX, newY) && !checkMonsterCollision(newX, newY)) {
                targetX = newX;
                targetY = newY;
                stepX = (targetX - worldX) / (gp.tileSize / speed);
                stepY = (targetY - worldY) / (gp.tileSize / speed);
                isMoving = true;
                return;
            }
        }
    }

    private void moveStep() {
        int nextX = worldX + stepX;
        int nextY = worldY + stepY;

        if (checkWallCollision(nextX, nextY)) {
            findNewTarget();
            return;
        }

        worldX = nextX;
        worldY = nextY;

        if (worldX == targetX && worldY == targetY) {
            isMoving = false;
            findNewTarget();
        }
    }



    private boolean checkWallCollision(int newX, int newY) {
        int leftX   = (newX + solidArea.x) / gp.tileSize;
        int rightX  = (newX + solidArea.x + solidArea.width) / gp.tileSize;
        int topY    = (newY + solidArea.y) / gp.tileSize;
        int bottomY = (newY + solidArea.y + solidArea.height) / gp.tileSize;

        if (leftX < 0 || rightX >= gp.tileM.mapTileNum[0].length || topY < 0 || bottomY >= gp.tileM.mapTileNum.length) {
            return true;
        }

        return gp.tileM.tile[gp.tileM.mapTileNum[topY][leftX]].collision ||
                gp.tileM.tile[gp.tileM.mapTileNum[topY][rightX]].collision ||
                gp.tileM.tile[gp.tileM.mapTileNum[bottomY][leftX]].collision ||
                gp.tileM.tile[gp.tileM.mapTileNum[bottomY][rightX]].collision;
    }


    private boolean checkMonsterCollision(int newX, int newY) {
        for (Monsters monster : gp.monsters) {
            if (monster != null && monster != this && monster.worldX == newX && monster.worldY == newY) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPlayerCollision(Entity monster, Entity player) {
        Rectangle playerSolidArea = new Rectangle(player.solidArea);
        Rectangle monsterSolidArea = new Rectangle(monster.solidArea);

        monsterSolidArea.x = monster.worldX + monster.solidArea.x;
        monsterSolidArea.y = monster.worldY + monster.solidArea.y;

        playerSolidArea.x = player.worldX + player.solidArea.x;
        playerSolidArea.y = player.worldY + player.solidArea.y;



        return monsterSolidArea.intersects(playerSolidArea);
    }

    private int[] shuffleIndexes() {
        int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7};
        for (int i = 0; i < indexes.length; i++) {
            int randomIndex = rand.nextInt(indexes.length);
            int temp = indexes[i];
            indexes[i] = indexes[randomIndex];
            indexes[randomIndex] = temp;
        }
        return indexes;
    }

    public void draw(Graphics2D g2) {
        int playerX = gp.player.worldX;
        int playerY = gp.player.worldY;
        int screenX = worldX - playerX + gp.player.screenX;
        int screenY = worldY - playerY + gp.player.screenY;

        int distanceX = Math.abs(worldX - playerX);
        int distanceY = Math.abs(worldY - playerY);

        if (distanceX < gp.screenWidth / 2 && distanceY < gp.screenHeight / 2) {
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
        }
    }
}
