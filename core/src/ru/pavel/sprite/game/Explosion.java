package ru.pavel.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.pavel.base.Sprite;

public class Explosion extends Sprite {
    private float animateInterval = 0.015f;
    private float animateTimer;
    private Sound exposionSound;

    public Explosion(TextureRegion region, int rows, int cols, int frames, Sound exposionSound) {
        super(region, rows, cols, frames);
        this.exposionSound = exposionSound;
    }

    public void set(float height, Vector2 pos) {
        this.pos.set(pos);
        setHeightProportion(height);
        exposionSound.play(0.5f);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
