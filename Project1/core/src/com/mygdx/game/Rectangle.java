package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

class Rectangle {
    private ShapeRenderer shapeRenderer;
    private Vector2 size;
    private Vector2 position;
    private Vector2 velocity;

    Rectangle(float sizeX, float sizeY, float positionX, float positionY, float velocityX,
              float velocityY){
        size = new Vector2(sizeX, sizeY);
        position = new Vector2(positionX, positionY);
        velocity = new Vector2(velocityX, velocityY);
        shapeRenderer = new ShapeRenderer();
    }

    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(position.x,position.y,size.x,size.y);
        shapeRenderer.end();
    }

    /*Increments the position with speed, checks for collision with roof and floor, sets position
    and flips y-velocity accordingly
     */
    public void updatePosition(){
        position.x += velocity.x;
        position.y += velocity.y;

        if(position.y <= 0){
            setPosition(new Vector2(position.x, 0));
            changeVelocityY();
        }
        else if(position.y >= Gdx.graphics.getHeight() - size.y){
            setPosition(new Vector2(position.x, Gdx.graphics.getHeight() - size.y));
            changeVelocityY();
        }
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

    public void setVelocityY(float velocityY){
        velocity.y = velocityY;
    }

    public Vector2 getSize() {
        return size;
    }

    public void dispose(){
        shapeRenderer.dispose();
    }
}
