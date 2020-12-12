package br.com.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "/cripto" , description = "Criptografia para o Usuario")
@RequestMapping("/cripto")
public class CriptografiaController {
	
	
	@Inject
	private UsuarioService service;
	

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(value = "/criptografar" )
	@ApiOperation(value = "Metodo para criptografar as informações do usuario" , produces = "UTF-8")
	public ResponseEntity<?> criptografar(@RequestParam(required = true) String publicKey) throws Exception {
		return new ResponseEntity(service.criptografar(publicKey), HttpStatus.OK);
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/descriptografar" )
	@ApiOperation(value = "Metodo para descriptografar as informações do usuario" , produces = "UTF-8")
	public ResponseEntity<?> descriptografar(@RequestParam(required = true) String textoCriptografado , @RequestParam(required = true) Integer idUsuario) throws Exception {
		return new ResponseEntity(service.descriptografar(textoCriptografado , idUsuario), HttpStatus.OK);
	}
}
