package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


// controller code and images from BrentAureli @github
public class Controller {
    private Viewport viewport;
    private Stage stage;
    private boolean upPressed, downPressed;
    private OrthographicCamera cam;

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public Controller(){
        cam = new OrthographicCamera();
        viewport = new FitViewport(800,480, cam);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.left().bottom();

        Image upImage = new Image(new Texture("up.png"));
        upImage.setSize(50,50);
        upImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image downImage = new Image(new Texture("down.png"));
        downImage.setSize(50,50);
        downImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        table.add(upImage).size(upImage.getWidth(), upImage.getHeight());
        table.row().pad(5,5,5,5);
        table.add(downImage).size(downImage.getWidth(), downImage.getHeight());

        stage.addActor(table);
    }

    public void draw(){
        stage.draw();
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    public void dispose(){
        stage.dispose();
    }
}