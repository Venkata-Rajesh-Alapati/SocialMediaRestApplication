package com.example.restfulwebservice.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HellowWorldController {
	
	@GetMapping("/hello-world/{name}")
	public HelloWorld getMethod(@PathVariable String name) {
		return new HelloWorld("Hello World " + name);
	}
}
