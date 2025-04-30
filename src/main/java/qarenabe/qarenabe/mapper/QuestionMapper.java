package qarenabe.qarenabe.mapper;

import org.mapstruct.Mapper;
import qarenabe.qarenabe.dto.QuestionRequestDTO;
import qarenabe.qarenabe.dto.QuestionResponseDTO;
import qarenabe.qarenabe.entity.Question;

@Mapper(componentModel = "spring")

public interface QuestionMapper {
    Question toEntity(QuestionRequestDTO dto);
    QuestionResponseDTO toDTO(Question question);
}
