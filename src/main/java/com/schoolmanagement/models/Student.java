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
@ToString(exclude = {"courses"})
public class Student {

    @Id
    @NonNull
    @Column(name = "Email", length = 50, unique = true, nullable = false)
    private String email;

    @Column(name = "Name", length = 50, nullable = false)
    @NonNull
    private String name;

    @Column(name = "Password", length = 50, nullable = false)
    @NonNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "courses_id")
    )
    private Set<Course> courses = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return email == student.email;
    }

    @Override
    public int hashCode() { return Objects.hash(email); }
}
