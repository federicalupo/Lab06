package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	// ----------------------PROVOOOOO--------------
	// provo questo anziche getLocalitaUmidita, prendo citta da db, controller - >
	// faccio for e per ogni citta stampo citta
	// e chiamo metodo => obiettivo : non salvare umidita

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
		final String sql = "SELECT Day(data) as giorno, Umidita " + 
				"FROM situazione " + 
				"where month(data)=? and localita=? " + 
				"ORDER BY data asc " + 
				"limit 15 ";
	
		List<Rilevamento> rilevamentiLocalitaMese = new LinkedList<Rilevamento>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, mese);
			st.setString(2, localita);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(localita, rs.getInt("giorno"), rs.getInt("Umidita"));
				rilevamentiLocalitaMese.add(r);
			}

			conn.close();
			return rilevamentiLocalitaMese;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		

	}

	// fatto da me
	public List<Rilevamento> getLocalitaUmidita(int mese) {

		final String sql = "Select localita, avg(umidita) as media " + "From situazione " + "where month(data) = ? "
				+ "group by localita "; // count(*) as numero, sum(umidita) as somma ==> c'è avg

		List<Rilevamento> localitaUmidita = new LinkedList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("localita"), rs.getDouble("media"));
				localitaUmidita.add(r);
			}

			conn.close();
			return localitaUmidita;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public List<Citta> cittaPresenti() {
		final String sql = "SELECT distinct Localita FROM situazione ";

		List<Citta> citta = new LinkedList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Citta c = new Citta(rs.getString("localita"));
				

				citta.add(c);

			}

			conn.close();
			return citta;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
