package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.base.Base2DScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.pool.PlusHPPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.game.Bullet;
import ru.geekbrains.sprite.game.Enemy;
import ru.geekbrains.sprite.game.MainShip;
import ru.geekbrains.sprite.game.MessageGameOver;
import ru.geekbrains.sprite.game.PlusHP;
import ru.geekbrains.sprite.game.ProgressBar;
import ru.geekbrains.sprite.game.StartNewGame;
import ru.geekbrains.utils.EnemyEmitter;
import ru.geekbrains.utils.Font;
import ru.geekbrains.utils.PlusHpEmitter;

public class GameScreen extends Base2DScreen {

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private enum State {PLAYING, GAME_OVER}

    private TextureAtlas atlas;
    private TextureAtlas atlas2;
    private Texture bg;
    private Background background;
    private Star star[];
    private MainShip mainShip;

    private MessageGameOver messageGameOver;
    private StartNewGame startNewGame;
    private ProgressBar progressBar;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private EnemyEmitter enemyEmitter;

    private PlusHPPool plusHPPool;
    private PlusHpEmitter plusHpEmitter;

    private Music music;

    private State state;

    private Font font;
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHP = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();

    private Font fontL;

    int frags = 0;
    int levels = 0;

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
        atlas2 = new TextureAtlas("hp/boxes.pack");
        star = new Star[64];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, worldBounds);
        enemyPool = new EnemyPool(bulletPool, worldBounds, explosionPool, mainShip);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool, worldBounds);
        plusHPPool = new PlusHPPool(worldBounds, mainShip);
        plusHpEmitter = new PlusHpEmitter(atlas2, plusHPPool, worldBounds, this);

        messageGameOver = new MessageGameOver(atlas);
        startNewGame = new StartNewGame(atlas, this);
        atlas2 = new TextureAtlas("progressbar/mys.pack");
        progressBar = new ProgressBar(atlas2, mainShip);
        this.font = new Font("font/font.fnt", "font/font.png");
        this.font.setSize(0.025f);
        this.fontL = new Font("font/font.fnt", "font/font.png");
        this.fontL.setSize(0.05f);
        startNewGame();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void update(float delta) {
        for (Star aStar : star) {
            aStar.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        progressBar.update(delta);
        switch (state){
            case PLAYING:
                mainShip.update(delta);
                mainShip.changeLevelAddHp(levels);
                bulletPool.updateActiveSprites(delta);
                enemyPool.updateActiveSprites(delta);
                enemyEmitter.generate(delta, frags);
                plusHPPool.updateActiveSprites(delta);
                plusHpEmitter.generate(delta);
                break;
            case GAME_OVER:
                break;
        }
    }

    private void checkCollisions() {
        if(state == State.PLAYING) {
            List<Enemy> enemyList = enemyPool.getActiveObjects();
            for (Enemy enemy : enemyList) {
                if (enemy.isDestroyed()) {
                    continue;
                }
                float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
                if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                    enemy.destroy();
                    mainShip.damage(enemy.getDamage());
                    progressBar.changeBar();
                    return;
                }
            }
            List<Bullet> bulletList = bulletPool.getActiveObjects();

            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    progressBar.changeBar();
                    bullet.destroy();
                }
            }

            for (Enemy enemy : enemyList) {
                if (enemy.isDestroyed()) {
                    continue;
                }
                for (Bullet bullet : bulletList) {
                    if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                        continue;
                    }
                    if (enemy.isBulletCollision(bullet)) {
                        enemy.damage(mainShip.getDamage());
                        if(enemy.isDestroyed()){
                            frags++;
                        }
                        bullet.destroy();
                    }
                }
            }

            List<PlusHP> plusHPList = plusHPPool.getActiveObjects();
            for (PlusHP plusHP : plusHPList) {
                if(mainShip.isHpCollisionUp(plusHP) || mainShip.isHpCollisionDown(plusHP)){
                    mainShip.addHp(plusHP);
                }
            }
        }
    }

    private void deleteAllDestroyed() {
        if(mainShip.isDestroyed()){
            state = State.GAME_OVER;
        }
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        plusHPPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star aStar : star) {
            aStar.draw(batch);
        }
        switch (state){
            case PLAYING:
                mainShip.draw(batch);
                bulletPool.drawActiveSprites(batch);
                enemyPool.drawActiveSprites(batch);
                plusHPPool.drawActiveSprites(batch);
                break;
            case GAME_OVER:
                messageGameOver.draw(batch);
                startNewGame.draw(batch);
                break;
        }
        explosionPool.drawActiveSprites(batch);
        progressBar.draw(batch);
        printInfo();
        printLevel();
        batch.end();
    }

    public void printInfo(){
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbHP.append(HP), worldBounds.pos.x - 0.03f, worldBounds.getTop(), Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(levels), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    public void printLevel(){
        if(levels != enemyEmitter.getLevel()){
            levels = enemyEmitter.getLevel();
            sbLevel.setLength(0);
            fontL.draw(batch, sbLevel.append(LEVEL).append(levels), worldBounds.pos.x, worldBounds.pos.y, Align.center);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star aStar : star) {
            aStar.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        progressBar.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        plusHPPool.dispose();
        mainShip.dispose();
        music.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer);
        } else {
            startNewGame.touchDown(touch, pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer);
        } else {
            startNewGame.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }

    public void startNewGame(){
        state = State.PLAYING;

        mainShip.startNewGame();
        frags = 0;
        enemyEmitter.setLevel(1);

        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        plusHPPool.freeAllActiveObjects();
    }

    public int getLevels() {
        return levels;
    }
}
