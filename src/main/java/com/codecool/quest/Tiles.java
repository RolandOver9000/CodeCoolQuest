package com.codecool.quest;

import com.codecool.quest.logic.Drawable;
import com.codecool.quest.util.OffsetMatrix;
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
        tileMap.put("stairs down", new Tile(3, 6));
        tileMap.put("dead skeleton", new Tile(0, 15));
        tileMap.put("bronze torch", new Tile(3, 15));
        tileMap.put("campfire", new Tile(14, 10));
        tileMap.put("dried branch", new Tile(6, 2));
        tileMap.put("stones", new Tile(5, 2));
        tileMap.put("grass2", new Tile(6, 0));
        tileMap.put("pot", new Tile(14, 26));
        tileMap.put("chest closed", new Tile(8, 6));
        tileMap.put("chest open", new Tile(9, 6));
        tileMap.put("bone", new Tile(16, 24));
        tileMap.put("coins", new Tile(9, 26));
        tileMap.put("door closed", new Tile(3, 9));
        tileMap.put("door opened", new Tile(6, 9));
        tileMap.put("the boss", new Tile(20, 26));

        tileMap.put("crosshair", new Tile(22, 24));
        tileMap.put("exclamation mark", new Tile(19, 25));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        drawTile(context, d.getTileName(), x, y);
    }

    public static void drawTile(GraphicsContext context, String tileName, int x, int y) {
        drawTile(context, tileName, x, y, OffsetMatrix.LEAVE);
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y, OffsetMatrix om) {
        drawTile(context, d.getTileName(), x, y, om);
    }

    public static void drawTile(GraphicsContext context, String tileName, int x, int y, OffsetMatrix om) {
        Tile tile = tileMap.get(tileName);
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                TILE_WIDTH * (x + om.kdx),
                TILE_WIDTH * (y + om.kdy),
                (float) TILE_WIDTH * om.kw,
                (float) TILE_WIDTH * om.kh);
    }

    public static void drawHintCrossHair(GraphicsContext context, int x, int y) {
        drawTile(context, "crosshair", x, y, OffsetMatrix.HINT_CROSSHAIR);
    }

    public static void drawHint(GraphicsContext context, Drawable d, int x, int y) {
        drawHint(context, d.getTileName(), x, y);
    }

    public static void drawHint(GraphicsContext context, String tileName, int x, int y) {
        drawHintCrossHair(context, x, y);
        drawTile(context, tileName, x, y, OffsetMatrix.HINT_CONTENT);
    }
}
