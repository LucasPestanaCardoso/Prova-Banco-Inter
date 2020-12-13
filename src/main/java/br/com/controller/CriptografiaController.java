package br.com.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "/cripto" , description = "Criptografia para o Usuario")
@RequestMapping("/cripto")
public class CriptografiaController {
	
	
	@Inject
	private UsuarioService service;
	

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(value = "/criptografar" , consumes = MediaType.TEXT_PLAIN_VALUE)
	@ApiOperation(value = "Metodo para criptografar as informações do usuario" , produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> criptografar(@RequestBody 			
			@ApiParam(name="PublicKey",value=" Exemplo: MIIBIjANBgkqhkiG9w0BAQE... sem Aspas")			
			String publicKey ) throws Exception {
		return new ResponseEntity(service.criptografar(publicKey), HttpStatus.OK);
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/descriptografar" )
	@ApiOperation(value = "Metodo para descriptografar as informações do usuario" , produces = "UTF-8")
	public ResponseEntity<?> descriptografar(@RequestParam(required = true) String textoCriptografado , @RequestParam(required = true) Integer idUsuario) throws Exception {
		return new ResponseEntity(service.descriptografar(textoCriptografado , idUsuario), HttpStatus.OK);
	}
}
