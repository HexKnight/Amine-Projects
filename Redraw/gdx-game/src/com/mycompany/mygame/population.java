package com.mycompany.mygame;

import com.badlogic.gdx.math.*;
import java.util.*;

public class population
{
	private solution[] s;
	private Vector2[] cities;
	private Random r;
	private int pNum;
	private float mutation;
	private int factorial;
	private int gens;
	private int progress;

	public population(int num, float mutation, Vector2[] cities){
		this.pNum = num;
		this.cities = cities;
		this.mutation = mutation;
		s = new solution[pNum];
		r = new Random();
		factorial = fact(cities.length);
		gens = 0;
		progress = 1;
	}

	public void generateRandom(){
		gens++;
		int[] laying = new int[cities.length];
		for(int i=0; i<cities.length; i++)
			laying[i] = i;
		for(int i=0; i<pNum; i++)
			s[i] = new solution(this.cities, laying);
		for(int i=0; i<pNum; i++){
			for(int j=0; j<20; j++)
				swap(s[i].getLaying(), r.nextInt(cities.length), r.nextInt(cities.length));
		}

	}

	public solution getBest(){
		sort(s);
		return s[pNum-1];
	}

	public solution getWorst(){
		sort(s);
		return s[0];
	}
	public void crossover(){
		gens++;
		solution[] best = selection();
		if(mutation == 0.0f){
			for(int i=0; i<s.length; i++){
				s[i] = null;
			}
			s = new solution[pNum];
			int[] a = new int[cities.length];
			for(int i=0; i<s.length; i++){
				for(int j=0; j<cities.length; j++){
					a[j] = best[r.nextInt((pNum/2))].getLaying()[r.nextInt(cities.length)];
					s[i] = new solution(cities, a);
				}
			}
		}else{

		}
		
		boolean b;

		for(int i=0; i<s.length; i++){
			b = true;
			for(int j=0; j<s.length; j++){
				if(equal(s[i], s[j])){
					b = false;
				}
			}
			progress += b ? 1 : 0;
		}
	}

	public float getComplete(){
		float f = 100*(progress/factorial);
		return f;
	}

	private solution[] selection(){
		sort(s);
		int x = 0;
		for(int i=1; i<=pNum; i++)
			x += i;
		solution[] frq = new solution[x];
		int y = 0;
		for(int i=0; i<pNum; i++){
			for(int j=0; j<=y; j++){
				if(y<frq.length){
					frq[y] = s[i];
					y++;
				}else{
					break;
				}
			}
		}
		solution[] a = new solution[pNum/2];
		for(int i=0; i<a.length; i++){
			a[i] = frq[r.nextInt(frq.length)];
		}
		return a;
	}

	public int getGenerations(){
		return this.gens;
	}
	
	public solution[] getPopulation(){
		return this.s;
	}

	private int fact(int in){
		int out = 1;
		if(in == 0){
			return 1;
		}else{
			for(int i=1; i<=in; i++){
				out *= i;
			}
			return out;
		}
	}

	private void swap(int[] i, int x, int y){
		int temp;
		temp = i[x];
		i[x] = i[y];
		i[y] = temp;
	}

	private void sort(solution[] s){
		solution a;
		for(int i=0; i<s.length; i++){
			for(int j=0; j<s.length-1; j++){
				if(s[j].getFit()>s[j+1].getFit()){
					a = s[j];
					s[j] = s[j+1];
					s[j+1] = a;
				}
			}
		}
	}

	private boolean equal(solution s1, solution s2){
		boolean[] b = new boolean[cities.length];
		boolean ret = true;
		for(int i=0; i<cities.length; i++){
			if(s1.getLaying()[i] == s2.getLaying()[i]){
				b[i] = true;
			}else{
				ret = false;
				break;
			}
		}
		return ret ? true : false;
	}
}
