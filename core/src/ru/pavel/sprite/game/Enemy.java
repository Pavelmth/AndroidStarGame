package ru.pavel.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pavel.math.Rect;
import ru.pavel.pool.BulletPool;

public class Enemy extends Ship {
    private Vector2 speed0 = new Vector2();

    public Enemy(Sound shootSound, BulletPool bulletPool, Rect worldBounds) {
        super();
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        this.bulletPool = bulletPool;
        this.speed.set(speed0);
        this.bulletS = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.pos.mulAdd(speed, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 speed0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletSY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int health
            )
    {
        this.regions = regions;
        this.speed0.set(speed0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletS.set(0, bulletSY);
        this.damage = bulletDamage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.health = health;
        reloadTimer = reloadInterval;
        speed.set(speed0);
    }
}
