package ru.geekbrains.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.geekbrains.Lesson4HW;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		float aspect = 3f / 4f;
		config.width = 400;
		config.height = (int) (config.width / aspect);
//		config.resizable = false; // нельзя изменять размер экрана
		new LwjglApplication(new Lesson4HW(), config);
	}
}
