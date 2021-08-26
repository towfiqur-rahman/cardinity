package com.cardinity.projecttask.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardinity.projecttask.models.Task;
import com.cardinity.projecttask.repositories.TaskRepository;
import com.cardinity.projecttask.services.TaskService;
import com.cardinity.projecttask.utils.ProjectTaskEnums;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public Task saveTask(Task task) throws IllegalArgumentException {
		if (!statusValid(task.getStatus())) {
			throw new IllegalArgumentException("Staus is invalid");
		}
		return taskRepository.save(task);
	}

	private boolean statusValid(String status) {
		return status.equalsIgnoreCase(ProjectTaskEnums.OPEN.toString())
				|| status.equalsIgnoreCase(ProjectTaskEnums.IN_PROGRESS.getValue())
				|| status.equalsIgnoreCase(ProjectTaskEnums.CLOSED.toString());

	}
}
