package ru.pavel.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.pavel.base.BaseScreen;
import ru.pavel.math.Rect;
import ru.pavel.pool.BulletPool;
import ru.pavel.pool.EnemyPool;
import ru.pavel.pool.ExplosionPool;
import ru.pavel.sprite.Background;
import ru.pavel.sprite.Star;
import ru.pavel.sprite.game.Bullet;
import ru.pavel.sprite.game.Enemy;
import ru.pavel.sprite.game.GameOver;
import ru.pavel.sprite.game.MainShip;
import ru.pavel.sprite.game.NewGame;
import ru.pavel.utils.EnemyEmitter;
import ru.pavel.utils.Font;

public class GameScreen extends BaseScreen {
    public static final String FRAGS = "Frags: ";
    public static final String HEALTH = "HP: ";
    public static final String LEVEL = "Level: ";
    public static final String SCORE = "Score: ";

    private TextureAtlas atlas;
    private Texture bg;
    private Background background;
    private Star star[];
    private MainShip mainShip;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private EnemyEmitter enemyEmitter;

    private GameOver gameOver;
    private NewGame newGame;

    int frags = 0;
    int score = 0;

    private Font font;

    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHealth = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();
    private StringBuilder sbScore = new StringBuilder();

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        star = new Star[64];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }

        gameOver = new GameOver(atlas);
        newGame = new NewGame(atlas, this);

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, worldBounds);
        enemyPool = new EnemyPool(bulletPool, worldBounds, explosionPool, mainShip);
        enemyEmitter = new EnemyEmitter(enemyPool, atlas, worldBounds);
        this.font = new Font("font/font.fnt","font/font.png");
        this.font.setSize(0.02f);
        startNewGame();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void update(float delta) {
        for (Star aStar : star) {
            aStar.update(delta);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            explosionPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
        }
    }

    private void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy: enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            //distance between middle points of ships
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.destroy();
                mainShip.damage(enemy.getDamage());
                return;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for (Bullet bullet: bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }

        for (Enemy enemy: enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet: bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(mainShip.getDamage());
                    if (enemy.isDestroyed()) {
                        frags++;
                        score += enemy.getDamage();
                    }
                    bullet.destroy();
                }
            }
        }
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star aStar : star) {
            aStar.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            explosionPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
            newGame.draw(batch);
        }
        printInfo();
        batch.end();
    }

    public void printInfo() {
        sbFrags.setLength(0);
        sbHealth.setLength(0);
        sbLevel.setLength(0);
        sbScore.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop() - 0.03f);
        font.draw(batch, sbHealth.append(HEALTH).append(mainShip.getHealth()), worldBounds.pos.x, worldBounds.getTop() - 0.03f, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight(), worldBounds.getTop() - 0.03f, Align.right);
        font.draw(batch, sbScore.append(SCORE).append(score), worldBounds.getLeft(), worldBounds.pos.y + 0.44f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star aStar : star) {
            aStar.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        mainShip.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!mainShip.isDestroyed()) mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchDown(touch, pointer);
        } else {
            newGame.touchDown(touch, pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchUp(touch, pointer);
        } else {
            newGame.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }

    public void startNewGame() {
        frags = 0;
        score = 0;
        enemyEmitter.setLevel(1);

        mainShip.startNewGame();
        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }
}