package qarenabe.qarenabe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
public class Course extends BaseEntity {

    @Column(nullable = false, unique = true)
    String title;

    @Column(columnDefinition = "TEXT", length = 2000000000)
    String description;

    @Column(name = "type", nullable = false)
    String type;

    @Column(name = "is_required", nullable = false)
    Boolean isRequired = false;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    List<UserCourse> userCourses = new ArrayList<>();

    @Column(name = "is_blocked", nullable = false)
    Boolean isBlocked = true;

    @Column(name = "link_Img")
    String linkImg;

    @ManyToOne()
    @JoinColumn(name = "required_course_id")
    private Course requiredCourse;

    @OneToMany(mappedBy = "requiredCourse")
    private List<Course> dependentCourses = new ArrayList<>();

}
