package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    //Rendering loop variables
    private long currentTime = System.nanoTime();
    private long accumulator;

    //Game variables and actors
    private Controller controller;
    private Rectangle ball;
    private Rectangle player;
    private Rectangle opponent;

    private Vector2 score = new Vector2(0,0);
    private float playerSpeed = 2;
    private Vector2 startingPosition;
    private Vector2 startingVelocity;

    private SpriteBatch batch;
    private BitmapFont font;

    @Override
    public void create() {
        startingPosition = new Vector2(Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2);
        startingVelocity =  new Vector2(1,3);

        controller = new Controller();
        ball = new Rectangle(10, 10, startingPosition.x,
                startingPosition.y, startingVelocity.x, startingVelocity.y);
        player = new Rectangle(10, 150, 0, 0, 0, 0);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
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

            handleInput();

            ball.updatePosition();
            player.updatePosition();

            checkForScore();

            //TODO update position of AI bar
            //TODO check ball for collision with bar and update its x-velocity
            //TODO more advanced, update ball's y-velocity based on y-velocity of bar

            accumulator -= nanosPerLogicTick;
        }

	    //Rendering
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        controller.draw();
        ball.draw();
        player.draw();

        //Score
        batch.begin();
        font.draw(batch,  (int)score.x + " - " + (int)score.y,Gdx.graphics.getWidth()/2 - 10,
                Gdx.graphics.getHeight() - 10);
        batch.end();
    }

    //Handle controller input for the player bar
    public void handleInput(){
        if(controller.isUpPressed()) {
            player.setVelocityY(playerSpeed);
        }
        else if(controller.isDownPressed()){
            player.setVelocityY(-playerSpeed);
        }
        else{
            player.setVelocityY(0);
        }
    }

    public void checkForScore(){
        if(ball.getPosition().x < 0){
            score.x += 1;
            ball.setPosition(startingPosition);
            ball.setVelocityY(startingVelocity.y);
        }
        else if(ball.getPosition().x > Gdx.graphics.getWidth() - ball.getSize().x){
            score.y += 1;
            ball.setPosition(startingPosition);
            ball.setVelocityY(startingVelocity.y);
        }
    }

    @Override
    public void dispose() {
        controller.dispose();
        ball.dispose();
        player.dispose();
        batch.dispose();
        font.dispose();
        opponent.dispose();
    }
}