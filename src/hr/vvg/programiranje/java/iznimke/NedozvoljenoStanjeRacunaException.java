package hr.vvg.programiranje.java.iznimke;

public class NedozvoljenoStanjeRacunaException extends RuntimeException {

	private static final long serialVersionUID = 3227045318081308569L;

	public NedozvoljenoStanjeRacunaException(String massage){
		super(massage);
	}
	
	public NedozvoljenoStanjeRacunaException (String massage, Throwable cause){
		super(massage, cause);
	}
}
