package br.com.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import br.com.config.RSA;
import br.com.exception.BusinessException;
import br.com.model.Usuario;
import br.com.repository.DigitosUnicosRepository;
import br.com.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Inject
	private UsuarioRepository repository;

	@Inject
	private DigitosUnicosRepository digitosUnicosRepository;
	
	@Inject
	private RSA rsa;
	
	private static final Pattern EMAIL_REGEX = 
		    Pattern.compile("^(.+)@(.+)$", Pattern.CASE_INSENSITIVE);


	@Transactional
	public List<Usuario> getUsuarios(String nome) throws BusinessException {
		
		if(StringUtils.isBlank(nome)) {
			return	repository.findAll();
		} else {
			return Arrays.asList(findByNome(nome));
		}
	}

	public void salvar(String nome, String email) throws BusinessException {
		this.salvar(null, nome, email);
	}

	public void alterar(Integer id, String nome, String email) throws BusinessException {
		this.salvar(id, nome, email);
	}

	
	@Transactional(rollbackFor = BusinessException.class)
	public void salvar(Integer id, String nome, String email) throws BusinessException {
		Usuario usuario = null;
		
		validaUsuario(nome, email);

		if (id != null) {
			usuario = this.find(id);
		} else {
			usuario = new Usuario();
		}

		usuario.setNome(nome);
		usuario.setEmail(email);
        gerarKeys(usuario);
	}
	
	private void validaUsuario(String nome, String email) throws BusinessException {
		
		if(StringUtils.isBlank(nome) || StringUtils.isBlank(email)) {
			throw new BusinessException("Campos obrigatorios nao preenchidos");
		}
		
		Matcher matcher = EMAIL_REGEX.matcher(email);
		if(!matcher.find()) {
			throw new BusinessException("Email fora do padrao");
		}
		
		Usuario usuario =  repository.findByEmail(email);
		if(usuario != null) {
			throw new BusinessException("Ja existe um usuario cadastrado com esse e-mail no sistema.");
		}
		
	}

	public void save(Usuario usuario) { 
		repository.save(usuario);
	}

	@Transactional
	public void deletar(Integer id) throws BusinessException {
		Usuario usuario = this.find(id);

		digitosUnicosRepository.deleteAll(usuario.getDigitosUnicos());
		repository.delete(usuario);

	}

	public Usuario find(Integer id) throws BusinessException {

		Optional<Usuario> usu = repository.findById(id);

		if (!usu.isPresent()) {
			throw new BusinessException("Usuario não encontrado");
		}
		return usu.get();
	}

	public Usuario findByNome(String nome) throws BusinessException {

		List<Usuario> usu = repository.findByNome(nome);

		if (CollectionUtils.isEmpty(usu)) {
			throw new BusinessException("Usuario não encontrado");
		}

		return usu.get(0);
	}
	
	public String gerarKeys(Usuario usuario) throws BusinessException {
		return rsa.gerarKeys(usuario);
	}
	
	public String criptografar(String publicKey) throws Exception {
		Usuario usuario = repository.findByPublicKey(publicKey);
		 
		if(usuario == null) {
			throw new BusinessException("Usuario não encontrado ou chave não foi gerada pelo sistema.");
		}
		
		return "Criptografia: " + rsa.criptografar(publicKey, usuario);
	}
	
	public String descriptografar(String texto , Integer id) throws Exception {
		Usuario usuario = find(id);
		return "Original: " + rsa.descriptografarBase(texto , usuario);
	}
	
}
