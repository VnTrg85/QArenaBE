package qarenabe.qarenabe.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class UserDeviceRequestDTO {
    @Getter
    @Setter
    private Long id;
    
    @Getter
    @Setter
    private String selectedVersion;

    @Getter
    @Setter
    private List<Long> browserIds;
}
