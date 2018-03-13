package org.roorkee.rkerestapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
public class ImageController {

    private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
            .initialRetryDelayMillis(10)
            .retryMaxAttempts(10)
            .totalRetryPeriodMillis(15000)
            .build());
    private static final int BUFFER_SIZE = 2 * 1024 * 1024;

    @PostMapping("testCreate")
    public String testCreate(HttpServletRequest req) throws IOException{
        GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
        GcsFilename fileName = new GcsFilename("bucketName", "objectName");
        GcsOutputChannel outputChannel;
        outputChannel = gcsService.createOrReplace(fileName, instance);
        copy(req.getInputStream(), Channels.newOutputStream(outputChannel));
        return null;
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
