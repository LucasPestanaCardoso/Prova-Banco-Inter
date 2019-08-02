package br.com.dto;

import br.com.model.DigitosUnicos;

public class DigitosUnicosDTO {

	private Integer k;
	private Integer n;
	private Integer resultado;
	
	public DigitosUnicosDTO(DigitosUnicos digitosUnicos) {  
		this.k = digitosUnicos.getEntradaK();
		this.n = digitosUnicos.getEntradaN();
		this.resultado = digitosUnicos.getResultado();
	}
	
	
	public Integer getK() {
		return k;
	}

	public void setK(Integer k) {
		this.k = k;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}


	public Integer getResultado() {
		return resultado;
	}


	public void setResultado(Integer resultado) {
		this.resultado = resultado;
	}
	
}
