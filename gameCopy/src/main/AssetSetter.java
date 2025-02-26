package main;

import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePannel gp ;
    public AssetSetter(GamePannel gp){
        this.gp = gp;
    }
    public void setObject(){
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 20 * gp.tileSize;
        gp.obj[0].worldY = 20 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 15 * gp.tileSize;
        gp.obj[1].worldY = 15 * gp.tileSize;

        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldX = 11 * gp.tileSize;
        gp.obj[2].worldY = 9 * gp.tileSize;

    }
}
