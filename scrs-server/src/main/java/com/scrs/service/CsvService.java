package com.scrs.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.service.CsvServiceImpl.RecordMapper;

public interface CsvService {

	List<CSVRecord> parseCsv(MultipartFile file, String[] headers) throws IOException;

	<T> List<T> parseCsv(MultipartFile file, String[] headers, RecordMapper<T> mapper) throws IOException;

}
