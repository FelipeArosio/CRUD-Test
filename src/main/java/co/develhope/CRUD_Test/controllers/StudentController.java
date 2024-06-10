package co.develhope.CRUD_Test.controllers;

import co.develhope.CRUD_Test.entities.Student;
import co.develhope.CRUD_Test.repositories.StudentRepository;
import co.develhope.CRUD_Test.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    // (1) Crea un nuovo Student
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    // (2) Ottieni la lista di tutti gli Student
    @GetMapping
    public Iterable<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // (3) Prendi uno Student specifico
    @GetMapping("/{id}")
    public Optional<Student> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id);
    }

    // (4) Aggiorna uno Student
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentRepository.findById(id).map(student -> {
            student.setName(updatedStudent.getName());
            student.setSurname(updatedStudent.getSurname());
            return studentRepository.save(student);
        }).orElseGet(() -> {
            updatedStudent.setId(id);
            return studentRepository.save(updatedStudent);
        });
    }

    // (5) Aggiorna il valore di isWorking
    @PatchMapping("/{id}/working")
    public Student updateWorkingStatus(@PathVariable Long id, @RequestParam boolean working) {
        return studentService.changeWorkingStatus(id, working);
    }

    // (6) Cancella uno Student
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}

