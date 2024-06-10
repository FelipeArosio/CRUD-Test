package co.develhope.CRUD_Test.controllers;

import co.develhope.CRUD_Test.entities.Student;
import co.develhope.CRUD_Test.repositories.StudentRepository;
import co.develhope.CRUD_Test.services.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private StudentService studentService;

    public StudentControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setName("John");
        student.setSurname("Doe");

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType("application/json")
                        .content("{\"name\": \"John\", \"surname\": \"Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    public void testGetAllStudents() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/students/1")
                        .contentType("application/json")
                        .content("{\"name\": \"Jane\", \"surname\": \"Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    public void testUpdateWorkingStatus() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");
        student.setWorking(false);

        when(studentService.changeWorkingStatus(anyLong(), anyBoolean())).thenReturn(student);

        mockMvc.perform(patch("/students/1/working")
                        .param("working", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.working").value(true));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());
    }
}
