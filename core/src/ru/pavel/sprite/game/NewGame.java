package ru.pavel.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.pavel.base.Sprite;
import ru.pavel.math.Rect;
import ru.pavel.pool.BulletPool;
import ru.pavel.pool.EnemyPool;
import ru.pavel.screen.GameScreen;
import ru.pavel.sprite.menu.ScaledTouchUpButton;

public class NewGame extends ScaledTouchUpButton {
        BulletPool bulletPool;
        EnemyPool enemyPool;
        MainShip mainShip;

    public NewGame(TextureAtlas atlas, MainShip mainShip) {
        super(atlas.findRegion("button_new_game"));
        setHeightProportion(0.05f);
        pos.y = pos.y - 0.2f;

        this.mainShip = mainShip;

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        //Тут не срабатывает позиционирование пришлось писать в конструктор
        //setBottom(worldBounds.getBottom() + 0.02f);
    }

    @Override
    public void action() {
        mainShip.flushDestroy();
    }
}
