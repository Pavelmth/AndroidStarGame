package ru.pavel.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.pavel.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    // 113 x 97
    Texture img;
    Texture background;
    Texture rifle;

    Vector2 pos;
    Vector2 v;
    Vector2 buf;

    //
    int pace = 0;
    private static final float V_LEN = 0.001f;
    private final float SURVIVOR_WIDTH = 0.2f;
    private final float SURVIVOR_HIGHT = 0.2f * 0.858f;

    @Override
    public void show() {
        super.show();
        background = new Texture("grass.jpg");
        //img = new Texture("move/survivor-move_handgun_0.png");
        rifle = new Texture("apocalypse/rifle.png");
        pos = new Vector2(0,0);
        v = new Vector2(0 ,0);
        buf = new Vector2(0,0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, - 1f ,- 0.5f,2, 1);
        batch.draw(rifle,0.5f,0.5f, 0.5f, 0.5f);

        buf.set(touch);

        batch.draw(new Texture("move/survivor-move_handgun_" + pace + ".png"), pos.x - (SURVIVOR_WIDTH / 2), pos.y - (SURVIVOR_HIGHT / 2), SURVIVOR_WIDTH, SURVIVOR_HIGHT);

        if(buf.sub(pos).len() > V_LEN) {
            pos.add(v);
            //walk
            if (pace < 18) {
                pace++;
            } else  {
                pace = 0;
            }
        } else {
            pos.set(touch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        img.dispose();
        rifle.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX,screenY,pointer,button);
        v.set(touch.cpy().sub(pos).setLength(V_LEN));
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
