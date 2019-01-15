package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
    private Texture img;

	private Stage stage;
    private Touchpad touchpad;
    private Skin skin;
    Controller controller;

    // Coordinates of sprite/img
    private int X = 0;
    private int Y = 0;

    // Speed/direction for sprite
    private int DX = 0;
    private int DY = 0;

    private long currentTime = System.nanoTime();
    private long accumulator;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("heli1.png"); // Replace with heli img

		/*
		stage = new Stage();
		Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
		touchpad = new Touchpad(20, touchpadStyle);
		touchpad.setBounds(15, 15, 100, 100);
		stage.addActor(touchpad);
		*/

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
	        if (X >= Gdx.graphics.getWidth() - 162 || X < 0) {
	            // Change direction X-direction
                DX *= -1;
            }

            // Checks for sprite bouncing (colliding) the horizontal walls (spritelength = 65px)
            if (Y >= Gdx.graphics.getHeight() - 65 || Y < 0) {
	            // Changes direction in Y-direction
                DY *= -1;
            }

            // Apply the next coordinates to sprite
            X += DX;
	        Y += DY;

	        accumulator -= nanosPerLogicTick;
        }

		// Convert to sprite and flip the image based on direction
        Sprite sprite = new Sprite(img);
        sprite.flip(DX >= 0, false);

		batch.begin();
		batch.draw(sprite, X, Y);
		batch.end();

		controller.draw();

		/*
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		*/
	}

	public void handleInput(){
		if(controller.isUpPressed()) {
			DY = 2;
		}
		else{
			DY = 0;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
        img.dispose();
	}
}
