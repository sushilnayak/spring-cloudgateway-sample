package com.nayak.studentservice;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class StudentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args);
    }

}

@Builder
@Data
class StudentDto {
    private Long id;
    private String name;
    private Integer age;
}

@Data
@Builder
class Student {
    private String name;
    private Integer age;
}

@Repository
@RequiredArgsConstructor
class StudentRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<StudentDto> findAllStudents() {
        return jdbcTemplate.query("select * from student", new RowMapper<>() {
            @Override
            public StudentDto mapRow(ResultSet resultSet, int i) throws SQLException {
                return StudentDto.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .age(resultSet.getInt("age"))
                        .build();
            }
        });
    }
}

@Service
@RequiredArgsConstructor
class StudentService {
    private final StudentRepository studentRepository;

    public List<StudentDto> getAllStudents() {
        return studentRepository.findAllStudents();
    }

}

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class StudentController {

    private final StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> allStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
}