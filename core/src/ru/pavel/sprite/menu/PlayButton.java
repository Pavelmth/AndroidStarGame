package ru.pavel.sprite.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.pavel.math.Rect;
import ru.pavel.screen.GameScreen;

public class PlayButton extends ScaledTouchUpButton {

    private Game game;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.02f);
        setRight(worldBounds.getRight() - 0.02f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
