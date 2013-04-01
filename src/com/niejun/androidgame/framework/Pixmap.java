/**
 * 
 */
package com.niejun.androidgame.framework;

import com.niejun.androidgame.framework.Graphics.PixmapFormat;

/**
 * @author NIEJUN
 *
 */
public interface Pixmap {

	public int getWidth();
	public int getHeight();
	public PixmapFormat getFormat();
	public void dispose();
}
