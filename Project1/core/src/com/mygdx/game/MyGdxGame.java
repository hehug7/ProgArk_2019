package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;

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

    // List of bars
    private List<Rectangle> bars = new ArrayList<Rectangle>();

    private Vector2 score = new Vector2(0,0);
    private float playerSpeed = 2;
    private float opponentSpeed = 1;
    private Vector2 startingPosition;
    private Vector2 startingVelocity;

    private SpriteBatch batch;
    private BitmapFont font;

    @Override
    public void create() {
        // Starting position and velocity
        startingPosition = new Vector2(Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2);
        startingVelocity =  new Vector2(-2,-4);

        // Controller for userinput
        controller = new Controller();

        ball = new Rectangle(10, 10, startingPosition.x,
                startingPosition.y, startingVelocity.x, startingVelocity.y);

        // Left bar
        player = new Rectangle(10, 150,
                0,
                startingPosition.y-75,
                0, 0);

        // Right bar
        opponent = new Rectangle(
                10, 150,
                Gdx.graphics.getWidth()-10,
                startingPosition.y-75,
                0, 0);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);

        // adds player and opponent to ball colliding elements
        bars.add(player);
        bars.add(opponent);
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

            // Update position to all objects
            ball.updatePosition();
            player.updatePosition();
            opponent.updatePosition();

            for (Rectangle bar : bars) {
                // If bar and ball collides. Bar first and ball next
                if (collideX(bar, ball) && collideY(bar, ball)) {
                    if (ball.getPosition().x <= player.getSize().x) {

                        // Reset offset to prevent jittering
                        ball.setPosition(new Vector2(player.getSize().x, ball.getPosition().y));
                    }
                    //change direction of the ball
                    ball.changeVelocityX();

                    // If the ball collides, increase the ball's speed in the same direction
                    if (ball.getVelocity().x > 0) {
                        ball.setVelocityX(++ball.getVelocity().x);
                    } else {
                        ball.setVelocityX(--ball.getVelocity().x);
                    }
                }
            }

            // AI logic for tracking ball
            if (opponent.getPosition().y + (int) (opponent.getSize().y/2)
                    < ball.getPosition().y + (int) (ball.getSize().y/2)) {
                opponent.setVelocityY(opponentSpeed);
            } else {
                opponent.setVelocityY(-opponentSpeed);
            }

            // Checks if ball is out of bounds (goal) and updates score
            checkForScore();

            accumulator -= nanosPerLogicTick;
        }

	    //Rendering
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        controller.draw();
        ball.draw();
        player.draw();
        opponent.draw();

        //Score
        batch.begin();
        font.draw(batch,  (int)score.y + " - " + (int)score.x,Gdx.graphics.getWidth()/2 - 10,
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

    // Updates score. Reset ball when a player scores.
    // Increment AI's speed depending on player's amount of goals.
    public void checkForScore(){
        if(ball.getPosition().x < 0){
            score.x++;
            ball.setPosition(startingPosition);
            ball.setVelocityX(startingVelocity.x);
            ball.setVelocityY(startingVelocity.y);
        }
        else if(ball.getPosition().x > Gdx.graphics.getWidth() - ball.getSize().x){
            score.y++;
            opponentSpeed++;
            ball.setPosition(startingPosition);
            ball.setVelocityX(startingVelocity.x);
            ball.setVelocityY(startingVelocity.y);
        }
    }

    // Check for overlap in x-direction
    private boolean collideX(Rectangle rect1, Rectangle rect2) {
        return (rect1.getPosition().x + rect2.getSize().x >= rect2.getPosition().x
                && rect1.getPosition().x <= rect2.getPosition().x + rect2.getSize().x);
    }

    // Check for overlap in y-direction
    private boolean collideY(Rectangle rect1, Rectangle rect2) {
        return (rect1.getPosition().y <= rect2.getPosition().y
                && rect1.getPosition().y + rect1.getSize().y >= rect2.getPosition().y + rect2.getSize().y);
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