package ru.geekbrains.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class Enemy extends Ship {

    private enum State {DESCENT, FIGHT}

    private Vector2 v0 = new Vector2();
    private State state;
    private Vector2 descentV = new Vector2(0, -0.15f);
    private MainShip mainShip;

    public Enemy(Sound shootSound, BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, MainShip mainShip) {
        super();
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.mainShip = mainShip;
        this.v.set(v0);
        this.bulletV = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.pos.mulAdd(v, delta);
        switch (state){
            case DESCENT:
                if(getTop() <= worldBounds.getTop()){
                    v.set(v0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                reloadTimer += delta;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }
                if (getBottom() < worldBounds.getBottom()) {
                    mainShip.damage(this.damage);
                    destroy();
                }
                break;
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = bulletDamage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        reloadTimer = reloadInterval;
        v.set(descentV);
        state = State.DESCENT;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public boolean isBulletCollision(Rect bullet){
        return !( bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }
}
