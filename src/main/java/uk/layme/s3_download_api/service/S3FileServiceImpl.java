package uk.layme.s3_download_api.service;

import uk.layme.s3_download_api.dto.FileData;
import uk.layme.s3_download_api.dto.FileDownloadResponse;
import uk.layme.s3_download_api.dto.FileReader;
import uk.layme.s3_download_api.dto.S3ObjectRequest;

import java.io.IOException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements FileService {
    private final FileReader s3ResponseReader;

    @Override
    public FileDownloadResponse downloadFile(String bucketName, String objectKey) {
        try {
            S3ObjectRequest s3Request = S3ObjectRequest.builder()
                .bucketName(bucketName)
                .objectKey(objectKey)
                .build();

            FileData s3Response = s3ResponseReader.readResponse(s3Request);

            String contentType = s3Response.getContentType();
            String contentDisposition = s3Response.getContentDisposition();
            byte[] fileContent = s3Response.getFileContent();
            String key = s3Response.getRequest()
                .key();
            String filename = extractFilenameFromKey(key);

            String originalFilename = contentDisposition == null ? filename : contentDisposition.substring(contentDisposition.indexOf("=") + 1);

            return FileDownloadResponse.builder()
                .fileContent(fileContent)
                .originalFilename(originalFilename)
                .contentType(contentType)
                .build();
        } catch (IOException | S3Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String extractFilenameFromKey(String objectKey) {
        int lastSlashIndex = objectKey.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex < objectKey.length() - 1) {
            return objectKey.substring(lastSlashIndex + 1);
        }
        return objectKey;
    }
}
