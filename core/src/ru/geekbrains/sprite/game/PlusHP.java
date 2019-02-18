package ru.geekbrains.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class PlusHP extends Sprite {

    protected Rect worldBounds;
    protected Sound heartSound;
    protected Vector2 v = new Vector2();
    protected int hp;

    private Vector2 v0 = new Vector2();
    private enum State {DESCENT, FLYING}
    private State state;
    private Vector2 descentV = new Vector2(0, -0.15f);
    private MainShip mainShip;

    public PlusHP(Sound heartSound, Rect worldBounds, MainShip mainShip) {
        this.worldBounds = worldBounds;
        this.heartSound = heartSound;
        this.mainShip = mainShip;
        this.v.set(v0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.pos.mulAdd(v, delta);
        switch (state){
            case DESCENT:
                if(getTop() <= worldBounds.getTop()){
                    v.set(v0);
                    state = State.FLYING;
                }
                break;
            case FLYING:
                if (isOutside(worldBounds)) {
                    destroy();
                }
                break;
        }
    }

    public void set(
            TextureRegion[] region,
            Vector2 v0,
            float height,
            int hp
    ) {
        this.regions = region;
        this.v0.set(v0);
        setHeightProportion(height);
        this.hp = hp;
        v.set(descentV);
        state = State.DESCENT;
    }
}
