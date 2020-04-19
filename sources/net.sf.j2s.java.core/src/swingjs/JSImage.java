package swingjs;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import javajs.util.Base64;
import swingjs.JSFileSystem.JSPath;
import swingjs.api.js.DOMNode;
import swingjs.plaf.JSComponentUI;

/**
 * A JavaScript version of BufferedImage.
 * 
 * Created from JSImagekit when creating an image from byte[] or from loading a
 * GIF, PNG, or JPG image.
 * 
 * The difference is that when from byte[] data, the field 秘pix will be
 * initialized to the raster data, but 秘imgNode will be null.
 * 
 * When an image is used, then 秘pix will be there, but it will not be populated
 * unless a call to getRaster() or one of the setRGB() methods is made. 
 * Until then, JSGraphics2D.drawImage will simply use the image itself.
 * 
 * useful states: (needs checking)
 * 
 * a) 秘imgNode != null implies came from an image node (image file)
 *
 * b) 秘canvas != null implies an image has been created from raster data
 * 
 * @author Bob Hanson
 *
 */
public class JSImage extends BufferedImage {

	public String src;

	/**
	 * Frome JSImageKit reading pixels from an image file.
	 * 
	 * @param argb
	 * @param width
	 * @param height
	 * @param src
	 */
	public JSImage(int[] argb, int width, int height, String src) {
		super(width, height, TYPE_INT_ARGB);
		this.src = src;
		秘setPixels(argb);
	}

	public JSImage(byte[] pixelBytes, int width, int height, String src) {
		// Actually do not know what I am supposed to do with this.
		super(width, height, TYPE_INT_ARGB);
		this.src = src;
		秘setPixels((int[]) (Object) pixelBytes);
	}

	/**
	 * Use HTML5 to load PNG, JPG, or GIF image in order to extract its pixels
	 * 
	 * @param b
	 * @param type
	 */
	@SuppressWarnings("unused")
	void setImageNode(JSPath source, byte[] b, String type) {
		DOMNode img = null;
		if (type == "video") {
			try {
				String src = (source == null ? null : JSUtil.getWebPathFor(source.toString()));
				if (b == null && source != null)
					b = source.秘bytes;
				System.out.println("JSImage video " + src + " " + (b == null ? 0 : b.length));
				img = DOMNode.createElement("video", File.createTempFile("video_", "").getName());
				DOMNode node = img;
				Runnable r = new Runnable() {
					// set dimension when available
					@Override
					public void run() {
						int w = 0, h = 0;
						DOMNode n = node;
						/**
						 * @j2sNative
						 * 
						 * 			w = n.width = n.videoWidth; h = n.height = n.videoHeight;
						 * 
						 */
						JSComponentUI ui = (JSComponentUI) DOMNode.getAttr(node, "data-ui");
						System.out.println("JSImage w,h " + w + " " + h);
						秘init(w, h, TYPE_INT_ARGB);
						if (ui != null && ui.jc instanceof JLabel) {
							JLabel label = (JLabel) ui.jc;
							w = label.getWidth();
							h = label.getHeight();
							n.setAttribute("width", w + "");
							n.setAttribute("height", h + "");
							ui.setTainted();
							ImageIcon icon = (ImageIcon) label.getIcon();
							if (icon != null) {
								icon.秘setIconSize(w, h);
							}
						}
					}					
				};
				/**
				 * @j2sNative
				 * 
				 * 			//document.body.appendChild(img);
				 * 			img.src = (b == null ? src : URL.createObjectURL(new Blob([b])));
				 * 			img.onloadedmetadata = function(){ r.run$()};
				 *          img.load();
				 */

			} catch (IOException e) {
			}
		} else {
			String dataurl = "data:image/" + type + ";base64," + Base64.getBase64(b).toString();
			/**
			 * @j2sNative img = new Image(this.width, this.height); img.src = dataurl;
			 */

		}
		秘imgNode = img;
	}

	/**
	 * font will be derived from this component when graphics is created
	 * 
	 * @param c
	 * @return
	 */
	public Image setComponent(Component c) {
		秘component = c;
		return this;
	}

}
