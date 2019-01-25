package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.menu.TouchExit;

public class MenuScreen extends Base2DScreen {

    private TextureAtlas atlas;
    private Texture bg;
    private Background background;
    private Star[] star;
    private TouchExit btExit;

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
        btExit = new TouchExit(atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta){
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
    }

    public void draw(){
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        btExit.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        btExit.resize(worldBounds);
    }

    @Override
    public void hide() {
        dispose();
        super.hide();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        batch.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }
}
