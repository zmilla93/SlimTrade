package com.slimtrade.core.managers;

import com.slimtrade.core.audio.Sound;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AudioManager {

    public ArrayList<Sound> soundfiles = new ArrayList<>();
    public static final int MIN_VOLUME = -30;
    public static final int MAX_VOLUME = 6;
    public static final int RANGE = Math.abs(MIN_VOLUME) + MAX_VOLUME;

    AudioFormat defaultAudioFormat;

    public AudioManager() {
        rebuildSoundList();
    }

    public void rebuildSoundList() {
        soundfiles = new ArrayList<>();
        addDefaultSoundFiles();
        addCustomSoundFiles();
    }

    private void addDefaultSoundFiles() {
        soundfiles.add(new Sound("Ping 1", "audio/ping1.wav", Sound.SoundType.INBUILT));
        soundfiles.add(new Sound("Ping 2", "audio/ping2.wav", Sound.SoundType.INBUILT));
        soundfiles.add(new Sound("Blip 1", "audio/blip1.wav", Sound.SoundType.INBUILT));
        soundfiles.add(new Sound("Blip 2", "audio/blip2.wav", Sound.SoundType.INBUILT));
        soundfiles.add(new Sound("Blip 3", "audio/blip3.wav", Sound.SoundType.INBUILT));
        soundfiles.add(new Sound("m1 3", "currency.wav", Sound.SoundType.CUSTOM));
        soundfiles.add(new Sound("m2 3", "maps.wav", Sound.SoundType.CUSTOM));
    }

    private void addCustomSoundFiles() {
        File audioDir = new File(SaveManager.getAudioDirectory());
        if (audioDir.exists()) {

        }
    }

    public void playSoundMP3(Sound sound, float volume) {
        BasicPlayer player = new BasicPlayer();
        try {
            player.open(sound.getURL());
            player.play();
//                player.setGain(volume);
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }


    public void playSoundRaw(Sound sound, float volume) {
        if (volume <= MIN_VOLUME) {
            return;
        }
        if (volume > MAX_VOLUME) {
            volume = MAX_VOLUME;
        }
        Clip clip = null;
        AudioInputStream stream = null;
        try {
            clip = AudioSystem.getClip();
            URL url = sound.getURL();
            stream = AudioSystem.getAudioInputStream(url);
            clip.open(stream);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
            if (defaultAudioFormat == null) {
                defaultAudioFormat = stream.getFormat();
            }
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        assert clip != null;
        clip.start();
        Clip finalClip = clip;
        AudioInputStream finalStream = stream;
        clip.addLineListener(event -> {
            LineEvent.Type type = event.getType();
            if (type.equals(LineEvent.Type.STOP)) {
                System.out.println("stopped clip");
                event.getLine();
                finalClip.stop();
                finalClip.close();
                if (finalStream != null) {
                    try {
                        finalStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static float percentToRange(int percent) {
        float f = MIN_VOLUME + (RANGE / (float) 100 * percent);
        return f;
    }

    public static byte[] mp3ToWav(byte[] sourceBytes, AudioFormat audioFormat) throws UnsupportedAudioFileException, IllegalArgumentException, Exception {
        if (sourceBytes == null || sourceBytes.length == 0 || audioFormat == null) {
            throw new IllegalArgumentException("Illegal Argument passed to this method");
        }

        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        AudioInputStream sourceAIS = null;
        AudioInputStream convert1AIS = null;
        AudioInputStream convert2AIS = null;

        try {
            bais = new ByteArrayInputStream(sourceBytes);
            sourceAIS = AudioSystem.getAudioInputStream(bais);
            AudioFormat sourceFormat = sourceAIS.getFormat();
            AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(), sourceFormat.getChannels() * 2, sourceFormat.getSampleRate(), false);
            convert1AIS = AudioSystem.getAudioInputStream(convertFormat, sourceAIS);
            convert2AIS = AudioSystem.getAudioInputStream(audioFormat, convert1AIS);

            baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[8192];
            while (true) {
                int readCount = convert2AIS.read(buffer, 0, buffer.length);
                if (readCount == -1) {
                    break;
                }
                baos.write(buffer, 0, readCount);
            }
            return baos.toByteArray();
        } catch (UnsupportedAudioFileException uafe) {
            //uafe.printStackTrace();
            throw uafe;
        } catch (IOException ioe) {
            //ioe.printStackTrace();
            throw ioe;
        } catch (IllegalArgumentException iae) {
            //iae.printStackTrace();
            throw iae;
        } catch (Exception e) {
            //e.printStackTrace();
            throw e;
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                }
            }
            if (convert2AIS != null) {
                try {
                    convert2AIS.close();
                } catch (Exception e) {
                }
            }
            if (convert1AIS != null) {
                try {
                    convert1AIS.close();
                } catch (Exception e) {
                }
            }
            if (sourceAIS != null) {
                try {
                    sourceAIS.close();
                } catch (Exception e) {
                }
            }
            if (bais != null) {
                try {
                    bais.close();
                } catch (Exception e) {
                }
            }
        }
    }

}
