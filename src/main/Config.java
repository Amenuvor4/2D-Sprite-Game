package main;

import java.io.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp){
        this.gp =gp;
    }

    public void saveConfig(){


        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
            // Full Screen
            if (gp.fullScreenOn) {
                bw.write("On");
            }

            if (!gp.fullScreenOn) {
                bw.write("Off");
            }
            bw.newLine();

            //MUSIC VOLUME
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            //SE VOLUME
            bw.write(String.valueOf(gp.soundEffect.volumeScale));
            bw.newLine();


            bw.close();


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            // FULL SCREEN
            String s = br.readLine();
            if (s.equals("On")) {
                gp.fullScreenOn = true;
            } else if (s.equals("Off")) {
                gp.fullScreenOn = false;
            }

            // MUSIC VOLUME
            s = br.readLine();  // Read the next line for music volume
            gp.music.volumeScale = Integer.parseInt(s);

            // SOUND EFFECT VOLUME
            s = br.readLine();  // Read the next line for sound effect volume
            gp.soundEffect.volumeScale = Integer.parseInt(s);

            br.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


}
