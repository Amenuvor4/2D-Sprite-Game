package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DataStorage implements Serializable {

    // PLAYER STATS
    int level;
    int maxLife;
    int life;
    int maxMana;
    int mana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;


    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;


    // OBJECTS ON MAP
    String[][] mapObjectNames;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;
    String[][] mapObjectLootNames;
    boolean[][] mapObjectOpened;


}
