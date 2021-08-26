package com.cardinity.projecttask.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cardinity.projecttask.models.Project;
import com.cardinity.projecttask.models.User;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	Page<Project> findByCreator(User creator, Pageable pageable);
}
