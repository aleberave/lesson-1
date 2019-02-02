package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.utils.Regions;

public class Sprite extends Rect {

    protected float angle; // угол поворота картинки
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;
    private boolean isDestroyed; // объект на удаление

    public Sprite(){

    }

      public Sprite(TextureRegion region) {
        if (region == null){
            throw new NullPointerException("Create Sprite with null region");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        if (region == null){
            throw new NullPointerException("Create Sprite with null region");
        }
        this.regions = Regions.split(region, rows, cols, frames);
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

    // помечает объект на удаление
    public void destroy(){
        this.isDestroyed = true;
    }

    // позволяет повторное использование объекта
    public void flushDestroyed(){
        this.isDestroyed = false;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
