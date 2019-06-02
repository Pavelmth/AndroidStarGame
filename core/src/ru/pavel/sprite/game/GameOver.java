package ru.pavel.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.pavel.base.Sprite;
import ru.pavel.math.Rect;

public class GameOver extends Sprite {
    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        setHeightProportion(0.05f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }
}
