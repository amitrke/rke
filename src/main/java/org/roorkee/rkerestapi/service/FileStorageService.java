package org.roorkee.rkerestapi.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;

import org.roorkee.rkerestapi.util.RkeException;
import org.springframework.stereotype.Service;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.ListItem;
import com.google.appengine.tools.cloudstorage.ListOptions;
import com.google.appengine.tools.cloudstorage.ListResult;
import com.google.appengine.tools.cloudstorage.RetryParams;

@Service
public class FileStorageService {

    private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
            .initialRetryDelayMillis(10)
            .retryMaxAttempts(10)
            .totalRetryPeriodMillis(15000)
            .build());
    private static final int BUFFER_SIZE = 2 * 1024 * 1024;
    
    public List<String> list(final String folder, final String bucketName) {
    	ListOptions listOptions = new ListOptions.Builder().setPrefix(folder).build();
    	List<String> files = new ArrayList<>();
    	try {
			ListResult listResult = gcsService.list(bucketName, listOptions);
			while(listResult.hasNext()) {
				ListItem listItem = listResult.next();
				files.add(listItem.getName());
			}
			return files;
		} catch (IOException e) {
			throw new RkeException(e);
		}
    }

    public boolean delete(final String filename, final String bucketName){
        GcsFilename gcsFileName = new GcsFilename(bucketName, filename);
        try {
            return gcsService.delete(gcsFileName);
        }
        catch(IOException e){
            throw new RkeException(e);
        }
    }

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
