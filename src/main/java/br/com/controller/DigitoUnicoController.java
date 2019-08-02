package br.com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Api para calculo do digito unico")
@RequestMapping("/digito-unico")
public class DigitoUnicoController {

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/calcular" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Metodo para o calculo de digito unico")
	public ResponseEntity<?> calcular(@RequestParam(required = true) String n,
			@RequestParam(required = true) Integer k , @RequestParam(required = false) Integer idUsuario) {
		
	  
		return new ResponseEntity("" , HttpStatus.OK);
	}
	
}
