package main;

import entity.Entity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed , enterPressed, shotKeyPressed, spacedPressed;
    //DEBUG//DEBUG//DEBuG//DEBUG//DEBuG//DEBUG//DEBuG//DEBUG//DEBUG//DEBUG//DEBUG//DEBUG
    boolean checkDrawTime = false;
    public boolean godMode = false;
    public boolean teleport = false;
    //DEBUG//DEBUG//DEBUG//DEBUG//DEBUG//DEBUG//DEBUG//DEBUG//DEBUG//DEBUG//DEBUG//DEBUG
    public Entity npc;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
//GAME STATE KEY TOGGLES//GAME STATE KEY TOGGLES//GAME STATE KEY TOGGLES//GAME STATE KEY TOGGLES
        if(gp.gameState == gp.titleState) {titleState(code);}
        else if (gp.gameState == gp.playState) {playState(code);}
        else if (gp.gameState == gp.pauseState) {pauseState(code);}
        else if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutSceneState) {dialogueState(code);}
        else if(gp.gameState == gp.characterState) {characterState(code);}
        else if(gp.gameState == gp.optionState){optionsState(code);}
        else if(gp.gameState == gp.questState){questState(code);}
        else if(gp.gameState == gp.gameOverState){gameOverState(code);}
        else if(gp.gameState == gp.tradeState){tradeState(code);}
        else if(gp.gameState == gp.mapState){mapState(code);}
    }

    public void titleState(int code){
        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {gp.ui.commandNum = (gp.ui.commandNum-- <= 0) ? 2 : gp.ui.commandNum--;}
            if (code == KeyEvent.VK_S) {gp.ui.commandNum = (gp.ui.commandNum++ >= 2) ? 0 : gp.ui.commandNum++;}
            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum) {
                    case 0: gp.ui.titleScreenState = 1; break;
                    case 1: gp.saveLoad.load(); gp.gameState = gp.playState; gp.playMusic(0); break;
                    case 2: System.exit(0);}
            }}
        else if (gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {gp.ui.commandNum = (gp.ui.commandNum-- <= 0) ? 3 : gp.ui.commandNum--;}
            if (code == KeyEvent.VK_S) {gp.ui.commandNum = (gp.ui.commandNum++ >= 3) ? 0 : gp.ui.commandNum++;}
            if (code == KeyEvent.VK_ENTER) {
                gp.playMusic(0);
                switch (gp.ui.commandNum) {
                    //FIGHTER STUFF
                    case 0: gp.gameState = gp.playState; break;
                    //THIEF STUFF
                    case 1: gp.gameState = gp.playState; break;
                    //SORCERER STUFF
                    case 2: gp.gameState = gp.playState; break;
                    //RETURN TO MAIN SCREEN
                    case 3:
                        gp.ui.titleScreenState = 0;
                        gp.ui.commandNum = 0;
                        break;
                }
            }
        }
    }


    private boolean shiftPressed = false;

    public void playState(int code){
        if(code == KeyEvent.VK_SHIFT){shiftPressed = true;}
        if (code == KeyEvent.VK_W) {upPressed = true;}
        if (code == KeyEvent.VK_S) {downPressed = true;}
        if (code == KeyEvent.VK_A) {leftPressed = true;}
        if (code == KeyEvent.VK_D) {rightPressed = true;}
        if (code == KeyEvent.VK_P) {gp.gameState = gp.pauseState;}
        if (code == KeyEvent.VK_C) {gp.gameState = gp.characterState;}
        if (code == KeyEvent.VK_ENTER) {enterPressed = true;}
        if (code == KeyEvent.VK_F) {shotKeyPressed = true;}
        if (code == KeyEvent.VK_SPACE){spacedPressed = true;}
        if (code == KeyEvent.VK_ESCAPE) {gp.gameState = gp.optionState;}
        if (code == KeyEvent.VK_M){gp.gameState = gp.mapState;}
        if(code == KeyEvent.VK_Q){gp.gameState = gp.questState;}
        if (code == KeyEvent.VK_X){
            if(!gp.map.miniMapOn){gp.map.miniMapOn = true;}
            else{gp.map.miniMapOn = false;}
        }
        //DEBUG
        if  (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
        if(code == KeyEvent.VK_R){
            switch(gp.currentMap){
                case 0: gp.tileM.loadMap("/maps/tutorial_mapV2",0); break;
                case 1: gp.tileM.loadMap("/maps/interior01.txt",1); break;

            }
        }


        if (code == KeyEvent.VK_Q && shiftPressed) {
            teleport = true;
            System.out.println("Q is being pressed");
        }
        if (code == KeyEvent.VK_G){
            if(!godMode){
                godMode = true;
            } else if (godMode) {
                godMode = false;
            }
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {gp.gameState = gp.playState;}
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {enterPressed = true;}

        if (code == KeyEvent.VK_A) {gp.ui.commandNum = (gp.ui.commandNum-- <= 0) ? 1 : gp.ui.commandNum--;}
        if (code == KeyEvent.VK_D) {gp.ui.commandNum = (gp.ui.commandNum++ >= 1) ? 0 : gp.ui.commandNum++;}
    }

    public void characterState(int code) {
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }

        playerInventory(code);

    }
    public void optionsState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){enterPressed = true;}

        int maxCommandNum = 0;
        switch(gp.ui.subState){
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            gp.ui.commandNum--;
            gp.playSE(9);
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = maxCommandNum;
            }
        }

        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            gp.ui.commandNum++;
            gp.playSE(9);
            if(gp.ui.commandNum > maxCommandNum){
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum == 2 && gp.soundEffect.volumeScale > 0){
                    gp.soundEffect.volumeScale--;
                    gp.playSE(9);
                }

            }

        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum == 2 && gp.soundEffect.volumeScale < 5){
                    gp.soundEffect.volumeScale++;
                    gp.playSE(9);
                }
            }

        }

    }

    public void questState(int code) {
        // Exit the quest log
        if (code == KeyEvent.VK_Q) {
            gp.gameState = gp.playState;
        }

        if(code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
            gp.playSE(4);
        }

        // Navigate up the quest list
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = gp.player.quests.size() - 1; // Wrap to the bottom
            }
            gp.playSE(9); // Optional sound effect
        }

        // Navigate down the quest list
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum >= gp.player.quests.size()) {
                gp.ui.commandNum = 0; // Wrap to the top
            }
            gp.playSE(9); // Optional sound effect
        }
    }



    public void gameOverState(int code){
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 1;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            gp.ui.commandNum++;
            if(gp.ui.commandNum >1){
                gp.ui.commandNum = 0;
            }
            gp.playSE(9);
        }

        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.resetGame(false);
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            else if(gp.ui.commandNum == 1){
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.titleState;
                gp.stopMusic();
                gp.resetGame(true);
            }
        }
    }


    public void tradeState(int code){
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        if(gp.ui.subState == 0){
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;

                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }

                gp.playSE(9);
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum =0;
                }
            }
        }

        if(gp.ui.subState == 1){
            npcInventory(code);

            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
        if(gp.ui.subState == 2){
            playerInventory(code);

            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
    }


    public void mapState(int code){

        if(code == KeyEvent.VK_M){
            gp.gameState = gp.playState;
        }
    }

    public void playerInventory(int code){
        if(code == KeyEvent.VK_W){
            if(gp.ui.playerSlotRow != 0){
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }
    }


    public void npcInventory(int code){
        if(code == KeyEvent.VK_W){
            if(gp.ui.npcSlotRow != 0){
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){upPressed = false;}
        if(code == KeyEvent.VK_S){downPressed = false;}
        if(code == KeyEvent.VK_A){leftPressed = false;}
        if(code == KeyEvent.VK_D){rightPressed = false;}
        if(code == KeyEvent.VK_F){shotKeyPressed = false;}
        if(code == KeyEvent.VK_ENTER){enterPressed = false;}
        if(code == KeyEvent.VK_SPACE){spacedPressed = false;}
        if(code == KeyEvent.VK_SHIFT){shiftPressed = false;}
    }

    //UNNEEDED METHOD
    public void keyTyped(KeyEvent e) {}

}



/**/