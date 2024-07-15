package uk.layme.s3_download_api.service;

import java.io.IOException;

import software.amazon.awssdk.services.s3.model.S3Exception;
import uk.layme.s3_download_api.dto.FileDownloadResponse;

public interface FileService {
    FileDownloadResponse downloadFile(String bucket, String bucketKey) throws IOException, S3Exception;
}
