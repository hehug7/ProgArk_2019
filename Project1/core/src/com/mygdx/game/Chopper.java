package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Chopper {

    // Coordinates of sprite/img
    private Vector2 position;

    // Speed/direction for sprite
    private Vector2 velocity;

    // List of image names
    private List<Texture> imageNames;
    private Texture img;
    private Sprite sprite;
    private SpriteBatch batch = new SpriteBatch();

    //Determines starting frame of animation
    private static int counter = 0;
    private int frameIdx = 0;

    Chopper(Vector2 startPos, Vector2 startVelocity, List<Texture> imageNames){
        this.imageNames = imageNames;
        position = startPos;
        velocity = startVelocity;

        // Image
        img = imageNames.get(frameIdx);
        sprite = new Sprite(img);

        if(counter >= imageNames.size()){
            counter = 0;
        }
        frameIdx = counter++;
    }

    //Update position and animation
    public void updateAnimation(){
        if(++frameIdx >= imageNames.size()){
            frameIdx = 0;
        }
        img = imageNames.get(frameIdx);
    }

    public void updatePosition(){
        position = new Vector2(position.x + velocity.x, position.y + velocity.y);
    }

    public void changeDirectionX(){
        velocity.x *= -1;
    }

    public void changeDirectionY(){
        velocity.y *= -1;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getImg() {
        return img;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void draw(){
        // Update sprite
        sprite.setRegion(img);
        // Convert to sprite and flip the image based on direction
        sprite.flip(velocity.x >= 0, false);

        batch.begin();
        batch.draw(sprite, position.x, position.y);
        batch.end();

    }

    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}