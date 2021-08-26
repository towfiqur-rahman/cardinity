package com.cardinity.projecttask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cardinity.projecttask.models.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {

}
