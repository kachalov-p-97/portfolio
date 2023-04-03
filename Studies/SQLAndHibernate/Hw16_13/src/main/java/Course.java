import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private int duration;


    @Column(columnDefinition = "enum ('DESIGN', 'PROGRAMMING', 'MARKETING', 'MANAGEMENT', 'BUSINESS')")
    private String type;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "students_count")
    private Integer studentsCount;
    private int price;
    private String description;
    @Column(name = "price_per_hour")
    private  float pricePerHour;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Subscriptions",
    joinColumns = {@JoinColumn(name = "course_id")},
    inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<Student> students;
}
