package Monsters;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;
import object.OBJ_hearts;

import java.util.Random;

public class MON_Bat extends Entity {

    GamePanel gp;
    public MON_Bat(GamePanel gp) {
        super(gp);

        this.gp = gp;
        type = type_monster;
        name = "Bat";
        defaultSpeed = 4;
        speed = defaultSpeed;
        maxLife  = 3;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);


        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage(){

        up1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);

    }

    // Method for if you want the monster to attack the player when the player gets too close


    public void setAction() {

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;


        if(onPath){

//            // Check if it stops chasing
//            checkStopChasingOrNot(gp.player, 15, 100);
//
//            // Search the direction to go
//            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
//
//            // Check if it shoots a projectile
//            checkShootOrNot(200, 30);

        }else{
//            // Check if it starts chasing
//            checkStartChasingOrNot(gp.player, 5, 100);
            // Get a random direction
            getRandomDirection(10);

        }
    }

    public void damageReaction(){
        actionLockCounter = 0;
       //onPath = true;
    }

    public void checkDrop(){

        int i = new Random().nextInt(100)+1;
        // SET THE MONSTER DROP
        if(i< 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }else if(i < 75){
            dropItem(new OBJ_hearts(gp));
        }else if(i < 100){
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }

}

