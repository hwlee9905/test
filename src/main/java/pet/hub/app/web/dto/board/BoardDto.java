package pet.hub.app.web.dto.board;

import lombok.*;
import pet.hub.app.domain.board.enums.BoardTab;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private Long boardId;
    private String title;
    private String content;
    private BoardTab boardtab;
    private Integer commentCount;
    private Integer likeCount;

}
