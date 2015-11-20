package juego.sound;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	public static Sound JOHNCENA = loadSound("/res/audio/JOHNCENA.wav");
	public static Sound spawnMusic = loadSound("/res/audio/spawnMusic.wav");
	public static Sound encounter = loadSound("/res/audio/encounter.wav");
	public static Sound vsRed = loadSound("/res/audio/vsRed.wav");

	public List<Sound> sounds = new ArrayList<>();
	
	private boolean added = false;
	
	//This will load your sound
	public static Sound loadSound(String fileName) {

	    Sound sound = new Sound();
	    try {
	        AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
	        Clip clip = AudioSystem.getClip();
	        clip.open(ais);
	        sound.clip = clip;
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	    
	    return sound;
	}

	public void addAllSounds(){
		sounds.add(JOHNCENA);
		sounds.add(spawnMusic);
		sounds.add(encounter);
		sounds.add(vsRed);
	}
	
	public Clip clip;
	//you can play the clip by running this
	public void play() {
	    try {
	        if (clip != null) {
	            new Thread() {
	                //run method
	                public void run() {
	                    synchronized (clip) {
	                        clip.stop();
	                        clip.setFramePosition(0);
	                        clip.start();
	                    }
	                }
	            }.start();
	        }
	    } catch (Exception e) {
	        System.out.println(e + ":(");
	    }
	}
	
	public void loop() {
		stopAll();
	    try {
	        if (clip != null) {
                clip.loop(100000000);
	        }
	    } catch (Exception e) {
	        System.out.println(e + ":(");
	    }
	}
	
	public void stop(){
		try {
	        if (clip != null) {
                clip.stop();
                clip.setFramePosition(0);
	        }
	    } catch (Exception e) {
	        System.out.println(e + ":(");
	    }
	}
	
	public void stopAll(){
		if(!added){
			addAllSounds();
			added = true;
		}

		for(int i = 0; i < sounds.size(); i++){
			if(sounds.get(i).clip != null && sounds.get(i).clip.isActive()) sounds.get(i).stop();
		}
	}
}
