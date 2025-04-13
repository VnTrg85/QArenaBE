package qarenabe.qarenabe.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class BugReportDTOSecond {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private Date reported_at;
    @Getter
    @Setter
    private String bugTypeIcon;
    @Getter
    @Setter
    private String bugTypeName;
    @Getter
    @Setter
    private UserDTO user;

    public BugReportDTOSecond(Long id, String title, String status, Date reported_at, String icon_link, String bugtype_name, UserDTO user) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.reported_at  = reported_at;
        this.bugTypeIcon = icon_link;
        this.bugTypeName = bugtype_name;
        this.user = user;
    }
}
