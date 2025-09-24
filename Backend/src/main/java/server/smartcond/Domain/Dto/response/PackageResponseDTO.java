package server.smartcond.Domain.Dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import server.smartcond.Domain.Utils.PackageStatus;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageResponseDTO {

    private Long id;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receivedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deliveredAt;
    private PackageStatus status;
    private Integer apartment;
}
