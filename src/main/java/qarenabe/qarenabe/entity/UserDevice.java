package qarenabe.qarenabe.entity;

import java.util.List;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import qarenabe.qarenabe.converter.LongListConverter;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserDevice {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String versionSelected;

    @Getter
    @Setter
    @Convert(converter = LongListConverter.class)
    private List<Long> browserIds;

    @ManyToOne
    @JoinColumn(name = "device_id")
    @Getter
    @Setter
    private Device device;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;
}
