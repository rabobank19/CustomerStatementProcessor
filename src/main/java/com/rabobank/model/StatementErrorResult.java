package com.rabobank.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public final class StatementErrorResult {

	private final List<StatementError> statementErrorList;
	private final int statementsProcessed;
	private final int violationsFound;
	private final String fileName;

	public StatementErrorResult(final List<StatementError> statementErrorList, int statementsProcessed,
			int violationsFound, String fileName) {

		this.statementErrorList = statementErrorList;
		this.statementsProcessed = statementsProcessed;
		this.violationsFound = violationsFound;
		this.fileName = fileName;
	}
}
