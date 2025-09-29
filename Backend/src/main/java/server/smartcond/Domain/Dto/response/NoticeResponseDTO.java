package server.smartcond.Domain.Dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;
}
