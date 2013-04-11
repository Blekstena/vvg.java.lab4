package hr.vvg.programiranje.java.banka;

import hr.vvg.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.vvg.programiranje.java.iznimke.NepodrzanaValutaException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DeviznaTransakcija extends Transakcija implements Devizna {

	private static final BigDecimal TECAJ_EUR_KN = new BigDecimal(7.5);
	private static final String PODRZANA_VALUTA = "Euro";

	// mora ti biti public da ju mozes pozvat u glavnoj klasi

	public static Valuta provjeriValutu(String valuta)
			throws NepodrzanaValutaException {
		try {
			return Valuta.valueOf(valuta);
		} catch (IllegalArgumentException ex) {
			throw new NepodrzanaValutaException("Valuta " + valuta
					+ " nije podržana!", ex);
		}
	}

	public DeviznaTransakcija(TekuciRacun polazniRacun,
			DevizniRacun dolazniRacun, BigDecimal iznosZaPrebaciti) {
		super(polazniRacun, dolazniRacun, iznosZaPrebaciti);
	}

	public BigDecimal mjenjacnica(BigDecimal iznosZaPrebaciti, Valuta valuta) {

		for (Tecaj tecaj : Tecajnica.dohvatiTecajeve()) {
			if (tecaj.getValuta().compareTo(valuta) == 0) {
				BigDecimal iznos = iznosZaPrebaciti.divide(
						tecaj.getTecajPremaKuni(), 2, RoundingMode.HALF_UP);
				return iznos;
			}
		}
		return iznosZaPrebaciti;
	}

	@Override
	public void provediTransakciju() {

		if (polazniRacun.getStanjeRacuna().compareTo(super.iznosZaPrebaciti) == -1) {
			// zamijenio si iznimke, NedozvoljenoStanjeRacunaException je
			// RuntimeException
			// a NepodrzanaValutaException je Exception
			// dakle ovdje ne moras imat onaj throws u deklaraciji metode a u
			// provjeriValutu
			// moras (ti je trenutno i imas tamo ali je nepotrebna momentalno)
			throw new NedozvoljenoStanjeRacunaException(
					"Nedovoljno sredstava na raèunu :"
							+ polazniRacun.getStanjeRacuna()
							+ "; unesite ponovno iznos."
							+ super.iznosZaPrebaciti);
		} else {
			polazniRacun.isplatiSRacuna(super.iznosZaPrebaciti);
			BigDecimal konvertiraniIznos = mjenjacnica(super.iznosZaPrebaciti,
					((DevizniRacun) dolazniRacun).getValuta());
			dolazniRacun.uplatiNaRacun(konvertiraniIznos);
		}
	}

}