package Monsters;

import data.Progress;
import entity.Entity;
import entity.PlayerDummy;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_ManaCrystal;
import object.OBJ_hearts;

import java.util.Random;

public class MON_Skull_King extends Entity {

    GamePanel gp;
    public static final String monName = "Skull King";
    public MON_Skull_King(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 40;
        life = maxLife;
        attack = 16;
        defense = 3;
        exp = 40;
        knockBackPower = 5;
        sleep = true;

        int size = gp.tileSize * 5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48*2;
        solidArea.height = size - 48 ;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_direction = 25;
        motion2_direction = 50;

        getImage();
        getAttackImage();
        setDialogue();
    }

    public void getImage(){

        int i = 5;
        if(!inRage) {
            up1 = setup("/monster/SkullKing/skeletonlord_up_1", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("/monster/SkullKing/skeletonlord_up_2", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/monster/SkullKing/skeletonlord_down_1", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("/monster/SkullKing/skeletonlord_down_2", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("/monster/SkullKing/skeletonlord_left_1", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("/monster/SkullKing/skeletonlord_left_2", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("/monster/SkullKing/skeletonlord_right_1", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("/monster/SkullKing/skeletonlord_right_2", gp.tileSize * i, gp.tileSize * i);
        }
        if(inRage){
            up1 = setup("/monster/SkullKing/Phase2/skeletonlord_phase2_up_1", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_up_2", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_down_1", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_down_2", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_left_1", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_left_2", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_right_1", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_right_2", gp.tileSize * i, gp.tileSize * i);
        }
    }

    public void getAttackImage(){
        int i = 5;
        if(!inRage){
            attackUp1 = setup("/monster/SkullKing/skeletonlord_attack_up_1", gp.tileSize*i, gp.tileSize*i*2);
            attackUp2 = setup("/monster/SkullKing/skeletonlord_attack_up_2", gp.tileSize*i, gp.tileSize*i*2);
            attackDown1 = setup("/monster/SkullKing/skeletonlord_attack_down_1", gp.tileSize*i, gp.tileSize*i*2);
            attackDown2 = setup("/monster/SkullKing/skeletonlord_attack_down_2", gp.tileSize*i, gp.tileSize*i*2);
            attackLeft1 = setup("/monster/SkullKing/skeletonlord_attack_left_1", gp.tileSize*i*2, gp.tileSize*i);
            attackLeft2 = setup("/monster/SkullKing/skeletonlord_attack_left_2", gp.tileSize*i*2, gp.tileSize*i);
            attackRight1 = setup("/monster/SkullKing/skeletonlord_attack_right_1", gp.tileSize*i*2, gp.tileSize*i);
            attackRight2 = setup("/monster/SkullKing/skeletonlord_attack_right_2", gp.tileSize*i*2, gp.tileSize*i);
        }
        if(inRage){
            attackUp1 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_attack_up_1", gp.tileSize*i, gp.tileSize*i*2);
            attackUp2 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_attack_up_2", gp.tileSize*i, gp.tileSize*i*2);
            attackDown1 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_attack_down_1", gp.tileSize*i, gp.tileSize*i*2);
            attackDown2 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_attack_down_2", gp.tileSize*i, gp.tileSize*i*2);
            attackLeft1 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_attack_left_1", gp.tileSize*i*2, gp.tileSize*i);
            attackLeft2 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_attack_left_2", gp.tileSize*i*2, gp.tileSize*i);
            attackRight1 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_attack_right_1", gp.tileSize*i*2, gp.tileSize*i);
            attackRight2 = setup("/monster/SkullKing//Phase2/skeletonlord_phase2_attack_right_2", gp.tileSize*i*2, gp.tileSize*i);
        }

    }




    public void setDialogue() {

        dialogues[0][0] = "No one can steal my treasure!";
        dialogues[0][1] = "You will die here!";
        dialogues[0][2] = "WELCOME TO YOUR DOOM!";




    }
    // Method for if you want the monster to attack the player when the player gets too close
    public void setAction() {

        if(!inRage && life < maxLife/2){
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed =defaultSpeed;
            attack += 2;
        }

        if(getTileDistance(gp.player) < 10){

            moveTowardsPlayer(60);

        }else{
            // Get a random direction
            getRandomDirection(120);

        }

        if(!attacking){
            // The smaller the number the more aggressive the monster
            checkAttackOrNot(30, gp.tileSize*7, gp.tileSize*5);
        }
    }

    public void damageReaction(){
        actionLockCounter = 0;
    }

    public void checkDrop(){

        gp.bossBattleOn = false;
        Progress.skeletonDefeated = true;
        // Restore the previous music
        gp.stopMusic();
        gp.playMusic(19);

        // Remove the iron door
        for(int i=0; i<gp.obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)){
               gp.playSE(19);
               gp.obj[gp.currentMap][i] = null;
            }
        }

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