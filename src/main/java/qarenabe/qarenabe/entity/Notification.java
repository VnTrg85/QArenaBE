package qarenabe.qarenabe.entity;
import java.util.Date;

import com.example.demo.enums.TypeNotification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
      Long id;

    @Getter
    @Setter
    private TypeNotification type;

    @Getter
    @Setter
      String content;

    @Getter
    @Setter
      Long link_id;

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
