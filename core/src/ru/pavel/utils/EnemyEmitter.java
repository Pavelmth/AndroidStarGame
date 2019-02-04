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
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_SY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HEALTH = 1;

    private Vector2 enemySmallS = new Vector2(0, -0.2f);
    private TextureRegion[] enemySmallRegion;

    private TextureRegion bulletRegion;

    private float generateInterval = 4f;
    private float generateTimer;

    private EnemyPool enemyPool;
    private Rect worldBounds;

    public EnemyEmitter(EnemyPool enemyPool, TextureAtlas atlas, Rect worldBounds) {
        this.enemyPool = enemyPool;
        TextureRegion textureRegion = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(textureRegion, 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
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
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
