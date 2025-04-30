package qarenabe.qarenabe.dto;


import java.util.Date;

import com.example.demo.enums.TypeNotification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO{
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private TypeNotification type;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private String link_url;

    @Getter
    @Setter
    private UserDTO sender;
    @Getter
    @Setter
    private UserDTO receiver;

    
}
