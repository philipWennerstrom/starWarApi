package br.com.b2sky.infra.errors;

public class PlanetNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private final String name;
	public PlanetNotFoundException(String name) {
		super();
		this.name = name;
	}

	public PlanetNotFoundException(String name,String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.name = name;
	}

	public PlanetNotFoundException(String name,String message, Throwable cause) {
		super(message, cause);
		this.name = name;
	}

	public PlanetNotFoundException(String name, String message) {
		super(message);
		this.name = name;
	}

	public PlanetNotFoundException(String name, Throwable cause) {
		super(cause);
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}