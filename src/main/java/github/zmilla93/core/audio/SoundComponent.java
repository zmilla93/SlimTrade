package github.zmilla93.core.audio;

public class SoundComponent {

    public Sound sound;
    public int volume;

    public SoundComponent(Sound sound, int volume) {
        this.sound = sound;
        this.volume = volume;
    }

    @Override
    public String toString() {
        String soundString = sound == null ? "null" : sound.toString();
        return "SoundComponent[sound=" + soundString + ", volume=" + volume + "]";
    }
}
