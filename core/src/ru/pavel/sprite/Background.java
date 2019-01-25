package ru.pavel.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.pavel.base.Sprite;
import ru.pavel.math.Rect;

public class Background extends Sprite {
    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect wolldBounds) {
        pos.set(wolldBounds.pos);
        setHeightProportion(wolldBounds.getHeight());
    }
}
