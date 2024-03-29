package cicd.controller;

import cicd.Student;
import cicd.User;
import cicd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    public UserRepository userRepository;
    @GetMapping("/students")
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        // Thêm các sinh viên vào danh sách
        students.add(new Student(1,"long", "12A", 17, "123 Main St", 85, 75, 90, 80, 90));
        students.add(new Student(2,"big", "11B", 16, "456 Park Ave", 90, 80, 85, 75, 89));
        students.add(new Student(3,"Alice", "10C", 15, "789 Oak St", 80, 85, 70, 90, 78));

        return students;
    }

    @GetMapping("/users")
    public List<User> getUser() {
        return userRepository.findAll();
    }

}
