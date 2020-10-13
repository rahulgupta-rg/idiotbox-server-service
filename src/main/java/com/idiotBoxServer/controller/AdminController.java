package com.idiotBoxServer.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.idiotBoxServer.SMTPEmailSender;
import com.idiotBoxServer.pojos.Admin;
import com.idiotBoxServer.pojos.Feedback;
import com.idiotBoxServer.pojos.OtpAdminWrapper;
import com.idiotBoxServer.pojos.Video;
import com.idiotBoxServer.service.AdminService;
import com.idiotBoxServer.service.VideoService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService service;

	@Autowired
	private VideoService videoService;

	@Autowired
	private SMTPEmailSender smtpEmailSender;

	@PostMapping("/login/{email}")
	public ResponseEntity<OtpAdminWrapper> validateLoginFormAdmin(@PathVariable String email,
			@RequestBody String userPassword, HttpSession hs) {
		Admin admin = null;
		try {
			admin = service.validateAdmin(email, userPassword);
			System.out.println(admin);
			if (admin != null) {
				smtpEmailSender.setValues();
				smtpEmailSender.send(email, smtpEmailSender.getEmailSubject(), smtpEmailSender.getBody());
				OtpAdminWrapper wrapper = new OtpAdminWrapper(smtpEmailSender.getPassword(), admin);
				ResponseEntity<OtpAdminWrapper> entity = new ResponseEntity<OtpAdminWrapper>(wrapper, HttpStatus.OK);
				return entity;
			} else {
				OtpAdminWrapper wrapper = null;
				ResponseEntity<OtpAdminWrapper> entity = new ResponseEntity<OtpAdminWrapper>(wrapper,
						HttpStatus.NOT_FOUND);
				return entity;
			}
		} catch (Exception e) {
			OtpAdminWrapper wrapper = new OtpAdminWrapper();
			ResponseEntity<OtpAdminWrapper> entity = new ResponseEntity<OtpAdminWrapper>(wrapper, HttpStatus.NOT_FOUND);
			return entity;
		}
	}

	@PostMapping("/register")
	public ResponseEntity<String> postRegisterForm(@RequestBody Admin admin) {
		try {
			smtpEmailSender.setValues();
			smtpEmailSender.send(admin.getEmail(), smtpEmailSender.getEmailSubject(), smtpEmailSender.getBody());
			String password = smtpEmailSender.getPassword();
			ResponseEntity<String> entity = new ResponseEntity<String>(password, HttpStatus.OK);
			return entity;
		} catch (Exception e) {
			ResponseEntity<String> entity = new ResponseEntity<String>("Error in Registration", HttpStatus.NOT_FOUND);
			return entity;
		}
	}

	@PostMapping("/verifyOtp")
	public ResponseEntity<String> verifyOtp(@RequestBody Admin admin) {
		try {
			String str = service.registerAdmin(admin);
			ResponseEntity<String> entity = new ResponseEntity<String>(str, HttpStatus.OK);
			return entity;
		} catch (Exception e) {
			ResponseEntity<String> entity = new ResponseEntity<String>("Error", HttpStatus.NOT_FOUND);
			return entity;
		}
	}

	@PostMapping("/viewAdmins")
	public ResponseEntity<List<Admin>> getAllAdmins(@RequestBody Admin admin) {
		try {
			List<Admin> list = service.getAllAdmins(admin.getAdminId());
			ResponseEntity<List<Admin>> entity = new ResponseEntity<List<Admin>>(list, HttpStatus.OK);
			return entity;
		} catch (Exception e) {
			List<Admin> list = null;
			ResponseEntity<List<Admin>> entity = new ResponseEntity<List<Admin>>(list, HttpStatus.NOT_FOUND);
			return entity;
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteAdmin(@RequestBody Integer adminId) {
		try {
			String str = service.deleteAdmin(adminId);
			ResponseEntity<String> entity = new ResponseEntity<String>(str, HttpStatus.OK);
			return entity;
		} catch (Exception e) {
			ResponseEntity<String> entity = new ResponseEntity<String>("Can't Be Deleted", HttpStatus.OK);
			return entity;
		}
	}

	@GetMapping("/comments")
	public String getComments() {
		return "/admin/comments";
	}

	@PostMapping("/comments")
	public String postComments() {
		return "/admin/comments";
	}

	@PostMapping("/addVideo")
	public ResponseEntity<String> registerVideo(@RequestParam("file") MultipartFile file) {
		ResponseEntity<String> entity = null;
		System.out.println(file.getOriginalFilename());
		String id = videoService.registerVideo(file);
		if (!id.equals("")) {
			entity = new ResponseEntity<String>(id, HttpStatus.OK);
		} else {
			entity = new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
		}
		return entity;
	}

	@PostMapping("/addVideoDetail")
	public ResponseEntity<Integer> registerVideoDetail(@RequestBody Video video) {
		int id = videoService.registerVideoDetail(video);
		if (id == -1) {
			ResponseEntity<Integer> entity = new ResponseEntity<Integer>(1, HttpStatus.NOT_FOUND);
			return entity;
		}
		ResponseEntity<Integer> entity = new ResponseEntity<Integer>(1, HttpStatus.OK);
		return entity;
	}

	@PostMapping("/getVideo")
	public ResponseEntity<File> getVideoByID(@RequestBody int VideoID) throws MalformedURLException, IOException {
		System.out.println("Video ID " + VideoID);
		Video video = videoService.getVideo(VideoID);
		String videoPath = video.getVideoPath();
		String uploadLocation = "E:/IdiotBox/IdiotBoxRepository/";
		File videoFile = new File(uploadLocation + videoPath);
		if (videoFile.exists()) {
			ResponseEntity<File> entity = new ResponseEntity<File>(videoFile, HttpStatus.OK);
			return entity;
		}
		return new ResponseEntity<File>(videoFile, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getFeedback")
	public ResponseEntity<?> getFeedbackList() {
		try {
			List<Feedback> feedbacks = service.getFeedbackList();
			ResponseEntity<List<Feedback>> entity = new ResponseEntity<List<Feedback>>(feedbacks, HttpStatus.OK);
			System.out.println("List is " + feedbacks);
			return entity;
		} catch (Exception e) {
			ResponseEntity<String> entity = new ResponseEntity<String>("No feedback found", HttpStatus.NOT_FOUND);
			return entity;
		}
	}
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody Admin admin)
	{
		System.out.println("Received user details : "+admin);
		String updateStatus = service.updateAdmin(admin);
		ResponseEntity<String> entity = new ResponseEntity<String>(updateStatus,HttpStatus.OK);
		System.out.println("Sending entity after update");
		return entity;
	}
}