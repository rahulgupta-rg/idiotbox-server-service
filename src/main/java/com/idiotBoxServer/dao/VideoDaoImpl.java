package com.idiotBoxServer.dao;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.idiotBoxServer.pojos.Comment;
import com.idiotBoxServer.pojos.Video;

@Repository
public class VideoDaoImpl implements VideoDao {

	@Autowired
	private EntityManager manager;

	private Session getSession() {
		return manager.unwrap(Session.class);
	}

	private String query;

	@Override
	public String registerVideo(MultipartFile uploadedFile) {
		String movieName = uploadedFile.getOriginalFilename();
		String uploadLocation = "D:/IdiotBox/IdiotBoxRepository";
		File dest = new File(uploadLocation, movieName);
		try {
			uploadedFile.transferTo(dest);
			String filePath = uploadedFile.getOriginalFilename();
			System.out.println(filePath + " in dao");
			return filePath;
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public Integer registerVideoDetail(Video video) {
		try {
			Session session = getSession();
			return (Integer) session.save(video);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public Video getVideo(int id) {
		String query = "select v from Video v where v.vid = :i";
		Video video = null;
		try {
			Session session = getSession();
			video = session.createQuery(query, Video.class).setParameter("i", id).getSingleResult();
			return video;
		} catch (Exception e) {
			return video;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> movies() {
		try {
			Session session = getSession();
			query = "select v from Video v";
			List<Video> videoList = session.createQuery(query).getResultList();
			return videoList;
		} catch (Exception e) {
			List<Video> videoList = null;
			return videoList;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> nrecom(int n) {
		List<Video> ls = new LinkedList<>();
		Video video = null;
		try {
			Session session = getSession();

			// Getting Current Video
			query = "select v from Video v where vid=:m";
			video = (Video) session.createQuery(query).setParameter("m", n).getSingleResult();

			// By Genre
			List<String> gs = video.getGenre();
			query = "select v from Video v where :s member of genre";
			ls.addAll(session.createQuery(query).setParameter("s", gs.get(0)).getResultList());
			if (gs.get(1) != null) {
				ls.addAll(session.createQuery(query).setParameter("s", gs.get(1)).getResultList());
			}

			// By Actors
			List<String> cs = video.getActors();
			query = "select v from Video v where :a member of actors";
			ls.addAll(session.createQuery(query).setParameter("a", cs.get(0)).getResultList());
			if (cs.get(1) != null) {
				ls.addAll(session.createQuery(query).setParameter("a", cs.get(1)).getResultList());
			}

			// By Director
			query = "select v from Video v where director=:sd";
			ls.addAll(session.createQuery(query).setParameter("sd", video.getDirector()).getResultList());

		} catch (Exception e) {
			List<Video> videoList = null;
			return videoList;
		}
		Collections.sort(ls, new Comparator<Video>() {
			@Override
			public int compare(Video o1, Video o2) {
				Integer x = Collections.frequency(ls, o1);
				Integer y = Collections.frequency(ls, o2);
				return y.compareTo(x);
			}
		});

		List<Video> videoList = new LinkedList<>();
		for (Video cx : ls) {
			if (!videoList.contains(cx))
				videoList.add(cx);
		}
		videoList.remove(video);
		return videoList; // append this part after adding movies to your directory ".subList(0, 5);"
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> recom(int n) { // temporary recommends, use sublist method of list to limit movies before
		List<Video> videoList = new LinkedList<>();
		Video video = null;
		try {
			// Get Current Video
			query = "select v from Video v where vid=:m";
			video = (Video) getSession().createQuery(query).setParameter("m", n).getSingleResult();

			// By Genre
			List<String> gs = video.getGenre();
			query = "select v from Video v where :s member of genre";
			videoList.addAll(getSession().createQuery(query).setParameter("s", gs.get(0)).getResultList());
			if (gs.get(1) != null) {
				videoList.addAll(getSession().createQuery(query).setParameter("s", gs.get(1)).getResultList());
			}

			// By Actors
			List<String> cs = video.getActors();
			query = "select v from Video v where :a member of actors";
			videoList.addAll(getSession().createQuery(query).setParameter("a", cs.get(0)).getResultList());
			if (cs.get(1) != null) {
				videoList.addAll(getSession().createQuery(query).setParameter("a", cs.get(1)).getResultList());
			}

			// By Director
			query = "select v from Video v where director=:sd";
			videoList.addAll(getSession().createQuery(query).setParameter("sd", video.getDirector()).getResultList());
		} catch (Exception e) {
			videoList = null;
			return null;
		}
		Set<Video> videoSet = new LinkedHashSet<>(videoList);
		videoList.clear();
		videoList.addAll(videoSet);
		videoList.remove(video);
		return videoList; // append this part after adding movies to your directory ".subList(0, 5);"
	}

	@Override
	public List<Comment> gcoms(String commentString) {
		try {
			query = "select v from Video v where videoName=:c ";
			Session session = getSession();
			List<Comment> commentList = new LinkedList<>();
			Video video = (Video) session.createQuery(query).setParameter("c", commentString).getSingleResult();
			commentList.addAll(video.getComments());
			return commentList;
		} catch (Exception e) {
			List<Comment> commentList = null;
			return commentList;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> byname(String na) {
		try {
			Session session = getSession();
			query = "select v from Video v where videoName like CONCAT('%',:c,'%') ";
			List<Video> vide = session.createQuery(query).setParameter("c", na).getResultList();
			return vide;
		} catch (Exception e) {
			List<Video> videoList = null;
			return videoList;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> bycat(String nc) {
		try {
			Session session = getSession();
			query = "select v from Video v where :s member of genre";
			List<Video> vidist = session.createQuery(query).setParameter("s", nc).getResultList();
			return vidist;
		} catch (Exception e) {
			List<Video> vidist = null;
			return vidist;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> byact(String nac) {
		try {
			Session session = getSession();
			query = "select v from Video v where :a member of actors";
			List<Video> vidist = session.createQuery(query).setParameter("a", nac).getResultList();
			return vidist;
		} catch (Exception e) {
			List<Video> vidist = null;
			return vidist;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> bydir(String nd) {
		try {
			Session session = getSession();
			query = "select v from Video v where director=:d";
			List<Video> videoList = session.createQuery(query).setParameter("d", nd).getResultList();
			return videoList;
		} catch (Exception e) {
			List<Video> videoList = null;
			return videoList;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> popular() {
		try {
			Session session = getSession();
			query = "select v from Video v order by v.likee desc";
			List<Video> videoList = session.createQuery(query).setMaxResults(10).getResultList();
			return videoList;
		} catch (Exception e) {
			List<Video> videoList = null;
			return videoList;
		}
	}

	@Override
	public void likev(int i) {
		try {
			Session session = getSession();
			query = "select v from Video v where vid=:id";
			Video video = (Video) session.createQuery(query).setParameter("id", i).getSingleResult();
			video.setlikee(video.getlikee() + 1);
			session.persist(video);
		} catch (Exception e) {
		}
	}

	@Override
	public void dislikev(int i) {
		try {
			Session session = getSession();
			query = "select v from Video v where vid=:id";
			Video video = (Video) session.createQuery(query).setParameter("id", i).getSingleResult();
			video.setDislikee(video.getDislikee() + 1);
			session.persist(video);
		} catch (Exception e) {
		}
	}

	@Override
	public void deletecom(int id) {
		try {
			Session session = getSession();
			query = "select c from Comment c where cid=:i";
			Comment comment = (Comment) session.createQuery(query).setParameter("i", id).getSingleResult();
			session.remove(comment);
		} catch (Exception e) {
		}

	}

	@Override
	public void deletevideo(int id) {
		try {
			Session session = getSession();
			query = "select v from Video v where vid=:id";
			Video video = (Video) session.createQuery(query).setParameter("id", id).getSingleResult();
			session.remove(video);
		} catch (Exception e) {

		}
	}

	@Override
	public void adc(int vid, Comment d) {
		try {
			Session session = getSession();
			query = "select v from Video v where vid=:id";
			Video video = (Video) session.createQuery(query).setParameter("id", vid).getSingleResult();
			video.getComments().add(d);
			session.persist(video);
		} catch (Exception e) {
		}
	}
}