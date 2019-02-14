package ru.pavel.pool;

import ru.pavel.base.SpritesPool;
import ru.pavel.sprite.game.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
