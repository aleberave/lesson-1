package ru.geekbrains.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.game.MainShip;
import ru.geekbrains.sprite.game.PlusHP;

public class PlusHPPool extends SpritesPool<PlusHP> {

    private Sound heartSound;
    private Rect worldBounds;
    private MainShip mainShip;

    public PlusHPPool(Rect worldBounds, MainShip mainShip) {
        this.heartSound = Gdx.audio.newSound(Gdx.files.internal("hp/reload_hp.wav"));
        this.mainShip = mainShip;
        this.worldBounds = worldBounds;
    }

    @Override
    protected PlusHP newObject() {
        return new PlusHP(heartSound, worldBounds, mainShip);
    }

    @Override
    public void dispose() {
        super.dispose();
        heartSound.dispose();
    }
}
