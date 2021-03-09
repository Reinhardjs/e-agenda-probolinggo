package com.example.e_agendaprobolinggo.utils;

import android.os.Handler;

import androidx.annotation.UiThread;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

public class DownloadManager {
    private static final long BUFFER_SIZE = 2048;
    private static final long FLUSH_SIZE = 1048 * 1048;
    private static final long UPDATE_RATE = 1000;

    private final ExecutorService mExecutorService;
    private final Handler mHandler;

    @UiThread
    public DownloadManager() {
        mExecutorService = Executors.newSingleThreadExecutor();
        mHandler = new Handler();
    }

    public void download(final String url, final String path, final Callback callback) {
        mExecutorService.execute(() -> downloadFile(url, path, callback));
    }

    private void downloadFile(String url, String path, Callback callback) {
        try {
            File downloadDir = new File(path);
            downloadDir.mkdirs();

            String filenameExtension = url.substring(url.lastIndexOf('/') + 1);
            String[] filenameSplit = filenameExtension.split("\\.");
            String filename = filenameSplit[0] + "_" + System.currentTimeMillis() / 1000 + "." + filenameSplit[1];

            File file = new File(downloadDir, filename);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response;

            response = client.newCall(request).execute();

            BufferedSink sink = Okio.buffer(Okio.sink(file));

            long totalLength = response.body().contentLength();
            notifyStarted(callback, totalLength);

            long downloadedLength = 0;
            long bufferSize = 0;
            long lastUpdate = 0;
            while (true) {
                long length = response.body().source().read(sink.buffer(), BUFFER_SIZE);

                if (length < 0) break;

                downloadedLength += length;
                bufferSize += length;

                // Avoid overflowing the cache
                if (bufferSize >= FLUSH_SIZE) {
                    sink.flush();
                    bufferSize = 0;
                }

                if (System.currentTimeMillis() > (lastUpdate + UPDATE_RATE)) {
                    notifyProgress(callback, totalLength, downloadedLength);
                    lastUpdate = System.currentTimeMillis();
                }
            }

            sink.close();

            notifyComplete(callback, file);
        } catch (IOException e) {
            e.printStackTrace();
            notifyError(callback, e);
        }
    }

    private void notifyStarted(final Callback callback, final long totalLength) {
        mHandler.post(() -> callback.onDownloadStarted(totalLength));
    }

    private void notifyProgress(final Callback callback, final long totalLength, final long downloadedLength) {
        mHandler.post(() -> callback.onDownloadProgress(totalLength, downloadedLength));
    }

    private void notifyComplete(final Callback callback, final File file) {
        mHandler.post(() -> callback.onDownloadComplete(file));
    }

    private void notifyError(final Callback callback, final Exception e) {
        mHandler.post(() -> callback.onDownloadError(e));
    }

    public interface Callback {
        void onDownloadStarted(long totalLength);

        void onDownloadProgress(long totalLength, long downloadedLength);

        void onDownloadComplete(File file);

        void onDownloadError(Exception e);
    }
}
