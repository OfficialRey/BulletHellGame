package sound;

import util.Util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class AudioClip {
    private final String audioFilePath;
    private final Clip audioClip;

    public AudioClip(String audioFilePath) {
        this.audioFilePath = audioFilePath;
        try {
            File file = new File(audioFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file.toURI().toURL());
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            audioClip.addLineListener(new ClipListener());
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setVolume(float volume) {
        FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(Util.dbToVolume(volume));
    }

    private void resetClip() {
        audioClip.setFramePosition(0);
    }

    public void play(float volume) {
        if (!audioClip.isRunning()) {
            setVolume(volume);
            audioClip.start();
            return;
        }
        AudioClip clip = new AudioClip(audioFilePath);
        clip.play(volume);
    }

    public void loop(float volume) {
        setVolume(volume);
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    private AudioClip copy() {
        return new AudioClip(audioFilePath);
    }

    private class ClipListener implements LineListener {

        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.STOP) {
                resetClip();
            }
        }
    }
}