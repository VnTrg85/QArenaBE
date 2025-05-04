package qarenabe.qarenabe.service.MemberService;


import org.springframework.data.domain.Pageable;
import qarenabe.qarenabe.dto.MemberRequestDTO;
import qarenabe.qarenabe.dto.MemberSearchResponseDTO;


public interface MemberService {
    MemberSearchResponseDTO getAllMembers(Pageable pageable);
    MemberSearchResponseDTO searchMembers(MemberRequestDTO memberRequestDTO);
}
