package com.idiotBoxServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idiotBoxServer.pojos.Comment;
import com.idiotBoxServer.pojos.CommentList;
import com.idiotBoxServer.pojos.Video;
import com.idiotBoxServer.pojos.VideoList;
import com.idiotBoxServer.service.VideoService;

@RestController
@RequestMapping("/vcr")
public class RecommendationController {

	@Autowired
	VideoService vs;

	@GetMapping("/getrecom/{s}")
	public VideoList gre(@PathVariable int s) {
		System.out.println("rest get recommendation called for " + s);
		VideoList v = new VideoList(vs.grec(s));
		System.out.println(v);
		return v;
	}

	@GetMapping("/getadvrecom/{d}")
	public VideoList advgre(@PathVariable int d) {
		System.out.println("rest get recommendation called for " + d);
		VideoList v = new VideoList(vs.advgrec(d));
		System.out.println(v);
		return v;
	}

	@GetMapping("/get/{id}")
	public Video ad(@PathVariable int id) {
		return vs.getVideo(id);
	}

	@GetMapping("/getcomments/{sa}")
	public CommentList gacom(@PathVariable String sa) {
		CommentList commentList = new CommentList(vs.gac(sa));
		return commentList;
	}

	@GetMapping("/getvideo")
	public VideoList gem() {
		VideoList v = new VideoList(vs.gam());
		return v;
	}

	@GetMapping("/getpopular")
	public VideoList gpm() {
		VideoList v = new VideoList(vs.popularm());
		return v;
	}

	@GetMapping("like/{id}")
	public String vlike(@PathVariable int id) {
		vs.likv(id);
		return "liked";
	}

	@GetMapping("dislike/{id}")
	public String vdislike(@PathVariable int id) {
		vs.dislikv(id);
		return "disliked";
	}

	@DeleteMapping("deletevideo/{id}")
	public String delvid(@PathVariable int id) {
		vs.deletevideo(id);
		return "Deleted Succesfully";
	}

	@DeleteMapping("deletecom/{id}")
	public String ser(@PathVariable int id) {
		vs.deletecomment(id);
		return "Deleted comment";
	}

	@PostMapping("addcomment/{id}")
	public void ser(@PathVariable int id, @RequestBody Comment d) {
		vs.addcom(id, d);
	}
}