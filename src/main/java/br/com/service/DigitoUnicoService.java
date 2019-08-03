package br.com.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import br.com.config.Cache;
import br.com.exception.BusinessException;
import br.com.model.DigitosUnicos;
import br.com.model.Usuario;
import br.com.repository.DigitosUnicosRepository;

@Service
public class DigitoUnicoService {

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private DigitosUnicosRepository digitosUnicosRepository;

	@Inject
	private Cache<Integer> cache;

	private static final Double limiteK = Math.pow(10, 5);
	private static final Double limiteN = Math.pow(10, 1000000);

	public Integer digitoUnico(String n, Integer k, Integer idUsuario) throws BusinessException {

		String key = n.concat(":").concat(k.toString());
		Integer resultado = cache.get(key);

		if (resultado == null) {

			resultado = calcularDigitoUnico(n, k, idUsuario);

			cache.put(key, resultado);

			return resultado;

		} else {

			if (idUsuario != null) {
				this.salvar(k, Integer.valueOf(n), resultado, idUsuario);
			}

			return resultado;
		}
	}

	public Integer calcularDigitoUnico(String n, Integer k, Integer idUsuario) throws BusinessException {

		String digitoUnico = "";
		Integer soma;
		Integer resultado;

		if (n != null && !NumberUtils.isDigits(n) || k != null && !NumberUtils.isDigits(k.toString())) {
			throw new BusinessException("Os numeros são inválidos.");
		}

		if (k >= limiteK || Double.parseDouble(n) >= limiteN) {
			throw new BusinessException("Numeros acima do permitido.");
		}

		for (int i = 0; i < k; i++) {
			digitoUnico += StringUtils.join(n);
		}

		do {
			soma = digitoUnico(digitoUnico);
			digitoUnico = soma.toString();

		} while (digitoUnico.length() > 1);

		resultado = Integer.valueOf(digitoUnico);

		this.salvar(k, Integer.valueOf(n), resultado, idUsuario);

		return resultado;
	}

	public Integer digitoUnico(String valor) {

		int soma = 0;
		String[] caracteres = valor.split("");

		for (int i = 0; i < caracteres.length; i++) {
			if (NumberUtils.isDigits(caracteres[i])) {
				soma += Integer.valueOf(caracteres[i]);
			}
		}

		return soma;
	}

	public void salvar(Integer k, Integer n, Integer resultado, Integer idUsuario) throws BusinessException {

		DigitosUnicos digitosUnicos = new DigitosUnicos();

		digitosUnicos.setEntradaK(k);
		digitosUnicos.setEntradaN(n);
		digitosUnicos.setResultado(resultado);

		if (idUsuario != null) {
			digitosUnicos.setUsuario(usuarioService.find(idUsuario));
		}

		digitosUnicosRepository.save(digitosUnicos);
	}
	
	
	public List<DigitosUnicos> buscarDigitosByUsuario(Integer id) throws BusinessException {
		Usuario usuario =  usuarioService.find(id);
		return digitosUnicosRepository.findByUsuario(usuario);
	}

}
