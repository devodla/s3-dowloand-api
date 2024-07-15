package uk.layme.s3_download_api.web;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uk.layme.s3_download_api.dto.FileDownloadResponse;
import uk.layme.s3_download_api.service.FileService;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadFile(@RequestParam String bucket, @RequestParam String objectKey) throws IOException {
        FileDownloadResponse fileDownloadResponse = fileService.downloadFile(bucket, objectKey);
        if (fileDownloadResponse != null) {
            try {
                byte[] fileContent = fileDownloadResponse.getFileContent();
                String originalFilename = fileDownloadResponse.getOriginalFilename();
                String contentType = fileDownloadResponse.getContentType();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(contentType));
                headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + originalFilename);

                return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
            }
        } else {
            return ResponseEntity.notFound()
                .build();
        }
    }
}
