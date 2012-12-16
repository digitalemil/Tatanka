package de.digitalemil.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class GTexture {
	Image img;
	ImageElement imgElement;
	boolean imageLoaded;

	public GTexture (String name) {
		img = new Image(name);
		img.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				imageLoaded = true;
				imgElement = (ImageElement) img.getElement().cast();
				System.out.println("GTexture: "+ img.getUrl());
			}
		});
		img.setVisible(false);
		RootPanel.get().add(img); // image must be on page to fire load
	}
	
	public ImageElement getImageElement() {
		return imgElement;
	}

	public void update(double mouseX, double mouseY) {
		if (!imageLoaded) {
			return;
		}

	}

	public void draw(Context2d context) {
		if (!imageLoaded) {
			return;
		}
		context.save();
		//context.translate(this.pos.x, this.pos.y);
		//context.rotate(rot);
		context.drawImage(imgElement, 0, 0);
		context.restore();

		// draw(context);
	}
}
