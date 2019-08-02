package br.com.dto;

import br.com.model.DigitosUnicos;

public class DigitosUnicosDTO {

	private Long k;
	private Long n;
	private Long resultado;
	
	public DigitosUnicosDTO(DigitosUnicos digitosUnicos) {  
		this.k = digitosUnicos.getEntradaK();
		this.n = digitosUnicos.getEntradaN();
		this.resultado = digitosUnicos.getResultado();
	}
	
	
	public Long getK() {
		return k;
	}

	public void setK(Long k) {
		this.k = k;
	}

	public Long getN() {
		return n;
	}

	public void setN(Long n) {
		this.n = n;
	}


	public Long getResultado() {
		return resultado;
	}


	public void setResultado(Long resultado) {
		this.resultado = resultado;
	}
	
}
