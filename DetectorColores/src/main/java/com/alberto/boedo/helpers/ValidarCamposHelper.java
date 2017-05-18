package com.alberto.boedo.helpers;

public class ValidarCamposHelper {

	public static boolean longitudPasswd(String passwd) {
		return passwd.length() >= 8;
	}

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

	public static boolean formatoEmail(String mail) {
		return mail.matches("[-\\w\\.]+@\\w+\\.\\w+");
	}

	public static boolean formatoTelefono(String telefono) {
		return telefono.matches("\\d{3}+-\\d{3}+-\\d{3}");
	}

}
