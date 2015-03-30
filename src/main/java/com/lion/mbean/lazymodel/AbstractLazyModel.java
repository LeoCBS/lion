package com.lion.mbean.lazymodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.lion.entity.AbstractEntity;
import com.lion.service.AbstractService;

/**
 * 
 * @author Leonardo Cesar Borges
 * 
 *         Classe para padronizar a implementacao das listagens do sistema,
 *         contem os metodos principais do DataTable do Primefaces
 * 
 * @param <T>
 */
public abstract class AbstractLazyModel<T extends AbstractEntity, M extends AbstractService<T>>
		extends LazyDataModel<T> {

	private Map<String, Object> filters;

	private Map<String, Object> filtersBkp;

	protected M service;

	public AbstractLazyModel() {
		super();
		filters = new HashMap<String, Object>();
	}

	@Override
	public void setRowIndex(final int rowIndex) {
		if (rowIndex == -1 || getPageSize() == 0) {
			super.setRowIndex(-1);
		} else {
			super.setRowIndex(rowIndex % getPageSize());
		}
	}

	@Override
	public List<T> load(int first, final int pageSize, final String sortField,
			final SortOrder sortOrder, final Map<String, Object> filters) {
		if (!this.filters.equals(filtersBkp)) {
			first = 0;
		}
		this.filtersBkp = new HashMap<String, Object>(this.filters);

		int count = count().intValue();
		first = count < pageSize ? 0 : first;
		setRowCount(count);

		List<T> result = this.search(first, pageSize);
		if (result.isEmpty()) {
			setRowCount(0);
		}

		return result;
	}

	public Long count() {
		return this.service.countByFilters(this.getFilters());
	}

	public List<T> search(int first, int pageSize) {
		return this.service.findByFilters(first, pageSize, this.getFilters());
	}

	public void clearFilters() {
		this.setFilters(new HashMap<String, Object>());
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

}
