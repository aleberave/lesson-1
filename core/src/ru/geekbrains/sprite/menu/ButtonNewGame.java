package ru.geekbrains.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonNewGame extends ScaledTouchUpButton {

    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        setHeightProportion(0.05f);
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        pos.set(0, -0.05f);
    }

    @Override
    public void action() {
        gameScreen.bng();
    }
}
