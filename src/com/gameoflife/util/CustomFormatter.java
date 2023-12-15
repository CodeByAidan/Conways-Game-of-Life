package com.gameoflife.util;

import java.text.MessageFormat;
import java.util.logging.LogRecord;

/**
 * Custom formatter for the logger.
 * @see com.gameoflife.util.LogConfiguration#configure() com.gameoflife.util.LogConfiguration#configure()
 * @see java.util.logging.Formatter
 */
public class CustomFormatter extends java.util.logging.Formatter {

    /**
     * This method formats the log record.
     * @param logRecord
     *        The log record to be formatted as a {@code LogRecord}
     * @return The formatted log record as a {@code String}
     * @see java.util.logging.Formatter#format(LogRecord)
     */
    @Override
    public String format(LogRecord logRecord) {
        String message = formatMessage(logRecord);
        String throwable = "";
        if (logRecord.getThrown() != null) {
            throwable = MessageFormat.format("{0}\n", logRecord.getThrown().toString());
        }
        return MessageFormat.format("{0} {1} {2}\n",
                logRecord.getLevel().getLocalizedName(),
                logRecord.getLoggerName(),
                throwable + message);
    }
}
