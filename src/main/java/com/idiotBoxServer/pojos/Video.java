package com.idiotBoxServer.pojos;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Video {
	Integer vid;
	int runtimee;
	int year;
	int likee;
	int dislikee;
	String descr;
	String videoName; 
	String director;
	List<String> genre;
	List<String> actors;
	List<Comment> comments;
	@Column(columnDefinition = "varchar(max)")
	String poster;
	String videoPath;
	String certificate;

	public Video() {
		super();
	}

	@Column(name = "posterurl")
	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	@Column(name = "videoPath")
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	@Column(nullable = true)
	public int getlikee() {
		return likee;
	}

	public void setlikee(int likee) {
		this.likee = likee;
	}

	@Column(nullable = true)
	public int getDislikee() {
		return dislikee;
	}

	public void setDislikee(int dislikee) {
		this.dislikee = dislikee;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "genre", joinColumns = @JoinColumn(name = "vid"))
	public List<String> getGenre() {
		return genre;
	}

	public void setGenre(List<String> genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "actors", joinColumns = @JoinColumn(name = "vid"))
	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "vid")
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getruntimee() {
		return runtimee;
	}

	public void setruntimee(int runtimee) {
		this.runtimee = runtimee;
	}

	public String getdescr() {
		return descr;
	}

	public void setdescr(String descr) {
		this.descr = descr;
	}

	public Video(Integer vid, String videoName, String director, List<String> genre, int year, List<String> actors,
			String pos, String videoPath, int runtimee, String descr) {
		super();
		this.vid = vid;
		this.videoName = videoName;
		this.director = director;
		this.genre = genre;
		this.year = year;
		this.actors = actors;
		this.runtimee = runtimee;
		this.descr = descr;
		this.poster = pos;
		this.videoPath = videoPath;
	}

	public Video(Integer vid, String videoName, String director, List<String> genre, int year, int likee, int dislikee,
			List<String> actors, List<Comment> comments, String pos, String videoPath, int runtimee, String descr, String certificate) {
		super();
		this.vid = vid;
		this.videoName = videoName;
		this.director = director;
		this.genre = genre;
		this.year = year;
		this.likee = likee;
		this.dislikee = dislikee;
		this.actors = actors;
		this.comments = comments;
		this.runtimee = runtimee;
		this.descr = descr;
		this.poster = pos;
		this.videoPath = videoPath;
		this.certificate = certificate;
	}

	@Override
	public String toString() {
		return "Video [id=" + vid + ", videoName=" + videoName + ", director=" + director + ", genre=" + genre + ", year=" + year
				+ ", \t likee=" + likee + ", dislikee=" + dislikee + ", \n actors=" + actors + ",  \n descr=" + descr
				+ ", runtimee=" + runtimee + ", comments=" + comments + "]";
	}
}