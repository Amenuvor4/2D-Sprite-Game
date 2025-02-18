package entity;

import Quests.Quest;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Entity {
    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
            attackLeft1, attackLeft2, attackRight1, attackRight2,
    guardUp, guardDown, guardLeft, guardRight;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public String[][] dialogues = new String[20][20];
    public ArrayList<String> questDialogues = new ArrayList<>();
    public Entity attacker;
    public Entity linkedEntity;
    public boolean temp = false;

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    public int dialogueIndex = 0;
    public int dialogueSet = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath;
    public boolean knockBack = false;
    public String knockBackDirection;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    //Loot Items
    public Entity loot;
    public boolean opened = false;
    public boolean inRage = false;
    public boolean sleep = false;
    public boolean drawing = true;


    // COUNTERS
    public int spriteCounter = 0;
    public int invincibleCounter = 0;
    public int actionLockCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    public int hpBarCounter = 0;
    int knockBackCounter = 0;
    public int guardCounter = 0;
    int offBalanceCounter = 0;
    public int progressCounter;


    // CHARACTER STATUS
    public int speed;
    public int defaultSpeed;
    public int maxLife;
    public int life;
    public int ammo;
    public int maxMana;
    public int mana;

    public String name;
    public boolean collision = false;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public double nextLevelExp;
    public int coin;
    public int motion1_direction;
    public int motion2_direction;
    public Entity currentWeapon;
    public Entity currentShield;
    public String currentDialogue;
    public Entity currentLight;
    public Projectile projectile;
    public boolean boss;


    //ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public ArrayList<Quest> quests = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable =  false;
    public int amount = 1;
    public int lightRadius;


    //TYPE
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;
    public final int type_light = 9;
    public final int type_pickaxe = 10;




    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public int getScreenX(){
        return  worldX -gp.player.worldX + gp.player.screenX;
    }
    public int getScreenY(){
        return worldY - gp.player.worldY + gp.player.screenY;
    }
    public int getLeftX(){
        return worldX + solidArea.x;
    }
    public int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }
    public int getTopY(){
        return worldY + solidArea.y;
    }
    public int getBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }
    public int getCol(){
        return (worldX + solidArea.x)/gp.tileSize;
    }
    public int getRow(){
        return (worldY + solidArea.y)/gp.tileSize;
    }
    public int getCenterX() {
        return worldX + left1.getWidth()/2;
    }
    public int getCenterY(){
        return worldX + up1.getHeight()/2;
    }

    public int getXDistance(Entity target){
        return  Math.abs(getCenterX() - target.getCenterX());
    }
    public int getYDistance(Entity target){
        return Math.abs(getCenterY() - target.getCenterY());
    }
    public int getTileDistance(Entity target){
        return (getXDistance(target) + getYDistance(target))/gp.tileSize;
    }
    public int getGoalCol(Entity target){
        return (target.worldX + target.solidArea.x)/gp.tileSize;
    }
    public int getGoalRow(Entity target){
        return (target.worldY + target.solidArea.y)/gp.tileSize;
    }
    public void resetCounter(){
         spriteCounter = 0;
         invincibleCounter = 0;
         actionLockCounter = 0;
         shotAvailableCounter = 0;
         dyingCounter = 0;
         hpBarCounter = 0;
         knockBackCounter = 0;
         guardCounter = 0;
         offBalanceCounter = 0;


    }
    public void setLoot(Entity loot) {}
    public void setAction(){}
    public void move(String direction){}
    public void damageReaction(){}
    public void speak(){}

    public void facePlayer(){
        switch (gp.player.direction){
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }
    }
    public void startDialogue(Entity entity, int setNum){
        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        if(!gp.ui.declinedQuest){
            dialogueSet = setNum;
        }else{
            dialogueSet = 3;
        }

    }
    public void setDialogue(){}
    public void interact(){

    }


    public boolean use(Entity entity){return  true;}
    public void checkDrop(){}
    public void dropItem(Entity droppedItem){
        for(int i = 0; i<gp.obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] == null){
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;   // the dead monster's worldx
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public Color getParticleColor(){
        return null;
    }

    public int getParticleSize(){
        return 0;
    }

    public int getParticleSpeed(){
        return 0;
    }

    public int getParticleMaxLife(){
        return 0;
    }



    public void generateParticle(Entity generator, Entity target){

        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();


        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2,-1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 1,1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -1,1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2,-1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);

    }
    public void checkCollision(){
        collisionOn = false;
        gp.checker.checkTile(this);
        gp.checker.checkObject(this, false);
        gp.checker.checkEntity(this, gp.npc);
        gp.checker.checkEntity(this, gp.monster);
        gp.checker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.checker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer){
            damagePlayer(attack);
        }

    }
    public void update(){
        if(!sleep) {
            if (knockBack) {
                checkCollision();

                if (collisionOn) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;
                } else if (!collisionOn) {
                    switch (knockBackDirection) {
                        case "up":
                            worldY -= speed;
                            break;
                        case "down":
                            worldY += speed;
                            break;
                        case "left":
                            worldX -= speed;
                            break;
                        case "right":
                            worldX += speed;
                            break;
                    }
                }
                knockBackCounter++;

                if (knockBackCounter == 10) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;
                }

            } else if (attacking) {
                attacking();
            } else {
                setAction();
                checkCollision();

                if (!collisionOn) {
                    switch (direction) {
                        case "up":
                            worldY -= speed;
                            break;
                        case "down":
                            worldY += speed;
                            break;
                        case "left":
                            worldX -= speed;
                            break;
                        case "right":
                            worldX += speed;
                            break;
                    }
                }

                // Update sprite animation
                spriteCounter++;
                if (spriteCounter > 24) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }

            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 40) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
            if (shotAvailableCounter < 30) {
                shotAvailableCounter++;
            }

            if (offBalance) {
                offBalanceCounter++;
                if (offBalanceCounter > 60) {
                    offBalance = false;
                    offBalanceCounter = 0;
                }
            }
        }

    }

    public void checkShootOrNot(int rate, int shotInterval ){
        int i = new Random().nextInt(rate);
        if (i == 0 && !projectile.alive && shotAvailableCounter == shotInterval) {
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

    }
    public void checkStartChasingOrNot(Entity target, int distance, int rate){

        if(getTileDistance(target) < distance){
            int i  = new Random().nextInt(rate);
            if(i == 0){
                onPath = true;
            }
        }

    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate){

        if(getTileDistance(target) > distance){
            int i  = new Random().nextInt(rate);
            if(i == 0){
                onPath = false;
            }
        }

    }

    public void getRandomDirection(int interval){
        actionLockCounter++;
        if (actionLockCounter > interval) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) direction = "up";
            if (i > 25 && i <= 50) direction = "down";
            if (i > 50 && i <= 75) direction = "left";
            if (i > 75 && i < 100) direction = "right";
            actionLockCounter = 0;

        }

    }

    public void attacking(){
        spriteCounter++;
        if(spriteCounter <= motion1_direction){
            spriteNum = 1;
        }
        if(spriteCounter > motion1_direction && spriteCounter <= motion2_direction){
            spriteNum = 2;

            //Save the current WorldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldx/y for the attack area
            switch(direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width;
                case "right": worldX += attackArea.width;


            }
            // attackArea becomes solid are
            solidAreaWidth = attackArea.width;
            solidAreaHeight = attackArea.height;


            if(type == type_monster){

                if(gp.checker.checkPlayer(this)){
                    damagePlayer(attack);
                }

            }else{

                // if the weapon is hitting the monster
                int monsterIndex = gp.checker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this,  attack,  currentWeapon.knockBackPower);

                int iTileIndex = gp.checker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);  // iffy

                int projectileIndex =  gp.checker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);

            }


            // RESET TO ORIGINAL POSITION
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.height = solidAreaHeight;
            solidArea.width = solidAreaWidth;

        }
        if(spriteCounter>motion2_direction){
            spriteNum = 1;
            spriteCounter =0;
            attacking = false;
        }

    }

    public void checkAttackOrNot(int rate, int straight, int horizontal){
        boolean targetIntRange = false;
        int xDistance = getXDistance(gp.player);
        int yDistance =  getYDistance(gp.player);

        switch(direction){
            case "up":
                if(gp.player.getCenterY() < getCenterY() && yDistance < straight && xDistance < horizontal){
                    targetIntRange = true;
                }
                break;
            case "down":
                if(gp.player.getCenterY() > getCenterY() && yDistance < straight && xDistance < horizontal){
                    targetIntRange = true;
                }
                break;
            case "left":
                if(gp.player.getCenterX() < getCenterX() && xDistance < straight && yDistance < horizontal){
                    targetIntRange = true;
                }
                break;
            case "right":
                if(gp.player.getCenterX() > getCenterX() && xDistance < straight && yDistance < horizontal){
                    targetIntRange = true;
                }
                break;
            }

            if(targetIntRange){
                // Check if it initiates an attack
                int i = new Random().nextInt(rate);
                if(i == 0){
                    attacking = true;
                    spriteNum = 1;
                    spriteCounter = 0;
                    shotAvailableCounter = 0;
                }
            }

    }
    public  void moveTowardsPlayer(int interval){
        actionLockCounter++;
        if(actionLockCounter > interval) {
            if (getXDistance(gp.player) > getYDistance(gp.player)) {
                if (gp.player.getCenterX() < getCenterX()) {
                    direction = "left";
                } else {
                    direction = "right";
                }
            } else if (getXDistance(gp.player) < getYDistance(gp.player)) {
                if (gp.player.getCenterY() < getCenterY()) {
                    direction = "up";
                } else {
                    direction = "down";
                }

            }
            actionLockCounter = 0;

        }
    }
    public String getOppositeDirection(String direction){

        String oppositeDirection = "";

        switch(direction){
            case "up": oppositeDirection = "down"; break;
            case "down": oppositeDirection = "up"; break;
            case "left": oppositeDirection = "right"; break;
            case "right": oppositeDirection = "left"; break;
        }
        return oppositeDirection;
    }
    // HELPER TO ATTACK PLAYER
    public void damagePlayer(int attack){
        if(!gp.player.invincible){
            gp.playSE(7);

            int damage = attack - gp.player.defense;

            String canGuardDirection = getOppositeDirection(direction);
            if(gp.player.guarding && gp.player.direction.equals(canGuardDirection)){


                //Parry
                if(gp.player.guardCounter < 10){
                    damage = 0;
                    gp.playSE(12);
                    setKnockBack(this, gp.player, knockBackPower);
                    offBalance = true;
                    spriteCounter =- 60;
                }
                else {
                    damage /= 3;
                    gp.playSE(13);
                }

            }else{
                if(damage < 1){
                    System.out.println("Damage == 0");
                    damage = 1;
                }
            }


            if(damage > 0){
                gp.player.transparent = true;
                setKnockBack(gp.player, this, knockBackPower);
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }

    }
    public void setKnockBack(Entity target, Entity attacker,  int knockBackPower) {
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;
    }
    public boolean inCamera(){
        boolean inCamera = false;
        if(worldX + gp.tileSize*5 > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize*5 > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) { inCamera = true;}
        return inCamera;
    }
    public void draw(Graphics2D g2){


        BufferedImage image = null;
        if(inCamera()) {

            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();
            switch (direction) {
                case "up":
                    if(!attacking){
                        if (spriteNum == 1) {image = up1;}
                        if (spriteNum == 2) {image = up2;}
                    }if(attacking){
                    tempScreenY = getScreenY() - up1.getHeight();
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}

                }
                    break;
                case "down":
                    if(!attacking){
                        if (spriteNum == 1) {image = down1;}
                        if (spriteNum == 2) {image = down2;}
                    }if(attacking){
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }

                    break;
                case "right":
                    if(!attacking){
                        if (spriteNum == 1) {image = right1;}
                        if (spriteNum == 2) {image = right2;}
                    }if(attacking){
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
                    break;
                case "left":
                    if(!attacking){
                        if (spriteNum == 1) {image = left1;}
                        if (spriteNum == 2) {image = left2;}
                    }if(attacking){
                    tempScreenX = getScreenX() - left1.getHeight();
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
                    break;
            }
            if(invincible){
                hpBarOn = true;
                hpBarCounter = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3F));
            }
            if(dying){
                dyingAnimation(g2);
            }
            g2.drawImage(image, tempScreenX, tempScreenY, null);

           changeAlpha(g2, 1F);
        }


    }

    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        int i = 5;
        if(dyingCounter <= i){changeAlpha(g2, 0f);}
        if(dyingCounter > i && dyingCounter <= i*2){changeAlpha(g2, 1f);}
        if(dyingCounter > i*2  && dyingCounter <= i*3){changeAlpha(g2, 0f);}
        if(dyingCounter > i*3 && dyingCounter <= i*4){changeAlpha(g2, 1f);}
        if(dyingCounter > i*4 && dyingCounter <= i*5){changeAlpha(g2, 0f);}
        if(dyingCounter > i*5 && dyingCounter <= i*6){changeAlpha(g2, 1f);}
        if(dyingCounter > i*6 && dyingCounter <= i*7){changeAlpha(g2, 0f);}
        if(dyingCounter > i*7 && dyingCounter <= i*8){changeAlpha(g2, 1f);}
        if(dyingCounter > i*8){

            alive = false;
        }
    }


    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

    }
    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream( imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gp.pFinder.search()){
            // NEXT WORLD X / Y
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // ENTITY SOLID AREA POSITION
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;


            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            }else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                // LEFT OR RIGHT
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                // UP OR LEFT
                direction = "up";
                checkCollision();

                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // UP OR RIGHT
                direction = "up";
                checkCollision();

                if (collisionOn) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                // DOWN OR LEFT
                direction = "down";
                checkCollision();

                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // DOWN OR RIGHT
                direction = "down";
                checkCollision();

                if (collisionOn) {
                    direction = "right";
                }
            }

            // IF REACH ThE GOAL, STOP THE SEARCH
            // int nextCol = gp.pFinder.pathList.get(0).col;
            // int nextRow = gp.pFinder.pathList.get(0).row;

            // if (nextCol == goalCol && nextRow == goalRow) {
            // onPath = false;
            // }
        }

    }

    public int getDetected(Entity user, Entity[][] target, String targetName){
        int index = 999;
        // CHECK THE SURROUNDING OBJ
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction){
            case "up": nextWorldY = user.getTopY()-gp.player.speed; break;
            case "down": nextWorldY = user.getBottomY()+gp.player.speed; break;
            case "left": nextWorldY = user.getLeftX()-gp.player.speed; break;
            case "right": nextWorldY = user.getRightX()+gp.player.speed; break;
        }
        int col = nextWorldX/gp.tileSize;
        int row = nextWorldY/gp.tileSize;

        for(int i = 0; i<target[1].length; i++){
            if(target[gp.currentMap][i] != null){
                if(target[gp.currentMap][i].getCol() == col &&
                        target[gp.currentMap][i].getRow() == row &&
                        target[gp.currentMap][i].name.equals(targetName)){
                    index = i;
                    break;
                }
            }
        }

        return index;
    }
}


