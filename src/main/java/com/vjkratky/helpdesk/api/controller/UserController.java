package com.vjkratky.helpdesk.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vjkratky.helpdesk.api.entity.User;
import com.vjkratky.helpdesk.api.response.Response;
import com.vjkratky.helpdesk.api.service.UserService;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<User>> create(HttpServletRequest httpServletRequest, @RequestBody User user,
			BindingResult bindingResult) {
		Response<User> response = new Response<User>();
		try {
			validateCreateUser(user, bindingResult);
			if (bindingResult.hasErrors()) {
				bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			User userPersisted = (User) this.userService.createOrUpdate(user);
			response.setGenericEntity(userPersisted);
		} catch (DuplicateKeyException duplicateKeyException) {
			response.getErrors().add("E-mail already registered!");
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateCreateUser(User user, BindingResult bindingResult) {
		if (user.getEmail() == null) {
			bindingResult.addError(new ObjectError("User", "E-mail no information"));
		}
	}

	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<User>> update(HttpServletRequest httpServletRequest, @RequestBody User user,
			BindingResult bindingResult) {
		Response<User> response = new Response<User>();
		try {
			validateUpdateUser(user, bindingResult);
			if (bindingResult.hasErrors()) {
				bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			User userPersisted = (User) this.userService.createOrUpdate(user);
			response.setGenericEntity(userPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateUpdateUser(User user, BindingResult bindingResult) {
		if (user.getId() == null) {
			bindingResult.addError(new ObjectError("User", "Id no information"));
		}
		if (user.getEmail() == null) {
			bindingResult.addError(new ObjectError("User", "E-mail no information"));
		}
	}

	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<User>> findById(@PathVariable("id") String id) {
		Response<User> response = new Response<User>();
		User user = this.userService.findById(id).get();
		if (user == null) {
			response.getErrors().add("Register not found: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
		Response<String> response = new Response<String>();
		User user = this.userService.findById(id).get();
		if (user == null) {
			response.getErrors().add("Register not found: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(new Response<String>());
	}

	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Page<User>>> findAll(@PathVariable("page") int page,
			@PathVariable("count") int count) {
		Response<Page<User>> response = new Response<Page<User>>();
		Page<User> users = this.userService.findAll(page, count);
		response.setGenericEntity(users);
		return ResponseEntity.ok(response);
	}
}
