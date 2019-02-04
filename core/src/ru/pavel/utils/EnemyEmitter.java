package ru.pavel.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pavel.math.Rect;
import ru.pavel.math.Rnd;
import ru.pavel.pool.EnemyPool;
import ru.pavel.sprite.game.Enemy;

public class EnemyEmitter {
    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_SY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HEALTH = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.12f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_SY = -0.25f;
    private static final int ENEMY_MEDIUM_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MEDIUM_HEALTH = 5;

    private static final float ENEMY_LARGE_HEIGHT = 0.2f;
    private static final float ENEMY_LARGE_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_LARGE_BULLET_SY = -0.25f;
    private static final int ENEMY_LARGE_DAMAGE = 10;
    private static final float ENEMY_LARGE_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_LARGE_HEALTH = 10;

    private Vector2 enemySmallS = new Vector2(0, -0.2f);
    private Vector2 enemyMediumS = new Vector2(0, -0.12f);
    private Vector2 enemyLargeS = new Vector2(0, -0.05f);
    private TextureRegion[] enemySmallRegion;
    private TextureRegion[] enemyMediumRegion;
    private TextureRegion[] enemyLargeRegion;

    private TextureRegion bulletRegion;

    private float generateInterval = 4f;
    private float generateTimer;

    private EnemyPool enemyPool;
    private Rect worldBounds;

    public EnemyEmitter(EnemyPool enemyPool, TextureAtlas atlas, Rect worldBounds) {
        this.enemyPool = enemyPool;
        TextureRegion textureRegion0 = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(textureRegion0, 1, 2, 2);
        TextureRegion textureRegion1 = atlas.findRegion("enemy1");
        this.enemyMediumRegion = Regions.split(textureRegion1, 1, 2, 2);
        TextureRegion textureRegion2 = atlas.findRegion("enemy2");
        this.enemyLargeRegion = Regions.split(textureRegion2, 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;

            Enemy enemy = enemyPool.obtain();
            float ShipType = (float) Math.random();
            if (ShipType < 0.5f) {
                enemy.set(
                        enemySmallRegion,
                        enemySmallS,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_SY,
                        ENEMY_SMALL_DAMAGE,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HEALTH
                );
            } else if (ShipType < 0.8f) {
                enemy.set(
                        enemyMediumRegion,
                        enemyMediumS,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_SY,
                        ENEMY_MEDIUM_DAMAGE,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_HEALTH
                );
            } else {
                enemy.set(
                        enemyLargeRegion,
                        enemyLargeS,
                        bulletRegion,
                        ENEMY_LARGE_BULLET_HEIGHT,
                        ENEMY_LARGE_BULLET_SY,
                        ENEMY_LARGE_DAMAGE,
                        ENEMY_LARGE_RELOAD_INTERVAL,
                        ENEMY_LARGE_HEIGHT,
                        ENEMY_LARGE_HEALTH
                );
            }

            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
