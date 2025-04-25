package qarenabe.qarenabe.entity;

import java.util.Date;

import jakarta.persistence.Column;
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
public class User {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String avatar;
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Column(name = "email", unique = true)
    private String email;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    @Column(length = 2000000000 )
    private String bio;
    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private String city;
    
    @Getter
    @Setter
    private Date date_of_birth;

    @Getter
    @Setter
    private String payout_method;

    @Getter
    @Setter
    private String payout_account_info;
    
    @Getter
    @Setter
    private Date create_at;
    @ManyToOne
    @JoinColumn(name = "roleId")
    @Getter
    @Setter
    private UserRole userRole;


    public User(Long id,String name,String avatar) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
    }
}