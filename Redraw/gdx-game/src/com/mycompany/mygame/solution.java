package com.mycompany.mygame;

import com.badlogic.gdx.math.*;

public class solution
{
	private int[] laying;
	private float fitness;

	public solution(Vector2[] cities, int[] laying){
		this.laying = laying;
		for(int i=0;i<cities.length-1; i++){
			fitness += distance(cities[this.laying[i]], cities[this.laying[i+1]]);
		}
	}

	public float getFit(){
		return this.fitness;
	}

	public int[] getLaying(){
		return this.laying;
	}

	public void setLaying(int[] i){
		this.laying = i;
	}

	public float distance(Vector2 v1, Vector2 v2){
		float a = 0;
		float b = 0;
		if(v1.x > v2.x)
			a = v1.x - v2.x;
		else if(v1.x < v2.x)
			a = v2.x - v1.x;
		else if(v1.x == v2.x)
			a = 0;
		if(v1.y > v2.y)
			b = v1.y - v2.y;
		else if(v1.y < v2.y)
			b = v2.y - v1.y;
		else if(v1.y == v2.y)
			b = 0;
		return (float) Math.sqrt((a*a) + (b*b));
	}
}
