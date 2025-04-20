package qarenabe.qarenabe.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
      Long id;

    @Getter
    @Setter
    private String avatar;
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Column(name = "email", unique = true)
      String email;

    @Getter
    @Setter
      String phone;

    @Getter
    @Setter
      String password;


    @Getter
    @Setter
      String address;

    @Getter
    @Setter
      Date create_at;

    @ManyToOne
    @JoinColumn(name = "roleId")
    @Getter
    @Setter
      UserRole userRole;


    public User(Long id,String name,String avatar) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
    }
    @OneToMany(mappedBy = "user")
      List<UserCourse> userCourses;
}