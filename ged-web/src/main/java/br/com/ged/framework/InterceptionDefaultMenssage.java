package br.com.ged.framework;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import br.com.ged.domain.Mensagem;

/**
 * 
 * @author pedro.oliveira
 * 
 * <p>Classe utilizada para interceptar métodos que devem enviar uma mensagem padrão a tela do usuário.</p>
 * 
 * <p>Exemplo de implementação no método {@link GenericServiceController#salvar(br.com.ged.generics.EntidadeBasica)}</p>
 * 
 */
public class InterceptionDefaultMenssage extends AbstractInterceptionMessage{

	/**
	 * <p>Método responsável por interceptar qualquer outro método que esteja anotado com essa classe e evniar uma mensagem padrão do sistema,
	 * nesse caso Mensagem.MDF001 onde MDF001 é a chave no arquivo messages.properties que possui o valor (Operação realizada com sucesso!).</p>
	 * 
	 * Exemplo de uso:<br><br>
	 * 
	 * {@code @Interceptors(InterceptionDefaultMenssage.class)}
	 * <br>
		public void metodoExemplo() {
		<br>
			//Realiza as ações do método, ao final dele será enviado a mensagem para o usário na tela
		<br>
		}
	 * 
	 * @param context {@link InvocationContext} injetado automaticamente pelo contexto EJB
	 * 
	 */
	@AroundInvoke
	public Object intercept(InvocationContext context) {

		Object result = null;
		
		try {
			result = context.proceed();
			enviaMensagemDefault(Mensagem.MDF001);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}