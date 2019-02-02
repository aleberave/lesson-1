package ru.geekbrains.sprite.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class MainShip extends Sprite {

    private Rect worldBounds;

    private final Vector2 v0 = new Vector2(0.5f, 0);
    private Vector2 v = new Vector2();

    private boolean isPressedLeft;
    private boolean isPressedRight;

    private BulletPool bulletPool;

    private TextureRegion bulletRegion;

    private Sound sound;
    private int pointer;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, Sound sound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletPool = bulletPool;
        this.sound = sound;
        this.isPressedLeft = false;
        this.isPressedRight = false;
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        updateShipWorldBounds();
        pos.mulAdd(v, delta);
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = true;
                moveRight();
                break;
            case Input.Keys.SPACE:
                sound.play();
                shoot();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = false;
                if (isPressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = false;
                if (isPressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (this.pointer != pointer || isPressedRight || isPressedLeft) return false;
        if (pos.x < touch.x) {
            moveRight();
            this.isPressedRight = true;
        }
        if (pos.x > touch.x) {
            moveLeft();
            this.isPressedLeft = true;
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (isPressedRight) {
            isPressedRight = false;
            moveRight();
        } else if (isPressedLeft) {
            isPressedLeft = false;
            moveLeft();
        } else {
            stop();
        }
        return super.touchUp(touch, pointer);
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, sound, pos, new Vector2(0, 0.5f), 0.01f, worldBounds, 1);
    }

    private void updateShipWorldBounds() {
        if (getRight() >= worldBounds.getRight()) {
            stop();
            pos.x = (float) getRight() - pos.x;
            pos.x = worldBounds.getRight() - pos.x - 0.0001f;
        }
        if (getLeft() <= worldBounds.getLeft()) {
            stop();
            pos.x = (float) getLeft() + Math.abs(pos.x);
            pos.x = worldBounds.getLeft() + Math.abs(pos.x) + 0.0001f;
        }
    }
}
