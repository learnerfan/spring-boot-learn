package com.sc.zhenli.conf;

public class DBSequenceUtil {
	private static DBSequenceUtil _oSeqGenerator;
	private long _lID;

	private DBSequenceUtil() {
		_lID = 0;
	}

	public final static synchronized DBSequenceUtil getInstance() {
		if (_oSeqGenerator == null) {
			_oSeqGenerator = new DBSequenceUtil();
		}
		return _oSeqGenerator;
	}

	public synchronized String getSequence() {
		_lID++;
		long lCurTime_ = System.currentTimeMillis() - 1000000000;
		long lTempID_ = _lID + lCurTime_;
		return Long.toString(lTempID_);
	}
}
