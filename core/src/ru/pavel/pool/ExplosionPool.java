package ru.pavel.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.pavel.base.SpritesPool;
import ru.pavel.sprite.game.Explosion;
import ru.pavel.sprite.menu.ExitButton;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureRegion region;
    private Sound exposionSound;

    public ExplosionPool(TextureAtlas atlas) {
        this.region = atlas.findRegion("explosion");
        this.exposionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

    }

    @Override
    protected Explosion newObject() {
        return new Explosion(region, 9, 9 ,74, exposionSound);
    }

    public void dispose() {
        exposionSound.dispose();
        super.dispose();
    }
}
