package it.polito.tdp.PremierLeague.model;

public class Sconfitti implements Comparable<Sconfitti>{
	Player p;
	double peso;
	public Sconfitti(Player p, double peso) {
		super();
		this.p = p;
		this.peso = peso;
	}
	public Player getP() {
		return p;
	}
	public void setP(Player p) {
		this.p = p;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Sconfitti arg0) {
		// TODO Auto-generated method stub
		if (this.peso>arg0.peso)
			return -1;
		if (this.peso<arg0.peso)
			return  1;
		return 0;
	}
	

}
