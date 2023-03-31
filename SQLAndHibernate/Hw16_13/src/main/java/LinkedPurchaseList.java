import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "LinkedPurchaseList")
@Getter
@Setter
public class LinkedPurchaseList {
    @EmbeddedId
    private LinkedPurchaseId linkedPurchaseId;
}

@Getter
@Setter
@Embeddable
@NoArgsConstructor
class LinkedPurchaseId implements Serializable {
    public LinkedPurchaseId(int courseId, int studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "student_id")
    private int studentId;
}