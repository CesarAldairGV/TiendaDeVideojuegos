package proyecto.utilidades;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImagenUtilidades {

	public static ImageIcon getImage(String src, int width, int height) {
		URL url = ImagenUtilidades.class.getResource(src);
		ImageIcon icon = new ImageIcon(url);
		Image image = icon.getImage();
		icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_DEFAULT));
		return icon;
	}
	
}
