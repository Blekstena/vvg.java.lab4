package hr.vvg.programiranje.java.iznimke;

public class NepodrzanaValutaException extends Exception {
	
	private static final long serialVersionUID = 5563652576202375587L;

	public NepodrzanaValutaException(String massage){
		super(massage);
	}
	
	public NepodrzanaValutaException(String massage, Throwable cause){
		super(massage, cause);
	}

}
