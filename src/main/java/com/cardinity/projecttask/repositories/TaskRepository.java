package com.cardinity.projecttask.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cardinity.projecttask.models.Task;
import com.cardinity.projecttask.models.User;

public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

	Page<Task> findByCreator(User creator, Pageable pageable);

}
