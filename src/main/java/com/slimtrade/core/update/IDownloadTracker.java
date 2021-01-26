package com.slimtrade.core.update;

/**
 * Callback interface for downloading files
 */

public interface IDownloadTracker {

    void downloadPercentCallback(int progress);

    void textCallback(String message);

}
