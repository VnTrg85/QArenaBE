package qarenabe.qarenabe.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import qarenabe.qarenabe.dto.ApiResponse;
import qarenabe.qarenabe.dto.MemberRequestDTO;
import qarenabe.qarenabe.dto.MemberResponseDTO;
import qarenabe.qarenabe.dto.MemberSearchResponseDTO;
import qarenabe.qarenabe.service.MemberService.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel. PRIVATE, makeFinal = true)
@RequestMapping("/member")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {

MemberService memberService;

    @GetMapping("/getAll")
    public ApiResponse<MemberSearchResponseDTO> getAllMembers(@RequestParam(value = "offSet",required = false, defaultValue = "1") int offSet,
                                                              @RequestParam(value = "limit",required = false,defaultValue = "10") int limit) {
        Pageable pageable= (Pageable) PageRequest.of(offSet/limit,limit);
        return ApiResponse.<MemberSearchResponseDTO>builder().data(
                memberService.getAllMembers(pageable)
        ).build();
    }

    @PostMapping("/doSearch")
    public ApiResponse<MemberSearchResponseDTO> searchMembers(@RequestBody MemberRequestDTO memberRequestDTO) {
        return ApiResponse.<MemberSearchResponseDTO>builder().data(
                memberService.searchMembers(memberRequestDTO)
        ).build();
    }
}
