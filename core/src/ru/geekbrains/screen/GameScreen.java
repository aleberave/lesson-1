package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.game.Bullet;
import ru.geekbrains.sprite.game.Enemy;
import ru.geekbrains.sprite.game.Explosion;
import ru.geekbrains.sprite.game.MainShip;
import ru.geekbrains.sprite.menu.ButtonNewGame;
import ru.geekbrains.sprite.menu.MessageGameOver;
import ru.geekbrains.utils.EnemyEmitter;

public class GameScreen extends Base2DScreen {

    private TextureAtlas atlas;
    private Texture bg;
    private Background background;
    private Star star[];
    private MainShip mainShip;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private EnemyEmitter enemyEmitter;

    private Music music;

    private ButtonNewGame buttonNewGame;
    private MessageGameOver messageGameOver;

    private Game game;
    public GameScreen(){}
    public GameScreen(Game game){ this.game = game; }

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.setVolume(0.8f);
        music.play();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        star = new Star[64];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyPool = new EnemyPool(bulletPool, worldBounds, explosionPool, mainShip);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool, worldBounds);
        buttonNewGame = new ButtonNewGame(atlas, this);
        messageGameOver = new MessageGameOver(atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
        if(!mainShip.isDestroyed()) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            explosionPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        }
    }

    private void checkCollisions(){
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for(Enemy enemy: enemyList){
            if(enemy.isDestroyed()){
                continue;
            }
            float mindist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if(enemy.pos.dst2(mainShip.pos) < mindist * mindist){
                enemy.destroy();
                mainShip.damage(enemy.getDamage());
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for(Bullet bullet: bulletList){
            if(bullet.getOwner() == mainShip || bullet.isDestroyed()){
                continue;
            }
            if(mainShip.isBulletCollision(bullet)){
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }

        for (Enemy enemy: enemyList){
            if(enemy.isDestroyed()){
                continue;
            }
            for (Bullet bullet: bulletList){
                if(bullet.getOwner() != mainShip || bullet.isDestroyed()){
                    continue;
                }
                if(enemy.isBulletCollision(bullet)){
                    enemy.damage(mainShip.getDamage());
                    bullet.destroy();
                }
            }
        }
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        if(!mainShip.isDestroyed()){
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            explosionPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        }
        if(mainShip.isDestroyed()){
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        messageGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        mainShip.dispose();
        music.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(!mainShip.isDestroyed()) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if(!mainShip.isDestroyed()) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(!mainShip.isDestroyed()) {
            mainShip.touchDown(touch, pointer);
        }
        if(mainShip.isDestroyed()) {
            buttonNewGame.touchDown(touch, pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(!mainShip.isDestroyed()) {
            mainShip.touchUp(touch, pointer);
        }
        if(mainShip.isDestroyed()) {
            buttonNewGame.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }

    public void bng(){
        if(mainShip.isDestroyed()) {
            mainShip.destroy();
            bulletPool = new BulletPool();
            explosionPool = new ExplosionPool(atlas);
            mainShip = new MainShip(atlas, bulletPool, explosionPool);
            enemyPool = new EnemyPool(bulletPool, worldBounds, explosionPool, mainShip);
            enemyEmitter = new EnemyEmitter(atlas, enemyPool, worldBounds);
            resize(worldBounds);
        }
    }
}
