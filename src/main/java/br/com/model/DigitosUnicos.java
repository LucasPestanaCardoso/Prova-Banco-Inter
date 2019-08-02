package br.com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_digitos")
public class DigitosUnicos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "digi_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( name = "usu_id")
	private Usuario usuario;
	
	@NotNull
	@Column(name = "digi_entrada_n")
	private Long entradaN;
	
	@NotNull
	@Column(name = "digi_entrada_k")
	private Long entradaK;
	
	@NotNull
	@Column(name = "digi_resultado")
	private Long resultado;

	public Integer getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getResultado() {
		return resultado;
	}

	public void setResultado(Long resultado) {
		this.resultado = resultado;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getEntradaN() {
		return entradaN;
	}

	public void setEntradaN(Long entradaN) {
		this.entradaN = entradaN;
	}

	public Long getEntradaK() {
		return entradaK;
	}

	public void setEntradaK(Long entradaK) {
		this.entradaK = entradaK;
	}

	
	
}
