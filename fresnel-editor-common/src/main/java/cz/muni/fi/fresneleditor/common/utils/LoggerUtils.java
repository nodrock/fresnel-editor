package cz.muni.fi.fresneleditor.common.utils;

import org.openrdf.sail.memory.model.MemStatement;
import org.openrdf.sail.memory.model.MemStatementList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(LoggerUtils.class);

	public static final void debugPrintStatementsList(MemStatementList list) {
		for (int i = 0; i < list.size(); i++) {
			logger.debug("" + list.get(i));
		}
	}

	public static void debugPringStatements(MemStatement statement) {
		logger.debug("printing statement {}" + statement);

		logger.debug("statement.getObject().getObjectStatementList():");
		debugPrintStatementsList(statement.getObject().getObjectStatementList());

		logger.debug("statement.getSubject().getObjectStatementList():");
		debugPrintStatementsList(statement.getSubject()
				.getObjectStatementList());
		logger.debug("statement.getSubject().getSubjectStatementList():");
		debugPrintStatementsList(statement.getSubject()
				.getSubjectStatementList());

		logger.debug("statement.getPredicate().getContextStatementList():");
		debugPrintStatementsList(statement.getPredicate()
				.getContextStatementList());
		logger.debug("statement.getPredicate().getSubjectStatementList():");
		debugPrintStatementsList(statement.getPredicate()
				.getSubjectStatementList());
		logger.debug("statement.getPredicate().getObjectStatementList():");
		debugPrintStatementsList(statement.getPredicate()
				.getObjectStatementList());
	}

}
