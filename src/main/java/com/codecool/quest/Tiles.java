package com.codecool.quest;

import com.codecool.quest.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();

    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("bat", new Tile(26,8));
        tileMap.put("golem", new Tile(28, 6));
        tileMap.put("duck", new Tile(25, 7));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("hammer", new Tile(7, 29));
        tileMap.put("stairs from up", new Tile(2, 6));
        tileMap.put("dead skeleton", new Tile(0, 15));
        tileMap.put("bronze torch", new Tile(3, 15));
        tileMap.put("campfire", new Tile(14, 10));
        tileMap.put("dried branch", new Tile(6, 2));
        tileMap.put("stones", new Tile(5, 2));
        tileMap.put("grass2", new Tile(6, 0));
        tileMap.put("pot", new Tile(14, 26));
        tileMap.put("chest closed", new Tile(8, 6));
        tileMap.put("chest open", new Tile(9, 7));
        tileMap.put("bone", new Tile(16, 24));
        tileMap.put("coins", new Tile(9, 26));

    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
