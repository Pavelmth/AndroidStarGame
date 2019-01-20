package ru.pavel.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.pavel.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    SpriteBatch batch;
    // 113 x 97
    Texture img;
    Texture background;

    Vector2 pos;
    Vector2 v;
    Vector2 touch;
    Vector2 buf;

    //
    int i = 0;
    private static final float V_LEN = 1f;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        background = new Texture("grass.jpg");
        img = new Texture("move/survivor-move_handgun_0.png");
        pos = new Vector2((Gdx.graphics.getWidth() / 2) - (113 / 2) ,(Gdx.graphics.getHeight() / 2) - (97 / 2));
        v = new Vector2(0 ,0);
        touch = new Vector2(0,0);
        buf = new Vector2(0,0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0,0);
        buf.set(touch);

        if(buf.sub(pos).len() > V_LEN) {
            pos.add(v);
        } else {
            pos.set(touch);
        }

        //moving solder
        batch.draw(new Texture("move/survivor-move_handgun_" + (i + 1) + ".png"), pos.x, pos.y);
        if (i < 18) {
            i++;
        } else  {
            i = 0;
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        v.set(touch.cpy().sub(pos).setLength(V_LEN));
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
