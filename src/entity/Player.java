package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    // indicates how many keys a player currently has.
    //public int hasKey = 0;


    public boolean attackCancel = false;
    /**
     * Constructor for the Player class. It initializes the player object with the game panel and key handler references.
     * It also sets the player's screen position and initializes the solid area for collision detection.
     * Finally, it sets the default values for the player and loads the player's images.
     *
     * @param gp  The GamePanel object which contains game-related information and settings.
     * @param keyH The KeyHandler object to manage user inputs.
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        type = type_player;
        this.keyH = keyH;



        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.height = 16;
        solidArea.width = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultPositions();
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();

    }

    /**
     * Sets the default values for the player. This includes the starting position, speed, and initial direction.
     */
    public void setDefaultValues() {

        // PLAYERS STATUS
        level = 1;
        maxLife = 4;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        defaultSpeed = 4;
        speed = defaultSpeed;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 10000;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_FireBall(gp);
        attack = getAttack();
        defense = getDefense();

    }

    public void setDefaultPositions(){
        if(gp.currentMap == 0){
            worldX = gp.tileSize * 23;
            worldY = gp.tileSize * 21;
        }
        if(gp.currentMap == 1){
            worldX = gp.tileSize * 12;
            worldY = gp.tileSize * 13;
            direction = "down";

        }
    }
    public void restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
        invincible = false;

    }

    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Boots(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Key(gp));

    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;

    }
    /**
     * Loads the player's images for different directions (up, down, left, right).
     * Each direction has two images to create an animation effect.
     */
    public void getPlayerImage() {

        up1 = setup("/player/move/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/move/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/move/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/move/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/move/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/move/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/move/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/move/boy_right_2", gp.tileSize, gp.tileSize);
    }
    public void getPlayerAttackImage(){
        if(currentWeapon.type == type_sword){
            attackUp1 = setup("/player/attack/Sword/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/attack/Sword/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/player/attack/Sword/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/attack/Sword/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/attack/Sword/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/attack/Sword/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/attack/Sword/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/player/attack/Sword/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe){
            attackUp1 = setup("/player/attack/Axe/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/attack/Axe/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/player/attack/Axe/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/attack/Axe/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/attack/Axe/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/attack/Axe/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/attack/Axe/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/player/attack/Axe/boy_axe_right_2", gp.tileSize*2, gp.tileSize);


        }









    }

    /**
     * Updates the player's state including position, direction, and animation.
     * It checks for user inputs to change the player's direction and position.
     * If there are no collisions detected, the player moves in the specified direction.
     * The method also handles the animation by switching between two sprites for each direction.
     */
    public void update(){

        if(attacking){
            attacking();
        }

        if (keyH.downPressed || keyH.leftPressed || keyH.upPressed || keyH.rightPressed || keyH.enterPressed) {
            if (keyH.upPressed) {
                direction = "up";
                this.changeSpriteNum();
            } else if (keyH.downPressed) {
                direction = "down";
                this.changeSpriteNum();
            } else if (keyH.leftPressed) {
                direction = "left";
                this.changeSpriteNum();
            } else if (keyH.rightPressed) {
                direction = "right";
                this.changeSpriteNum();
            }

            // Check for collisions
            collisionOn = false;
            gp.checker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.checker.checkObject(this, true);
            pickupObject(objIndex);

            //CHECK NPC COLLISION

            int npcIndex = gp.checker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIDX = gp.checker.checkEntity(this, gp.monster);
            contactMonster(monsterIDX);

            // CHECK INTERACTIVE COLLISION
            int iTileIndex = gp.checker.checkEntity(this, gp.iTile);
            interactTree(iTileIndex);

            // CHECK EVENT
            gp.eHandler.checkEvent();


            // Move player if no collision is detected
            if (!collisionOn && !keyH.enterPressed) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            if(keyH.enterPressed && !attackCancel){
                gp.playSE(8);
                attacking = true;
                spriteCounter = 0;
            }
            attackCancel = false;
            keyH.enterPressed = false;
            // Update sprite animation
            spriteCounter++;
            if(spriteCounter > 13){
                if (spriteNum == 1){
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;

                }
                spriteCounter = 0;
            }
        }
        if(keyH.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.haveResource(this)){
            // SET DEFAULT COORDINATES, DIRECTION AND USER
            projectile.set(worldX, worldY, direction, true, this);

            projectile.subtractResource(this);
            // ADD TO THE ARRAY LIST
            //gp.projectileList.add(projectile);

            // CHECK VACANCY
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
            gp.playSE(10);
        }
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }

        if(life > maxLife){
            life = maxLife;
        }
        if(mana > maxMana){
            mana = maxMana;
        }

        if(life <= 0){
            gp.gameState = gp.gameOverState;
            gp.stopMusic();
        }
    }

    //METHOD    -   for alternating image for appearance of movement
    public void changeSpriteNum(){
        if(spriteCounter > 20) {
            if(spriteNum == 1) {
                spriteNum = 2;}
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void attacking(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
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

            // if the weapon is hitting the monster
            int monsterIndex = gp.checker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack,  currentWeapon.knockBackPower);

            int iTileIndex = gp.checker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            int projectileIndex =  gp.checker.checkEntity(this, gp.projectile);
            damageProjectile(projectileIndex);

            // RESET TO ORIGINAL POSITION
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.height = solidAreaHeight;
            solidArea.width = solidAreaWidth;

        }
        if(spriteCounter>25){
            spriteNum = 1;
            spriteCounter =0;
            attacking = false;
        }

    }



    public void pickupObject(int i){
        if( i != 999) {
            // PICKUP ONLY ITEMS
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly){ // FIXED

                gp.obj[gp.currentMap][i].use(this); // FIXED
                gp.obj[gp.currentMap][i] = null;

            } // OBSTACLE
            else if (gp.obj[gp.currentMap][i].type == type_obstacle) {
                if (keyH.enterPressed){
                    attackCancel = true;
                    gp.obj[gp.currentMap][i].interact();
                }

            } else{
                // INVENTORY ITEMS
                String text;
                if(canObtainItem(gp.obj[gp.currentMap][i])){
                    //inventory.add(gp.obj[gp.currentMap][i]); // FIXED
                    gp.playSE(1);
                    text = "Got a "  + gp.obj[gp.currentMap][i].name +"!"; // FIXED
                }else{
                    text = "You cannot carry anymore!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null; // FIXED
            }


        }
    }
    public void contactMonster(int i){
        if(i != 999){

            if(!invincible && !gp.monster[gp.currentMap][i].dying){ // FIXED
                gp.playSE(7);

                int damage = gp.monster[gp.currentMap][i].attack - defense; // FIXED
                if(damage < 0){
                    damage = 0;
                }
                life -= damage;
                invincible = true;

            }
        }
    }

    public void knockBack(Entity entity, int knockBackPower) {
        entity.direction = direction;
        entity.speed += knockBackPower;
        entity.knockBack = true;
    }

    private void damageInteractiveTile(int i) {

        if(i != 999 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].isCorrectItem(this) // FIXED
            && !gp.iTile[gp.currentMap][i].invincible){
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;

            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]); // FIXED
            if(gp.iTile[gp.currentMap][i].life == 0){
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm(); // FIXED
            }
        }
    }

    public void damageProjectile(int i){
        if(i != 999){
           Entity projectile = gp.projectile[gp.currentMap][i];
           projectile.alive = false;
           generateParticle(projectile, projectile);
        }




    }

    public void damageMonster(int i, int attack, int kbPower){
        if(i != 999) {
            if(!gp.monster[gp.currentMap][i].invincible){ // FIXED
                gp.playSE(6);

                if(kbPower > 0){
                    knockBack(gp.monster[gp.currentMap][i], kbPower);
                }

                int damage =  attack - gp.monster[gp.currentMap][i].defense; // FIXED
                if(damage < 0){
                    damage = 0;
                }
                gp.monster[gp.currentMap][i].life -= damage; // FIXED
                gp.ui.addMessage(damage + " damage!");

                gp.monster[gp.currentMap][i].invincible = true; // FIXED
                gp.monster[gp.currentMap][i].damageReaction(); // FIXED

                if(gp.monster[gp.currentMap][i].life <= 0){ // FIXED
                    gp.monster[gp.currentMap][i].dying = true; // FIXED
                    gp.ui.addMessage(gp.monster[gp.currentMap][i].name + " Slayed!"); // FIXED
                    gp.ui.addMessage("Exp: +" + gp.monster[gp.currentMap][i].exp); // FIXED
                    exp += gp.monster[gp.currentMap][i].exp; // FIXED
                    checkLevelUp();
                }
            }


        }
    }

    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp *= 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(2);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are now on level "+ level;
        }
    }
    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
            }
            if(selectedItem.type == type_consumable){
                if(selectedItem.use(this)){
                    if(!selectedItem.stackable || selectedItem.amount <= 1){
                        inventory.remove(itemIndex);
                    }else{
                        selectedItem.amount--;


                    }
                }

            }
        }
    }

    public void interactNPC(int i){

        if(keyH.enterPressed) {
            if (i != 999) {
                attackCancel = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak(); // FIXED
            }
        }
    }
    public void interactTree(int i){
        if(keyH.enterPressed){
            if(i != 999){
                // cutting down tree
            }
        }

    }
    public int searchItemInInventory(String itemName){
        int itemIndex = 999;

        for(int i = 0; i<inventory.size(); i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public boolean canObtainItem(Entity item){
        boolean canObtain = false;

        // CHECK IF THE ITEM IS STACKABLE
        if(item.stackable){
            int index = searchItemInInventory(item.name);

            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            else{ // NEW ITEM SP NEED TO CHECK VACANCY
                if(inventory.size() != maxInventorySize){
                    inventory.add(item);
                    canObtain = true;
                }

            }
        }
        else{ // NOT STACKABLE SO CHECK ITEM VACANCY
            if(inventory.size() != maxInventorySize){
                inventory.add(item);
                canObtain = true;
            }
        }


        return canObtain;
    }
    /**
     * Draws the player on the screen at the current position.
     * The method selects the appropriate image based on the player's direction and animation frame.
     *
     * @param g2 The Graphics2D object used for drawing the player.
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (direction) {
            case "up":
                if(!attacking){
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }if(attacking){
                    tempScreenY = screenY - gp.tileSize;
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
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
                break;
        }

        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3F));
        }
        g2.drawImage(image, tempScreenX, tempScreenY,null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        //For debugging and to check if collision detector is actually working.
        //g2.setColor(Color.red);
        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y,solidArea.width,solidArea.height);


        /*g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.white);
        g2.drawString("Invincible:" + invincibleCounter, 10, 400);
         */
    }
}

