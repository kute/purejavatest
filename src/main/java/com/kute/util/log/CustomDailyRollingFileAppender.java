package com.kute.util.log;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Layout;

public class CustomDailyRollingFileAppender
		extends org.apache.log4j.DailyRollingFileAppender {

	private int maxBackupIndex = 3;

	private boolean deleteBackup = false;

	private final String YEAR_MONTH_DAY = "'_'yyyyMMdd";

	private SimpleDateFormat sdf = new SimpleDateFormat(this.YEAR_MONTH_DAY);

	public CustomDailyRollingFileAppender() {
	}

	public CustomDailyRollingFileAppender(Layout layout, String filename,
                                          String datePattern, int maxBackupIndex) throws IOException {
		super(layout, filename, datePattern);
		this.maxBackupIndex = maxBackupIndex;
		this.setDeleteBackup(datePattern);
	}

	public void setDeleteBackup(String pattern) {
		if (pattern.equals(this.YEAR_MONTH_DAY)) {
			this.deleteBackup = true;
		}
	}

	public int getMaxBackupIndex() {
		return maxBackupIndex;
	}

	public void setMaxBackupIndex(int maxBackupIndex) {
		this.maxBackupIndex = maxBackupIndex;
	}

	public void setDatePattern(String pattern) {
		super.setDatePattern(pattern);
		this.setDeleteBackup(pattern);
	}

	protected void closeFile() {
		if (this.deleteBackup) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 0 - this.maxBackupIndex);
			String datedFilename = this.fileName +
					this.sdf.format(calendar.getTime());
			File target = new File(datedFilename);
			if (target.exists()) {
				target.delete();
			}
		}
		super.closeFile();
	}

}