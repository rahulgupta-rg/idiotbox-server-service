package com.idiotBoxServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idiotBoxServer.pojos.VideoList;
import com.idiotBoxServer.service.VideoService;

@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	private VideoService vs;

	@GetMapping("/byname/{s}")
	public VideoList seaname(@PathVariable String s) {
		return new VideoList(vs.searname(s));
	}

	@GetMapping("/bycat/{s}")
	public VideoList seacat(@PathVariable String s) {
		return new VideoList(vs.searcat(s));
	}

	@GetMapping("/byact/{s}")
	public VideoList seaact(@PathVariable String s) {
		return new VideoList(vs.searact(s));
	}

	@GetMapping("/bydir/{s}")
	public VideoList sedir(@PathVariable String s) {
		return new VideoList(vs.bydirector(s));
	}
}