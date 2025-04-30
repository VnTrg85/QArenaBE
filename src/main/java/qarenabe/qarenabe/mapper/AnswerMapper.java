package qarenabe.qarenabe.mapper;

import org.mapstruct.Mapper;
import qarenabe.qarenabe.dto.AnswerRequestDTO;
import qarenabe.qarenabe.dto.AnswerResponseDTO;
import qarenabe.qarenabe.entity.Answer;

@Mapper(componentModel = "spring")

public interface AnswerMapper {
    Answer toEntity(AnswerRequestDTO dto);
    AnswerResponseDTO toDto(Answer answer);
}
