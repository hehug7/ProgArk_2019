package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
    private Texture img;

    // Coordinates of sprite/img
	private Vector2 position;

    // Speed/direction for sprite
    private Vector2 velocity;

    private long currentTime = System.nanoTime();
    private long accumulator;
    private long tempAccumulator;

	@Override
	public void create () {
		position = new Vector2(0,0);
		velocity = new Vector2(2, 5);
		batch = new SpriteBatch();
		img = new Texture("heli1.png"); // Replace with heli img
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

	    tempAccumulator = accumulator;

	    // Checks whether it has been a change in deltatime
	    while (tempAccumulator >= nanosPerLogicTick) {

	        // Checks for sprite bouncing the Vertical walls (spritelength = 162px)
	        if (position.x >= Gdx.graphics.getWidth() - 162 || position.x < 0) {
				// Change direction X-direction
				velocity.x *= -1;
			}

            // Checks for sprite bouncing (colliding) the horizontal walls (spritelength = 65px)
            if (position.y >= Gdx.graphics.getHeight() - 65 || position.y < 0) {
	            // Changes direction in Y-direction
                velocity.y *= -1;
			}

            // Apply the next coordinates to sprite
            position.x += velocity.x;
	        position.y += velocity.y;

	        tempAccumulator -= nanosPerLogicTick;
        }

        // Checks whether to update animation
		while(accumulator >= nanosPerAnimationTick){
			// For helicopters in list
			// heli.updateAnimation();
		}


		// Convert to sprite and flip the image based on direction
        Sprite sprite = new Sprite(img);
        sprite.flip(velocity.x >= 0, false);

		batch.begin();
		batch.draw(sprite, position.x, position.y);
		batch.end();
		//TODO add helicopter to stage
	}

	@Override
	public void dispose () {
		batch.dispose();
        img.dispose();
	}
}