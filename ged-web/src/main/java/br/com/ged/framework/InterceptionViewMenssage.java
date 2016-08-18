package br.com.ged.framework;

import javax.faces.application.FacesMessage;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import br.com.ged.domain.Mensagem;
import br.com.ged.domain.TipoMensagem;
import br.com.ged.excecao.NegocioException;

/**
 * 
 * @author pedro.oliveira
 * 
 * <p>Classe deve ser utilizada nas classes de validação que possuem métodos que lançam {@link NegocioException}.</p>
 * 
 * <p>Dependendo do tipo da mensagem contida na exceção(NegocioException) este interceptor mostra a mensagem na tela do usuário.</p>
 * 
 *  Exemplo:
 *   <br><br>
 *   
 *   {@code //A classe deve estar anotada da seguinte forma: }
 *   <br>
 *   {@code	@Interceptors(InterceptionViewMenssage.class) }
 *   <br>
 *   {@code	public class ClasseValidadorView {.....}
 *   <br><br>
 *   
 *   {@code //Método simulando uma validação que lança execção de negócio }
 *   <br>
 *   public void confirmaSenha(Usuario usuario, String confirmarSenha) throws NegocioException {
			 <br>
		<p style="margin-left: 16"> 
			if (!usuario.getSenha().equals(confirmarSenha)){ 
		</p>
		<p style="margin-left: 32">	
				throw new NegocioException(Mensagem.MNG005); 
		</p>
		<p style="margin-left: 16">
			}
		</p>
	 }
	 
	<p> Ao lançar a exceção o interceptor vai captura-la e tratar enviando uma mensagem a tela do usuário pelo tipo da mensagem passada.</p>
	 
	 <p>Nesse caso a {@link Mensagem#MNG005} possui o tipo de mensagem {@link TipoMensagem#ALERTA} o que faz a mensagem JSF enviada para tela ser do tipo {@link FacesMessage#SEVERITY_WARN}.</p>
	 
	 <p>Para entender os tipos de mensagem, dê uma olhada na descrição do enum {@link TipoMensagem}</p>
 *  
 *
 */
public class InterceptionViewMenssage extends AbstractInterceptionMessage{

	/**
	 * 
	 * <p>Responsável por interceptar métodos de validação a procura da exceção {@link NegocioException}.</p>
	 * 
	 * <p>Caso encontre a exceção, é verificado o {@link TipoMensagem} e a {@link Mensagem} dentro da exceção é mostrada na tela do usuário.</p>
	 * 
	 * <p>Caso seja algum erro inesperado é mostrado no log do console e o fluxo é interrompido com o lançamento da exceção {@link NegocioException}.</p>
	 * 
	 * <p>Se não encontrar nenhuma exceção o fluxo segue normalmente.</p>
	 * 
	 * @param context {@link InvocationContext}
	 * @throws NegocioException
	 * Relança a exceção capturada para cortar o fluxo.
	 */
	@AroundInvoke
	public Object intercept(InvocationContext context) throws NegocioException {

		Object result = null;
		Boolean erro = Boolean.FALSE;
		Throwable cause = null;
		
		try {
			
			result = context.proceed();
			
		} catch (NegocioException e) {
			
			enviaMensagemPorTipo(e);
			erro = Boolean.TRUE;
			cause = e;
			
		} catch (Exception e) {
			e.printStackTrace();
			erro = Boolean.TRUE;
			cause = e;
			
		}finally{
			
			//Após tratar as mensagens, relança a exeção para cortar o fluxo de execução.
			if (erro){
				
				cause.printStackTrace();
				
				throw new NegocioException(cause);
			}
		}

		return result;
	}
}