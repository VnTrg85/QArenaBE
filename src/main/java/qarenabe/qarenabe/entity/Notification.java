package qarenabe.qarenabe.entity;

import com.example.demo.enums.TypeNotification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "senderId")
    @Getter
    @Setter
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    @Getter
    @Setter
    private User receiver;
}
