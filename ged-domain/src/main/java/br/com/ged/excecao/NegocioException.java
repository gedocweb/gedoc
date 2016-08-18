package br.com.ged.excecao;

import br.com.ged.domain.Mensagem;
import br.com.ged.domain.TipoMensagem;
import br.com.ged.util.InitMessageProperties;

public class NegocioException extends Exception{

	private static final long serialVersionUID = -6019618454815377751L;
	
	private String valor;
	private TipoMensagem tipo;
	private Mensagem mensagemEnum;
	
	public NegocioException(Mensagem msg, Throwable cause) {
		super(InitMessageProperties.getValue(msg), cause);
		init(msg);
		this.mensagemEnum = msg;
	}

	public NegocioException(Mensagem msg) {
		super(InitMessageProperties.getValue(msg));
		init(msg);
		this.mensagemEnum = msg;
	}
	
	public NegocioException(Throwable cause) {
		super(cause);
	}
	
	private void init(Mensagem msg) {
		
		initMessageProperties(msg);
		verificaErroInterno(msg);
	}

	private void initMessageProperties(Mensagem msg) {
		valor = InitMessageProperties.getValue(msg);
		tipo = msg.getTipo();
	}

	private void verificaErroInterno(Mensagem msg) {
		
		if (TipoMensagem.INTERNO.equals(msg.getTipo())){
			super.printStackTrace();
		}
	}

	public String getMensagem() {
		return valor;
	}

	public TipoMensagem getTipo() {
		return tipo;
	}

	public Mensagem getMensagemEnum() {
		return mensagemEnum;
	}
}