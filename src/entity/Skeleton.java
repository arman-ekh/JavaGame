package entity;

import main.GamePannel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Skeleton extends Monsters {
    boolean hitPlayer;

    public Skeleton(GamePannel gp) {
        super(gp);
        this.damage = 15;
        this.health = 40;
        this.speed = 2;
        getImage();
        this.direction = "right";
        this.hitPlayer = false;
    }

    @Override
    public void getImage() {
        try {
            up1 = ImageIO.read(getClass().getResource("/skeleton/skeleton_up_1.png"));
            up2 = ImageIO.read(getClass().getResource("/skeleton/skeleton_up_1.png"));
            down1 = ImageIO.read(getClass().getResource("/skeleton/skeleton_up_1.png"));
            down2 = ImageIO.read(getClass().getResource("/skeleton/skeleton_up_1.png"));
            right1 = ImageIO.read(getClass().getResource("/monster/boy_up_1.png"));
            right2 = ImageIO.read(getClass().getResource("/skeleton/skeleton_up_1.png"));
            left1 = ImageIO.read(getClass().getResource("/skeleton/skeleton_up_1.png"));
            left2 = ImageIO.read(getClass().getResource("/skeleton/skeleton_up_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (this.direction.equals("right")) {
            worldX += speed;
        } else if (this.direction.equals("left")) {
            worldX -= speed;
        }


        if (checkWallCollision(worldX, worldY)) {
            if (this.direction.equals("right")) {
                this.direction = "left";
            } else if (this.direction.equals("left")) {
                this.direction = "right";
            }
        }

        if (checkPlayerCollision(this, gp.player)) {
            long currentTime = System.currentTimeMillis();

            if (currentTime - gp.player.lastDamageTime > gp.player.damageCooldown) {
                gp.player.health = gp.player.health - damage;
                gp.player.lastDamageTime = currentTime;
                System.out.println("Player-Skeleton collision detected! Health: " + gp.player.health);
            }
        }

        hitPlayer = throwObject();

        spriteCounter++;
        if (spriteCounter >= 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
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

    public boolean throwObject(){
        int distanceX = Math.abs(worldX - gp.player.worldX) / gp.tileSize;
        int distanceY = Math.abs(worldY - gp.player.worldY) / gp.tileSize;
//        System.out.println(
//                distanceX + " " + distanceY
//        );
        if(distanceY <= 5 && distanceX <= 5){//throwable item radiuos
            System.out.println("test");
        }

        return false;
    }
}
