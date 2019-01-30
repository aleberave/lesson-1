package ru.geekbrains.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Bullet extends Sprite {

    private Rect worldbounds; // границы мира
    private Vector2 v = new Vector2(); // скорость пули
    private int damage; // урон от пули
    private Object owner; // владелец пули
    private Sound sound;

    public Bullet(){
        regions = new TextureRegion[1];
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldbounds)){
            destroy();
        }
    }

    public void set(
            Object owner, // владелец пули
            TextureRegion region, // текстура пули
            Sound sound,
            Vector2 pos0, // вектор позиции пули, начальная
            Vector2 v0, // вектор скорости пули, начальная
            float height, // размер пули
            Rect worldbounds, // границы мира
            int damage // урон от пули
            ){
        this.owner = owner;
        this.regions[0] = region;
        this.sound = sound;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.worldbounds = worldbounds;
        this.damage = damage;

    }

    public int getDamage() {
        return damage;
    }

    public Object getOwner() {
        return owner;
    }
}
