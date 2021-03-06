package com.amine.evolution;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.math.*;

public class MyGdxGame implements ApplicationListener
{
	//Constants Variables:
	public final float width = 1280;
	public final float height = 720;
	public final float ptm = 100;
	//Variables Defenitions:
	private OrthographicCamera camera;
	private Viewport port;
	private ShapeRenderer sr;
	private World world;
	private Box2DDebugRenderer b2dr;
	
	@Override
	public void create()
	{
		camera = new OrthographicCamera(width, height);
		camera.position.set(width/2, height/2, 0);
		camera.update();
		port = new StretchViewport(width, height, camera);
		b2dr = new Box2DDebugRenderer();
		sr = new ShapeRenderer();
		world = new World(new Vector2(0, -9.81f), true);
		
	}

	@Override
	public void render()
	{        
	    world.step(Gdx.graphics.getDeltaTime(), 2, 6);
		b2dr.render(world, camera.combined);
		Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeRenderer.ShapeType.Filled);
		
		
		
		sr.end();
	}

	@Override
	public void dispose()
	{
		sr.dispose();
		b2dr.dispose();
		world.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		port.update(width, height);
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
