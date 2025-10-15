package server.smartcond.Domain.Dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeRequestDTO {
    private String title;
    private String content;
}
