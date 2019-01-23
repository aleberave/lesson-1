package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.math.MatrixUtils;

public class MenuScreen extends Base2DScreen {

    private static final float V_LEN = 0.005f;

    Texture img;
    Texture background;

    Vector2 pos;
    Vector2 v;

    Vector2 tou;
    Vector2 buf;

    @Override
    public void show() {
        super.show();
        background = new Texture("bg.png");
        img = new Texture("badlogic.jpg");
        pos = new Vector2(-0.5f, -0.5f);
        v = new Vector2(0, 0);
        tou = new Vector2(0,0);
        buf = new Vector2(0, 0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        buf.set(tou);
        if(buf.sub(pos).len() > V_LEN) {
            pos.add(v);
        } else pos.set(tou);

        batch.begin();
        batch.draw(background, -0.5f, -0.5f, 1f, 1f);
        batch.draw(img, pos.x, pos.y, 0.5f, 0.5f);
        batch.end();
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
        super.touchDown(screenX, screenY, pointer, button);
        tou.set(getTouch());
        v.set(tou.cpy().sub(pos).setLength(V_LEN));
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case 19: v.set(0,0.001f);
                break;
            case 20: v.set(0,-0.001f);
                break;
            case 21: v.set(-0.001f, 0);
                break;
            case 22: v.set(0.001f, 0);
                break;
            default: ;
                break;
        }
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }
}
