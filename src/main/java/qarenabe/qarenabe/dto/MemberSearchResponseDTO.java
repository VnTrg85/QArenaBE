package qarenabe.qarenabe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSearchResponseDTO {
    List<MemberResponseDTO> content;
    int totalPage;
}
