package application;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class SoundManager {
    Thread sound_thread;
    Clip clip;
    Player player;
    boolean type_mp3 = false;
    boolean type_wav = false;

    public void PlaySound(String soundFilePath) {
        Player_And_Thread_Stop();
        sound_thread = new Thread() {
            public void run() {
                if (check_path(soundFilePath) == true) {
                    if (soundFilePath.toLowerCase().endsWith("mp3")) {
                        mp3_player(soundFilePath);
                    }
                    if (soundFilePath.toLowerCase().endsWith("wav")) {
                        wav_player(soundFilePath);
                    }
                }
            }


        };

        sound_thread.start();

    }

    private boolean check_path(String soundFilePath) {
        File f = new File(soundFilePath);
        if (f.exists()) {
            return true;
        }
        return false;
    }

    private void mp3_player(String soundFilePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(soundFilePath);
            player = new Player(fileInputStream);
            player.play();
            type_mp3 = true;
            type_wav = false;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JavaLayerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void Player_And_Thread_Stop() {
        try {
            if(type_wav) {
                clip.close();
                sound_thread.interrupt();
            }
            if(type_mp3){
                player.close();
                sound_thread.interrupt();
            }
        }
        catch(Exception e){}
    }

    private void wav_player(String soundFilePath) {
        File Clap = new File(soundFilePath);
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Clap));
            clip.start();
            type_wav = true;
            type_mp3 = false;
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {}
    }


    public double Sound_Time(String soundFilePath) {
        if (check_path(soundFilePath) == true) {
            if (soundFilePath.toLowerCase().endsWith("wav")) {
                return duration_wav(soundFilePath);
            }
            if (soundFilePath.toLowerCase().endsWith("mp3")) {
                return duration_mp3(soundFilePath);
            }
        }
        return 0.0;
    }

    private double duration_wav(String soundFilePath) {
        File Clap = new File(soundFilePath);
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Clap));
            return clip.getMicrosecondLength() / 1000000;
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private double duration_mp3(String soundFilePath) {
        double duration = 0;
        File file = new File(soundFilePath);

        AudioFile audioFile = null;
        try {
            audioFile = AudioFileIO.read(file);
        } catch (CannotReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }
        duration = audioFile.getAudioHeader().getTrackLength();
        return duration;
    }
}