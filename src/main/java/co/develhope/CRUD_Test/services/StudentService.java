package co.develhope.CRUD_Test.services;

import co.develhope.CRUD_Test.entities.Student;
import co.develhope.CRUD_Test.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student changeWorkingStatus(Long id, boolean isWorking) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setWorking(isWorking);
            return studentRepository.save(student);
        }
        return null;
    }
}

