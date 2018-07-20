package org.roorkee.rkerestapi.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

import org.roorkee.rkerestapi.util.RkeException;
import org.springframework.stereotype.Service;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

@Service
public class FileStorageService {

    private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
            .initialRetryDelayMillis(10)
            .retryMaxAttempts(10)
            .totalRetryPeriodMillis(15000)
            .build());
    private static final int BUFFER_SIZE = 2 * 1024 * 1024;

    public String uploadFile2(InputStream is, final String hdrFileName, final String bucketName) {
        GcsFilename fileName = new GcsFilename(bucketName, hdrFileName);
        GcsOutputChannel outputChannel;
        GcsFileOptions options = new GcsFileOptions.Builder()
                .acl("public-read").build();
        try {
            outputChannel = gcsService.createOrReplace(fileName, options);
            copy(is, Channels.newOutputStream(outputChannel));
        }
        catch(IOException e){
            throw new RkeException(e);
        }
        return bucketName+hdrFileName;
    }

    /**
     * Transfer the data from the inputStream to the outputStream. Then close both streams.
     */
    private void copy(InputStream input, OutputStream output) throws IOException {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = input.read(buffer);
            while (bytesRead != -1) {
                output.write(buffer, 0, bytesRead);
                bytesRead = input.read(buffer);
            }
        } finally {
            input.close();
            output.close();
        }
    }
}
