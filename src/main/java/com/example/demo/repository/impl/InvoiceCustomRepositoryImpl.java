package com.example.demo.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demo.dto.InvoiceCriteria;
import com.example.demo.dto.InvoicePage;
import com.example.demo.entity.InvoiceEntity;
import com.example.demo.entity.ItemEntity;
import com.example.demo.repository.InvoiceCustomRepository;

public class InvoiceCustomRepositoryImpl implements InvoiceCustomRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<InvoiceEntity> findByCriteria(InvoiceCriteria invoiceCriteria, InvoicePage invoicePage) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InvoiceEntity> criteriaQuery = criteriaBuilder.createQuery(InvoiceEntity.class);
		Root<InvoiceEntity> root = criteriaQuery.from(InvoiceEntity.class);

		Predicate predicate = getPredicate(invoiceCriteria, criteriaQuery, criteriaBuilder, root);
		criteriaQuery.where(predicate);
		
		setOrder(invoicePage, criteriaBuilder, criteriaQuery, root);
		
		TypedQuery<InvoiceEntity> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(invoicePage.getPageNumber() * invoicePage.getPageSize());
		query.setMaxResults(invoicePage.getPageSize());

		Pageable pageable = getPageable(invoicePage);

		long count = getInvoiceCount(predicate, criteriaBuilder);

		return new PageImpl<>(query.getResultList(), pageable, count);
	}

	private Predicate getPredicate(InvoiceCriteria invoiceCriteria, CriteriaQuery<InvoiceEntity> criteriaQuery, CriteriaBuilder criteriaBuilder,
			Root<InvoiceEntity> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (Objects.nonNull(invoiceCriteria.getFromDate()) && Objects.nonNull(invoiceCriteria.getToDate())) {
			predicates.add(criteriaBuilder.between(root.get("date"), invoiceCriteria.getFromDate(),
					invoiceCriteria.getToDate()));
		}

		if (Objects.nonNull(invoiceCriteria.getFromPrice()) && Objects.nonNull(invoiceCriteria.getToPrice())) {
			predicates.add(criteriaBuilder.between(root.get("total"), invoiceCriteria.getFromPrice(),
					invoiceCriteria.getToPrice()));
		}
		
		if (Objects.nonNull(invoiceCriteria.getFromAmountItem()) && Objects.nonNull(invoiceCriteria.getToAmountItem())) {
			
			Subquery<Long> sub = criteriaQuery.subquery(Long.class);
			Root<ItemEntity> subRoot = sub.from(ItemEntity.class);
			sub.select(criteriaBuilder.count(subRoot.get("id")));
			sub.where(criteriaBuilder.equal(root.get("id"), subRoot.get("invoice").get("id")));
			
			predicates.add(criteriaBuilder.between(sub, invoiceCriteria.getFromAmountItem(),
					invoiceCriteria.getToAmountItem()));
		}

		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

	private void setOrder(InvoicePage invoicePage, CriteriaBuilder criteriaBuilder,
			CriteriaQuery<InvoiceEntity> criteriaQuery, Root<InvoiceEntity> root) {
		if (invoicePage.getSortBy() != null) {
			if ("asc".equals(invoicePage.getSortDirection().toLowerCase())) {
				if ("amountItem".equals(invoicePage.getSortBy())){
					sortByItem(criteriaBuilder, criteriaQuery, root, String.valueOf("asc"));
				}
				else criteriaQuery.orderBy(criteriaBuilder.asc(root.get(invoicePage.getSortBy())));
			} else if ("desc".equals(invoicePage.getSortDirection().toLowerCase())) {
				if ("amountItem".equals(invoicePage.getSortBy())){
					sortByItem(criteriaBuilder, criteriaQuery, root, String.valueOf("desc"));
				}
				else criteriaQuery.orderBy(criteriaBuilder.desc(root.get(invoicePage.getSortBy())));
			}
		}
	}

	private Pageable getPageable(InvoicePage invoicePage) {
		if (invoicePage.getSortBy() == null || "amountItem".equals(invoicePage.getSortBy())) {
			return PageRequest.of(invoicePage.getPageNumber(), invoicePage.getPageSize());
		}
		Sort sort = Sort.by(invoicePage.getSortDirection(), invoicePage.getSortBy());
		return PageRequest.of(invoicePage.getPageNumber(), invoicePage.getPageSize(), sort);
	}

	private long getInvoiceCount(Predicate predicate, CriteriaBuilder criteriaBuilder) {
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<InvoiceEntity> countRoot = countQuery.from(InvoiceEntity.class);
		countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);

		return entityManager.createQuery(countQuery).getSingleResult();
	}
	
	private void sortByItem(CriteriaBuilder criteriaBuilder, CriteriaQuery<InvoiceEntity> criteriaQuery,
			Root<InvoiceEntity> root, String direction) {
		Join<InvoiceEntity, ItemEntity> join = root.join("items", JoinType.INNER);
		Expression<Long> countExp = criteriaBuilder.count(join.get("id"));
		criteriaQuery.groupBy(root.get("id"));
		if (direction.equals("asc")) criteriaQuery.orderBy(criteriaBuilder.asc(countExp));
		if (direction.equals("desc")) criteriaQuery.orderBy(criteriaBuilder.desc(countExp));
	}

}
