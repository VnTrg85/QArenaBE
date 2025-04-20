package qarenabe.qarenabe.dto;


import java.util.Date;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    @Nullable
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private Date time_created;


    @Getter
    @Setter
    @Nullable
    private Long testProjectId;

    @Getter
    @Setter
    @Nullable
    private Long bugReportId;

    @Getter
    @Setter
    @Nullable
    private UserDTO user;


    
}
