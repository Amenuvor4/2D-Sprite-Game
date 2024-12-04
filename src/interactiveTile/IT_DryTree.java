package interactiveTile;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_DryTree extends InteractiveTile {

    GamePanel gp;
    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize*row;



        down1 = setup("/interactive_Tile/drytree", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 3;
    }

    public void playSE(){
        gp.playSE(11);
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new IT_trunk(gp, worldX/ gp.tileSize, worldY/gp.tileSize);

        return tile;
    }

    public boolean isCorrectItem(Entity entity){
        boolean isCorrectedItem = false;
        if(entity.currentWeapon.type == type_axe){
            isCorrectedItem = true;
        }
        return isCorrectedItem;
    }


    public Color getParticleColor(){
        Color color = new Color(65,50,23);
        return color;
    }

    public int getParticleSize(){
        int size = 6;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}
