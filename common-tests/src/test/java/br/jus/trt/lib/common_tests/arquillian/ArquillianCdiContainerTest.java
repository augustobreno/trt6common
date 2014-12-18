package br.jus.trt.lib.common_tests.arquillian;

import java.io.File;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.jus.trt.lib.common_tests.cdi.CDI;


/**
 * Tenta garantir que o container de injeção de dependências via CDI está devidamente funcional.
 * @author augusto
 *
 */
@RunWith(ArquillianCommonRunning.class)
public class ArquillianCdiContainerTest {

	@Inject
	private EmptyBeanA emptyBeanA;
	
    @Deployment
    public static WebArchive createDeployment() {

    	// montando a aplicação para teste
        WebArchive webArquive = ShrinkWrap.create(WebArchive.class, "test.war")
        		.addClasses(EmptyBean.class, EmptyBeanA.class, EmptyBeanB.class, CDI.class, 
        				QualifierB.class)
        		.addClasses(ArquillianCommonRunning.class)		
        		.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        
        // incluindo as bibliotecas do maven
    	PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies();
    	File[] libs = pom.resolve().withTransitivity().asFile();
    	
        for(File file : libs){
            webArquive.addAsLibrary(file);
        }
        
        System.out.println(webArquive.toString(true));
        
		return webArquive;
    }
	
	
	/**
	 * Verifica se a injeção de dependência está sendo habilitada para a classe de teste
	 * que executa com {@link ArquillianCommonRunning}
	 */
	@Test
	public void cdiBeanInjectionTest() {
		Assert.assertNotNull(emptyBeanA);
	}
	
	/**
	 * Verifica se a injeção de pripriedades aninhadas está funcionando para a classe de teste
	 * que executa com {@link ArquillianCommonRunning}
	 */
	@Test
	public void cdiBeanNestedInjectionTest() {
		Assert.assertNotNull(emptyBeanA.getEmptyBeanB());
	}
	
	/**
	 * Teste de lookup de bean sem qualifiers
	 */
	@Test
	public void simpleLookupTest() {
		EmptyBeanA emptyBeanA = CDI.lookup(EmptyBeanA.class);
		Assert.assertNotNull(emptyBeanA);
	}
	
	/**
	 * Teste de lookup de bean com qualifiers.
	 */
	@Test
	public void withQualifierLookupTest() {
		
		/*
		 * Busca um bean pela interface + um qualifier que especifica o tipo concreto.
		 */
		
		@SuppressWarnings("serial")
		EmptyBean emptyBean = CDI.lookup(EmptyBean.class,  new AnnotationLiteral<QualifierB>() {});
		Assert.assertNotNull(emptyBean);
		Assert.assertTrue(EmptyBeanB.class.equals(emptyBean.getClass()));;
	}
	
}
