package com.schoolmanagement.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(exclude = {"students"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CourseID")
    private int courseId;

    @Column(name = "Name", length = 50, nullable = false)
    @NonNull
    private String name;

    @Column(name = "Instructor", length = 50, nullable = false)
    @NonNull
    private String instructor;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId;
    }

    @Override
    public int hashCode() { return Objects.hash(courseId); }
}
