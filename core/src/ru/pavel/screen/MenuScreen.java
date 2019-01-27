package ru.pavel.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pavel.StarGame;
import ru.pavel.base.BaseScreen;
import ru.pavel.math.Rect;
import ru.pavel.sprite.Background;
import ru.pavel.sprite.Star;
import ru.pavel.sprite.menu.ExitButton;
import ru.pavel.sprite.menu.PlayButton;

public class MenuScreen extends BaseScreen {

    private TextureAtlas atlas;
    private Texture bg;
    private Background background;
    private Star star[];
    private PlayButton playButton;
    private ExitButton exitButton;
    StarGame starGame;
    Game game;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        star = new Star[256];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        playButton = new PlayButton(atlas, game);
        exitButton = new ExitButton(atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        playButton.draw(batch);
        exitButton.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        playButton.resize(worldBounds);
        exitButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        playButton.touchDown(touch, pointer);
        exitButton.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        playButton.touchUp(touch, pointer);
        exitButton.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }
}
