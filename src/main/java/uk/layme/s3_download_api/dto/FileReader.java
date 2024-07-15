package uk.layme.s3_download_api.dto;

import java.io.IOException;

public interface FileReader {
    FileData readResponse(S3ObjectRequest s3ObjectRequest) throws IOException;
}
