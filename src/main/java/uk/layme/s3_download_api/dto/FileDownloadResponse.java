package uk.layme.s3_download_api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Builder
public class FileDownloadResponse {
    private final byte[] fileContent;
    private final String originalFilename;
    private final String contentType;    
}
