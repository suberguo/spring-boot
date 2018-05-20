package com.dds.upload;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface StorageService {
	public void init();

	public void store(MultipartFile file) throws Exception;

	public Stream<Path> loadAll();

	public Path load(String fileName);

	public Resource loadAsResource(String fileName) throws StorageFileNotFoundException;

	public void deleteAll();
}
