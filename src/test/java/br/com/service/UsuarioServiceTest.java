package br.com.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@TestPropertySource(locations = "classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioServiceTest {

	@Inject
	private UsuarioService service;

	@Test
	public void test1() throws BusinessException {

		service.salvar("Amanda Rodrigues", "amanda@gmail.com");
		Usuario usuario = service.findByNome("Amanda Rodrigues");

		assertNotNull(usuario);

	}

	@Test
	public void test2() throws BusinessException {

		Usuario usuario = service.findByNome("Amanda Rodrigues");
		service.alterar(usuario.getId(), "Diego Pereira", "diego@gmail.com");

		assertNotNull(service.findByNome("Diego Pereira"));

	}


	@Test(expected = BusinessException.class)
	public void test4() throws BusinessException {

		Usuario usuario = service.findByNome("Diego Pereira");
		service.deletar(usuario.getId());
		assertNull(service.findByNome("Diego Pereira"));

	}

}
