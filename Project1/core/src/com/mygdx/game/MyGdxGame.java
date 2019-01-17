package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
    private List<Chopper> choppers = new ArrayList<Chopper>();
    private List<Texture> imageNames = new ArrayList<Texture>();
    private long currentTime = System.nanoTime();
    private long accumulator;

    @Override
    public void create() {

        // Add images to list
        imageNames.add(new Texture("heli1.png"));
        imageNames.add(new Texture("heli2.png"));
        imageNames.add(new Texture("heli3.png"));
        imageNames.add(new Texture("heli4.png"));


        // Add three chopper entities
        for (int i = 0; i < 3; i++) {
            Chopper c = new Chopper(
                    new Vector2(i*70, i*200), // Different position
                    new Vector2(1,  1),
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


	        // Update position, direction and collision with eachother
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

                // Update choppers position per timeframe
                c.updatePosition();

                //Iterate through the choppers
                for (Chopper chopper : choppers) {
                    //Don't compare with self
                    if (c != chopper) {
                        // Check if collision
                        if(collideX(c, chopper) && collideY(c, chopper)){

                            // Check wether the entities goes the same direction (X)
                            if (c.getVelocity().x * chopper.getVelocity().x > 0) {
                                // Change direction of the chopper that is coming from behind if
                                // they collide in the same direction
                                if (c.getVelocity().x > 0 && c.getPosition().x > chopper.getPosition().x) {
                                    chopper.changeDirectionX();
                                } else {
                                    c.changeDirectionX();
                                }
                            } else { // If they collide when going in opposite direction, change direction of both
                                chopper.changeDirectionX();
                                c.changeDirectionX();
                            }

                            // Same for Y
                            // Check wether the entities goes the same direction (Y)
                            if (c.getVelocity().y * chopper.getVelocity().y > 0) {
                                // Change direction of the chopper that is coming from "behind" if
                                // they collide in the same direction on the Y-axis
                                if (c.getVelocity().y > 0 && c.getPosition().y > chopper.getPosition().y) {
                                    chopper.changeDirectionY();
                                } else {
                                    c.changeDirectionY();
                                }
                            } else {
                                // If they collide in opposite direction, change both
                                chopper.changeDirectionY();
                                c.changeDirectionY();
                            }
                        }
                    }
                }
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
    public void dispose() {
        // Dispose choppers
        for (Chopper c : choppers) {
            c.dispose();
        }
    }

    // Check for overlap in x-direction
    private boolean collideX(Chopper chopper1, Chopper chopper2) {
        return (chopper1.getPosition().x >= chopper2.getPosition().x
                && chopper1.getPosition().x <= chopper2.getPosition().x + chopper2.getImg().getWidth());
    }

    // Check for overlap in y-direction
    private boolean collideY(Chopper chopper1, Chopper chopper2) {
        return (chopper1.getPosition().y >= chopper2.getPosition().y
                && chopper1.getPosition().y <= chopper2.getPosition().y + chopper2.getImg().getHeight());
    }
}