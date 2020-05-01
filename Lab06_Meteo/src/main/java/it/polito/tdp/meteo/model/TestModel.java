package it.polito.tdp.meteo.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(12));
		

		System.out.println("sequenza migliore "+m.trovaSequenza(5)+" "+m.getCostoMigliore());
		

	}

}
