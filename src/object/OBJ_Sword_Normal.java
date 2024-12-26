package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public static final String objName = "Normal Sword";

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = objName;
        down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 1;
        description = "[" + name+ "]\nAn old sword";
        attackArea.width = 36;
        attackArea.height = 36;
        price = 10;
        knockBackPower = 3;
        motion1_direction = 5;
        motion2_direction = 25;

    }
}

