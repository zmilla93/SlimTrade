package com.slimtrade.modules.updater;

/**
 * Callbacks for tracking download progress.
 * All callbacks are called on the EventDispatchThread.
 */
public interface IUpdateProgressListener {

    // Called with a number 0-100
    void onDownloadProgress(int progressPercent);

    void onDownloadComplete();

    void onDownloadFailed();

}
