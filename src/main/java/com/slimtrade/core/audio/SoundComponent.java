package com.slimtrade.core.audio;

public enum SoundComponent {

	INCOMING_MESSAGE(Sound.PING1, 0),
	OUTGOING_MESSAGE(Sound.PING1, 0),
	SCANNER_MESSAGE(Sound.PING1, 0),
	BUTTON_CLICK(Sound.CLICK1, 0),
	;
	
	private Sound sound;
	private float volume;
	
	SoundComponent(Sound sound, float volume){
		this.sound = sound;
		this.volume = volume;
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}
	
}
