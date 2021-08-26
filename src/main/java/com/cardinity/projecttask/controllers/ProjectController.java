package com.cardinity.projecttask.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardinity.projecttask.models.Project;
import com.cardinity.projecttask.models.User;
import com.cardinity.projecttask.repositories.ProjectRepository;
import com.cardinity.projecttask.repositories.UserRepository;
import com.cardinity.projecttask.utils.LoggedInUserDetails;
import com.cardinity.projecttask.utils.ProjectTaskEnums;

@RestController
@RequestMapping("projects")
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public ResponseEntity<Page<Project>> getAllProjects(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Pageable pageable = PageRequest.of(page, size);
		User user = LoggedInUserDetails.getLoggedInUser();

		if (user.getAuthorities() != null && LoggedInUserDetails.userHasRole(user, ProjectTaskEnums.ADMIN)) {
			return ResponseEntity.ok(projectRepository.findAll(pageable));
		} else {
			return ResponseEntity.ok(projectRepository.findByCreator(user, pageable));
		}
	}

	@PostMapping
	@RequestMapping("/create")
	public ResponseEntity<Project> createProject(@RequestBody Project project) {
		project.setCreator(LoggedInUserDetails.getLoggedInUser());
		project = projectRepository.save(project);
		return ResponseEntity.ok(project);
	}

	@GetMapping
	@RequestMapping("/projects-by-user/{userId}")
	public ResponseEntity<?> projectsByUser(@PathVariable Long userId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.badRequest().body("User with id does not exist");
		}
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(projectRepository.findByCreator(user.get(), pageable));
	}

	@DeleteMapping
	@RequestMapping("/delete-project/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable Integer projectId) {

		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if (optionalProject.isPresent()) {
			Project project = optionalProject.get();
			User user = LoggedInUserDetails.getLoggedInUser();

			if (user.getAuthorities() != null && !LoggedInUserDetails.userHasRole(user, ProjectTaskEnums.ADMIN)
					&& !project.getCreator().getId().equals(user.getId())) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User has no permission to delete project");
			}
			projectRepository.delete(project);
			return ResponseEntity.accepted().body("Project has been deleted");
		}
		return ResponseEntity.badRequest().body("Project with id does not exist");
	}

}
