/**
 * 
 */
package com.lion.utils;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.lion.message.MessageUtils;

/**
 * Classe utilitaria para os back beans
 * 
 * @author Leonardo
 * 
 */
public class BeanHelper implements Serializable {

	protected Logger log = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 1L;

	public BeanHelper() {

	}

	public static BeanHelper newInstance() {
		return new BeanHelper();
	}

	/**
	 * Trata o erro
	 * 
	 * @param mensagemUsuario
	 * @param mensagemLog
	 */
	public void exibiMensagemErro(String mensagemUsuario, String mensagemLog,
			Throwable e) {
		FacesMessage message = new FacesMessage(mensagemUsuario);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		log.error(mensagemLog);
		e.printStackTrace();
	}

	public void showMessageFromCode(String code, Throwable e) {
		String messageString = MessageUtils.getMessage(FacesContext
				.getCurrentInstance().getViewRoot().getLocale(), code);
		FacesMessage message = new FacesMessage(messageString);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		log.error(messageString);
		e.printStackTrace();
	}

	public void showMessageFromCode(String code) {
		String messageString = MessageUtils.getMessage(FacesContext
				.getCurrentInstance().getViewRoot().getLocale(), code);
		FacesMessage message = new FacesMessage(messageString);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
	}

	public void showMessageFromCodeToID(String code, Throwable e,
			String componentId) {
		String messageString = MessageUtils.getMessage(FacesContext
				.getCurrentInstance().getViewRoot().getLocale(), code);
		FacesMessage message = new FacesMessage(messageString);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(componentId, message);
		log.error(messageString);
		e.printStackTrace();
	}

	public void exibiMensagemErro(String mensagem, Throwable e) {
		FacesMessage message = new FacesMessage(mensagem);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		log.error(mensagem);
		e.printStackTrace();
	}

	/**
	 * Trata o erro
	 * 
	 * @param mensagemUsuario
	 * @param mensagemLog
	 */
	public void exibiMensagem(String mensagemUsuario) {
		FacesMessage message = new FacesMessage(mensagemUsuario);
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
	}

	public void exibiMensagem(String mensagemUsuario, Severity severity) {
		FacesMessage message = new FacesMessage(mensagemUsuario);
		message.setSeverity(severity);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
	}

	/**
	 * Mostra uma mensagem de erro para um determinado campo<br>
	 * Deve ser passado apenas o nome do campo, o m�todo assume o nome do form
	 * como sendo "form"<br>
	 * 
	 */
	public void exibirMensagemErroCampoEspecifico(String nmCampo, String msg) {
		FacesMessage message = new FacesMessage(msg);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
	}

	public void exibiMensagemErroEsperado(String mensagemUsuario,
			String mensagemLog, Severity severity) {
		FacesMessage message = new FacesMessage(mensagemUsuario);
		message.setSeverity(severity);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		log.error(mensagemLog);

	}

	public void exibirMensagemErroInesperado() {
		FacesMessage message = new FacesMessage(MessageUtils.getMessage(
				FacesContext.getCurrentInstance().getViewRoot().getLocale(),
				"msg.erro.default"));
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);

	}

	public void exibirMensagemCreatePadrao(String formID) {
		FacesMessage message = new FacesMessage(MessageUtils.getMessage(
				FacesContext.getCurrentInstance().getViewRoot().getLocale(),
				"msg.sucesso.create"));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(formID, message);

	}

	public void exibirMensagemUpdatePadrao() {
		FacesMessage message = new FacesMessage(MessageUtils.getMessage(
				FacesContext.getCurrentInstance().getViewRoot().getLocale(),
				"msg.sucesso.update"));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);

	}

	public void exibirMensagemUpdatePadrao(String formID) {
		FacesMessage message = new FacesMessage(MessageUtils.getMessage(
				FacesContext.getCurrentInstance().getViewRoot().getLocale(),
				"msg.sucesso.update"));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(formID, message);

	}

	public void exibirMensagemDeletePadrao() {
		FacesMessage message = new FacesMessage(MessageUtils.getMessage(
				FacesContext.getCurrentInstance().getViewRoot().getLocale(),
				"msg.sucesso.delete"));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);

	}

}
