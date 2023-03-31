import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "subscriptions")
@Getter
@Setter
public class Subscription {
    @EmbeddedId
    private SubscriptionId id;
    @Column(name = "subscription_date")
    private Date subscriptionDate;
}
@Embeddable
@Getter
@Setter
@NoArgsConstructor
class SubscriptionId implements Serializable {

    public SubscriptionId(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;
}

