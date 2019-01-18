package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/* PONG
Make a ball, score, two bars and one controller.
The bars and controller behave differently -> split into two classes.
Ball bounces off floor, ceiling and bars.
Game checks if someone scored by checking the x-coordinate of the ball against the borders of the
    screen, if so it updates the score and re-spawns the ball.
Controller controls left bar.
Right bar is simple AI with max speed that centers bar on the y-coordinate of the ball
 */
public class MyGdxGame extends ApplicationAdapter {
    private long currentTime = System.nanoTime();
    private long accumulator;

    private Controller controller;

    @Override
    public void create() {
        controller = new Controller();
    }

	@Override
	public void render () {
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

	    //Rendering
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        controller.draw();
	}

    @Override
    public void dispose() {
        controller.dispose();
    }
}