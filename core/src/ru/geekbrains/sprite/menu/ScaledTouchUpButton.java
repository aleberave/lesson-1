package ru.geekbrains.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;

public abstract class ScaledTouchUpButton extends Sprite {

    private static final float PRESS_SCALE = 0.9f;

    private int pointer;
    private boolean isPressed;

    public ScaledTouchUpButton(TextureRegion region) {
        super(region);
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isPressed || !isMe(touch)) {
            return false;
        }
        this.pointer = pointer;
        this.scale = PRESS_SCALE;
        this.isPressed = true;
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (this.pointer != pointer || !isPressed) {
            return false;
        }
        if (isMe(touch)) {
            action();
        }
        this.isPressed = false;
        scale = 1f;
        return super.touchUp(touch, pointer);
    }

    public abstract void action();
}
