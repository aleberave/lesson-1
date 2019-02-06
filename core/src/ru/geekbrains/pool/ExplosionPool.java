package ru.geekbrains.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.game.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureRegion region;
    private Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas) {
        this.region = atlas.findRegion("explosion");
        this.explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(region, 9, 9, 74, explosionSound);
    }

    public void dispose() {
        explosionSound.dispose();
        super.dispose();
    }
}
