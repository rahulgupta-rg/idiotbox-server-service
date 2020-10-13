package com.idiotBoxServer.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.idiotBoxServer.pojos.Comment;
import com.idiotBoxServer.pojos.Video;

public interface VideoService {
	String registerVideo(MultipartFile uploadedFile);

	Integer registerVideoDetail(Video video);

	Video getVideo(int id);

	List<Video> gam();

	List<Video> grec(int n);

	List<Comment> gac(String a);

	List<Video> searname(String na);

	List<Video> searcat(String nc);

	List<Video> searact(String nacs);

	List<Video> bydirector(String nod);

	List<Video> popularm();

	void likv(int id);

	void dislikv(int id);

	void deletecomment(int id);

	void deletevideo(int id);

	Comment addcom(int vid, Comment d);

	List<Video> advgrec(int n);
}