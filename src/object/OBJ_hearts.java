package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_hearts extends Entity {

    GamePanel gp;
    public static final String objName = "Heart";


    public OBJ_hearts(GamePanel gp){

        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        name = objName;
        value = 2;
        down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);

    }

    public boolean use(Entity entity){
        gp.playSE(3);
        gp.ui.addMessage("Life +" + value);
        gp.player.life += value;
        return  true;
    }
}
