package com.rabobank.controller;

import com.rabobank.util.CsvFileParser;
import com.rabobank.util.FileParser;
import com.rabobank.util.XmlFileParser;
import com.rabobank.model.StatementError;
import com.rabobank.model.StatementErrorResult;
import com.rabobank.service.FileStorageService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class FileController {

	@Autowired
	private FileStorageService fileStorageService;

	private PrintStream printStream = System.out;

	@Value("${file.upload-dir}")
	private String fileUploadLocation;

	@PostMapping("/uploadFile")
	public StatementErrorResult uploadFile(@RequestParam("file") MultipartFile file) {

		log.info("inside FileController --> uploadFile method ");
		
		StatementErrorResult statementErrorResult = null;

		String fileName = fileStorageService.storeFile(file);

		String fullFileName = fileUploadLocation + fileName;

		Map<String, StatementErrorResult> processMap = new FileController().run(fullFileName);

		List<StatementError> statementErrorList = new ArrayList<>();

		for (String path : processMap.keySet()) {

			statementErrorResult = processMap.get(path);
		}
		
		 return statementErrorResult;
		
	}

	@PostMapping("/uploadMultipleFiles")
	public List<StatementErrorResult> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		
		List<StatementErrorResult> statementErrorResult = new ArrayList<>();
		log.info("inside FileController --> uploadMultipleFiles method ");
		return  Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	protected Map<String, StatementErrorResult> run(final String... paths) {

		Map<String, StatementErrorResult> processMap = new HashMap<>();
		for (String path : paths) {

			String fileName = path.substring(path.lastIndexOf("/") + 1);
			log.info("\n");
			log.info("Processing " + fileName + " ...");
			processMap.put(fileName, this.process(path));
		}

		return processMap;
	}

	protected StatementErrorResult process(final String pathName) {

		List<StatementError> statementErrorList = new ArrayList<>();

		StatementErrorResult statementErrorResult = null;

		String ext = pathName.substring(pathName.indexOf('.') + 1);

		InputStream stream = null;

		try {
			stream = new FileInputStream(pathName);
		} catch (Exception e) {
			log.info("Exception thrown when creating file input stream " + e);
		}

		FileParser processor = buildProcessor(ext);
		if (processor == null) {
			log.info(pathName + " Not processed: extension not supported");
		} else {

			statementErrorResult = processor.fileProcess(stream, pathName);
		}

		return statementErrorResult;
	}

	protected FileParser buildProcessor(final String ext) {
		if ("csv".equals(ext)) {
			return new CsvFileParser(this.printStream);
		} else if ("xml".equals(ext)) {
			return new XmlFileParser(this.printStream);
		} else {
			log.info("unsupported extension type so returning null");
			return null;
		}
	}
}
