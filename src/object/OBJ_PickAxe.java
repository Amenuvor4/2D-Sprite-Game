package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_PickAxe extends Entity {

    public static final String objName = "PickAxe";

    public OBJ_PickAxe(GamePanel gp) {
        super(gp);
        type = type_pickaxe;
        name = objName;
        down1 = setup("/objects/pickaxe", gp.tileSize, gp.tileSize);
        attackValue = (int)2.5;
        description = "[" + name + "]\nThis PickAxe is a basic one but will come in\n handy when mining";
        attackArea.width = 35;
        attackArea.height = 35;
        price = 65;
        knockBackPower = 5;
        motion1_direction = 10;
        motion2_direction = 20;
    }
}
