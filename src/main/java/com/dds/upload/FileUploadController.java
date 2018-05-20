package com.dds.upload;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {
	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;

	}

	@GetMapping("/upload")
	public String listUploadedFiles(Model model) {
		model.addAttribute("files",
				storageService.loadAll()
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
								.build().toString())
						.collect(Collectors.toList()));
		return "upload";
	}

	@GetMapping("/files/{fileName}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String fileName) throws StorageFileNotFoundException {
		Resource resource = storageService.loadAsResource(fileName);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@PostMapping
	public String handleFileUpload(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes)
			throws Exception {
		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
		return "redirect:/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<Resource> handleStorageFileNotFound(StorageFileNotFoundException exp) {
		return ResponseEntity.notFound().build();
	}
}
