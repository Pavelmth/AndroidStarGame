package ru.pavel.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pavel.math.Rect;
import ru.pavel.pool.BulletPool;
import ru.pavel.pool.ExplosionPool;

public class Enemy extends Ship {
    private enum State {
        DESCENT, FIGHT
    }

    private Vector2 speed0 = new Vector2();
    private State state;
    private Vector2 descentS = new Vector2(0, -0.2f);
    private MainShip mainShip;

    public Enemy(Sound shootSound, BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, MainShip mainShip) {
        super();
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        this.bulletPool = bulletPool;
        this.speed.set(speed0);
        this.explosionPool = explosionPool;
        this.bulletS = new Vector2();
        this.mainShip = mainShip;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.pos.mulAdd(speed, delta);
        switch (state) {
            case DESCENT:
                if (getTop() <= worldBounds.getTop()) {
                    speed.set(speed0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                reloadTimer += delta;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }
                if (getBottom() < worldBounds.getBottom()) {
                    mainShip.damage(this.damage);
                    destroy();
                }
                break;
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
        speed.set(descentS);
        state = State.DESCENT;
    }

    @Override
    public void destroy() {
        super.destroy();
        explosion();
    }

    //bullet should get to center of ship
    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
        || bullet.getLeft() > getRight()
        || bullet.getBottom() > getTop()
        || bullet.getTop() < pos.y);
    }
}
