package br.com.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.model.Usuario;

public class UsuarioDTO  {

	private String nome;
	private String email;
	private Integer id;
	private List<DigitosUnicosDTO> digitosUnicos;
	private String publickey;
	private String textoCifrado;

	
	public UsuarioDTO (Usuario usuario) {
		this.id  = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.digitosUnicos = usuario.getDigitosUnicos().stream().map(du -> new DigitosUnicosDTO(du)).collect(Collectors.toList());
	    this.publickey = usuario.getPublicKey();
	    this.textoCifrado = usuario.getTextoCifrado();
 	}
	
	
	public String getPublickey() {
		return publickey;
	}

	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}
	
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
	public List<DigitosUnicosDTO> getDigitosUnicos() {
		return digitosUnicos;
	}
	public void setDigitosUnicos(List<DigitosUnicosDTO> digitosUnicos) {
		this.digitosUnicos = digitosUnicos;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTextoCifrado() {
		return textoCifrado;
	}


	public void setTextoCifrado(String textoCifrado) {
		this.textoCifrado = textoCifrado;
	}
	
}
