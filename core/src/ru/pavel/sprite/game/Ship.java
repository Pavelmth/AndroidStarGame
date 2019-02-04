package ru.pavel.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pavel.base.Sprite;
import ru.pavel.math.Rect;
import ru.pavel.pool.BulletPool;

public class Ship extends Sprite {

    protected Rect worldBounds;

    protected TextureRegion bulletRegion;

    protected BulletPool bulletPool;
    protected Vector2 speed = new Vector2();

    protected float reloadInterval;
    protected float reloadTimer;

    protected Sound shootSound;

    protected Vector2 bulletS;
    protected float bulletHeight;
    protected int damage;

    protected int health;

    public Ship() {
        super();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    public void shoot() {
        shootSound.play(1.0f); // play sound
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletS, bulletHeight, worldBounds, damage);
    }

    public void dispose() {
        shootSound.dispose();
    }
}
