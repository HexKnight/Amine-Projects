import java.util.*;

public class GeneticAlgo
{
	public static void main(String[] args)
	{
		Population society = new Population(2000, "Amine", 0f);
		society.generateRandom();
		String s = "";
		for(;;){
			for(int i=0; i<society.best().chars.length; i++)
				s += society.best().chars[i];
			System.out.println(s);
			s = "";
			society.crossover();
		}
	}
}

class Population{
	private int generation;
	private int amount;
	private String goal;
	private float mutation;
	private Creature[] population;
	
	public Population(int amount, String goal, float mutation){
		this.amount  = amount;
		this.mutation = mutation;
		this.goal = goal;
		this.population = new Creature[this.amount];
	}
	
	public void generateRandom(){
		generation++;
		Random r = new Random();
		for(int i=0; i<amount; i++){
			char[] c = new char[goal.length()];
			this.population[i] = new Creature(goal);
			for(int j=0; j<goal.length(); j++)
				c[j] = (char)r.nextInt(256);
			this.population[i].set(c);
		}
	}
	
	public void crossover(){
		sort();
		updateCreatures();
		Creature[] next = new Creature[amount];
		Random r = new Random();
		Creature[] parents;
		for(int i=0; i<amount; i++){
			parents = selection();
			char[] c = new char[this.goal.length()];
			for(int j=0; j<this.goal.length(); j++){
				c[j] = parents[r.nextInt(2)].chars[j];
			}
			next[i] = new Creature(this.goal);
			next[i].set(c);
		}
		this.population = next;
	}
	
	public Creature[] selection(){
		Creature[] parents = new Creature[2];
		int j = 0;
		Random r = new Random();
		for(;;){
			int i = r.nextInt(amount);
			float guess = r.nextFloat();
			
			if(guess <= this.population[i].fitness()){
				parents[j] = this.population[i];
				j++;
				if(j>=2)break;
			}
		}
		
		return parents;
	}
	
	public Creature best(){
		this.sort();
		return this.population[amount-1];
	}
	
	public Creature[] get(){
		return this.population;
	}
	
	public void sort(){
		updateCreatures();
		for(int i=0; i<amount; i++){
			for(int j=0; j<amount-1; j++){
				if(population[j].fitness() > population[j+1].fitness())
					swape(j, j+1);
			}
		}
	}
	
	private void swape(int i1, int i2){
		Creature temp = population[i1];
		population[i1] = population[i2];
		population[i2] = temp;
	}
	
	private void updateCreatures(){
		for(int i=0; i<amount; i++)
			population[i].update();
	}
	
}

class Creature{
	public char[] chars;
	private String goal;
	private float fitness;
	
	public Creature(String goal){
		this.goal = goal;
		this.chars = new char[this.goal.length()];
	}
	
	public void set(char[] c){
		this.chars = new char[chars.length];
		this.chars = c;
	}
	
	public void update(){
		float precent = 1f / (float)this.goal.length();
		this.fitness = 0;
		for(int i=0; i<this.goal.length(); i++){
			if(this.chars[i] == this.goal.toCharArray()[i]){
				this.fitness += precent;
			}
		}
	}
	
	public void clear(){
		this.fitness = 0;
		chars = new char[goal.length()];
	}
	
	public float fitness(){
		return this.fitness;
	}
	
}
