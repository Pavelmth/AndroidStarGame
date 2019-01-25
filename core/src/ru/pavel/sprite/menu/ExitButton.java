package ru.pavel.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.pavel.base.Sprite;
import ru.pavel.math.Rect;
import ru.pavel.screen.MenuScreen;

public class ExitButton extends ScaledTouchUpButton {
    //
    MenuScreen menuScreen;
    //
    public ExitButton(TextureAtlas atlas, MenuScreen menuScreen) {
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.15f);
        this.menuScreen = menuScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        float posX = -0.15f;
        float posY = -0.33f;
        pos.set(posX, posY);
    }

    @Override
    public void action() {
        menuScreen.dispose();
    }
}
