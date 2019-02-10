package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.PlusHPPool;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.sprite.game.PlusHP;

public class PlusHpEmitter {

    private static final float HP_HEIGHT = 0.05f;

    private static final int HP_SMALL_HP = 1;
    private static final int HP_MIDDLE_HP = 5;
    private static final int HP_BIG_HP = 10;

    private Vector2 hpSmallV = new Vector2(0, -0.15f);
    private Vector2 hpMiddleV = new Vector2(0, -0.1f);
    private Vector2 hpBigV = new Vector2(0, -0.05f);
    private TextureRegion[] hpSmallRegion = new TextureRegion[1];
    private TextureRegion[] hpMiddleRegion = new TextureRegion[1];
    private TextureRegion[] hpBigRegion = new TextureRegion[1];
    private Rect worldBounds;
    private PlusHPPool plusHPPool;

    private float generateInterval = 20f;
    private float generateTimer;

    private GameScreen gameScreen;

    public PlusHpEmitter(TextureAtlas atlas, PlusHPPool plusHPPool, Rect worldBounds, GameScreen gameScreen) {
        this.plusHPPool = plusHPPool;
        this.gameScreen = gameScreen;
        TextureRegion textureRegion0 = atlas.findRegion("hpbox_empty");
        this.hpSmallRegion[0] = textureRegion0;
        TextureRegion textureRegion1 = atlas.findRegion("hpbox_half");
        this.hpMiddleRegion[0] = textureRegion1;
        TextureRegion textureRegion2 = atlas.findRegion("hpbox_full");
        this.hpBigRegion[0] = textureRegion2;
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            PlusHP plusHP = plusHPPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                plusHP.set(
                        hpSmallRegion,
                        hpSmallV,
                        HP_HEIGHT,
                        HP_SMALL_HP * gameScreen.getLevels()
                );
            } else if (type < 0.8f) {
                plusHP.set(
                        hpMiddleRegion,
                        hpMiddleV,
                        HP_HEIGHT,
                        HP_MIDDLE_HP * gameScreen.getLevels()
                );
            } else {
                plusHP.set(
                        hpBigRegion,
                        hpBigV,
                        HP_HEIGHT,
                        HP_BIG_HP * gameScreen.getLevels()
                );
            }
            plusHP.pos.x = Rnd.nextFloat(worldBounds.getLeft() + plusHP.getHalfWidth(), worldBounds.getRight() - plusHP.getHalfWidth());
            plusHP.setBottom(worldBounds.getTop());
        }
    }
}
