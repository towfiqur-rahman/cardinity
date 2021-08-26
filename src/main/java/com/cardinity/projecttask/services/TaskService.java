package com.cardinity.projecttask.services;

import com.cardinity.projecttask.models.Task;

public interface TaskService {

	Task saveTask(Task task) throws IllegalArgumentException ;
}
