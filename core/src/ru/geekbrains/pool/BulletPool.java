package ru.geekbrains.pool;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.game.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
