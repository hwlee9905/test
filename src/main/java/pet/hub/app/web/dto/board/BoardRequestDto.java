package pet.hub.app.web.dto.board;

import lombok.*;
import pet.hub.app.domain.board.enums.BoardTab;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardRequestDto {
    private Long userId;
    private String title;
    private String content;
    private BoardTab boardTab;
}
