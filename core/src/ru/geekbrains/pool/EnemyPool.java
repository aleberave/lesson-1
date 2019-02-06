package ru.geekbrains.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.game.Enemy;
import ru.geekbrains.sprite.game.MainShip;

public class EnemyPool extends SpritesPool<Enemy> {

    private Sound shootSound;
    private BulletPool bulletPool;
    private Rect worldBounds;
    private ExplosionPool explosionPool;
    private MainShip mainShip;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool, MainShip mainShip) {
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.mainShip = mainShip;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(shootSound, bulletPool, explosionPool, worldBounds, mainShip);
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }
}
