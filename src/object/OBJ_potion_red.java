package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_potion_red extends Entity {

    GamePanel gp;
    public OBJ_potion_red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = "Red Potion";
        stackable = true;
        value = 2;
        price = 5;
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nThis Serum will help heal you up";
    }

    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n" +
                "Your have restored " + value + " health!";
        entity.life += value;
        gp.playSE(2);
        return  true;

    }
}
