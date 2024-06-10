package co.develhope.CRUD_Test.servicesTest;

import co.develhope.CRUD_Test.entities.Student;
import co.develhope.CRUD_Test.repositories.StudentRepository;
import co.develhope.CRUD_Test.services.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    public StudentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testChangeWorkingStatus() {
        Long id = 1L;
        Student student = new Student();
        student.setId(id);
        student.setName("John");
        student.setSurname("Doe");
        student.setWorking(false);

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        Student updatedStudent = studentService.changeWorkingStatus(id, true);

        assertEquals(true, updatedStudent.isWorking());
        verify(studentRepository, times(1)).findById(id);
        verify(studentRepository, times(1)).save(student);
    }
}

