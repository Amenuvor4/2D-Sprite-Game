package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter =0;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;
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

        setDefaultValues();

    }

    /**
     * Sets the default values for the player. This includes the starting position, speed, and initial direction.
     */
    public void setDefaultValues() {

        // PLAYERS STATUS
        level = 1;
        maxLife = 10;
        life = maxLife;
        maxMana = 10;
        mana = maxMana;
        defaultSpeed = 4;
        speed = defaultSpeed;
        strength = 5;
        dexterity = 5;
        exp = 0;
        nextLevelExp = 5;
        coin = 10000;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        currentLight = null;
        projectile = new OBJ_FireBall(gp);
        attack = getAttack();
        defense = getDefense();

        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
        setDialogue();
        setDefaultPositions();

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
        if(gp.currentMap == 2){
            worldX = gp.tileSize * 9;
            worldY = gp.tileSize * 41;
        }
    }
    public void setDialogue() {
        dialogues[0][0] = "You are now on level "+ level;
    }
    public void restoreStatus(){

        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;

    }

    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Lantern(gp));

    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        motion1_direction = currentWeapon.motion1_direction;
        motion2_direction = currentWeapon.motion2_direction;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;

    }
    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentWeapon){
                currentWeaponSlot = 1;
            }
        }
        return currentWeaponSlot;
    }

    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentWeapon){
                currentShieldSlot = 1;
            }
        }
        return currentShieldSlot;
    }
    /**
     * Loads the player's images for different directions (up, down, left, right).
     * Each direction has two images to create an animation effect.
     */
    public void getImage() {

        up1 = setup("/player/move/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/move/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/move/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/move/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/move/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/move/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/move/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/move/boy_right_2", gp.tileSize, gp.tileSize);
    }

    public void getSleepingImage(BufferedImage image){

        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;

    }
    public void getAttackImage(){
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
        if(currentWeapon.type == type_pickaxe){
            attackUp1 = setup("/player/attack/PickAxe/boy_pick_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/attack/PickAxe/boy_pick_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/player/attack/PickAxe/boy_pick_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/attack/PickAxe/boy_pick_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/attack/PickAxe/boy_pick_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/attack/PickAxe/boy_pick_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/attack/PickAxe/boy_pick_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/player/attack/PickAxe/boy_pick_right_2", gp.tileSize*2, gp.tileSize);
        }

    }

    public void getGuardImage(){

        guardUp = setup("/player/Guard/boy_guard_up", gp.tileSize, gp.tileSize);
        guardDown = setup("/player/Guard/boy_guard_down", gp.tileSize, gp.tileSize);
        guardLeft = setup("/player/Guard/boy_guard_left", gp.tileSize, gp.tileSize);
        guardRight = setup("/player/Guard/boy_guard_right", gp.tileSize, gp.tileSize);
    }

    /**
     * Updates the player's state including position, direction, and animation.
     * It checks for user inputs to change the player's direction and position.
     * If there are no collisions detected, the player moves in the specified direction.
     * The method also handles the animation by switching between two sprites for each direction.
     */
    public void update(){

        if(knockBack){
            collision = false;
            gp.checker.checkTile(this);
            gp.checker.checkObject(this, true);
            gp.checker.checkEntity(this, gp.npc);
            gp.checker.checkEntity(this, gp.monster);
            gp.checker.checkEntity(this, gp.iTile);

            if(collisionOn){
                knockBackCounter = 0;
                knockBack = false;
                speed =  defaultSpeed;
            }else if(!collisionOn){
                switch(knockBackDirection){
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

        }
        else if(attacking){
            attacking();
        }
        else if(keyH.spacedPressed){
            guarding = true;
            guardCounter++;
        }
        else if (keyH.downPressed || keyH.leftPressed || keyH.upPressed || keyH.rightPressed || keyH.enterPressed) {
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
            guarding = false;
            guardCounter = 0;

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
        else{
            standCounter++;
            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
            guarding = false;
            guardCounter = 0;
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
                transparent = false;
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

        if(!keyH.godMode){
            if(life <= 0){
                gp.gameState = gp.gameOverState;
                gp.stopMusic();
                gp.playSE(14);
            }
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
                if(damage < 1){
                    damage = 1;
                }
                life -= damage;
                invincible = true;
                gp.player.transparent = true;


            }
        }
    }

    void damageInteractiveTile(int i) {

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

    public void damageMonster(int i, Entity attacker, int attack, int kbPower){
        if(i != 999) {
            if(!gp.monster[gp.currentMap][i].invincible){ // FIXED
                gp.playSE(6);

                if(kbPower > 0){
                    setKnockBack(gp.monster[gp.currentMap][i], attacker,  kbPower);
                }

                if(gp.monster[gp.currentMap][i].offBalance){
                    attack *= 5;
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
            dialogues[0][0] = "You are now on level "+ level;
            // or you can call set dialogue
            startDialogue(this,0);
        }
    }
    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
            }
            if(selectedItem.type  == type_light){
                if(currentLight == selectedItem){
                    currentLight = null;
                }
                else{
                    currentLight = selectedItem;
                }
                lightUpdated = true;
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

        if(i != 999) {

            if (keyH.enterPressed) {
                attackCancel = true;
                gp.npc[gp.currentMap][i].speak(); // FIXED
            }
            gp.npc[gp.currentMap][i].move(direction);
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

        Entity newItem = gp.eGenerator.getObject(item.name);
        // CHECK IF THE ITEM IS STACKABLE
        if(newItem.stackable){
            int index = searchItemInInventory(newItem.name);

            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            else{ // NEW ITEM SP NEED TO CHECK VACANCY
                if(inventory.size() != maxInventorySize){
                    inventory.add(newItem);
                    canObtain = true;
                }

            }
        }
        else{ // NOT STACKABLE SO CHECK ITEM VACANCY
            if(inventory.size() != maxInventorySize){
                inventory.add(newItem);
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
                if(guarding){
                    image = guardUp;
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
                if(guarding){
                    image = guardDown;
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
                if(guarding){
                    image = guardRight;
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
                if(guarding){
                    image = guardLeft;
                }
                break;
        }

        if(transparent){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .4F));
        }
        if(drawing){
            g2.drawImage(image, tempScreenX, tempScreenY,null);
        }
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

