package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;

public class MenuScreen extends Base2DScreen {

    private static final float V_LEN = 2.5f;

    Texture img;
    Texture background;

    Vector2 pos;
    Vector2 v;

    @Override
    public void show() {
        super.show();
        background = new Texture("bg.png");
        img = new Texture("badlogic.jpg");
        pos = new Vector2(-0.5f, -0.5f);
        v = new Vector2(0.002f, 0.002f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, -0.5f, -0.5f, 1f, 1f);
        batch.draw(img, pos.x, pos.y, 0.5f, 0.5f);
        batch.end();
        pos.add(v);
//        if((int)(Math.abs(touch.len() - pos.len())) < 0.0001) {
//            v.set(0,0);
//        } else pos.add(v);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        System.out.println("touchDown " + screenX + " " + (Gdx.graphics.getHeight() - screenY));
//        touch = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
//        v = touch.sub(pos);
//        v.nor();
//        touch = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case 19: v.set(0,1);
                break;
            case 20: v.set(0,-1);
                break;
            case 21: v.set(-1, 0);
                break;
            case 22: v.set(1, 0);
                break;
            default: ;
                break;
        }
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }
}
