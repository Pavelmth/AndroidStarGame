package ru.pavel.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.pavel.math.Rect;
import ru.pavel.pool.BulletPool;
import ru.pavel.pool.EnemyPool;
import ru.pavel.screen.GameScreen;
import ru.pavel.sprite.menu.ScaledTouchUpButton;

public class NewGame extends ScaledTouchUpButton {
        BulletPool bulletPool;
        EnemyPool enemyPool;
        GameScreen gameScreen;

    public NewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        setHeightProportion(0.05f);
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.3f);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
