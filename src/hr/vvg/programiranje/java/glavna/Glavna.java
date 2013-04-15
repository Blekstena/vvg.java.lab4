package hr.vvg.programiranje.java.glavna;

import hr.vvg.programiranje.java.banka.DeviznaTransakcija;
import hr.vvg.programiranje.java.banka.DevizniRacun;
import hr.vvg.programiranje.java.banka.TekuciRacun;
import hr.vvg.programiranje.java.banka.Transakcija;
import hr.vvg.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.vvg.programiranje.java.iznimke.NepodrzanaValutaException;
import hr.vvg.programiranje.java.osoba.Osoba;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Glavna {

	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	public static void main(String[] args) {

		Scanner unos = new Scanner(System.in);

		// podaci o prvom korisniku

		System.out.print("Unesi ime prvog korisnika racuna: ");
		String ime1 = unos.next();
		logger.info("Uneseno ime vlasnika prvog ra�una: " + ime1);
		
		System.out.print("Unesi prezime prvog korisnika racuna: ");
		String prezime1 = unos.next();
		logger.info("Uneseno prezime vlasnika prvog ra�una: " + prezime1);
		
		System.out.print("Unesi OIB prvog korisnika racuna: ");
		String oib1 = unos.next();
		logger.info("Unesen OIB vlasnika prvog ra�una: " + oib1);
		
		Osoba vlasnikRacuna1 = new Osoba(ime1, prezime1, oib1);

		// unos podataka o racunu prvog korisnika

		System.out.print("Unesi broj prvog racuna: ");
		String brojRacuna1 = unos.next();
		logger.info("Unesen broj vlasnika prvog ra�una: " + brojRacuna1);
		
		boolean error = false;
		BigDecimal stanjeRacuna1 = null;
		
		do {
			error = false;
			System.out.print("Unesi stanje prvog ra�una: ");
			try {
				stanjeRacuna1 = unos.nextBigDecimal();
				logger.info("Uneseno stanje prvog ra�una: " + stanjeRacuna1);
			} catch (InputMismatchException ex) {
				error = true;
				logger.error("Unesen neispravan iznos za stanje prvog ra�una!"
						+ stanjeRacuna1, ex);
				unos.nextLine();
				// imas beskonacnu petlju kad udjes u catch dio
				// fali ti ovjde unos.nextLine();
				// i u drugoj petlji dolje ti isto to fali
			}
			// ovdje ti pridruzujes true vrijednost u error
			// a trebas usporedjivat
			// i dolje isto to imas
		} while (error == true);
		
		
		TekuciRacun polazniRacun = new TekuciRacun(vlasnikRacuna1,
				stanjeRacuna1, brojRacuna1);

		// podaci o drugom korisniku

		System.out.print("Unesi ime drugog korisnika racuna: ");
		String ime2 = unos.next();
		logger.info("Uneseno ime korisnika drugog ra�una: " + ime2);
		
		System.out.print("Unesi prezime drugog korisnika racuna: ");
		String prezime2 = unos.next();
		logger.info("Uneseno prezime korisnika drugog ra�una: " + prezime2);
		
		System.out.print("Unesi OIB drugog korisnika racuna: ");
		String oib2 = unos.next();
		logger.info("Unesen OIB korisnika drugog ra�una: " + oib2);
		
		Osoba vlasnikRacuna2 = new Osoba(ime2, prezime2, oib2);

		// podavi o racunu drugog korisnika

		System.out.print("Unesi broj drugog racuna: ");
		String brojRacuna2 = unos.next();
		logger.info("Unesen broj drugog ra�una: " + brojRacuna2);

		BigDecimal stanjeRacuna2 = null;
		do {
			error = false;
			System.out.print("Unesi stanje drugog ra�una: ");
			try {
				stanjeRacuna2 = unos.nextBigDecimal();
				logger.info("Uneseno stanje drugog ra�una: " + stanjeRacuna2);
			} catch (InputMismatchException ex) {
				error = true;
				logger.error("Unesen neispravan iznos za stanje drugog ra�una!"
						+ stanjeRacuna2, ex);
				unos.nextLine();

			}
		} while (error == true);

		System.out.print("Unesi iBan racuna: ");
		String iban = unos.next();
		logger.info("Unesen iBan drugog ra�una: " + iban);

		// valuta

		String valuta = null;
		do {
			error = false;
			System.out.print("Unesi valutu drugog racuna: ");
			try {
				valuta = unos.next();
				logger.info("Unesena valuta drugog ra�una: " + valuta);
				DeviznaTransakcija.provjeriValutu(valuta);
			} catch (NepodrzanaValutaException ex) {
				error = true;
				logger.error("Unesena pogresna valuta! Unesite ponovno. "
						+ valuta, ex);
				unos.nextLine();

			}
		} while (error == true);

		DevizniRacun dolazniRacun = new DevizniRacun(vlasnikRacuna2,
				stanjeRacuna2, iban, valuta, brojRacuna2);

		System.out.print("Unesi iznos za prebaciti sa prvog na drugi racun: ");
		BigDecimal iznosZaPrebaciti = unos.nextBigDecimal();
		logger.info("Unesen iznos transakcije: " + iznosZaPrebaciti);
		Transakcija transakcija = new DeviznaTransakcija(polazniRacun,
				dolazniRacun, iznosZaPrebaciti);

		do {
				try {
				transakcija.provediTransakciju();
				String message = "Uspje�na transakcija.";
				System.out.println(message);
				logger.info(message);
			} catch (NedozvoljenoStanjeRacunaException ex) {
				System.out.println(ex.getMessage());
				logger.error(ex.getMessage(), ex);
				//error = true;
				//unos.nextLine();
				// malo l je a ne veliko L
				// veliko znaci da zoves staticku metodu u Logger klasi (a ta
				// metoda ne postoji, tj nije static)
				//logger.error("Nedovoljno sredstava na ra�unu za prebacivanje zeljenog iznosa!"
					//	+ ex);
			}
		} while (error == true);
		unos.close();

		System.out.println("Vlasnik prvog racuna:\nime:  "
				+ polazniRacun.getVlasnikRacuna().getIme() + "\nprezime:  "
				+ polazniRacun.getVlasnikRacuna().getPrezime() + "\noib:  "
				+ polazniRacun.getVlasnikRacuna().getOib() + "\nbroj ra�una: "
				+ polazniRacun.getBrojRacuna()
				+ "\nstanje na racunu nakon transakcije: "
				+ polazniRacun.getStanjeRacuna());

		System.out.println("Vlasnik drugog racuna:\nime: "
				+ dolazniRacun.getVlasnikRacuna().getIme() + ";\nprezime:  "
				+ dolazniRacun.getVlasnikRacuna().getPrezime() + ";\noib:  "
				+ dolazniRacun.getVlasnikRacuna().getOib() + ";\nbroj ra�una: "
				+ dolazniRacun.getBrojRacuna()
				+ ";\nstanje na racunu nakon transakcije: "
				+ dolazniRacun.getStanjeRacuna() + dolazniRacun.getValuta());

	}
}