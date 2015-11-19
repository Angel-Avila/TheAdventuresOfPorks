package juego.util;

import static juego.util.MathUtils.clamp;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public class ImageUtils {
	
	private ImageUtils(){
	}
	
	public static BufferedImage changeBrightness(BufferedImage original, int amount){
		BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		byte[] pixels = ((DataBufferByte)original.getRaster().getDataBuffer()).getData();
		int[] resultPixels = ((DataBufferInt)result.getRaster().getDataBuffer()).getData();
		
		int offset = 0;
		for(int y = 0; y < original.getHeight(); y++){
			for(int x = 0; x < original.getWidth(); x++){
				
				int a = Byte.toUnsignedInt(pixels[offset++]);
				int r = Byte.toUnsignedInt(pixels[offset++]);
				int g = Byte.toUnsignedInt(pixels[offset++]);
				int b = Byte.toUnsignedInt(pixels[offset++]);
				
				r = clamp(r + amount, 0, 255);
				g = clamp(g + amount, 0, 255);
				b = clamp(b + amount, 0, 255);
				
				resultPixels[x + y * original.getWidth()] = a << 24 | r << 16 | g << 8 | b;
			}
		}
		
		return result;
	}
}
