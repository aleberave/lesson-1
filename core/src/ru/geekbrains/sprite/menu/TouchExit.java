package ru.geekbrains.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.screen.MenuScreen;

public class TouchExit extends ScaledTouchUpButton {

    MenuScreen method_hide = new MenuScreen();

    public TouchExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        pos.set(0.2f, -0.3f);
        setHeightProportion(0.15f);
    }

    @Override
    public void action() {
        method_hide.hide();
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }
}
