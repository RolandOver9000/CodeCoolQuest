package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.AutoTarget;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;

public class Skeleton extends Actor implements Combative {

    AutoTarget autotarget = new AutoTarget(MONSTER_ATTACK_RANGE, this);

    private static final int INITIAL_HEALTH = 20;
    private static final int INITIAL_ATTACK_DAMAGE = 3;
    private static final int INITIAL_ARMOR = 0;
    private static final int MONSTER_ATTACK_RANGE = 3;
    private int dx;
    private int dy;

    private int coordinateSwitcher = -1;

    public Skeleton(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        this.setMonsterAttackRange(MONSTER_ATTACK_RANGE);
    }

    @Override
    public void act() {
        Cell nextCell;


        //if player is near it will search for the next closest possible cell
        if(isPlayerNear()) {
            nextCell= autotarget.pathFinding();

            //step on nextCell if possible
            if (!nextCell.isBlocking() && !isPlayerNexToIt()) {
                this.moveTo(nextCell);
                //if there is a player, then attack
            } else if(isPlayerNexToIt()){
                Actor target = getPlayerCurrentPosition().getActor();
                this.attack(target);
                //if something blocking the player's side then just stand and wait
            } else if(!nextCell.isBlocking()){
                this.moveTo(this.getCell());
            }
        }
        //if player is not near it will do the standard movement
        else {
            dx = coordinateSwitcher;
            dy = 0;
            nextCell = this.getCell().getNeighbor(dx, dy);
            standardMovement(nextCell);
        }

    }

    private void standardMovement(Cell nextCell) {

        //bounce if the nexcell is an obstacle
        if (nextCell.isBlocking()) {
            coordinateSwitcher *= -1;
            dx = coordinateSwitcher;
            nextCell = super.getCell().getNeighbor(dx, dy);
            this.moveTo(nextCell);
        }
        else {
            this.moveTo(nextCell);
        }

    }

    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.DEAD_SKELETON);
        this.getCell().getGameMap().removeCombativeActor(this);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

}
