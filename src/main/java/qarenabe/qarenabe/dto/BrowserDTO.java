package qarenabe.qarenabe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import qarenabe.qarenabe.entity.Browser;


@AllArgsConstructor
public class BrowserDTO {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Boolean selected;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String icon_link;
}
