package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.game.Enemy;

public class EnemyEmitter {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_MIDDLE_HEIGHT = 0.15f;
    private static final float ENEMY_BIG_HEIGHT = 0.2f;

    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_MIDDLE_BULLET_HEIGHT = 0.015f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.02f;

    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final float ENEMY_MIDDLE_BULLET_VY = -0.35f;
    private static final float ENEMY_BIG_BULLET_VY = -0.4f;

    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final int ENEMY_MIDDLE_DAMAGE = 5;
    private static final int ENEMY_BIG_DAMAGE = 10;

    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final float ENEMY_MIDDLE_RELOAD_INTERVAL = 4f;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 5f;

    private static final int ENEMY_SMALL_HP = 1;
    private static final int ENEMY_MIDDLE_HP = 1;
    private static final int ENEMY_BIG_HP = 1;

    private Vector2 enemySmallV = new Vector2(0, -0.2f);
    private Vector2 enemyMiddleV = new Vector2(0, -0.25f);
    private Vector2 enemyBigV = new Vector2(0, -0.3f);

    private TextureRegion[] enemySmallRegion;
    private TextureRegion[] enemyMiddleRegion;
    private TextureRegion[] enemyBigRegion;

    private float generateInterval = 4f;
    private float generateTimer;
    private float random;

    private TextureRegion bulletRegion;

    private EnemyPool enemyPool;

    private Rect worldBounds;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        this.enemyPool = enemyPool;
        TextureRegion textureRegion0 = atlas.findRegion("enemy0");
        TextureRegion textureRegion1 = atlas.findRegion("enemy1");
        TextureRegion textureRegion2 = atlas.findRegion("enemy2");
        this.enemySmallRegion = Regions.split(textureRegion0, 1,2,2);
        this.enemyMiddleRegion = Regions.split(textureRegion1, 1,2,2);
        this.enemyBigRegion = Regions.split(textureRegion2, 1,2,2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        System.out.println("GT " + generateTimer + "         RND " + (int) random + "          GI " + generateInterval);
        if (generateTimer >= generateInterval && generateInterval > (int) random && (int) random < 4f) {
            generateTimer = 0f;
            random = Rnd.nextFloat(2, 6);
            Enemy enemy = enemyPool.obtain();
            enemy.set(
                    worldBounds,
                    enemySmallRegion,
                    enemySmallV,
                    bulletRegion,
                    ENEMY_SMALL_BULLET_HEIGHT,
                    ENEMY_SMALL_BULLET_VY,
                    ENEMY_SMALL_DAMAGE,
                    ENEMY_SMALL_RELOAD_INTERVAL,
                    ENEMY_SMALL_HEIGHT,
                    ENEMY_SMALL_HP
            );
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
        if (generateTimer >= generateInterval && generateInterval <= (int) random && (int) random < 5f) {
            generateTimer = 0f;
            random = Rnd.nextFloat(2, 6);
            Enemy enemy = enemyPool.obtain();
            enemy.set(
                    worldBounds,
                    enemyMiddleRegion,
                    enemyMiddleV,
                    bulletRegion,
                    ENEMY_MIDDLE_BULLET_HEIGHT,
                    ENEMY_MIDDLE_BULLET_VY,
                    ENEMY_MIDDLE_DAMAGE,
                    ENEMY_MIDDLE_RELOAD_INTERVAL,
                    ENEMY_MIDDLE_HEIGHT,
                    ENEMY_MIDDLE_HP
            );
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
        if (generateTimer >= generateInterval && generateInterval <= (int) random) {
            generateTimer = 0f;
            random = Rnd.nextFloat(2, 6);
            Enemy enemy = enemyPool.obtain();
            enemy.set(
                    worldBounds,
                    enemyBigRegion,
                    enemyBigV,
                    bulletRegion,
                    ENEMY_BIG_BULLET_HEIGHT,
                    ENEMY_BIG_BULLET_VY,
                    ENEMY_BIG_DAMAGE,
                    ENEMY_BIG_RELOAD_INTERVAL,
                    ENEMY_BIG_HEIGHT,
                    ENEMY_BIG_HP
            );
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
