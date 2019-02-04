package ru.pavel.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pavel.math.Rect;
import ru.pavel.pool.BulletPool;

public class MainShip extends Ship {

    private static final int INVALID_POINTER = -1;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private final Vector2 speed0 = new Vector2(0.5f, 0);

    private boolean isPressedLeft;
    private boolean isPressedRight;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletPool = bulletPool;
        this.reloadInterval = 0.2f;
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/2576.mp3"));
        setHeightProportion(0.1f);
        this.bulletS = new Vector2(0, 0.5f);
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.health = 100;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(speed, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
         }
        //right limit
        if (getRight() > worldBounds.getRight()) {
            stop();
            setRight(worldBounds.getRight());
        }
        //left limit
        if (getLeft() < worldBounds.getLeft()) {
            stop();
            setLeft(worldBounds.getLeft());
        }
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
            if (leftPointer != INVALID_POINTER) return false;
            leftPointer = button;
            moveRight();
        }
        else if (touch.x < 0) {
            if (rightPointer != INVALID_POINTER) return false;
            rightPointer = button;
            moveLeft();
        }
        return super.touchDown(touch, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int button) {
        //checking if user doesn't remove off finger
        if (button == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (button == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
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
}
