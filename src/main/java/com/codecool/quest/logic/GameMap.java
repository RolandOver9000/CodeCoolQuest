package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.items.Hammer;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Key;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private List<Skeleton> skeletons = new ArrayList<>();
    private Hammer hammer;
    private Key key;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }


    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void addSkeleton(Skeleton skeleton) {
        this.skeletons.add(skeleton);
    }

    public List<Skeleton> getSkeletons() {
        return skeletons;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Hammer getHammer() {return hammer;}

    public Key getKey() {return key;}

    public void setHammer(Hammer hammer) {this.hammer = hammer;}

    public void setKey(Key key) {this.key = key;}

    public boolean removeItem(GameMap map) {
        int playerPositionX = map.getPlayer().getX();
        int playerPositionY = map.getPlayer().getY();
        Cell cell = map.getCell(playerPositionX, playerPositionY);
        if (cell.getTileName().equals("hammer")) {
            Hammer hammer = map.getHammer();
            hammer.getCell().setItem(null);
            cell.setType(CellType.FLOOR);
            return true;
        } else if (cell.getTileName().equals("key")) {
            Key key = map.getKey();
            key.getCell().setItem(null);
            cell.setType(CellType.FLOOR);
            return true;
        }
        return false;
    }
}
