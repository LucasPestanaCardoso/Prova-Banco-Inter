package br.com.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.exception.BusinessException;
import br.com.inter.InterApplication;
import br.com.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application.properties")
public class UsuarioServiceTest {

	@Inject
	private DigitoUnicoService digitoService;
	
	@Inject
	private UsuarioService service;
	
	
	@Test
	public void salvarTest() {
		try {
		   	service.salvar("Amanda Rodrigues", "amanda@gmail.com");
		   	Usuario usuario = service.findByNome("Amanda Rodrigues");
		   	
			assertNotNull(usuario);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	
	}
	
	@Test
	public void alterarTest() {
		try {
			
		   	Usuario usuario = service.findByNome("Amanda Rodrigues");
		   	service.alterar(usuario.getId() , "Diego Pereira", "amanda@gmail.com");
		   	
			assertNotNull(service.findByNome("Diego Pereira"));
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	
	}
	
	
	@Test
	public void deleteTest() {
		try {
			
		   	Usuario usuario = service.findByNome("Diego Pereira");
		   	service.deletar(usuario.getId());
		   	assertNull(service.findByNome("Diego Pereira"));
		   	
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	
	}
	
	
}
