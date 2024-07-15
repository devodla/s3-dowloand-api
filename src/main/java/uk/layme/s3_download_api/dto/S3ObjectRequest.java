package uk.layme.s3_download_api.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class S3ObjectRequest {
    private final String bucketName;
    private final String objectKey;
}
