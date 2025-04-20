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
public class Transaction {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
      Long id;

    @Getter
    @Setter
      String paymentMethod;

    @Getter
    @Setter
      Double amount;

    @Getter
    @Setter
      Date payment_date;

    @Getter
    @Setter
      String status;

    @ManyToOne
    @JoinColumn(name = "userId")
    @Getter
    @Setter
      User user;

    @ManyToOne
    @JoinColumn(name = "test_project_Id")
    @Getter
    @Setter
      TestProject testProject;
}
