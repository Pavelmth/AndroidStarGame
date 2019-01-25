package ru.pavel.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.pavel.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		float aspect = 9f/16f;
		config.width = 400;
		config.height = (int) (config.width / aspect);
		config.resizable = false;
		new LwjglApplication(new StarGame(), config);
	}
}
