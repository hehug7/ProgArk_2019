package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Score {
    private Vector2 scoreValues = new Vector2(0,0);
    private SpriteBatch batch;
    private BitmapFont font;

    Score(){
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
    }

    public void draw(){
        //Score
        batch.begin();
        font.draw(batch,  (int)scoreValues.y + " - " + (int)scoreValues.x, Gdx.graphics.getWidth()/2 - 10,
                Gdx.graphics.getHeight() - 10);
        batch.end();
    }

    public void incrementX(){
        scoreValues.x++;
    }

    public void incrementY(){
        scoreValues.y++;
    }

    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
