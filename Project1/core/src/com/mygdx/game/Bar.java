package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

class Bar {
    private ShapeRenderer shapeRenderer;
    private Vector2 size;
    private Vector2 position;
    private float velocityY = 0;

    Bar(float sizeX, float sizeY, float positionX, float positionY){
        size = new Vector2(sizeX, sizeY);
        position = new Vector2(positionX, positionY);
    }

    public void draw(){

    }

    public void dispose(){

    }
}
