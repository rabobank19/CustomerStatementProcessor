package com.rabobank.util;

import com.rabobank.model.CustomerStatement;
import com.rabobank.model.StatementError;
import com.rabobank.model.StatementErrorResult;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;

/**
 * This class is to parse and process the csv files.
 * 
 * @author balaji
 *
 */
@Slf4j
@SuppressFBWarnings(value = { "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE" })
public final class CsvFileParser extends FileParser {

	private CsvLineParser parser = new CsvLineParser();

	public CsvFileParser(final PrintStream printStream) {
		super(printStream);
	}

	/**
	 * This method is to process the customer statement.
	 */
	public void process(final InputStream stream) {
		
		List<String> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.ISO_8859_1))) {
			
			list = reader.lines().collect(Collectors.toList());
			list.forEach(this::processStatement);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			this.endReport();
		}
	}

	/**
	 * This method is to process the customer statement.
	 */
	public StatementErrorResult fileProcess(final InputStream stream, String fileName) {

		log.info("inside the fileProcess method");
		List<StatementError> statementErrorList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.ISO_8859_1))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				List<StatementError> statementErrorList1 = new ArrayList<>();
				statementErrorList1 = this.fetchStatement(line);

				if (!statementErrorList1.isEmpty()) {
					for (StatementError se : statementErrorList1) {
						statementErrorList.add(se);
					}
				}
			}
		} catch (IOException e) {
			log.info("exception during file process ......." + e);
			throw new RuntimeException(e);
		} finally {			
			this.getRecordCount();
		}

		StatementErrorResult statementErrorResult = new StatementErrorResult(statementErrorList, this.getRecordCount(),
				statementErrorList.size(), fileName.substring(fileName.lastIndexOf("/") + 1));
		return statementErrorResult;
	}

	@Override
	protected CustomerStatement parseStatement(final Object value) {
		return parser.parse((String) value);
	}
}
