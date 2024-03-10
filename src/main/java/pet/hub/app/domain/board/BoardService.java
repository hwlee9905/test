package pet.hub.app.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.hub.app.domain.user.entity.User;
import pet.hub.app.domain.user.repository.UserRepository;
import pet.hub.app.web.dto.board.BoardRequestDto;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Board saveBoard(BoardRequestDto requestDto){
        Long userId = requestDto.getUserId();
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()){
            User user = userOptional.get();
            Board board = Board.builder()
                    .user(user)
                    .title(requestDto.getTitle())
                    .content(requestDto.getContent())
                    .boardTab(requestDto.getBoardTab())
                    .build();
            return boardRepository.save(board);

        } else {
            throw new RuntimeException("유저를 찾을 수 없습니다: " + userId);
        }
    }

    @Transactional
    public Board updateBoard(Long boardId, BoardRequestDto requestDto){
        Board updatedBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException(boardId+"번 게시글을 찾을 수 없습니다."));
        updatedBoard.setContent(requestDto.getContent());
        updatedBoard.setBoardTab(requestDto.getBoardTab());
        updatedBoard.setTitle(requestDto.getTitle());

        return updatedBoard;
    }

    @Transactional(readOnly = true)
    public List<Board> searchAllBoards(String keyword) {
        return boardRepository.findByTitleContainingOrContentContaining(keyword);
    }

    @Transactional(readOnly = true)
    public List<Board> searchTitleBoards(String keyword) {
        return boardRepository.findByTitleContaining(keyword);
    }

    @Transactional(readOnly = true)
    public List<Board> searchContentBoards(String keyword) {
        return boardRepository.findByContentContaining(keyword);
    }

    @Transactional
    public void deleteBoard(Long boardId){
        boardRepository.deleteById(boardId);
    }
}
