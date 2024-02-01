package cicd.controller;

import cicd.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @GetMapping
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();

        // Thêm các sinh viên vào danh sách
        students.add(new Student("long", "12A", 17, "123 Main St", 85, 75, 90, 80,90));
        students.add(new Student("big", "11B", 16, "456 Park Ave", 90, 80, 85, 75,89));
        students.add(new Student("Alice", "10C", 15, "789 Oak St", 80, 85, 70, 90,78));

        return students;
    }
}


