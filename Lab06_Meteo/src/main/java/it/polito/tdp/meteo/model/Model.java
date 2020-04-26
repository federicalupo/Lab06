package it.polito.tdp.meteo.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {

	private MeteoDAO dao;

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	private List<Citta> sequenzaMigliore;
	private double costoMigliore;

	public Model() {

		dao = new MeteoDAO();

	}

	// of course you can change the String output with what you think works best
	/*
	 * public String getUmiditaMedia(int mese) {
	 * 
	 * 
	 * }
	 */

	public List<Rilevamento> getUmiditaMedia(int mese) {

		List<Rilevamento> lista = dao.getLocalitaUmidita(mese);

		return lista;
	}

	// of course you can change the String output with what you think works best
	public List<Citta> trovaSequenza(int mese) { // c'era String come val di ritorno

		this.sequenzaMigliore = new LinkedList<>(); // deve iniziare vuota
		this.costoMigliore = 0.0;

		List<Citta> citta = dao.cittaPresenti();

		for (Citta c : citta) {
			c.setCounter(0);
			c.setGgConsecutivi(0);
			c.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, c.getNome()));
		}

		List<Citta> sequenza = new LinkedList<>();

		cerca(citta, 0, sequenza);

		return sequenzaMigliore;
	}

	private void cerca(List<Citta> citta, int livello, List<Citta> sequenza) {

		if (sequenza.size() == NUMERO_GIORNI_TOTALI) {
			System.out.println(sequenza);
			return; // !!!!!

		}

		for (Citta c : citta) {

			if (livello == 0) {
				for (Citta temp : citta) {
					temp.setCounter(0);
					temp.setGgConsecutivi(0);
				}
			}

			if (c.getCounter() < NUMERO_GIORNI_CITTA_MAX) { // e < numgg consecutiv

				if (sequenza.size() != 0) // caso in cui sequenza è vuota
				{
					Citta prec = sequenza.get(sequenza.size() - 1);

					if ((prec.equals(c))) { // && c.getGgConsecutivi()<NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN
						c.increaseCounter();
						c.incrementaGgConsecutivi();

						sequenza.add(c);
						cerca(citta, livello + 1, sequenza);
						sequenza.remove(c);
						// aggiorna contatori !!!!
						c.decrementaCounter();
						c.decrementaGgConsecutivi();

					} else { // rientra anche il caso in cui sono uguali ma c.getGGConsecutivi> MIN
						if ((!prec.equals(c)) && prec.getGgConsecutivi() >= NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN
								&& (livello < (NUMERO_GIORNI_TOTALI - 2))) {

							// puoi cambiare, MA NON SI PUò CAMBIARE AL LIVELLO 13, se no riempi 13,14 e
							// manca il terzo

							c.increaseCounter();
							c.setGgConsecutivi(1);

							sequenza.add(c);
							cerca(citta, livello + 1, sequenza);
							sequenza.remove(c);
							c.decrementaCounter();
							c.decrementaGgConsecutivi();

						}
					}

				} else {

					c.increaseCounter();
					c.incrementaGgConsecutivi();

					sequenza.add(c);
					cerca(citta, livello + 1, sequenza);
					sequenza.remove(c);
					c.decrementaCounter();
					c.decrementaGgConsecutivi();

				}

			}

		}

	}

}
