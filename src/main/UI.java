package main;
/*
 * This class handles the user interaction aspects of the game, such as displaying text
 * when the character interacts with other NPCs or game objects.
 */

import Quests.Quest1;
import entity.Entity;
import entity.OldMan_NPC;
import object.OBJ_Coin_Bronze;
import object.OBJ_ManaCrystal;
import object.OBJ_hearts;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {


    //DEBUGING
    boolean displaying;

    // Reference to the main GamePanel, which holds the game's state
    GamePanel gp;

    Graphics2D g2;

    // Font to be used for displaying UI text
    public Font maruMonica;
    Font purisaB;
    // BufferedImage keyImage;
    BufferedImage heart_full, heart_half, heart_empty, crystal_full, crystal_blank, coin;


    public boolean messageOn = false;
    //public String message = "";
    //int messageCounter = 0;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public boolean gameFinished = false;
    public boolean declinedQuest;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    public int subState = 0;
    int counter = 0;
    public Entity npc;
    public int charIndex = 0;
    String combinedText = "";


    double playTime;

    DecimalFormat dFormat = new DecimalFormat("#0.00");

    // Constructor initializes the UI with a reference to the GamePanel and sets up the font
    public UI(GamePanel gp){
        this.gp = gp; // Use the passed GamePanel instance instead of creating a new one
        // Initialize the font once in the constructor

        try{
            InputStream is =  getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FontFormatException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        // CREATE HUD OBJECT
        Entity heart = new OBJ_hearts(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;
        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;
    }
    public void addMessage(String text){
        /*message = text;
        messageOn = true;*/

        message.add(text);
        messageCounter.add(0);
    }
    // Method to draw UI elements on the screen
    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);


        // TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //PLAY STATE
        if(gp.gameState == gp.playState){
            //Play state stuff
            drawPlayerLife();
            drawMonsterLife();
            drawMessage();
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPausedScreen();
        }
        // DIALOG STATE
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogScreen();

        }

        // CHARACTER STATE
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }

        // OPTIONS STATE
        if(gp.gameState == gp.optionState){
            drawOptionsScreen();
        }

        // QUEST STATE
        if(gp.gameState == gp.questState){
            drawQuestScreen(gp.player, true);
        }

        // GAME OVER STATE
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }


        // TRANSITION STATE
        if (gp.gameState == gp.transitionState){
            drawTransition();
        }
        // TRADE STATE
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }

        // SLEEP STATE
        if(gp.gameState == gp.sleepState){
            drawSleepScreen();
        }

    }
    public void drawMonsterLife(){
        //MONSTER HEALTH
        for(int i =0; i < gp.monster[1].length; i++) {
            Entity monster = gp.monster[gp.currentMap][i];
            if (monster != null && monster.inCamera()) {
                if (monster.hpBarOn && !monster.boss) {

                    double oneScale = (double) gp.tileSize / monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    g2.setColor(Color.darkGray);
                    g2.fillRect(monster.getScreenX() - 1, monster.getScreenY() - 16, gp.tileSize + 2, 12);
                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int) hpBarValue, 10);

                    monster.hpBarCounter++;
                    if (monster.hpBarCounter > 600) {
                        monster.hpBarCounter = 0;
                        monster.hpBarOn = false;
                    }
                }
                else if(monster.boss){
                    double oneScale = (double) gp.tileSize*8 / monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    int x = gp.screenWidth/2 - gp.tileSize*4;
                    int y = gp.tileSize*10;
                    g2.setColor(Color.darkGray);
                    g2.fillRect(x - 1, y - 1, gp.tileSize*8 + 2, 22);
                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int) hpBarValue, 20);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD,24f));
                    g2.setColor(Color.white);
                    g2.drawString(monster.name, x+4, y-10);

                }
            }
        }
    }
    public void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for(int i =0; i< message.size(); i++){
            if(message.get(i) != null){

                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+3, messageY+3);

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // messageCounter++;
                messageCounter.set(i, counter);// Set counter to the array
                messageY += 50;


                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    public void drawCharacterScreen(){
        // WINDOW COMPONENTS
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*6;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        //TEXT
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(30F));

        int textX = frameX + 10;
        int textY = frameY + gp.tileSize;
        final int lineHeight = frameHeight/13;

        //NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coins", textX, textY);
        textY += lineHeight;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight;
        g2.drawString("Shield", textX, textY);

        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        String value = String.valueOf(gp.player.level);
        textX = getXForAlignToRightText(value,tailX);
        textY = frameY + gp.tileSize;
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXForAlignToRightText(value,tailX);
        textY += lineHeight;
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXForAlignToRightText(value,tailX);
        textY += lineHeight;
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.attack);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.defense);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2.drawString(value, textX, textY);


        value = String.valueOf(gp.player.coin);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2.drawString(value, textX, textY);

        g2.drawImage(gp.player.currentWeapon.down1, tailX-gp.tileSize+10, textY, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX-gp.tileSize+10, textY, null);



    }
    public void drawPausedScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));
        String text = "Paused";
        int x = getXForCenter(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);

        y += gp.tileSize;
        // OPTIONS MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60));
        text = "Options:";
        y += gp.tileSize;




    }
    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y =gp.tileSize/2;
        int i = 0;

        // DRAW MAX LIFE
        while(i<gp.player.maxLife/2){
            g2.drawImage(heart_empty, x, y, null);
            i++;
            x += gp.tileSize;
        }
        // RESET
        x = gp.tileSize/2;
        y =gp.tileSize/2;
        i = 0;

        // DRAW CURRENT LIFE
        while(i<gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

        // DRAW MAX MANA
        x = gp.tileSize/2-5;
        y = (int) (gp.tileSize*1.5);
        i = 0;

        while(i<gp.player.maxMana){
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x+= (int) (gp.tileSize/1.3);
        }
        x = gp.tileSize/2-5;
        y = (int) (gp.tileSize*1.5);
        i = 0;
        while(i< gp.player.mana){
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x+= (int) (gp.tileSize/1.3);
        }



    }
    public void drawTitleScreen() {
        if (titleScreenState == 0) {

            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Blue Boy Adventure";
            int x = getXForCenter(text);
            int y = gp.tileSize * 3;

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + 5);

            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // BLUE BOY IMAGE
            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            // MAIN MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x = getXForCenter(text);
            y += (int) (gp.tileSize * 3.5);
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }

        if (titleScreenState == 1) {
            // CLASS SELECTION SCREEN
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class!";
            int x = getXForCenter(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getXForCenter(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Thief";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Sorcerer";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Back";
            x = getXForCenter(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }
    public void drawDialogScreen(){
        // WINDOW COMPONENTS
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - gp.tileSize*4;
        int height = gp.tileSize*4;
        drawSubWindow(x,y,width,height);



        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32));
        x += gp.tileSize;
        y += gp.tileSize;
        int questX = x;
        int questY = y + gp.tileSize*2;

        if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null){
            npc.currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];

            char[] characters = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();

            if(charIndex < characters.length){
                // ADD SOUND HERE
                gp.playSE(21);
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;
                charIndex++;
            }
            if(gp.keyH.enterPressed && !npc.questDialogues.contains(npc.currentDialogue)){
                charIndex = 0;
                combinedText = "";
                if(gp.gameState == gp.dialogueState || gp.gameState == gp.cutSceneState){
                    npc.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
            if(npc.questDialogues.contains(npc.currentDialogue)){
                String text = "Accept Quest";
                g2.drawString(text, questX, questY);
                if(commandNum == 0){
                    g2.drawString(">",  questX-20, questY);
                    if(gp.keyH.enterPressed){
                        charIndex = 0;
                        combinedText = "";
                        gp.player.quests.add(new Quest1(gp));
                        gp.player.currentQuest = gp.player.quests.get(0);
                        npc.dialogueIndex++;
                        gp.keyH.enterPressed = false;

                    }
                }

                text = "Decline Quest";
                questX += questX*2 - questX/2;
                g2.drawString(text, questX, questY);
                if(commandNum == 1){
                    g2.drawString(">", questX-20, questY);
                    if(gp.keyH.enterPressed){
                        declinedQuest = true;
                        npc.setDialogue();
                        charIndex = 0;
                        combinedText = "";
                        npc.dialogueIndex++;
                        gp.keyH.enterPressed = false;
                        declinedQuest = false;
                    }


                }

            }
        }
        else{
            // If no text is in the array
            npc.dialogueIndex = 0;
            if(gp.gameState == gp.dialogueState){
                gp.gameState = gp.playState;
            }
            if(gp.gameState == gp.cutSceneState){
                gp.csManager.scenePhase++;
            }
        }


        /* Splits the text at the keyword "\n". After that we draw the new line inside
        the dialog bar in which the position goes towards the middle of the screen by 40 frames */
        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }

    }
    public void drawInventory( Entity entity, boolean cursor){
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(entity == gp.player){
            //FRAME
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;

        } else{
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;

        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);


        //SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 3;

        //DRAW PLAYER ITEMS
        for (int i = 0; i < entity.inventory.size(); i++) {

            //EQUIP CURSOR
            if(entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield ||
                    entity.inventory.get(i) == entity.currentLight) {
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            // Debug
            //System.out.println("Inventory: " + entity.inventory);

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            //DISPLAY AMOUNT
            if(entity.equals(gp.player) && entity.inventory.get(i).amount > 1){
                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX;
                int amountY;

                String s = "" + entity.inventory.get(i).amount;
                amountX = getXForAlignToRightText(s, slotX+44);
                amountY = slotY + gp.tileSize;

                //SHADOW
                g2.setColor(new Color(60,60,60));
                g2.drawString(s, amountX, amountY);

                //NUMBER
                g2.setColor(Color.white);
                g2.drawString(s, amountX-3, amountY-3);
            }

            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }



        // CURSOR
        if(cursor){
            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;


            //DRAW CURSOR
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3.0f));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);


            // DESCRIPTION FRAME
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3 ;


            // DESCRIPTION TEXT
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(22F));
            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            // DRAW PLAYER'S ITEMS
            if(itemIndex < entity.inventory.size()){

                drawSubWindow(dFrameX, dFrameY, dFrameWidth,dFrameHeight);
                for(String line : entity.inventory.get(itemIndex).description.split("\n")){

                    g2.drawString(line, textX, textY);

                    textY += 32;
                }
            }

        }

    }


    public void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0, 150) );
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);


        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over";
        g2.setColor(Color.black);
        x = getXForCenter(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);

        // MAIN
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        //RESPAWN
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Respawn";
        x = getXForCenter(text);
        y += gp.tileSize*4;
        g2.drawString(text,x,y);
        if(commandNum == 0){
            g2.drawString(">", x-40, y);
        }



        // GO BACK TO THE TITLE SCREEN
        text = "Quit Game";
        y += gp.tileSize+7;
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.drawString(">", x-40, y);

        }
    }


    public void drawQuestScreen(Entity entity, boolean cursor){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int frameX = gp.tileSize;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*9;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        int textX = frameX + 28;
        int textY = frameY + gp.tileSize;
        final int lineHeight = frameHeight/13;
        String text =  "You currently have no quests!";
        if(entity.quests.isEmpty()){
            g2.drawString(text, textX, textY);

        }
        else{
            //TEXT
            g2.setFont(g2.getFont().deriveFont(32F));
            int startX = textX + 40;
            int startY = textY + 30;
            //SLOT
            final int slotXStart = frameX + 20;
            final int slotYStart = frameY + 20;
            int slotX =startX-45;;
            int slotY =  textX + 25;;
            int slotSize = gp.tileSize -20;

            for(int i = 0; i< entity.quests.size(); i++){
                Entity quest = entity.quests.get(i);


                if(quest == gp.player.currentQuest){
                    g2.setColor(new Color(240,190,90));
                    g2.fillRoundRect(slotX, slotY, slotSize, slotSize, 10, 10);
                }
                // Highlight Rectangle for teh Selected Quest
                if(commandNum == i){
                    int highlightX = slotXStart - 10; // Rectangle X position
                    int highlightY = startY - lineHeight ; // Rectangle Y position
                    int highlightWidth = frameWidth - 20; // Rectangle width
                    int highlightHeight = lineHeight + 15; // Rectangle height

                    // Draw Highlight Rectangle
                    g2.setColor(new Color(255, 255, 255, 100)); // Semi-transparent white
                    g2.fillRoundRect(highlightX, highlightY, highlightWidth, highlightHeight, 10, 10);




                }
                //Draw the quest name
                g2.setColor(Color.white);
                g2.drawString(quest.name, startX, startY);


                startY += lineHeight;
            }
            if(cursor){
                int cursorX = startX-45;
                int cursorY = textX + 25;



                //DRAW CURSOR
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(3.0f));
                g2.drawRoundRect(cursorX, cursorY, slotSize, slotSize, 10, 10);

                // DESCRIPTOR FRAME
                int dFrameX = gp.tileSize * 10;
                int dFrameY = frameY;
                int dFrameWidth = gp.tileSize * 9;
                int dFrameHeight = gp.tileSize * 10;
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

                // I want to add the image above the description.

                // Difficulty
                int dTextX = dFrameX + 20;
                int dTextY = (int) (dFrameY + gp.tileSize * 5.25);



                // DESCRIPTOR IMAGE
                int imageX = dFrameX + (dFrameWidth - gp.tileSize * 7) / 2; // Center the image
                int imageY = dFrameY + 20; // Position the image at the top
                int imageWidth = gp.tileSize * 7;
                int imageHeight = gp.tileSize * 4;


                // DESCRIPTION TEXT
                int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);

                if (itemIndex >= 0 && itemIndex < entity.quests.size()) {
                    Entity currentQuest = entity.quests.get(itemIndex);

                    // Draw the quest image
                    Image questImage = currentQuest.image;
                    if (questImage != null) {
                        g2.drawImage(questImage, imageX, imageY, imageWidth, imageHeight, null);
                    }

                    // Draw the difficulty text with color
                    g2.setFont(g2.getFont().deriveFont(32F));
                    g2.drawString("Difficulty: ", dTextX, dTextY);
                    g2.setColor(getDifficultyColor(currentQuest.difficulty));
                    dTextX += 125;
                    g2.drawString(currentQuest.difficulty, dTextX, dTextY);

                    // Draw the description
                    dTextX = dFrameX + 20;
                    dTextY = dFrameY + gp.tileSize * 6;
                    g2.setColor(Color.white);
                    g2.setFont(g2.getFont().deriveFont(22F));

                    for (String line : currentQuest.description.split("\n")) {
                        g2.drawString(line, dTextX, dTextY);
                        dTextY += 32;

                    }

                }


            }


        }

    }

    // Helper function to map difficulty levels to colors
    private Color getDifficultyColor(String difficulty) {
        switch (difficulty) {
            case "Very Easy": return new Color(0, 120, 0); // Dark green
            case "Easy": return new Color(100, 200, 90); // Light green
            case "Moderate": return new Color(180, 240, 50); // Green-yellow
            case "Intermediate": return new Color(240, 240, 0); // Yellow
            case "Challenging": return new Color(255, 200, 0); // Orange-yellow
            case "Hard": return new Color(255, 140, 0); // Orange
            case "Very Hard": return new Color(255, 80, 0); // Reddish-orange
            case "Extremely Hard": return new Color(255, 40, 0); // Red
            case "Insanely Hard": return new Color(150, 0, 0); // Dark red
            default: return Color.BLACK; // Unknown
        }
    }

    public void drawOptionsScreen(){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        //SUB WINDOW

        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*9 -15;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);


        switch(subState){
            case 0: options_top(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY); break;
            case 2: options_control(frameX, frameY); break;
            case 3: options_endGame(frameX, frameY); break;
        }

    }
    public void options_top(int frameX, int frameY){

        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXForCenter(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        // FULL SCREEN ON/OFF
        text = "Full Screen";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState = 1;
                commandNum = 0;
                if(!gp.fullScreenOn){
                    gp.fullScreenOn = true;
                } else{
                    gp.fullScreenOn = false;
                }
                gp.keyH.enterPressed = false;
            }
        }


        //MUSIC
        text = "Music";
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX-25, textY);
        }



        // SOUND EFFECTS
        text = "Sound Effects";
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 2){
            g2.drawString(">", textX-25, textY);
        }


        // CONTROL
        text = "Controls";
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState = 2;
                commandNum = 0;
                gp.keyH.enterPressed = false;
            }
        }


        //END GAME
        text = "Exit";
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 4){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState = 3;
                commandNum = 0;
                gp.keyH.enterPressed = false;
            }
        }

        // GO BACK
        text = "Back";
        textY += gp.tileSize*2;
        g2.drawString(text, textX, textY);
        if(commandNum == 5){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                gp.gameState = gp.playState;
                gp.keyH.enterPressed = false;
            }
        }


        // FULL SCREEN CHECK BOX
        textX = frameX + gp.tileSize*7;
        textY = (int)((frameY + gp.tileSize)*1.27);
        g2.setStroke(new BasicStroke());
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn){
            g2.fillRect(textX, textY, 24,24 );
        }else{
            g2.clearRect(textX, textY, 24,24 );
        }

        //MUSIC VOLUME
        textX = frameX + gp.tileSize*5;
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);  // 120/5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        // SOUND EFFECT VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.soundEffect.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);



        gp.config.saveConfig();
    }
    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "The change will take \neffect after restarting \nthe game.";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // BACK OPTION
        textY = frameY + gp.tileSize * 8;
        g2.drawString("Back", textX, textY);

        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed) {
                subState = 0;
                gp.keyH.enterPressed = false;
                gp.resetGame(true);
            }
        }
    }
    public void options_endGame(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "Quit the game and return\n to the title screen?";

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // YES
        textY -= 40;
        String text =  "Yes";
        textX = getXForCenter(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                titleScreenState = 0;
                gp.gameState = gp.titleState;
                gp.stopMusic();
                gp.keyH.enterPressed = false;
            }
        }

        // NO
        text =  "No";
        textX = getXForCenter(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                commandNum = 4;
                gp.keyH.enterPressed = false;
            }
        }
    }
    public void options_control(int frameX, int frameY){
        int textX;
        int textY;

        //String
        String text = "Control";
        textX = getXForCenter(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY);

        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY);

        textX = frameX + gp.tileSize;
        textY += (int) (gp.tileSize * 1.2);
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                gp.keyH.enterPressed = false;
            }

        }

    }
    public void drawTransition(){
        counter++;
        g2.setColor(new Color(0,0,0, counter*5));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        if (counter == 50){
            counter = 0;

            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;

            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;

            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
            gp.changeArea();
        }
    }
    public void drawTradeScreen(){
        switch (subState) {
            case 0:
                tradeSelect();
                break;
            case 1:
                tradeBuy();
                break;
            case 2:
                tradeSell();
                break;
        }

        gp.keyH.enterPressed = false;
    }
    public void tradeSelect() {

        npc.dialogueSet = 0;
        drawDialogScreen();

        //Draw Window
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int) (gp.tileSize * 3.5);

        drawSubWindow(x, y, width, height);

        //DRAW TEXTS
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);

        if(commandNum == 0){
            g2.drawString(">", x-24, y);

            if(gp.keyH.enterPressed){
                subState = 1;
            }
        }

        y += gp.tileSize;
        g2.drawString("Sell", x, y);

        if( commandNum == 1){
            g2.drawString(">",x-24, y);

            if(gp.keyH.enterPressed){
                subState = 2;
            }
        }

        y += gp.tileSize;

        g2.drawString("Leave", x, y);

        if(commandNum == 2){
            g2.drawString(">", x-24, y);

            if(gp.keyH.enterPressed){
                commandNum = 0;
                npc.startDialogue(npc, 1);
            }
        }
    }
    public void tradeBuy() {
        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, false);

        //DRAW NPC INVENTORY
        drawInventory(npc, true);

        // DRAW HINT WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x+24, y+60);

        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;

        drawSubWindow(x,y,width, height);
        g2.drawString("Your coins: " + gp.player.coin, x+24, y + 60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            x = (int) (gp.tileSize * 5.9);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;

            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x+10, y+8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXForAlignToRightText(text, (gp.tileSize*8) - 40);
            g2.drawString(text, x, y + 34);

            //BUY AN ITEM
            if (gp.keyH.enterPressed){
                if(npc.inventory.get(itemIndex).price > gp.player.coin){
                    subState = 0;
                    npc.startDialogue(npc,2);
                } else{
                    if(gp.player.canObtainItem(npc.inventory.get(itemIndex))){
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                    }
                    else{
                        subState = 0;
                        npc.startDialogue(npc, 3);}
                }
            }
        }

    }
    public void tradeSell(){
        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, true);

        // DRAW HINT WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width =  gp.tileSize * 6;
        int height = gp.tileSize * 2;

        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x+24, y+60);

        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()){
            x = (int) (gp.tileSize * 15.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;

            drawSubWindow(x,y,width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price /2;
            String text = "" + price;
            x = getXForAlignToRightText(text, gp.tileSize * 18 -20);
            g2.drawString(text, x, y+34);

            // SELL AN ITEM
            if (gp.keyH.enterPressed){
                if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon
                        || gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                    commandNum = 0;
                    subState = 0;
                    npc.startDialogue(npc,4);
                    } else {
                    if(gp.player.inventory.get(itemIndex).amount > 1){
                        gp.player.inventory.get(itemIndex).amount--;
                    }else{
                        gp.player.inventory.remove(itemIndex);

                    }
                    gp.player.coin += price;
                }
            }
        }
    }
    public void drawSleepScreen(){
        counter++;

        if(counter < 120){
            gp.eManager.lighting.filterAlpha += 0.01f;
            if(gp.eManager.lighting.filterAlpha > 1f){
                gp.eManager.lighting.filterAlpha = 1f;
            }
        }

        if(counter >= 120 ){
            gp.eManager.lighting.filterAlpha -= 0.01f;
            if(gp.eManager.lighting.filterAlpha <= 0){
                gp.eManager.lighting.filterAlpha = 0;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                gp.eManager.lighting.dayCounter = 0;
                gp.gameState = gp.playState;
                gp.player.getImage();
            }

        }
    }
    public int getItemIndexOnSlot(int slotCol, int slotRow){
        int itemIndex = slotCol + (slotRow*5);
        return itemIndex;
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height, 35,35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10, height-10, 25, 25);
    }
    public int getXForCenter(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;

        return x;
    }
    public int getXForAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}

