package ru.geekbrains.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.Lesson4HW;
import ru.geekbrains.screen.GameScreen;

public class TouchPlay extends ScaledTouchUpButton {

    Lesson4HW button_Play = new Lesson4HW();

    public TouchPlay(TextureAtlas atlas) {
        super(atlas.findRegion("btPlay"));
        pos.set(-0.2f, -0.3f);
        setHeightProportion(0.15f);
    }

    @Override
    public void action() {
        button_Play.create();
        button_Play.setScreen(new GameScreen());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }
}
