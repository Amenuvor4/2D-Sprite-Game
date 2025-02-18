package entity;

import main.GamePanel;
import object.*;

import java.awt.*;

public class Merchant_NPC extends Entity{

    public Merchant_NPC(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getImage();
        setDialogue();
        setItems();
    }



    public void getImage(){
        up1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue(){
        dialogues[0][0] = "You found me! He He, I have some good stuff. \n Do you want to trade?";
        dialogues[1][0] = "Come again, hehe!";
        dialogues[2][0] = "You need more coins to buy this";
        dialogues[3][0] = "You cannot carry any more!";
        dialogues[4][0] = "You cannot sell an equipped item!";
    }


    public void setItems(){
        inventory.add(new OBJ_potion_red(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
    }


    public void speak(){
        facePlayer();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }

}
