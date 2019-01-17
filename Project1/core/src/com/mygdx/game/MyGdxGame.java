package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import org.omg.CORBA.CharHolder;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
    private List<String> imageNames = new ArrayList<String>();
    private List<Chopper> choppers = new ArrayList<Chopper>();

    // Coordinates of sprite/img
	private Vector2 position;

    // Speed/direction for sprite
    private Vector2 velocity;

    private long currentTime = System.nanoTime();
    private long accumulator;

	@Override
	public void create () {
		position = new Vector2(0,0);
		velocity = new Vector2(2, 5);
		batch = new SpriteBatch();

		// Add images to list
		imageNames.add("heli1.png");
		imageNames.add("heli2.png");
		imageNames.add("heli3.png");
		imageNames.add("heli4.png");

		// Add three chopper entities
		for (int i = 0; i < 3; i ++) {
		    Chopper c = new Chopper(
                new Vector2(0,0), // Different position
                new Vector2(i+1,i+1), // different directions
                imageNames
            );

		    choppers.add(c);
        }
	}

	@Override
	public void render () {
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


	        // Update position, direction and //TODO stagedraw/collisions to other choppers?
            for (Chopper c : choppers) {
                // Checks for sprite bouncing the Vertical walls (spritelength = 162px)
                if (c.getPosition().x >= Gdx.graphics.getWidth() - 162 || c.getPosition().x < 0) {
                    // Change direction X-direction
                    c.changeDirectionX();
                }

                // Checks for sprite bouncing (colliding) the horizontal walls (spritelength = 65px)
                if (c.getPosition().y >= Gdx.graphics.getHeight() - 65 || c.getPosition().y < 0) {
                    // Changes direction in Y-direction
                    c.changeDirectionY();
                }

                c.updatePosition();

            }

	        tempAccumulator -= nanosPerLogicTick;
        }

        // Checks whether to update animation
		while(accumulator >= nanosPerAnimationTick){
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

	@Override
	public void dispose () {
        // Draw choppers
        for (Chopper c : choppers) {
            c.dispose();
        }
	}
}