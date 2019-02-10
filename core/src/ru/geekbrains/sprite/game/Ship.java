package ru.geekbrains.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class Ship extends Sprite {

    protected Rect worldBounds;

    protected Vector2 v = new Vector2();

    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;

    protected float reloadInterval;
    protected float reloadTimer;

    private float damageInterval = 0.1f;
    private float damageTimer = damageInterval;

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
    public void update(float delta) {
        super.update(delta);
        damageTimer += delta;
        if(damageTimer >= damageInterval){
            frame = 0;
        }
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

    public void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public void damage(int damage){
        frame = 1;
        damageTimer = 0f;
        hp -= damage;
        if(hp <= 0){
            hp = 0;
            destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

    public void dispose() {
        shootSound.dispose();
    }

    public int getHp() {
        return hp;
    }
}
