package br.jus.trt.lib.common_tests.arquillian;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runners.model.InitializationError;

/**
 * Brigde para o Junit Runner do Arquillian para garantir um ponto de customização comum.
 * @author augusto
 */
public class ArquillianCommonRunner extends Arquillian {

	public ArquillianCommonRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

}
