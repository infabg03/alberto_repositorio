package com.alberto.boedo.factoria;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeansFactory {

	private static volatile ApplicationContext applicationContext = null;

	private BeansFactory() {
		// SINGLETON
	}

	/**
	 * Inicia el proceso
	 */
	public static void init() {
		if (applicationContext == null) {
			BeansFactory.applicationContext = new ClassPathXmlApplicationContext("com/alberto/boedo/xml/beans.xml");
		}
	}

	/**
	 * Obtiene del aplicationContext el bean de tipo type
	 * 
	 * @param type
	 *            Class del bean a buscar
	 * @return objeto del bean buscado
	 */
	public static <T> T getBean(Class<T> type) {
		init();
		return applicationContext.getBean(type);
	}

	/**
	 * Obtiene del aplicationContext el bean de tipo type y nombre indicados
	 * como parámetros
	 * 
	 * @param beanName
	 *            name del bean
	 * @param type
	 *            Class del bean a buscar
	 * @return objeto del bean buscado
	 */
	public static <T> T getBean(String beanName, Class<T> type) {
		init();
		return applicationContext.getBean(beanName, type);
	}

	/**
	 * Obtiene bean. Indicando nombre del bean y parametros para el constructor
	 * de dicho bean
	 *
	 * @param beanName
	 *            bean name
	 * @param arg
	 *            arg
	 * @return bean
	 */
	public static Object getBean(String beanName, Object arg) {
		init();
		return applicationContext.getBean(beanName, arg);
	}
}
