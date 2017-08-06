package com.mycompany.mygame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.glutils.*;
import java.util.*;
import com.badlogic.gdx.math.*;

public class MyGdxGame implements ApplicationListener
{
	OrthographicCamera camera;
	Viewport view;
	ShapeRenderer sr;
	BitmapFont font;
	SpriteBatch batch;
	population ppt;
	Vector2[] cities;
	int r1, r2;

	@Override
	public void create()
	{
		camera = new OrthographicCamera(720, 1280);
		camera.position.set(720/2, 1280/2, 0);
		view = new StretchViewport(720, 1280, camera);
		sr = new ShapeRenderer();
		font = new BitmapFont();
		batch = new SpriteBatch();
		Random r = new Random();
		cities = new Vector2[8];
		for(int i=0; i<cities.length; i++){
			cities[i] = new Vector2(r.nextInt(700)+10, r.nextInt(620)+10+640);
		}
		ppt = new population(100, 0f, cities);
		ppt.generateRandom();
	}

	@Override
	public void render()
	{        
	    Gdx.gl.glClearColor(1, .98f, 1, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sr.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);

		sr.begin(ShapeRenderer.ShapeType.Filled);

		sr.setColor(Color.PURPLE);
		for(int i=0; i<cities.length-1; i++)
			sr.rectLine(cities[ppt.getBest().getLaying()[i]].x, cities[ppt.getBest().getLaying()[i]].y, cities[ppt.getBest().getLaying()[i+1]].x, cities[ppt.getBest().getLaying()[i+1]].y, 3);

		sr.setColor(Color.BLACK);
		for(int i=0; i<cities.length; i++)
			sr.circle(cities[i].x, cities[i].y, 8);

		sr.end();

		batch.begin();

		font.setColor(Color.ORANGE);
		font.setScale(1.5f);
		for(int i=0; i<cities.length; i++)
			font.draw(batch, ""+i, cities[i].x-8, cities[i].y-8);

		font.setColor(Color.BLUE);
		font.setScale(1.9f);
		for(int i=0; i<cities.length; i++)
			font.draw(batch, "b["+i+"]: "+ppt.getBest().getLaying()[i], 30, 600-(50*i));
		font.setColor(Color.RED);
		for(int j = 0; j<ppt.getPopulation().length; j++)
			for(int i=0; i<cities.length; i++)
				font.draw(batch, "s["+i+"]: "+ppt.getPopulation()[j].getLaying()[i], 170, 600-(50*i));

		font.setScale(2.1f);
		font.setColor(Color.PURPLE);
		font.draw(batch, "Generations: "+ppt.getGenerations(), 350, 600);
		font.setScale(1.9f);
		font.setColor(Color.BLACK);
		font.draw(batch, "B fitness: "+ppt.getBest().getFit(), 350, 500);
		
		font.setScale(1.9f);
		font.setColor(Color.BLUE);
		font.draw(batch, ""+ppt.getComplete()+"% Complete", 350, 400);
		
		batch.end();
		if(Gdx.input.justTouched())
			ppt.crossover();
	}

	@Override
	public void dispose()
	{
		font.dispose();
		batch.dispose();
		sr.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		view.update(width, height);
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}

}
