package com.slimtrade.core.update;

/**
 * Callback interface for downloading files
 */

public interface IDownloadTracker {

    public void downloadPercentCallback(int progress);

    public void textCallback(String message);

}
