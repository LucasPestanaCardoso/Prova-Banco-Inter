package br.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer>  {

	
	@Query
	public List<Usuario> findByNome(@Param("nome") String nome); 
}
