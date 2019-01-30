package ru.pavel.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pavel.base.Sprite;
import ru.pavel.math.Rect;
import ru.pavel.pool.BulletPool;

public class MainShip extends Sprite {

    private Rect worldBounds;

    private final Vector2 speed0 = new Vector2(0.5f, 0);
    private Vector2 speed = new Vector2();

    private boolean isPressedLeft;
    private boolean isPressedRight;

    private BulletPool bulletPool;

    private TextureRegion bulletRegion;

    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/2576.mp3"));

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletPool = bulletPool;
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(speed, delta);
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shoot();
                sound.play(1.0f); // воспроизводит новый звук
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isPressedLeft = false;
                if (isPressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight = false;
                if (isPressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int button) {
        if (touch.x > 0) {
            moveRight();
        }
        else if (touch.x < 0) {
            moveLeft();
        }
        return super.touchDown(touch, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int button) {
        stop();
        return super.touchUp(touch, button);
    }

    private void moveRight() {
        speed.set(speed0);
    }

    private void moveLeft() {
        speed.set(speed0).rotate(180);
    }

    private void stop() {
        speed.setZero();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, new Vector2(0, 0.5f), 0.01f, worldBounds, 1);
    }

}
