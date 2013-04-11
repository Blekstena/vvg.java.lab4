package hr.vvg.programiranje.java.banka;

import java.math.BigDecimal;

public class Tecaj {
	
	private Valuta valuta;
	private BigDecimal tecajPremaKuni;
	
	public Tecaj (Valuta valuta, BigDecimal tecajPremaKuni){
		this.valuta = valuta;
		this.tecajPremaKuni = tecajPremaKuni;
	}

	public Valuta getValuta() {
		return valuta;
	}

	public BigDecimal getTecajPremaKuni() {
		return tecajPremaKuni;
	}

}
