package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

/* PONG
Make a ball, score, two bars and one controller.
Ball and bars are similar enough to use the same general class Rectangle.
Ball bounces off floor, ceiling and bars.
Game checks if someone scored by checking the x-coordinate of the ball against the borders of the
    screen, if so it updates the score and re-spawns the ball.
Controller controls left bar.
Right bar is simple AI with max speed that centers bar on the y-coordinate of the ball
 */
public class MyGdxGame extends ApplicationAdapter {

    //Rendering variables
    private long currentTime = System.nanoTime();
    private long accumulator;

    //Game variables and actors
    private Controller controller;
    private Rectangle ball;
    private Rectangle player;
    private Rectangle opponent;
    private Vector2 score = new Vector2(0,0);

    @Override
    public void create() {
        controller = new Controller();
        ball = new Rectangle(10, 10, Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2, 1, 3);
        player = new Rectangle(10, 150, 0, 0, 0, 0);
    }

	@Override
	public void render () {
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

        while(accumulator >= nanosPerLogicTick){

            ball.updatePosition();

            //Check ball for roof/floor collision
            if(ball.getPosition().y <= 0){
                ball.setPosition(new Vector2(ball.getPosition().x, 0));
                ball.changeVelocityY();
            }
            else if(ball.getPosition().y >= Gdx.graphics.getHeight()){
                ball.setPosition(new Vector2(ball.getPosition().x, Gdx.graphics.getHeight()));
                ball.changeVelocityY();
            }

            //TODO update position of player bar based on velocity/controller input
            //TODO update position of AI bar
            //TODO check bars for collision with floor and roof
            //TODO check ball for collision with bar and update its x-velocity
            //TODO more advanced, update ball's y-velocity based on y-velocity of bar
            //TODO check ball for scoring and reset

            accumulator -= nanosPerLogicTick;
        }

	    //Rendering
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        controller.draw();
        ball.draw();
        player.draw();
    }

    @Override
    public void dispose() {
        controller.dispose();
        ball.dispose();
        player.dispose();
    }
}