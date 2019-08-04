package br.com.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.exception.BusinessException;
import br.com.inter.InterApplication;
import br.com.model.DigitosUnicos;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application.properties")
public class DigitoUnicoServiceTest {
	
	@Inject
	private DigitoUnicoService service;
	
	@Test
	public void calcularDigitoUnicoTest() throws BusinessException {
		
		    int retorno =	service.calcularDigitoUnico("9875", 4, null);
			assertEquals(8, retorno);

	}
	
	@Test
	public void digitoUnicoTest() {
		    int retorno =	service.digitoUnico("116");
			assertEquals(8, retorno);
	}
	
	@Test
	public void buscarDigitosByUsuarioTest() throws BusinessException {
		
			List<DigitosUnicos> digitosUnicos =	service.buscarDigitosByUsuario(1);
			assertEquals(1, digitosUnicos.size());
	}
	
	@Test
	public void calcularDigitoUnico2Test() throws BusinessException {
		    int retorno =	service.digitoUnico("9875", 4, 1);
			assertEquals(8, retorno);
	}
	
	


}
