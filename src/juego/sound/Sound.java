package juego.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	public static Sound JOHNCENA = loadSound("/res/audio/JOHNCENA.wav");
	public static Sound spawnMusic = loadSound("/res/audio/spawnMusic.wav");
	public static Sound encounter = loadSound("/res/audio/encounter.wav");

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
	
	public void quickPlay(Sound toPause){
		try {
	        if (clip != null) {
                if(toPause.clip.isActive()) toPause.clip.stop();
                this.play();
	        }
	    } catch (Exception e) {
	        System.out.println(e + ":(");
	    }
	}
}
