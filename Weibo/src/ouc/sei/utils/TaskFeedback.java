package ouc.sei.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class TaskFeedback {
	private Context _context;
	private ProgressDialog _dialog = null;

	private TaskFeedback() {
	}

	private static TaskFeedback _instance = null;

	public static TaskFeedback getInstance(Context _context) {
		if (_instance == null) {
			_instance = new TaskFeedback();
		}
		_instance.set_context(_context);

		return _instance;
	}

	public void set_context(Context _context) {
		this._context = _context;
	}

	public void cancel() {
		if (_dialog != null) {
			_dialog.dismiss();
		}
	}

	public void failed(String prompt) {
		if (_dialog != null) {
			_dialog.dismiss();
		}

		Toast toast = Toast.makeText(_context, prompt, Toast.LENGTH_SHORT);
		toast.show();
	}

	public void start(String prompt) {
		_dialog = ProgressDialog.show(_context, "", prompt, true);
		_dialog.setCancelable(true);
	}

	public void success(String prompt) {
		if (_dialog != null) {
			_dialog.dismiss();
		}
		Toast toast = Toast.makeText(_context, prompt, Toast.LENGTH_SHORT);
		toast.show();
	}

	public void success() {
		if (_dialog != null) {
			_dialog.dismiss();
		}
	}
}
