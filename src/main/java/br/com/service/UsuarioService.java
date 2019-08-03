package br.com.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.exception.BusinessException;
import br.com.model.DigitosUnicos;
import br.com.model.Usuario;
import br.com.repository.DigitosUnicosRepository;
import br.com.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Inject
	private UsuarioRepository repository;
	
	@Inject
	private DigitosUnicosRepository digitosUnicosRepository;
	
	
	@Transactional
	public List<Usuario> getUsuarios() {
		return repository.findAll();
	}
	
	public void salvar(String nome, String email) throws BusinessException {
		this.salvar(null, nome, email);
	}
	
	public void alterar(Integer id , String nome , String email) throws BusinessException {
		this.salvar(id, nome, email);
	}

	public void salvar(Integer id, String nome , String email) throws BusinessException {
		Usuario usuario = null;
		
		if(id != null) {
			usuario = this.find(id);
		} else {
			usuario = new Usuario();
		}
		
		usuario.setNome(nome);
		usuario.setEmail(email);
		
		repository.save(usuario);
	}
	
	@Transactional
	public void deletar(Integer id) throws BusinessException { 
		Optional<Usuario> usu = repository.findById(id);

		if(usu.isPresent()) {
			digitosUnicosRepository.deleteAll(usu.get().getDigitosUnicos());
			repository.delete(usu.get());
		} else {
			throw new BusinessException("Usuario não encontrado");
		}
	}
	
	public Usuario find(Integer id) throws BusinessException {
		
		Optional<Usuario> usu = repository.findById(id);

		if(!usu.isPresent()) {
			throw new BusinessException("Usuario não encontrado");
		}
		return usu.get();
	}
}
