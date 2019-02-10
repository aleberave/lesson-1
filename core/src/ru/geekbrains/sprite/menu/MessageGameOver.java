package ru.geekbrains.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class MessageGameOver extends Sprite {

    public MessageGameOver(TextureAtlas atlas){
        super(atlas.findRegion("message_game_over"));
        setHeightProportion(0.1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        pos.set(0, 0.05f);
    }

}
