package ru.geekbrains.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.math.Rect;

public class TouchExit extends ScaledTouchUpButton {

    private Rect worldBounds;

    public TouchExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void action() {
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = getWidth() / getHeight();
        float posY = 0.2f;
        pos.set(posX, posY);
        super.resize(worldBounds);
    }
}
