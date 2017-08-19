package org.utils.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.utils.entity.Result;
import org.utils.entity.Student;
import org.utils.service.BaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/student")
public class Controller {

	@Resource
	private BaseService service;

	@PostMapping("/addStudent")
	public Result addStudent(@RequestBody Student student) {
		return service.addStudent(student);
	}

	@PostMapping("/findAllStudent")
	public Result findAllStudent() {
		return service.findAllStudent();
	}

	@PostMapping("/updateStudent")
	public Result updateStudent(@RequestBody Student student) {
		return service.updateStudent(student);
	}

	@PostMapping("/delStudentById")
	public Result delStudentById(@RequestParam("id") String id) {
		return service.delStudentById(id);
	}
}
