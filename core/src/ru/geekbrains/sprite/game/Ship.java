package ru.geekbrains.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class Ship extends Sprite {

    protected Rect worldBounds;

    protected Vector2 v = new Vector2();

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;

    protected float reloadInterval;
    protected float reloadTimer;

    protected Sound shootSound;

    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;

    protected int hp;

    public Ship() {
        super();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    public void shoot() {
        shootSound.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }

    public void dispose() {
        shootSound.dispose();
    }
}
