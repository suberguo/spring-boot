package com.dds.upload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
public class StorageServiceImpl implements StorageService {

	private final Path rootPath;

	@Autowired
	public StorageServiceImpl(StorageProperties storageProperties) {
		this.rootPath = Paths.get(storageProperties.getLocation());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void store(MultipartFile file) {
		String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
		if (file.isEmpty()) {
			throw new RuntimeException("File is empty");
		}
		if (StringUtils.isEmpty(fileName)) {
			throw new RuntimeException("File is empty");
		}
		InputStream in = null;
		try {
			in = file.getInputStream();
			Files.copy(file.getInputStream(), rootPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Failed to store");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootPath, 1).filter(p -> !p.equals(this.rootPath)).map(this.rootPath::relativize);
		} catch (IOException e) {
			throw new RuntimeException("File not found");
		}
	}

	@Override
	public Path load(String fileName) {
		return this.rootPath.resolve(fileName);
	}

	@Override
	public Resource loadAsResource(String fileName) throws StorageFileNotFoundException {
		Path path = load(fileName);
		if (path == null) {
			throw new StorageFileNotFoundException("File not found");
		}

		UrlResource resource;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("File not found");
		}
		if (resource.exists() && resource.isReadable()) {
			return resource;
		} else {
			throw new StorageFileNotFoundException("File not found");
		}

	}

	@Override
	public void deleteAll() {
		try {
			FileSystemUtils.deleteRecursively(this.rootPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
