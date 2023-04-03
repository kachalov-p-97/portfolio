import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "purchaselist")
@Getter
@Setter

public class Purchaselist{
    @EmbeddedId
    private PurchaseId id;
    private int price;
    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @Override
    public String toString() {
        return "Purchaselist{" +
                "id=" + id +
                ", price=" + price +
                ", subscriptionDate=" + subscriptionDate +
                '}';
    }
}
@Embeddable
@Getter
@Setter
@NoArgsConstructor
class PurchaseId implements Serializable {
    @Column(name = "student_name", length = 50)
    private String studentName;
    @Column(name = "course_name", length = 50)
    private String courseName;

    public PurchaseId(String studentName, String  courseName) {
        this.studentName = studentName;
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "PurchaseId{" +
                "studentName=" + studentName +
                ", courseName=" + courseName +
                '}';
    }
}


