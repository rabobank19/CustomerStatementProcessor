package com.rabobank.util;

import com.rabobank.model.StatementError;

import java.io.PrintStream;
import java.util.Collection;

public final class ReportWriter {

	private static final int REFNR_COLUMN_WIDTH = 6;
	private static final int DESCR_COLUMN_WIDTH = 30;

	private PrintStream outputStream;
	private boolean headerPrinted = false;
	private long violationCount = 0;

	public ReportWriter(final PrintStream printStream) {
		this.outputStream = printStream;
	}

	/**
	 * This method is to report violations.
	 * 
	 * @param violations describes statement of violations
	 */
	public void printViolations(final Collection<StatementError> violations) {
		if (!violations.isEmpty()) {
			this.printHeader();
			for (StatementError error : violations) {
				violationCount++;
				printLine(renderLine(error));
			}
		}
	}

	/**
	 * This utility method prints the header.
	 */
	private void printHeader() {
		if (!headerPrinted) {
			printLine("");
			printLine(renderLine("RefNo.", "Description", "Validation Failed Case"));
			printLine(renderLine("______", "______________________________", "______________________________"));
			printLine("");
			headerPrinted = true;
		}
	}

	/**
	 * This utility method prints the end of report.
	 * 
	 * @param statementCount describes statement count
	 */
	public void endReport(final int statementCount) {
		printLine("");
		printLine("No. of statements processed: " + statementCount);
		String counts = (violationCount == 0) ? "NONE" : "" + violationCount;
		printLine("No. of violations found: " + counts);
	}

	/**
	 * This method prints line.
	 * 
	 * @param line describes line
	 */
	private void printLine(final String line) {
		outputStream.println(line);
	}

	/**
	 * This method is render line of violated value.
	 * 
	 * @param error describes statement error
	 * @return
	 */
	protected static String renderLine(final StatementError error) {
		return renderLine(error.getReferenceNumber(), error.getStatementDescription(), error.getErrorMessage());
	}

	/**
	 * This method aligns the result.
	 * 
	 * @param refNr           describes ref no.
	 * @param statementDescr  describes statement description
	 * @param validationError describes validation error
	 * @return
	 */
	protected static String renderLine(final String refNr, final String statementDescr, final String validationError) {
		return align(refNr, REFNR_COLUMN_WIDTH) + "   " + align(statementDescr, DESCR_COLUMN_WIDTH) + "   "
				+ validationError;
	}

	/**
	 * This method aligns the end result.
	 * 
	 * @param value  describes value
	 * @param length describes value
	 * @return
	 */
	protected static String align(String value, final int length) {
		if (value == null) {
			value = "[null]";
		}

		StringBuffer result = new StringBuffer();
		int copyLen = value.length() < length ? value.length() : length;
		result.append(value.substring(0, copyLen));
		while (result.length() < length) {
			result.append(' ');
		}
		return result.toString();
	}
}
