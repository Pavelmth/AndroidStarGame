package ru.pavel;

import com.badlogic.gdx.Game;

import ru.pavel.screen.MenuScreen;

//public class StarGame extends ApplicationAdapter {
public class StarGame extends Game {
	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
