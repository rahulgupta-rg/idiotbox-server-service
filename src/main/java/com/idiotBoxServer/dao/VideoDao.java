package com.idiotBoxServer.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.idiotBoxServer.pojos.Comment;
import com.idiotBoxServer.pojos.Video;

public interface VideoDao {
	String registerVideo(MultipartFile uploaded);

	Integer registerVideoDetail(Video video);

	Video getVideo(int id);

	List<Video> movies();

	List<Video> recom(int n);

	List<Comment> gcoms(String as);

	List<Video> byname(String na);

	List<Video> bycat(String nc);

	List<Video> byact(String nac);

	List<Video> bydir(String nd);

	List<Video> popular();

	void likev(int id);

	void dislikev(int id);

	void deletecom(int id);

	void deletevideo(int id);

	void adc(int vid, Comment d);

	List<Video> nrecom(int n);
}