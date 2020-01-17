package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class Hammer extends Item {

    private static List<Hammer> hammers = new ArrayList<>();

    public Hammer(Cell cell) {
        super(cell);
    }

    public static void addHammer(Hammer hammer) {
        hammers.add(hammer);
    }

    @Override
    public String getTileName() {
        return "hammer";
    }
}
