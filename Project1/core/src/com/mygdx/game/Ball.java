package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

class Ball {
    private ShapeRenderer shapeRenderer;
    private Vector2 size;
    private Vector2 position;
    private Vector2 velocity;

    Ball(int sizeX, int sizeY, int positionX, int positionY, int velocityX, int velocityY){
        size = new Vector2(sizeX, sizeY);
        position = new Vector2(positionX, positionY);
        velocity = new Vector2(velocityX, velocityY);
    }

    public void draw(){
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(position.x,position.y,size.x,size.y);
        shapeRenderer.end();
    }

    public void updatePosition(){
        position.x += velocity.x;
        position.y += velocity.y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void changeVelocityY(){
        velocity.y *= -1;
    }

    public void dispose(){
        shapeRenderer.dispose();
    }
}
