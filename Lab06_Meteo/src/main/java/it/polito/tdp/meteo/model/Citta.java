package it.polito.tdp.meteo.model;

import java.util.LinkedList;
import java.util.List;

public class Citta {
	
	
	private String nome;
	private List<Rilevamento> rilevamenti;
	private int counter = 0;
	private int ggConsecutivi=0;
	
	
	
	public Citta(String nome) {
		this.nome = nome;
		rilevamenti= new LinkedList<>();
	}
	
	public Citta(String nome, List<Rilevamento> rilevamenti) {
		this.nome = nome;
		this.rilevamenti = rilevamenti;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Rilevamento> getRilevamenti() {
		return rilevamenti;
	}

	public void setRilevamenti(List<Rilevamento> rilevamenti) {
		this.rilevamenti = rilevamenti;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public void increaseCounter() {
		this.counter += 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Citta other = (Citta) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}

	public int getGgConsecutivi() {
		return ggConsecutivi;
	}

	
	
	public void setGgConsecutivi(int ggConsecutivi) {
		this.ggConsecutivi = ggConsecutivi;
	}

	public void incrementaGgConsecutivi() {
		this.ggConsecutivi ++;
	}
	public void decrementaGgConsecutivi() {
		if(this.ggConsecutivi>0)  //genova(-3) genova(-2) genova(-1) .. .. .. genova(0) genova(1) genova(2)
			this.ggConsecutivi --;
		else
		{
			this.ggConsecutivi--;  //quindi per evitare che vada in negativo, diminuisco e aggiungo 3
			this.ggConsecutivi+= 3; 
		}
	}
	
	public void decrementaCounter() {
		this.counter--;
	}

	



}
