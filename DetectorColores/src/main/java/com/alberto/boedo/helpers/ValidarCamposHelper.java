package com.alberto.boedo.helpers;

public class ValidarCamposHelper {

	/**
	 * Comprueba que la longitud del password es correcta.
	 * 
	 * @param passwd
	 *            Password del que comprobaremos su longitud.
	 * @return Valor booleano asociado a la comprobacion de la longitud del
	 *         password.
	 */
	public static boolean longitudPasswd(String passwd) {
		return passwd.length() >= 8;
	}

	/**
	 * Comprueba que un password tiene tanto mayusculas como minusculas.
	 * 
	 * @param passwd
	 *            Password del que comprobaremos si contiene mayusculas y
	 *            minusculas.
	 * @return Valor booleano asociado a la comprobacion de la existencia de
	 *         mayusculas y minusculas en el password.
	 */
	public static boolean mayuscMinuscPasswd(String passwd) {
		boolean min = false, may = false;

		char[] caracteres = passwd.toCharArray();
		for (Character c : caracteres) {
			if (Character.isUpperCase(c))
				may = true;
			else
				min = true;
		}
		return may && min;
	}

	/**
	 * Comprueba si un email tiene el formato correcto.
	 * 
	 * @param mail
	 *            Email al que haremos la comprobacion.
	 * @return Valor booleano asociado a la comprobacion del formato del email.
	 */
	public static boolean formatoEmail(String mail) {
		return mail.matches("[-\\w\\.]+@\\w+\\.\\w+");
	}

	/**
	 * Comprueba si un telefono tiene el formato correcto.
	 * 
	 * @param telefono
	 *            Telefono al que haremos la comprobacion.
	 * @return Valor booleano asociado a la comprobacion del formato del
	 *         telefono.
	 */
	public static boolean formatoTelefono(String telefono) {
		return telefono.matches("\\d{3}+-\\d{3}+-\\d{3}");
	}

}
