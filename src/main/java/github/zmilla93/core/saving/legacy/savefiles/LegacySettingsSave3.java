package github.zmilla93.core.saving.legacy.savefiles;

import github.zmilla93.core.audio.Sound;
import github.zmilla93.modules.saving.AbstractSaveFile;

public class LegacySettingsSave3 extends AbstractSaveFile {

    public String clientPath;

    @Override
    public int getCurrentTargetVersion() {
        return 0;
    }

    public static class LegacySound {

//        public enum LegacySoundType {INBUILT, CUSTOM}

        public final String name;
        public Sound.SoundType soundType;

        public LegacySound(Sound sound) {
            this.name = sound.name;
            this.soundType = sound.soundType;
        }

        public Sound toSound() {
            String newName = name;
            if (soundType == Sound.SoundType.CUSTOM) newName += ".wav";
            return new Sound(newName, soundType);
        }

    }

}
