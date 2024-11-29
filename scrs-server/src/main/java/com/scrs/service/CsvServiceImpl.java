package com.scrs.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvServiceImpl implements CsvService{

	@Override
	public List<CSVRecord> parseCsv(MultipartFile file, String[] headers) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
				CSVParser csvParser = new CSVParser(reader,
				CSVFormat.DEFAULT.builder().setHeader(headers).setSkipHeaderRecord(true).build())) {
			return csvParser.getRecords();
		}
	}

	@Override
	public <T> List<T> parseCsv(MultipartFile file, String[] headers, RecordMapper<T> mapper) throws IOException {
		List<T> results = new ArrayList<>();
		for (CSVRecord record : parseCsv(file, headers)) {
			results.add(mapper.map(record));
		}
		return results;
	}

	@FunctionalInterface
	public interface RecordMapper<T> {
		T map(CSVRecord record);
	}
}
