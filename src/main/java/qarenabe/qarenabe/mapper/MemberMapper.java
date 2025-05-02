package qarenabe.qarenabe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import qarenabe.qarenabe.dto.MemberResponseDTO;
import qarenabe.qarenabe.entity.User;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    @Mapping(source = "user.id",target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "userRole.name", target = "role")
    @Mapping(source = "create_at", target = "joinedAt")
    MemberResponseDTO toMemberResponse(User user);
}
