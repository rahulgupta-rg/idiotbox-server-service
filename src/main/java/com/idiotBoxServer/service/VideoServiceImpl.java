package com.idiotBoxServer.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.idiotBoxServer.dao.VideoDao;
import com.idiotBoxServer.pojos.Comment;
import com.idiotBoxServer.pojos.Video;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoDao dao;

	@Override
	public String registerVideo(MultipartFile uploadedFile) {
		return dao.registerVideo(uploadedFile);
	}

	@Override
	public Integer registerVideoDetail(Video video) {
		return dao.registerVideoDetail(video);
	}

	@Override
	public Video getVideo(int id) {
		return dao.getVideo(id);
	}

	@Override
	public List<Video> gam() {
		return dao.movies();
	}

	@Override
	public List<Video> grec(int n) {
		return dao.recom(n);
	}

	@Override
	public List<Video> advgrec(int n) {
		return dao.nrecom(n);
	}

	@Override
	public List<Comment> gac(String f) {
		return dao.gcoms(f);
	}

	@Override
	public List<Video> searname(String na) {
		return dao.byname(na);
	}

	@Override
	public List<Video> searcat(String nc) {
		return dao.bycat(nc);
	}

	@Override
	public List<Video> searact(String nacs) {
		return dao.byact(nacs);
	}

	@Override
	public List<Video> bydirector(String nod) {
		return dao.bydir(nod);
	}

	@Override
	public List<Video> popularm() {
		return dao.popular();
	}

	@Override
	public void likv(int id) {
		dao.likev(id);

	}

	@Override
	public void dislikv(int id) {
		dao.dislikev(id);
	}

	@Override
	public void deletecomment(int id) {
		dao.deletecom(id);

	}

	@Override
	public void deletevideo(int id) {
		dao.deletevideo(id);
	}

	@Override
	public Comment addcom(int vid, Comment d) {
		dao.adc(vid, d);
		return d;
	}
}