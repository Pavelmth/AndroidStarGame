package ru.pavel.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.pavel.base.SpritesPool;
import ru.pavel.math.Rect;
import ru.pavel.sprite.game.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {
    private Sound shootSound;
    private BulletPool bulletPool;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds) {
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/2576.mp3"));
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(shootSound, bulletPool, worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }
}
