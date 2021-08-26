package com.cardinity.projecttask.controllers;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardinity.projecttask.dto.UpdateTaskDto;
import com.cardinity.projecttask.models.Project;
import com.cardinity.projecttask.models.Task;
import com.cardinity.projecttask.models.User;
import com.cardinity.projecttask.repositories.TaskRepository;
import com.cardinity.projecttask.repositories.UserRepository;
import com.cardinity.projecttask.services.TaskService;
import com.cardinity.projecttask.services.TaskSpecification;
import com.cardinity.projecttask.services.impl.TaskSpecificationImpl;
import com.cardinity.projecttask.utils.ControllerPermissionChecker;
import com.cardinity.projecttask.utils.LoggedInUserDetails;
import com.cardinity.projecttask.utils.ProjectTaskEnums;

@RestController
@RequestMapping("tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TaskService taskService;

	@GetMapping
	public ResponseEntity<Page<Task>> getAllTasks(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Pageable pageable = PageRequest.of(page, size);
		User user = LoggedInUserDetails.getLoggedInUser();

		if (user.getAuthorities() != null
				&& user.getAuthorities().iterator().next().getAuthority().equals(ProjectTaskEnums.ADMIN.toString())) {
			return ResponseEntity.ok(taskRepository.findAll(pageable));
		} else {
			User loggedInUser = userRepository.findByUsername(user.getUsername());
			return ResponseEntity.ok(taskRepository.findByCreator(loggedInUser, pageable));
		}
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<?> getTaskById(@PathVariable Integer taskId) {
		Optional<Task> task = taskRepository.findById(taskId);
		if (task.isPresent()) {
			return ResponseEntity.ok(task.get());
		}
		return ResponseEntity.badRequest().body("task with id does not exist");
	}

	@PostMapping("/create")
	public ResponseEntity<?> createTask(@Validated @RequestBody Task task) {
		return saveTask(task);
	}

	@PostMapping("/update/{taskId}")
	public ResponseEntity<?> updateTask(@RequestBody UpdateTaskDto task, @PathVariable Integer taskId) {
		Optional<Task> optionalExistingTask = taskRepository.findById(taskId);
		if (!optionalExistingTask.isPresent()) {
			return ResponseEntity.badRequest().body("Task with id does not exist");
		}
		if (!ControllerPermissionChecker.userHasPermission()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ControllerPermissionChecker.PERMISSSION_DENIED);
		}
		Task existingTask = optionalExistingTask.get();
		if (existingTask.getDueDate() != null && existingTask.getDueDate().after(new Date())) {
			return ResponseEntity.badRequest().body("Past due task cannot be updated");
		}

		if (existingTask.getStatus().equals(ProjectTaskEnums.CLOSED.toString())) {
			return ResponseEntity.badRequest().body("Closed task cannot be updated");
		}
		existingTask.setDescription(task.getDescription());
		existingTask.setStatus(task.getStatus());
		existingTask.setDueDate(task.getDueDate());
		return saveTask(existingTask);
	}

	@GetMapping("/tasks-by-user/{userId}")
	public ResponseEntity<?> getTasksByUser(@PathVariable Long userId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.badRequest().body("User with id does not exist");
		}
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(taskRepository.findByCreator(user.get(), pageable));

	}

	@GetMapping("/search-tasks")
	public ResponseEntity<Page<Task>> searchTasks(@RequestParam(required = false) String status,
			@RequestParam(required = false) Date after, @RequestParam(required = false) Date before,
			@RequestParam(required = false) Integer projectId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Task task = new Task();
		task.setStatus(status);

		Project project = new Project();
		project.setId(projectId);
		task.setProject(project);

		TaskSpecification spec = new TaskSpecificationImpl(task, after, before);
		return ResponseEntity.ok(taskRepository.findAll(spec, pageable));
	}

	private ResponseEntity<?> saveTask(Task task) {
		try {
			task = taskService.saveTask(task);
			return ResponseEntity.ok(task);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
