package com.lion.mbean;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;

import com.lion.entity.AbstractEntity;
import com.lion.mbean.lazymodel.AbstractLazyModel;
import com.lion.service.AbstractService;
import com.lion.utils.BeanHelper;

public abstract class AbstractBean<M extends AbstractEntity, T extends AbstractService<M>> {

	protected AbstractLazyModel<M, T> lazyDataModel;

	protected AbstractService<M> service;

	protected M entidade;

	protected Boolean isCreate;

	protected BeanHelper beanHelper;

	protected abstract M createNovaEntidade();

	protected abstract void preCreateUpdate();

	protected abstract T getService();

	protected abstract void posCreateUpdate();

	protected abstract void validaForm();

	protected abstract void posConstruct();

	protected abstract String getBindingFormID();

	/**
	 * ID utilizado para edicao de uma entidade, e passado atraves do evento
	 * preRenderView
	 */
	protected String id;

	@PostConstruct
	protected void posConstructAbstract() {
		this.beanHelper = BeanHelper.newInstance();
		this.entidade = this.createNovaEntidade();
		posConstruct();

	}

	public void init() {
		if (StringUtils.isBlank(this.id)) {
			this.isCreate = true;
		} else {
			this.entidade = getService().findByID(Integer.parseInt(this.id));
			this.isCreate = false;
		}
	}

	public void createUpdate() {
		try {
			validaForm();
			preCreateUpdate();
			if (isCreate) {
				this.getService().create(this.entidade);
				beanHelper.exibirMensagemCreatePadrao(getBindingFormID());
			} else {
				this.getService().update(this.entidade);
				beanHelper.exibirMensagemUpdatePadrao(getBindingFormID());
			}
			posCreateUpdate();
		} catch (Exception e) {
			beanHelper.exibirMensagemErroInesperado();
		}
	}

	public void delete() {
		try {
			this.getService().delete(entidade);
			beanHelper.exibirMensagemDeletePadrao();
		} catch (Exception e) {
			if (!exibeMsgErroPersonalizadaDelete()) {
				beanHelper.exibirMensagemErroInesperado();
			}
			e.printStackTrace();
		}
	}

	public void exportar() {

	}

	public abstract void cancel();

	public void clearFields() {
		this.entidade = this.createNovaEntidade();
		// valida se a tela tem filtos
		if (this.lazyDataModel != null) {
			this.lazyDataModel.setFilters(new HashMap<String, Object>());
		}
	}

	/**
	 * Metodo utilizado para exibicao de tratamentos de erros espeficicos ao
	 * deletar, <br>
	 * Exemplo: restricao de chave estrangeira.
	 * 
	 * @return true = caso o metodo foi implementado na classe filha; false =
	 *         metodo nao foi implementando
	 * 
	 */
	protected Boolean exibeMsgErroPersonalizadaDelete() {
		return false;
	}

	public AbstractLazyModel<M, T> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(AbstractLazyModel<M, T> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

	public M getEntidade() {
		return entidade;
	}

	public void setEntidade(M entidade) {
		this.entidade = entidade;
	}

	public Boolean getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(Boolean isCreate) {
		this.isCreate = isCreate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
