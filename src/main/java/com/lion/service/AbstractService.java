package com.lion.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.lion.entity.AbstractEntity;

/**
 * Prefixo 'find' = buscas com like <br>
 * Prefixo 'load' = buscas sem like
 * 
 * 
 * @author Leonardo Cesar Borges <leocborgess@gmail.com>
 * 
 */

public abstract class AbstractService<T extends AbstractEntity> {

	@PersistenceContext
	protected EntityManager entityManager;

	protected abstract Class<T> getEntityClass();

	protected abstract TypedQuery<T> getQuerySearch();

	protected abstract TypedQuery<Long> getQueryCount();

	/**
	 * verifica se a classe filho tem consulta definida, caso nao tenha cria
	 * criteria generico
	 * 
	 * @param first
	 * @param pageSize
	 * @param filters
	 * @return
	 */
	public List<T> findByFilters(int first, int pageSize,
			Map<String, Object> filters) {
		if (getQuerySearch() == null) {
			Class<T> entityClass = getEntityClass();
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(entityClass);
			Root<T> root = cq.from(entityClass);
			cq.select(root);

			this.addwherePredications(filters, cb, cq, root);

			Query query = entityManager.createQuery(cq);
			query.setFirstResult(first);
			query.setMaxResults(pageSize);
			return query.getResultList();

		} else {
			return executaConsultaFilho(filters);
		}
	}

	private List<T> executaConsultaFilho(Map<String, Object> filters) {
		TypedQuery<T> query = getQuerySearch();
		for (Map.Entry<String, Object> entry : filters.entrySet()) {
			if (entry.getValue() != null && !entry.getValue().equals("")) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.getResultList();
	}

	/**
	 * verifica se a classe filho tem count definido, caso nao tenha cria
	 * criteria generico para count
	 * 
	 * @param filters
	 * @return
	 */
	public Long countByFilters(Map<String, Object> filters) {
		if (getQueryCount() == null) {
			Class<T> entityClass = getEntityClass();
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			Root<T> root = cq.from(entityClass);
			cq.select((cb.countDistinct(root)));

			this.addwherePredicationsCount(filters, cb, cq, root);

			long l = entityManager.createQuery(cq).getSingleResult();
			return l;
		} else {
			return this.executaCountFiltro(filters);
		}

	}

	private Long executaCountFiltro(Map<String, Object> filters) {
		TypedQuery<Long> query = getQueryCount();
		for (Map.Entry<String, Object> entry : filters.entrySet()) {
			if (entry.getValue() != null && !entry.getValue().equals("")) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.getSingleResult();
	}

	private void addwherePredicationsCount(Map<String, Object> filters,
			CriteriaBuilder cb, CriteriaQuery<Long> cq, Root<T> root) {
		List<Predicate> predications = new ArrayList<Predicate>();

		for (Map.Entry<String, Object> entry : filters.entrySet()) {
			if (entry.getValue() != null && !entry.getValue().equals("")) {
				populatePredications(cb, root, predications, entry);
			}
		}
		cq.where(predications.toArray(new Predicate[predications.size()]));
	}

	private void populatePredications(CriteriaBuilder cb, Root<T> root,
			List<Predicate> predications, Map.Entry<String, Object> entry) {
		Expression<String> attribute;
		String[] valueJoin = entry.getKey().split("\\.");
		// check if need join
		if (valueJoin.length > 1) {
			Join p = root.join(valueJoin[0]);
			attribute = p.get(valueJoin[1]);
		} else {
			attribute = root.get(entry.getKey());
		}
		if (attribute.getJavaType().equals(Long.class)
				|| attribute.getJavaType().equals(Integer.class)) {
			predications.add(cb.equal(attribute, entry.getValue()));
		} else {
			predications.add(cb.like(cb.upper(attribute), "%"
					+ entry.getValue().toString().toUpperCase() + "%"));
		}
	}

	private void addwherePredications(Map<String, Object> filters,
			CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> root) {
		List<Predicate> predications = new ArrayList<Predicate>();

		for (Map.Entry<String, Object> entry : filters.entrySet()) {
			if (entry.getValue() != null && !entry.getValue().equals("")) {
				populatePredications(cb, root, predications, entry);
			}
		}
		cq.where(predications.toArray(new Predicate[predications.size()]));
	}

	public List<T> findAll() {
		TypedQuery<T> query = this.generateGenericQuery();
		return query.getResultList();
	}

	private TypedQuery<T> generateGenericQuery() {
		TypedQuery<T> query = this.getQuerySearch() != null ? this
				.getQuerySearch() : this.entityManager.createQuery(
				"SELECT t FROM " + getEntityClass().getSimpleName() + " t ",
				getEntityClass());
		return query;
	}

	public void create(final T toSave) throws Exception {
		entityManager.persist(toSave);
		this.entityManager.flush();
	}

	public void update(final T toUpdate) throws Exception {
		entityManager.merge(toUpdate);
	}

	public void delete(final T toDelete) throws Exception {
		try {
			// primeiro faz o merge e depois o remove para evitar o problema das
			// "detacheds entitys"
			entityManager.remove(entityManager.merge(toDelete));
			entityManager.flush();
		} catch (final Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public void delete(final Class classToDel, final Integer id)
			throws Exception {
		try {
			entityManager.remove(this.entityManager
					.getReference(classToDel, id));
			entityManager.flush();
		} catch (final Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public T findById(final int id, final String query) {
		return (T) entityManager.createNamedQuery(query).setParameter("id", id)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public T findById(final long id, final String query) {
		return (T) entityManager.createNamedQuery(query).setParameter("id", id)
				.getSingleResult();
	}

	/**
	 * Executa NamedQuery passada por parametro, os nomes dos filtros no map
	 * devem ser iguais ao nomes dos parametros da NamedQuery
	 * 
	 * @param sQuery
	 * @param classParam
	 * @param parameters
	 * @return
	 */
	public List<T> findByNamedQuery(String sQuery, Class<T> classParam,
			Map<String, Object> parameters) {
		TypedQuery<T> query = this.entityManager.createNamedQuery(sQuery,
				classParam);
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());

		}
		return query.getResultList();

	}

	public T findByID(Integer id) {
		return this.entityManager.find(getEntityClass(), id);
	}

	public T findByID(Long id) {
		return this.entityManager.find(getEntityClass(), id);
	}

	public List<T> findByAttribute(String value, String nmAttribute,
			int maxResults) {
		Class<T> entityClass = getEntityClass();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		Root<T> root = cq.from(entityClass);
		cq.select(root);

		List<Predicate> predications = new ArrayList<Predicate>();

		Expression<String> attribute = root.get(nmAttribute);
		predications.add(cb.like(cb.upper(attribute), "%" + value.toUpperCase()
				+ "%"));

		cq.where(predications.toArray(new Predicate[predications.size()]));

		Query query = entityManager.createQuery(cq);
		query.setMaxResults(maxResults);

		return query.getResultList();
	}
}