package br.com.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dto.DigitosUnicosDTO;
import br.com.exception.BusinessException;
import br.com.model.DigitosUnicos;
import br.com.service.DigitoUnicoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Api para calculo do digito unico"  , description = "Calculo do digito unico")
@RequestMapping("/digito-unico")
public class DigitoUnicoController {

	@Inject
	private DigitoUnicoService digitoUnicoService;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/calcular" , produces = MediaType.TEXT_PLAIN_VALUE)
	@ApiOperation(value = "Metodo para o calculo de digito unico")
	public ResponseEntity<?> calcular(@RequestParam(required = true) String n,
			@RequestParam(required = true) Integer k , @RequestParam(required = false) Integer idUsuario) throws BusinessException {
		
		Integer resultado = digitoUnicoService.digitoUnico(n, k, idUsuario);
		
		return new ResponseEntity("O resultado e: " + resultado, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/buscarCalculos" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Metodo para buscar todos os calculos de um usuário")
	public ResponseEntity<?> buscarCalculos(@RequestParam(required = true) Integer idUsuario) throws BusinessException {
		
		List<DigitosUnicos> digitos = digitoUnicoService.buscarDigitosByUsuario(idUsuario);
		List<DigitosUnicosDTO> resultado =  digitos.stream().map(du -> new DigitosUnicosDTO(du)).collect(Collectors.toList());
		
		return new ResponseEntity(resultado, HttpStatus.OK);
	}
	
}
