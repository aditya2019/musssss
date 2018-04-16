package com.song.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.lang.model.element.Element;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFSDBFile;
import com.mymusic.Mymusic;
import com.register.Register;
import com.song.repository.Songrepository;
import com.songlist.Song;

import javazoom.jl.player.Player;

@RestController
@RequestMapping("/musicguru")
public class SongController {
	Long currentFrame;
	Clip clip;
	AudioInputStream audioInputStream;
	String filePath;

	private Songrepository songrepository;

	public SongController(Songrepository songrepository) {
		this.songrepository = songrepository;
	}

	@GetMapping("/all")
	public List<Song> getAll() {
		return songrepository.findAll();
	}

	// ........ upload method for image
	@PostMapping("/file/upload")
	public String upload(@RequestParam("file") MultipartFile file) {
		String uploadPath = "src/main/resources/images/";
		File path = new File(uploadPath);
		if (!path.exists())
			path.mkdirs();
		System.out.println(uploadPath);
		try {
			Files.copy(file.getInputStream(), Paths.get(uploadPath, file.getOriginalFilename()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "upload successfully ";
	}
	// ........ close of upload image

	// ........ upload method for audio

	@PostMapping("/file/upload/audio")
	public String uploadaudio(@RequestParam("file") MultipartFile file) {
		String uploadPath = "src/main/resources/audios/";
		File path = new File(uploadPath);
		if (!path.exists())
			path.mkdirs();
		System.out.println(uploadPath);
		try {
			Files.copy(file.getInputStream(), Paths.get(uploadPath, file.getOriginalFilename()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Upload Audio is succesfully";

	}

	// ........ upload method for close

	// .........get image Api

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) throws IOException {
		System.out.println(id);
		ClassPathResource imgFile = new ClassPathResource("images/" + id + ".jpg");
		byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

		return ResponseEntity.ok().contentType(MediaType.ALL).body(bytes);

	}
	// //........Close get image

	// ......... api to play the song all proyog
	@RequestMapping(value = "/playsong", method = RequestMethod.GET, produces = MediaType.ALL_VALUE)
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Request fot mp3");

		// String fileName = (String) request.getParameter("file");
		// if (fileName == null || fileName.equals(""))
		// throw new ServletException(
		// "Invalid or non-existent file parameter in SendMp3 servlet.");
		//
		// if (fileName.indexOf(".mp3") == -1)
		String fileName = "apna" + ".mp3";
		//
		// String mp3Dir = getServletContext().getInitParameter("mp3-dir");
		// if (mp3Dir == null || mp3Dir.equals(""))
		// throw new ServletException(
		// "Invalid or non-existent mp3Dir context-param.");

		ServletOutputStream stream = null;
		BufferedInputStream buf = null;
		try {

			stream = response.getOutputStream();
			File mp3 = new File(
					"/home/kls103/Documents/workspace-sts-3.9.2.RELEASE/Music_Gallery_Project/src/main/resources/audios/"
							+ fileName);

			// set response headers
			response.setContentType("audio/mpeg");

			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

			response.setContentLength((int) mp3.length());

			FileInputStream input = new FileInputStream(mp3);
			buf = new BufferedInputStream(input);
			int readBytes = 0;
			// read from the file; write to the ServletOutputStream
			while ((readBytes = buf.read()) != -1)
				stream.write(readBytes);
		} catch (IOException ioe) {
			throw new ServletException(ioe.getMessage());
		} finally {
			if (stream != null)
				stream.close();
			if (buf != null)
				buf.close();
		}
	}

	// @RequestMapping(value = "/play/{id}", method = RequestMethod.GET)
	// @ResponseBody
	// public HttpEntity<byte[]> get(@PathVariable("name") String name) {
	// try {
	// Optional<GridFSDBFile> optionalCreated = maybeLoadFile(name);
	// if (optionalCreated.isPresent()) {
	// GridFSDBFile created = optionalCreated.get();
	// ByteArrayOutputStream os = new ByteArrayOutputStream();
	// created.writeTo(os);
	// HttpHeaders headers = new HttpHeaders();
	// headers.add(HttpHeaders.CONTENT_TYPE, created.getContentType());
	// return new HttpEntity<>(os.toByteArray(), headers);
	// } else {
	// return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// }
	// } catch (IOException e) {
	// return new ResponseEntity<>(HttpStatus.IM_USED);
	// }
	// }
	//

	// @RequestMapping(value = "/play/{id}", method = RequestMethod.GET)
	// @ResponseBody
	// public void play(@PathVariable("id") String id) {
	//
	// try{
	// filePath =
	// "/home/kls103/Documents/workspace-sts-3.9.2.RELEASE/Music_Gallery_Project/src/main/resources/audios/"+id+".aiff";
	// audioInputStream =
	// AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
	//
	// // create clip reference
	// clip = AudioSystem.getClip();
	//
	// // open audioInputStream to the clip
	// clip.open(audioInputStream);
	//
	// //clip.loop(Clip.LOOP_CONTINUOUSLY);
	//
	// clip.start();
	//
	//
	// } catch(Exception e){
	// System.out.println(e);
	// }
	// }

	// pause the song ............
	@RequestMapping(value = "/pause", method = RequestMethod.GET)
	@ResponseBody
	public void pause() {
		try {

			this.currentFrame = this.clip.getMicrosecondPosition();
			clip.stop();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// stop the song .................

	@RequestMapping(value = "/stop", method = RequestMethod.GET)
	@ResponseBody
	public void stop() {
		try {
			clip.stop();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// ......... close the play song
	@RequestMapping(value = "/songs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@RequestBody Song song) {
		try {

			songrepository.save(song);
			return ResponseEntity.noContent().build();

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	// ....................................

	// how to donload the Song
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ResponseBody
	public void download(HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream stream = null;
		BufferedInputStream buf = null;
		try {
			// my mp3 path to file
			String pathToFile = "/home/kls103/Documents/workspace-sts-3.9.2.RELEASE/Music_Gallery_Project/src/main/resources/audios/apna.aiff";
			stream = response.getOutputStream();
			File mp3 = new File(pathToFile);
			// header to force download
			response.setContentType("application/force-download");
			response.setContentLength((int) mp3.length());
			FileInputStream input = new FileInputStream(mp3);
			buf = new BufferedInputStream(input);
			int readBytes = 0;
			while ((readBytes = buf.read()) != -1)
				stream.write(readBytes);
		} catch (IOException ioe) {
			// throw new ServletException(ioe.getMessage());
		} finally {
			// if(stream != null)
			// // stream.close();
			// if(buf != null)
			// // buf.close();
		}
	}

	// --------- this closing of this class .
}
