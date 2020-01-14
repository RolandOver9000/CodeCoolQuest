package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.*;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private List<Skeleton> skeletons = new ArrayList<>();
    private List<Bat> bats = new ArrayList<>();
    private List<Golem> golems = new ArrayList<>();
    private List<Duck> ducks = new ArrayList<>();

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


    public void addBat(Bat bat) {
        this.bats.add(bat);
    }

    public List<Bat> getBats() {
        return bats;
    }

    public void addGolem(Golem golem) {
        this.golems.add(golem);
    }

    public List<Golem> getGolems() {
        return golems;
    }


    public void addDuck(Duck duck) {
        this.ducks.add(duck);
    }

    public List<Duck> getDucks() {
        return ducks;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
