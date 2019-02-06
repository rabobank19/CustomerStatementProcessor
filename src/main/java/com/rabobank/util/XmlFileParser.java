package com.rabobank.util;

import com.rabobank.model.CustomerStatement;
import com.rabobank.model.StatementError;
import com.rabobank.model.StatementErrorResult;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import noNamespace.CustomerStatementType;
import noNamespace.RecordsDocument;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

/**
 * This class is implements xml parser.
 * 
 * @author balaji
 *
 */
@Slf4j
public class XmlFileParser extends FileParser {

	private XmlStatementParser parser = new XmlStatementParser();

	public XmlFileParser(final PrintStream printStream) {
		super(printStream);
	}

	/**
	 * This method is to process the xml document.
	 */
	public void process(final InputStream stream) {
		try {
			ArrayList validationErrors = new ArrayList();
			XmlOptions options = buildOptions(validationErrors);
			RecordsDocument document = RecordsDocument.Factory.parse(stream, options);
			for (CustomerStatementType statement : document.getRecords().getRecordArray()) {
				this.processStatement(statement);
			}
		} catch (XmlException | IOException e) {
			log.info("Error while parsing the xml file");
		} finally {
			this.endReport();
		}
	}

	/**
	 * This method is to process the xml document.
	 */
	public StatementErrorResult fileProcess(final InputStream stream, String fileName) {

		List<StatementError> statementErrorList = new ArrayList<>();
		log.info("inside xml fileProcess method ... ");
		
		try {
			ArrayList validationErrors = new ArrayList();
			XmlOptions options = buildOptions(validationErrors);
			RecordsDocument document = RecordsDocument.Factory.parse(stream, options);
			for (CustomerStatementType statement : document.getRecords().getRecordArray()) {

				List<StatementError> statementErrorList1 = new ArrayList<>();
				statementErrorList1 = this.fetchStatement(statement);
				if (!statementErrorList1.isEmpty()) {
					for (StatementError se : statementErrorList1) {
						statementErrorList.add(se);
					}
				}
			}
		} catch (XmlException | IOException e) {
			log.info("exception while processing the xml file " + e);
		} finally {
			this.getRecordCount();
		}

		StatementErrorResult statementErrorResult = new StatementErrorResult(statementErrorList, this.getRecordCount(),
				statementErrorList.size(), fileName.substring(fileName.lastIndexOf("/") + 1));
		return statementErrorResult;

	}

	/**
	 * This method is to buildOptions.
	 * 
	 * @param validationErrors describes validation error
	 * @return
	 */
	private XmlOptions buildOptions(ArrayList validationErrors) {
		XmlOptions options = new XmlOptions();
		options.setErrorListener(validationErrors);
		return options;
	}

	/**
	 * This method is to parse the customer statement.
	 */
	@Override
	protected CustomerStatement parseStatement(Object value) {
		return this.parser.parse((CustomerStatementType) value);
	}
}
