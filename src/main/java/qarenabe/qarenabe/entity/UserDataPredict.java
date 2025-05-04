package qarenabe.qarenabe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UserDataPredict {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long projects_done;

    @Getter
    @Setter
    private Long reproductions_done;

    @Getter
    @Setter
    private Long device_match;

    @Getter
    @Setter
    private Long success_rate;

}
