package br.com.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "usu_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Column(name = "usu_nome")
	private String nome;
	
	@NotNull
	@Column(name = "usu_email")
	private String email;
	
	@OneToMany(mappedBy = "usuario" , fetch = FetchType.EAGER)
	private List<DigitosUnicos> digitosUnicos;

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id; 
	}

	public List<DigitosUnicos> getDigitosUnicos() {
		return digitosUnicos;
	}

	public void setDigitosUnicos(List<DigitosUnicos> digitosUnicos) {
		this.digitosUnicos = digitosUnicos;
	}
	 
	
}
