package entity;

import main.GamePannel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Monsters extends Entity {
    GamePannel gp;

    public Monsters(GamePannel gp) {
        this.gp = gp;
        health = 30;
        getImage();
        setDefaultValues();
        solidArea = new Rectangle(0, 0, 48, 48);
        direction = "down";
    }

    public void getImage() {
        try {
            up1 = ImageIO.read(getClass().getResource("/monster/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResource("/monster/boy_up_1.png"));
            down1 = ImageIO.read(getClass().getResource("/monster/boy_up_1.png"));
            down2 = ImageIO.read(getClass().getResource("/monster/boy_up_1.png"));
            right1 = ImageIO.read(getClass().getResource("/monster/boy_up_1.png"));
            right2 = ImageIO.read(getClass().getResource("/monster/boy_up_1.png"));
            left1 = ImageIO.read(getClass().getResource("/monster/boy_up_1.png"));
            left2 = ImageIO.read(getClass().getResource("/monster/boy_up_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        speed = 2;
        keyNum = 0;
    }

    public void update() {
        int playerX = gp.player.worldX;
        int playerY = gp.player.worldY;

        int distanceX = Math.abs(worldX - playerX);
        int distanceY = Math.abs(worldY - playerY);

        boolean canMove = false;


        checkMonsterCollision(this);

        if (checkPlayerCollision(this, gp.player, direction)) {
            return;
        }

        if (distanceX < gp.screenWidth / 2 && distanceY < gp.screenHeight / 2) {
            if (worldX < playerX) {
                direction = "right";
                if (!checkWallCollision("right")) {
                    worldX += speed;
                    canMove = true;
                }
            } else if (worldX > playerX) {
                direction = "left";
                if (!checkWallCollision("left")) {
                    worldX -= speed;
                    canMove = true;
                }
            }
        }

        if (distanceY < gp.screenHeight / 2 && distanceX < gp.screenWidth / 2) {
            if (worldY < playerY) {
                direction = "down";
                if (!checkWallCollision("down")) {
                    worldY += speed;
                    canMove = true;
                }
            } else if (worldY > playerY) {
                direction = "up";
                if (!checkWallCollision("up")) {
                    worldY -= speed;
                    canMove = true;
                }
            }
        }

        if (!canMove) {
            //
        }
    }
    public boolean checkPlayerCollision(Entity monster, Entity player, String direction) {

        Rectangle playerSolidArea = new Rectangle(player.solidArea);
        Rectangle monsterSolidArea = new Rectangle(monster.solidArea);

        monsterSolidArea.x = monster.worldX + monster.solidArea.x;
        monsterSolidArea.y = monster.worldY + monster.solidArea.y;

        playerSolidArea.x = player.worldX + player.solidArea.x;
        playerSolidArea.y = player.worldY + player.solidArea.y;

        if (monsterSolidArea.intersects(playerSolidArea)) {
            System.out.println("Player-Monster collision detected!");
            return true;
        }

        return false;
    }


    public boolean checkWallCollision(String direction) {
        int tileNum1, tileNum2;
        boolean collision = false;

        switch (direction) {
            case "up":
                tileNum1 = gp.tileM.mapTileNum[(worldY - speed) / gp.tileSize][worldX / gp.tileSize];
                tileNum2 = gp.tileM.mapTileNum[(worldY - speed) / gp.tileSize][(worldX + solidArea.width) / gp.tileSize];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    collision = true;
                }
                break;

            case "down":
                tileNum1 = gp.tileM.mapTileNum[(worldY + speed + solidArea.height) / gp.tileSize][worldX / gp.tileSize];
                tileNum2 = gp.tileM.mapTileNum[(worldY + speed + solidArea.height) / gp.tileSize][(worldX + solidArea.width) / gp.tileSize];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    collision = true;
                }
                break;

            case "left":
                tileNum1 = gp.tileM.mapTileNum[worldY / gp.tileSize][(worldX - speed) / gp.tileSize];
                tileNum2 = gp.tileM.mapTileNum[(worldY + solidArea.height) / gp.tileSize][(worldX - speed) / gp.tileSize];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    collision = true;
                }
                break;

            case "right":
                tileNum1 = gp.tileM.mapTileNum[worldY / gp.tileSize][(worldX + speed + solidArea.width) / gp.tileSize];
                tileNum2 = gp.tileM.mapTileNum[(worldY + solidArea.height) / gp.tileSize][(worldX + speed + solidArea.width) / gp.tileSize];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    collision = true;
                }
                break;
        }

        return collision;
    }


    public void draw(Graphics2D g2) {
        int playerX = gp.player.worldX;
        int playerY = gp.player.worldY;
        int screenX = worldX - playerX + gp.player.screenX;
        int screenY = worldY - playerY + gp.player.screenY;

        int distanceX = Math.abs(worldX - playerX);
        int distanceY = Math.abs(worldY - playerY);


        if (distanceX < gp.screenWidth / 2 && distanceY < gp.screenHeight / 2) {
            g2.drawImage(right1, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
    public void checkMonsterCollision(Entity monster) {
        for (int i = 0; i < gp.monsters.length; i++) {
            if (gp.monsters[i] != null && gp.monsters[i] != monster) {
                Rectangle monsterSolidArea = new Rectangle(gp.monsters[i].solidArea);
                Rectangle thisMonsterSolidArea = new Rectangle(monster.solidArea);


                thisMonsterSolidArea.x = monster.worldX + monster.solidArea.x;
                thisMonsterSolidArea.y = monster.worldY + monster.solidArea.y;

                monsterSolidArea.x = gp.monsters[i].worldX + gp.monsters[i].solidArea.x;
                monsterSolidArea.y = gp.monsters[i].worldY + gp.monsters[i].solidArea.y;

                if (thisMonsterSolidArea.intersects(monsterSolidArea)) {

                    monster.collisionOn = true;
                    System.out.println("Monster collision detected!");
                }
            }
        }
    }





}