package hr.vvg.programiranje.java.glavna;

import hr.vvg.programiranje.java.banka.DeviznaTransakcija;
import hr.vvg.programiranje.java.banka.DevizniRacun;
import hr.vvg.programiranje.java.banka.TekuciRacun;
import hr.vvg.programiranje.java.banka.Transakcija;
import hr.vvg.programiranje.java.banka.Valuta;
import hr.vvg.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.vvg.programiranje.java.iznimke.NepodrzanaValutaException;
import hr.vvg.programiranje.java.osoba.Osoba;
import hr.vvg.programiranje.java.sortirtanje.SortiranjeTransakcija;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

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

			}

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
		Valuta valutaDeviznogRacuna = null;
		do {
			error = false;
			System.out.print("Unesite valutu drugog ra�una:");
			try {
				valuta = unos.next();
				valutaDeviznogRacuna = DeviznaTransakcija
						.provjeriValutu(valuta);

			} catch (NepodrzanaValutaException ex) {
				error = true;
				String message = "Une�ena je krivo valuta racuna, ";
				System.out.printf(message + valuta
						+ " valuta nije podr�ana. \n", ex); // // problem s
															// ispisom je ok
															// ovdje ali me pati
															// ex
				logger.error(message);
				unos.nextLine();
			}

		} while (error == true);

		DevizniRacun dolazniRacun = new DevizniRacun(vlasnikRacuna2,
				stanjeRacuna2, iban, valutaDeviznogRacuna, brojRacuna2);

		System.out.print("Unesi iznos za prebaciti sa prvog na drugi racun: ");
		BigDecimal iznosZaPrebaciti = unos.nextBigDecimal();
		logger.info("Unesen iznos transakcije: " + iznosZaPrebaciti);
		// Transakcija transakcija = new DeviznaTransakcija(polazniRacun,
		// dolazniRacun, iznosZaPrebaciti);

		SortedSet<Transakcija> setTransakcija = new TreeSet<Transakcija>(
				new SortiranjeTransakcija());

		/*
		 * do { try { transakcija.provediTransakciju(); String message =
		 * "Uspje�na transakcija."; System.out.println(message);
		 * logger.info(message); } catch (NedozvoljenoStanjeRacunaException ex)
		 * { System.out.println(ex.getMessage()); logger.error(ex.getMessage(),
		 * ex); } } while (error == true);
		 */
		boolean ponavljam = false;
		do {
			ponavljam = false;

			DeviznaTransakcija transakcija = new DeviznaTransakcija(
					polazniRacun, dolazniRacun, iznosZaPrebaciti);
			try {
				transakcija.provediTransakciju();
				setTransakcija.add(transakcija);
			} catch (NedozvoljenoStanjeRacunaException ex) {
				String message = "Transakcija nije provedena!!!!";
				System.out.println(message);
				logger.error(message, ex);
			}

			System.out.println("�elite li novu transakciju (D/N)?");
			String odgovor = unos.next();

			if (odgovor.equals("D")) {
				ponavljam = true;
		}
			System.out.println("Unesi iznos novi za prebaciti sa prvog na drugi racun: ");
		} while (ponavljam == true);

		BigDecimal iznosNajveceTran = setTransakcija.first()
				.getIznosTransakcije();

		System.out.println("Iznos najvi�e transakcije je: " + iznosNajveceTran);

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