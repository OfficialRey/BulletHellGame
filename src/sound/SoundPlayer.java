package sound;

import java.util.Random;

public class SoundPlayer {

    private static final Random random = new Random();

    public enum Sound {
        SHOOT(new AudioClip[]{
                new AudioClip("C:\\Users\\reyof\\IdeaProjects\\Game\\resources\\sounds\\Shoot0.wav"),
                new AudioClip("C:\\Users\\reyof\\IdeaProjects\\Game\\resources\\sounds\\Shoot1.wav"),
                new AudioClip("C:\\Users\\reyof\\IdeaProjects\\Game\\resources\\sounds\\Shoot2.wav")
        }),
        SHOOT_HARD(new AudioClip[]{new AudioClip("C:\\Users\\reyof\\IdeaProjects\\Game\\resources\\sounds\\ShootHard0.wav")}),
        HIT(new AudioClip[]{new AudioClip("C:\\Users\\reyof\\IdeaProjects\\Game\\resources\\sounds\\Hit.wav")}),
        POWER_UP(new AudioClip[]{new AudioClip("C:\\Users\\reyof\\IdeaProjects\\Game\\resources\\sounds\\Powerup.wav")}),
        EXPLOSION(new AudioClip[]{
                new AudioClip("C:\\Users\\reyof\\IdeaProjects\\Game\\resources\\sounds\\Explosion.wav"),
                new AudioClip("C:\\Users\\reyof\\IdeaProjects\\Game\\resources\\sounds\\Explosion2.wav"),
                new AudioClip("C:\\Users\\reyof\\IdeaProjects\\Game\\resources\\sounds\\Explosion3.wav")}),
        TRANSFORMATION(new AudioClip[]{new AudioClip("C:\\Users\\reyof\\IdeaProjects\\Game\\resources\\sounds\\Transformation.wav")}),

        BACKGROUND(new AudioClip[]{new AudioClip("C:\\Users\\reyof\\IdeaProjects\\Game\\resources\\sounds\\BackgroundMusic0.wav")});

        public final AudioClip[] AUDIO_CLIPS;

        Sound(AudioClip[] audio) {
            AUDIO_CLIPS = audio;
        }
    }

    public static void playSound(Sound sound, float volume) {
        AudioClip audioClip = sound.AUDIO_CLIPS[random.nextInt(sound.AUDIO_CLIPS.length)];
        audioClip.play(volume);
    }

    public static void loopSound(Sound sound, float volume) {
        AudioClip audioClip = sound.AUDIO_CLIPS[random.nextInt(sound.AUDIO_CLIPS.length)];
        audioClip.loop(volume);
    }
}