package com.mygdx.dragmania;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.dragmania.controllers.LobbyController;
import com.mygdx.dragmania.views.HelpView;
import com.mygdx.dragmania.views.MainMenuView;
import com.mygdx.dragmania.views.ViewManager;

public class DragMania extends ApplicationAdapter {
	//public static final int WIDTH = 480;
	//public static final int HEIGHT = 800;
	SpriteBatch batch;
	Texture img;

	private ViewManager viewManager;
	
	@Override
	public void create () {
		viewManager = ViewManager.getInstance();
		viewManager.push(new MainMenuView(viewManager));
		LobbyController.getInstance().connectToServer();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewManager.render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
	}
}
