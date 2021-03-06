package br.com.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dto.UsuarioDTO;
import br.com.exception.BusinessException;
import br.com.model.Usuario;
import br.com.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "/usuario" , description = "Crud do Usuario")
@RequestMapping("/usuario")
public class UsuarioController {

	@Inject
	private UsuarioService service;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/listar" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Metodo para buscar todos os Usuários")
	public ResponseEntity<?> usuarios(
			@RequestParam(required = false) 
			@ApiParam(value = "Nome do Usuario", required = false, example = "Lucas") String nome) throws BusinessException {
		
	    List<Usuario> usuarios = service.getUsuarios(nome);
        List<UsuarioDTO> retorno = usuarios.stream().map(du -> new UsuarioDTO(du)).collect(Collectors.toList());
		return new ResponseEntity(retorno , HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(value = "/salvar" )
	@ApiOperation(value = "Metodo para salvar usuários" , produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> salvar(
	    @RequestParam(required = true) 
		@ApiParam(value = "Nome do Usuario", required = true, example = "Lucas") String nome,
	    @RequestParam(required = true)
		@ApiParam(value = "Email do Usuario", required = true, example = "lucas@gmail.com") String email) throws BusinessException {
		
		service.salvar(nome, email);
		return new ResponseEntity("Usuario salvo com sucesso !!", HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping(value = "/alterar" )
	@ApiOperation(value = "Metodo para atualizar usuários" , produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> alterar(
			@RequestParam(required = true) 
			@ApiParam(value = "ID do Usuario", required = true, example = "1") Integer id, 
			@RequestParam(required = true) 
			@ApiParam(value = "Novo Nome do Usuario", required = true, example = "Lucas") String nome,
			@RequestParam(required = true) 
			@ApiParam(value = "Novo Email do Usuario", required = true, example = "lucas@gmail.com") String email) throws BusinessException {
		
		service.alterar(id , nome , email);
		return new ResponseEntity("Usuario alterado com sucesso !!", HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping(value = "/deletar" )
	@ApiOperation(value = "Metodo para deletar usuários" , produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> deletar(
			@RequestParam(required = true) 
			@ApiParam(value = "ID do Usuario", required = true, example = "1") Integer idUsuario) throws BusinessException {
	
		service.deletar(idUsuario);
		return new ResponseEntity("Usuario deletado com sucesso !!", HttpStatus.OK);
	}
	
}
