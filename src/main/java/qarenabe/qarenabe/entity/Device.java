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
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String icon_link;


    @Getter
    @Setter
    private String[] version;

    @ManyToOne
    @JoinColumn(name = "category_device_id")
    @Getter
    @Setter
    private CategoryDevice categoryDevice;

    public Device (Long id, String name, String icon_link) {
        this.id = id;
        this.name = name;
        this.icon_link = icon_link;
    }

}
