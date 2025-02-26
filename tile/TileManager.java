package tile;

import main.GamePannel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePannel gp;
    public Tile[] tile;
    public int[][] mapTileNum;


    public TileManager(GamePannel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/map1.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/doorBackGround.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/woodenFloor.png"));




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                throw new IOException("File not found: " + filePath);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.maxWorldRow; row++) {
                String line = br.readLine();
                if (line == null) {
                    throw new IOException("Unexpected end of file at row: " + row);
                }

                String[] numbers = line.trim().split("\\s+");
                if (numbers.length != gp.maxWorldCol) {
                    throw new IOException("Invalid column count at row " + row + ". Expected: " + gp.maxWorldCol + ", Found: " + numbers.length);
                }

                for (int col = 0; col < gp.maxWorldCol; col++) {
                    int tileIndex = Integer.parseInt(numbers[col]);

                    if (tileIndex < 0 || tileIndex >= tile.length) {
                        throw new IllegalArgumentException("Invalid tile index at [" + row + "][" + col + "]: " + tileIndex);
                    }

                    mapTileNum[row][col] = tileIndex;
                }
            }


            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void draw(Graphics2D g2) {
        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                int tileNum = mapTileNum[row][col];

                int worldX = col * gp.tileSize;
                int worldY = row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

//                if (tile[tileNum].collision) { //show hit box
//                    g2.setColor(new Color(255, 0, 0, 100));
//                    g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
//                }
            }
        }
    }


}
