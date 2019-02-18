package ru.geekbrains.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class ProgressBar extends Sprite {

    private MainShip mainShip;

    private float frameInterval = 0.1f;
    private float frameTimer = frameInterval;

    public ProgressBar(TextureAtlas atlas, MainShip mainShip) {
        super(atlas.findRegion("myS"), 1, 5, 5);
        setHeightProportion(0.04f);
        this.mainShip = mainShip;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        frameTimer += delta;
        if(frameTimer >= frameInterval){
            if(mainShip.hp > 100){
                if (mainShip.hp == 0) {
                    frame = 0;
                } else if (mainShip.hp > 0 && mainShip.hp < (mainShip.getHpMax() * 0.25f)) {
                    frame = 1;
                } else if (mainShip.hp >= (mainShip.getHpMax() * 0.25f) && mainShip.hp < (mainShip.getHpMax() * 0.5f)) {
                    frame = 2;
                } else if (mainShip.hp >= (mainShip.getHpMax() * 0.5f) && mainShip.hp < (mainShip.getHpMax() * 0.75f)) {
                    frame = 3;
                } else {
                    frame = 4;
                }
            } else {
                if (mainShip.hp == 0) {
                    frame = 0;
                } else if (mainShip.hp > 0 && mainShip.hp < 25) {
                    frame = 1;
                } else if (mainShip.hp >= 25 && mainShip.hp < 50) {
                    frame = 2;
                } else if (mainShip.hp >= 50 && mainShip.hp < 75) {
                    frame = 3;
                } else {
                    frame = 4;
                }
            }
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        pos.set(getHalfWidth(), worldBounds.getTop() - 0.02f);
    }

    public void changeBar() {
        if(frame >= 1){
            frame--;
            frameTimer = 0f;
        }
    }
}

