package Monsters;


import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;
import object.OBJ_hearts;

import java.util.Random;

public class GreenSlime_Monster extends Entity {

    GamePanel gp;
    public GreenSlime_Monster(GamePanel gp) {
        super(gp);

        this.gp = gp;
        type = type_monster;
        name = "Green Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife  = 4;
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

        up1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);

    }

    // Method for if you want the monster to attack the player when the player gets too close
    /*public void update() {
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;

        if(onPath && tileDistance < 5){
            int i = new Random().nextInt(100)+1;
            if(i > 50){
                onPath = true;
            }

        }
        if(onPath && tileDistance > 20){
            onPath = false;

        }
    }*/

    public void setAction() {
        if(onPath){
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            searchPath(goalCol, goalRow);

            int i = new Random().nextInt(100)+1;
            if (i > 99 && !projectile.alive && shotAvailableCounter == 30) {
                projectile.set(worldX, worldY, direction, true, this);
                //gp.projectileList.add(projectile);

                // CHECK VACANCY
                for (int ii = 0; ii < gp.projectile[1].length; ii++) {
                    if (gp.projectile[gp.currentMap][ii] == null) {
                        gp.projectile[gp.currentMap][ii] = projectile;
                        break;
                    }
                }

                shotAvailableCounter = 0;
            }

        }else{
            actionLockCounter++;
            if (actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;

                if (i <= 25) direction = "up";
                if (i > 25 && i <= 50) direction = "down";
                if (i > 50 && i <= 75) direction = "left";
                if (i > 75 && i < 100) direction = "right";
                actionLockCounter = 0;

            }

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
