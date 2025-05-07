package qarenabe.qarenabe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberResponseDTO
{
    Long id;
    String name;
    String email;
    String phone;
    String role;
    LocalDateTime joinedAt;
    String avatar;
    int projectCount;
}
