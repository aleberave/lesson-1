package ru.geekbrains;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import ru.geekbrains.screen.MenuScreen;

public class Lesson4HW extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen());
    }

}
