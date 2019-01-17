package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class MyGdxGame extends ApplicationAdapter {
    private List<Texture> imageNames = new ArrayList<Texture>();
    private List<Chopper> choppers = new ArrayList<Chopper>();

    private long currentTime = System.nanoTime();
    private long accumulator;

    @Override
    public void create() {

        //TODO can optimze by making list of texture instead of names
        // Add images to list
        imageNames.add(new Texture("heli1.png"));
        imageNames.add(new Texture("heli2.png"));
        imageNames.add(new Texture("heli3.png"));
        imageNames.add(new Texture("heli4.png"));


        // Add three chopper entities
        for (int i = 0; i < 2; i++) {
            Chopper c = new Chopper(/*
                    new Vector2(i*200, 0), // Different position
                    new Vector2(i + 1, i + 1), // different directions*/
                    new Vector2(20, i*300), // Different position
                    new Vector2(i+1,  1), // different directions
                    imageNames
            );

            choppers.add(c);
        }
    }

    @Override
    public void render() {
        // Pink background to match sprite
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Implement a time based interval from TomGrill
        //https://badlogicgames.com/forum/viewtopic.php?f=11&t=21159
        long newTime = System.nanoTime();
        long frameTime = newTime - currentTime;
        long nanosPerLogicTick = 25000000;
        long nanosPerAnimationTick = 100000000;

        // Prevent overflow (infinite while loop)
        if (frameTime > nanosPerLogicTick) {
            frameTime = nanosPerLogicTick;
        }

        currentTime = newTime;
        accumulator += frameTime;

        long tempAccumulator = accumulator;

        // Checks whether it has been a change in deltatime
        while (tempAccumulator >= nanosPerLogicTick) {


            // Update position, direction and detect and correct for collision
            for (Chopper c : choppers) {
                // Checks for sprite bouncing the Vertical walls (spritelength = 162px)
                if (c.getPosition().x >= Gdx.graphics.getWidth() - c.getImg().getWidth() || c.getPosition().x < 0) {
                    // Change direction X-direction
                    c.changeDirectionX();
                }

                // Checks for sprite bouncing (colliding) the horizontal walls (spritelength = 65px)
                if (c.getPosition().y >= Gdx.graphics.getHeight() - c.getImg().getHeight() || c.getPosition().y < 0) {
                    // Changes direction in Y-direction
                    c.changeDirectionY();
                }

                c.updatePosition();

                for (Chopper chopper : choppers) {
                    if (c != chopper) {
                        if(collideX(c, chopper) && collideY(c, chopper)){
                            if(abs(c.getPosition().x - chopper.getPosition().x)
                                    > abs(c.getPosition().y - chopper.getPosition().y)){
                                c.changeDirectionY();
                                chopper.changeDirectionY();
                                System.out.println("Y");
                            } else if(abs(c.getPosition().x - chopper.getPosition().x)
                                    < abs(c.getPosition().y - chopper.getPosition().y)){
                                c.changeDirectionX();
                                chopper.changeDirectionX();
                                System.out.println("X");
                            }

                            else{
                                c.changeDirectionX();
                                chopper.changeDirectionX();
                                c.changeDirectionY();
                                chopper.changeDirectionY();
                                System.out.println("XY");
                            }
                        }
                    }
                }


                tempAccumulator -= nanosPerLogicTick;
            }

            // Checks whether to update animation
            while (accumulator >= nanosPerAnimationTick) {
                // For helicopters in list
                for (Chopper c : choppers) {
                    c.updateAnimation();
                }
                accumulator -= nanosPerAnimationTick;
            }

            // Draw choppers
            for (Chopper c : choppers) {
                c.draw();
            }
        }
    }

    @Override
    public void dispose() {
        // Draw choppers
        for (Chopper c : choppers) {
            c.dispose();
        }
    }

    //TODO add collision detect and correct
    //Check for collision towards all other choppers: new for loop.
    //Check must take into account what parts are colliding.
    //Left wall will always collide with right wall, bottom with top.
    private boolean collideX(Chopper chopper1, Chopper chopper2) {
        if(chopper1.getPosition().x >= chopper2.getPosition().x
                && chopper1.getPosition().x <= chopper2.getPosition().x + chopper2.getImg().getWidth())
        {
            return true;
        }
        else{return false;}
    }

    private boolean collideY(Chopper chopper1, Chopper chopper2) {
        if(chopper1.getPosition().y >= chopper2.getPosition().y
                && chopper1.getPosition().y <= chopper2.getPosition().y + chopper2.getImg().getHeight()){
            return true;
        }
        else{return false;}
    }
}