package ru.pavel.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.pavel.StarGame;
import ru.pavel.math.Rect;
import ru.pavel.screen.GameScreen;

public class PlayButton extends ScaledTouchUpButton {
    //
    StarGame starGame;
    //
    public PlayButton(TextureAtlas atlas, StarGame starGame) {
        super(atlas.findRegion("btPlay"));
        setHeightProportion(0.2f);
        this.starGame = starGame;
    }

    @Override
    public void resize(Rect worldBounds) {
        float posX = 0.15f;
        float posY = -0.3f;
        pos.set(posX, posY);
    }

    @Override
    public void action() {
        starGame.setScreen(new GameScreen());
    }
}
