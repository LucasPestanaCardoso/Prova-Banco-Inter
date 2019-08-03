package br.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.model.DigitosUnicos;
import br.com.model.Usuario;

public interface DigitosUnicosRepository extends JpaRepository<DigitosUnicos, Integer>  {
	
	@Query
	public List<DigitosUnicos> findByUsuario(@Param("usuario") Usuario usuario);   

}
