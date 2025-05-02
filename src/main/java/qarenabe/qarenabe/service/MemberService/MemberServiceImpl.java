package qarenabe.qarenabe.service.MemberService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qarenabe.qarenabe.dto.MemberRequestDTO;
import qarenabe.qarenabe.dto.MemberResponseDTO;
import qarenabe.qarenabe.dto.MemberSearchResponseDTO;
import qarenabe.qarenabe.entity.TestProject_User;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.mapper.MemberMapper;
import qarenabe.qarenabe.repository.RepoCustom.MemberRepoCustom;
import qarenabe.qarenabe.repository.TestProject_UserRepository;
import qarenabe.qarenabe.repository.UserRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    UserRepository userRepository;
    MemberMapper memberMapper;
    MemberRepoCustom memberRepoCustom;
    TestProject_UserRepository testProjectUserRepository;

    @Override
    public MemberSearchResponseDTO searchMembers(MemberRequestDTO memberRequestDTO) {
        int adjustedOffset = (memberRequestDTO.getOffset() - 1) * memberRequestDTO.getLimit();
        memberRequestDTO.setOffset(adjustedOffset);
        List<User> userSearch = memberRepoCustom.SearchMemberCustom(memberRequestDTO);

        Long requiredProjectCount = memberRequestDTO.getProjectCount();

        List<User> filteredUsers = (requiredProjectCount != null)
                ? userSearch.stream()
                .filter(user -> testProjectUserRepository.countByUserIdAndStatus(user.getId(),"done") >= requiredProjectCount)
                .toList()
                : userSearch;

        List<MemberResponseDTO> dtoList = filteredUsers.stream()
                .map(user -> {
                    MemberResponseDTO dto = memberMapper.toMemberResponse(user);
                    dto.setProjectCount(testProjectUserRepository.countByUserIdAndStatus(user.getId(),"done"));
                    return dto;
                }).toList();

        int totalCount = filteredUsers.size();
        int totalPage = (totalCount + memberRequestDTO.getLimit() - 1) / memberRequestDTO.getLimit();

        return new MemberSearchResponseDTO(dtoList, totalPage);
    }


    @Override
    public MemberSearchResponseDTO getAllMembers(Pageable pageable) {
        Page<User> members = userRepository.findAll(pageable);

        List<MemberResponseDTO> content = members.getContent().stream()
                .map(memberMapper::toMemberResponse)
                .toList();

        int totalPage = members.getTotalPages();

        return new MemberSearchResponseDTO(content, totalPage);
    }
}

