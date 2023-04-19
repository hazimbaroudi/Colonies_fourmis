package Colonie_Fourmis.Gui;

import java.net.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	Clip clip;
	float previousVolume=0;
	float currentVolume=0;
	FloatControl f;
	boolean mute=false;
	
	public void setFile(URL audio) {
		
		
		
		try {
		AudioInputStream audiostream= AudioSystem.getAudioInputStream(audio);
		clip=AudioSystem.getClip();
		clip.open(audiostream);
		f=(FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void play(URL audio) {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void loop(URL audio) {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop(URL audio) {
		clip.stop();
	}

}
