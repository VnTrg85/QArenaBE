package qarenabe.qarenabe.dto;

import java.util.List;

import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import qarenabe.qarenabe.converter.LongListConverter;
import qarenabe.qarenabe.entity.Browser;
import qarenabe.qarenabe.entity.CategoryDevice;
import qarenabe.qarenabe.entity.Device;


@AllArgsConstructor
public class UserDeviceDTO {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String versionSelected;

    @Getter
    @Setter
    @Convert(converter = LongListConverter.class)
    private List<Long> browserIds;
    
    @Getter
    @Setter
    private List<Browser> browsers;

    @Getter
    @Setter
    private Device devices;

}
