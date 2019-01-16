package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Chopper {

    // Coordinates of sprite/img
    private Vector2 position;

    // Speed/direction for sprite
    private Vector2 velocity;

    // List of image names
    private List<String> imageNames = new ArrayList<String>();

    //Determines starting frame of animation
    private static int counter = 0;
    private int frame = 0;

    Chopper(Vector2 startPos, Vector2 startVelocity){
        imageNames.add("heli1.png");
        imageNames.add("heli2.png");
        imageNames.add("heli3.png");
        imageNames.add("heli4.png");

        position = startPos;
        velocity = startVelocity;

        if(counter >= imageNames.size()){
            counter = 0;
        }
        frame = counter++;
    }

    //Update position and animation
    public void updateAnimation(){
        if(++frame >= imageNames.size()){
            frame = 0;
        }
    }

    public void updatePosition(){
        //TODO
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

    public void draw(){
        //TODO
    }
}
