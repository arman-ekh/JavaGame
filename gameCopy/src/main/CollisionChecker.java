package main;

import entity.Entity;
import object.OBJ_Door;

import java.awt.*;

public class CollisionChecker {
    GamePannel gp;


    public CollisionChecker(GamePannel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;
        entity.collisionOn = false;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                if (entityTopRow >= 0) {
                    tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                    tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    switch (tileNum1){
                        case 4:
                            entity.blurRadius = 200;
                            break;
                        case 0:
                            entity.blurRadius = 400;
                            break;
                    }
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                if (entityBottomRow < gp.maxWorldRow) {
                    tileNum1 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                    tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    switch (tileNum1){
                        case 4:
                            entity.blurRadius = 200;
                            break;
                        case 0:
                            entity.blurRadius = 400;
                            break;
                    }
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                if (entityLeftCol >= 0) {
                    tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                    tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    switch (tileNum1){
                        case 4:
                            entity.blurRadius = 200;
                            break;
                        case 0:
                            entity.blurRadius = 400;
                            break;
                    }
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                if (entityRightCol < gp.maxWorldCol) {
                    tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
                    tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    switch (tileNum1){
                        case 4:
                            entity.blurRadius = 200;
                            break;
                        case 0:
                            entity.blurRadius = 400;
                            break;
                    }
                }
                break;
        }
    }
    public void checkObj(Entity entity) {
        entity.isCollisionObjectOn = false;


        Rectangle entitySolidArea = new Rectangle(entity.solidArea);
        Rectangle objectSolidArea;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                objectSolidArea = new Rectangle(gp.obj[i].solidArea);


                entitySolidArea.x = entity.worldX + entity.solidArea.x;
                entitySolidArea.y = entity.worldY + entity.solidArea.y;

                objectSolidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x + 2;
                objectSolidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y + 2;


                if (entitySolidArea.intersects(objectSolidArea)) {
                    if(gp.obj[i].name.equals("key")){
                        gp.playSoundEffect(1);
                        entity.isCollisionObjectOn = true;
                        gp.obj[i].collision = true;
                        System.out.println("key picked up ");
                        gp.obj[i] = null;
                        entity.keyNum++;
                    }else if (gp.obj[i].name.equals("door")) {
                        OBJ_Door door = (OBJ_Door) gp.obj[i];

                        if (!door.open) {
                            if ((entity.direction.equals("up") && entity.worldY > gp.obj[i].worldY ||
                                    entity.direction.equals("down") && entity.worldY < gp.obj[i].worldY ||
                                    entity.direction.equals("left") && entity.worldX > gp.obj[i].worldX ||
                                    entity.direction.equals("right") && entity.worldX < gp.obj[i].worldX)) {

                                if(entity.keyNum<=0){
                                    entity.isCollisionObjectOn = true;
                                }else{
                                    entity.keyNum--;
                                    door.open = true;
                                 }
                            }
                        } else {
                            entity.collisionOn = false;
                        }
                    }

                }
            }
        }
    }








}
