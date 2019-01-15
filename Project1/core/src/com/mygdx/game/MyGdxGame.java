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
    private Controller controller;

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
		img = new Texture("heli1.png"); // Replace with heli img
		controller = new Controller();
	}

	@Override
	public void render () {
		handleInput();
        // Pink background to match sprite
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Implement a time based interval from TomGrill
        //https://badlogicgames.com/forum/viewtopic.php?f=11&t=21159
	    long newTime = System.nanoTime();
	    long frameTime = newTime - currentTime;
        long nanosPerLogicTick = 25000000;

        // Prevent overflow (infinite while loop)
	    if (frameTime > nanosPerLogicTick) {
	        frameTime = nanosPerLogicTick;
        }

        currentTime = newTime;
	    accumulator += frameTime;

	    // Checks whether it has been a change in deltatime
	    while (accumulator >= nanosPerLogicTick) {

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

	        accumulator -= nanosPerLogicTick;
        }

		// Convert to sprite and flip the image based on direction
        Sprite sprite = new Sprite(img);
        sprite.flip(velocity.x >= 0, false);

		batch.begin();
		batch.draw(sprite, position.x, position.y);
		batch.end();

		controller.draw();
	}

	public void handleInput(){
		if(controller.isUpPressed()) {
			velocity.y = 2;
		}
		else if(controller.isDownPressed()){
			velocity.y = -2;
		}
		else{
			velocity.y = 0;
		}
		if(controller.isLeftPressed()){
			velocity.x = -2;
		}
		else if(controller.isRightPressed()){
			velocity.x = 2;
		}
		else{
			velocity.x = 0;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
        img.dispose();
	}
}

/* TODO
- Helicopter doesn't keep orientation
- Bug when pushing it against bottom, up makes it go down. Probably because of the bounce
	implementation.
 */
