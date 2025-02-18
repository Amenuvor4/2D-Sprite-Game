package Monsters;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;
import object.OBJ_hearts;

import java.util.Random;

public class MON_Orc extends Entity {


    GamePanel gp;
    public MON_Orc(GamePanel gp) {
        super(gp);

        this.gp = gp;
        type = type_monster;
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife  = 10;
        life = maxLife;
        attack = 8;
        defense = 4;
        exp = 10;
        knockBackPower = 20;


        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        motion1_direction = 40;
        motion2_direction = 85;

        getImage();
        getAttackImage();
    }

    public void getImage(){

        up1 = setup("/monster/orc_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/orc_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/orc_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/orc_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/orc_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/orc_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/orc_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/orc_right_2", gp.tileSize, gp.tileSize);

    }

    public void getAttackImage(){
        attackUp1 = setup("/monster/attack/orc_attack_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/monster/attack/orc_attack_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/monster/attack/orc_attack_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/monster/attack/orc_attack_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/monster/attack/orc_attack_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/monster/attack/orc_attack_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/monster/attack/orc_attack_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/monster/attack/orc_attack_right_2", gp.tileSize*2, gp.tileSize);
    }




    // Method for if you want the monster to attack the player when the player gets too close
    public void setAction() {
        if(onPath){

            // Check if it stops chasing
            checkStopChasingOrNot(gp.player, 15, 100);

            // Search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

        }else{
            // Check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);
            // Get a random direction
            getRandomDirection(120);

        }

        if(!attacking){
            // The smaller the number the more aggressive the monster
            checkAttackOrNot(30, gp.tileSize*4, gp.tileSize);
        }
    }

    public void damageReaction(){
        actionLockCounter = 0;
        onPath = true;
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

