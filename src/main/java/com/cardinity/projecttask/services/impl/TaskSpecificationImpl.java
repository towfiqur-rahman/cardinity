package com.cardinity.projecttask.services.impl;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cardinity.projecttask.models.Project;
import com.cardinity.projecttask.models.Task;
import com.cardinity.projecttask.services.TaskSpecification;

public class TaskSpecificationImpl implements TaskSpecification {

	private static final long serialVersionUID = 1L;

	private Task taskFilter;

	private Date after, before;

	public TaskSpecificationImpl(Task filter, Date after, Date before) {
		super();
		this.taskFilter = filter;
		this.before = before;
		this.after = after;
	}

	public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

		Predicate predicate = criteriaBuilder.disjunction();

		if (after != null) {
			predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("dueDate"), after));
		}

		if (before != null) {
			predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("dueDate"), before));
		}
		if (taskFilter.getProject() != null) {

			Join<Task, Project> projectJoin = root.join("project");
			predicate.getExpressions().add(criteriaBuilder
					.and(criteriaBuilder.equal(projectJoin.<Integer>get("id"), taskFilter.getProject().getId())));
		}

		if (taskFilter.getStatus() != null) {
			predicate.getExpressions()
					.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), taskFilter.getStatus())));
		}

		return predicate;
	}

}
