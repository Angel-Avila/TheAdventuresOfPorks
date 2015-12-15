package juego.sound;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class OggAudioPlayer {
	
	public static Map<String, Music> musicMap = new HashMap<String, Music>();
	public static Map<String, Sound> soundMap = new HashMap<String, Sound>();
	
	public static void load(){
		
		try{
			soundMap.put("shoot", new Sound("res/shoot.ogg"));
			
			musicMap.put("spawn", new Music("res/spawnMusic.ogg"));
		} catch(SlickException e){
			e.printStackTrace();
		}
	}
	
	public static Music getMusic(String key){
		return musicMap.get(key);
	}
	
	public static Sound getSound(String key){
		return soundMap.get(key);
	}
}
