package ru.pavel.sprite.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.pavel.base.Sprite;
import ru.pavel.math.Rect;
import ru.pavel.screen.MenuScreen;

public class ExitButton extends ScaledTouchUpButton {

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.02f);
        setLeft(worldBounds.getLeft() + 0.02f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
