package qarenabe.qarenabe.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String avatar;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String phone;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private String city;
    @Getter
    @Setter
    private Date dateOfBirth;
    @Getter
    @Setter
    private Date createAt;
    @Getter
    @Setter
    private Long role;

    @Getter
    @Setter
    private String payout_method;

    @Getter
    @Setter
    private String payout_account_info;
    public UserDTO(Long id, String avatar, String name, String email, String phone, String address, String city, Date dateOfBirth, Date createAt, Long role) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.createAt = createAt;
        this.role = role;
    }
    public UserDTO(Long id,String name, String avatar) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
    }
    public UserDTO(Long id,String name, String avatar,Long roleId) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.role = roleId;
    }
    public UserDTO(String payout_method, String payout_account_info) {
        this.payout_method = payout_method;
        this.payout_account_info = payout_account_info;
    }
}
