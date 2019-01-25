package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;

public class Sprite extends Rect {

    protected float angle; // угол поворота картинки
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;

    public Sprite(TextureRegion region) {
        if (regions == null){
            throw new NullPointerException("Create Sprite with null region");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    // метод который автоматически пересчитывает ширину спрайта
    public void setHeightProportion(float height){
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height*aspect);
    }

    public void resize(Rect worldBounds){

    }

     public void update(float delta){

     }

     public boolean touchDown(Vector2 touch, int pointer){
        return false;
     }

     public boolean touchUp(Vector2 touch, int pointer){
        return false;
     }

    public void draw(SpriteBatch batch){
        batch.draw(regions[frame], getLeft(), getBottom(), halfWidth, halfHeight, getWidth(), getHeight(), scale, scale, angle);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

}
