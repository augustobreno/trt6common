package br.jus.trt.lib.common_tests.dataloader;

import javax.inject.Inject;

import org.junit.Test;

import br.jus.trt.lib.common_tests.CommonTestsArquillianTestBase;

/**
 * Testa o uso da anotação {@link LoadData} em classes de teste para carregamento de scripts SQL.
 * @author augusto
 *
 */
@LoadData(dataLoader=UF_aa_DataLoader.class)
public class LoadDataBeanArquillianTest extends CommonTestsArquillianTestBase implements LoadDataBeanTestDef {

	@Inject
	private LoadDataBeanTestImpl tester;
	
	@Override
	@Test
	public void loadDataInTypeTest() {
		tester.loadDataInTypeTest();
	}
	
	@Override
	@LoadData(dataLoader=UF_bb_DataLoader.class)
	@Test
	public void loadDataInMethodTest() {
		tester.loadDataInMethodTest();;
	}
	
	@Override
	@LoadData(dataLoader=UF_bb_DataLoader.class, precedence=2)
	@Test
	public void precedenceTest() {
		tester.precedenceTest();
	}
}
