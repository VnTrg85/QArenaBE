package qarenabe.qarenabe.mapper;

import org.mapstruct.Mapper;
import qarenabe.qarenabe.dto.CertificateResponseDTO;
import qarenabe.qarenabe.entity.Certificate;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    CertificateResponseDTO certificateResponseDTO(Certificate certificate);
}
