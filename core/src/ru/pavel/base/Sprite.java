package ru.pavel.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.pavel.math.Rect;

public class Sprite extends Rect {
    private float angele;
    private float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;

    public Sprite(TextureRegion region) {
        if (region == null) {
            throw  new NullPointerException("Create Sprite with null region");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame], // current region
                getLeft(), getBottom(), // alignment
                halfWidth, halfWidth, // rotation point
                getWidth(), getHeight(), //
                scale, scale, // scale x and y
                angele // rotation angel
                );
    }
}
