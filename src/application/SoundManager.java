package application;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SoundManager {

    public void PlaySound(String soundFilePath) {
        if(check_path(soundFilePath) == true) {
            if (soundFilePath.toLowerCase().endsWith("mp3")) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(soundFilePath);
                    Player player = new Player(fileInputStream);
                    System.out.println("Song is playing...");
                    player.play();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JavaLayerException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (soundFilePath.toLowerCase().endsWith("wav")) {
                File Clap = new File(soundFilePath);
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(Clap));
                    clip.start();
                    Thread.sleep(clip.getMicrosecondLength()/1000);
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean check_path(String soundFilePath){
        File f = new File(soundFilePath);
        if(f.exists()) {
            return true;
        }
        return false;
    }
}