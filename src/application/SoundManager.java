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
                mp3_player(soundFilePath);
            }
            if (soundFilePath.toLowerCase().endsWith("wav")) {
                wav_player(soundFilePath);
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

    private void mp3_player(String soundFilePath){
        try {
            FileInputStream fileInputStream = new FileInputStream(soundFilePath);
            Player player = new Player(fileInputStream);
           // System.out.println("Song is playing...");
            player.play();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JavaLayerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void wav_player(String soundFilePath){
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




    public double sound_time(String soundFilePath) {
        if (check_path(soundFilePath) == true) {
            if (soundFilePath.toLowerCase().endsWith("wav")) {
                duration_wav(soundFilePath);
            }
            if (soundFilePath.toLowerCase().endsWith("mp3")) {
                duration_mp3(soundFilePath);
            }
        }
        return 0.0;
    }

    private double duration_wav(String soundFilePath) {
        File Clap = new File(soundFilePath);
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Clap));
            return clip.getMicrosecondLength();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private double duration_mp3(String soundFilePath){
        try {
            FileInputStream fileInputStream = new FileInputStream(soundFilePath);       //problem> prehravanie mp3 aj ked len zistujem dlzku???
            Player player = new Player(fileInputStream);
            long startTime = System.currentTimeMillis();
            player.play();
            long estimatedTime = System.currentTimeMillis() - startTime;
            double elapsedTimeInSecond = (double) estimatedTime / 1_000_000_000;
            return estimatedTime;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JavaLayerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0.0;
    }

}