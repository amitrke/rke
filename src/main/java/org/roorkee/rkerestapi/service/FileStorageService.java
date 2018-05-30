package org.roorkee.rkerestapi.service;

import com.google.appengine.tools.cloudstorage.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.roorkee.rkerestapi.entity.Image;
import org.roorkee.rkerestapi.util.RkeException;
import org.springframework.stereotype.Service;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class FileStorageService {

    private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
            .initialRetryDelayMillis(10)
            .retryMaxAttempts(10)
            .totalRetryPeriodMillis(15000)
            .build());
    private static final int BUFFER_SIZE = 2 * 1024 * 1024;

    private static Storage storage = null;

    static {
        storage = StorageOptions.getDefaultInstance().getService();
    }

    @SuppressWarnings("deprecation")
    public String uploadFile(InputStream is, final String hdrFileName, final String bucketName) throws IOException {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
        DateTime dt = DateTime.now(DateTimeZone.UTC);
        String dtString = dt.toString(dtf);
        final String fileName = hdrFileName + dtString;

        // the inputstream is closed by default, so we don't need to close it here
        BlobInfo blobInfo =
                storage.create(
                        BlobInfo
                                .newBuilder(bucketName, fileName)
                                // Modify access list to allow all users with link to read file
                                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                                .build(),
                        is);
        // return the public download link
        return blobInfo.getMediaLink();
    }

    public Image uploadFile2(InputStream is, final String hdrFileName, final String bucketName) {
        GcsFilename fileName = new GcsFilename(bucketName, hdrFileName);
        GcsOutputChannel outputChannel;
        GcsFileOptions options = new GcsFileOptions.Builder()
                //.mimeType(mime)
                .acl("public-read").build();
        try {
            outputChannel = gcsService.createOrReplace(fileName, options);
            copy(is, Channels.newOutputStream(outputChannel));
        }
        catch(IOException e){
            throw new RkeException(e);
        }
        Image i = new Image();
        i.setFileName(bucketName+hdrFileName);
        return i;
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
