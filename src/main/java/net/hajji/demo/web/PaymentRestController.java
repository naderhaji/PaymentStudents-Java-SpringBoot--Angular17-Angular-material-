package net.hajji.demo.web;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.hajji.demo.dtos.NewPaymentDTO;
import net.hajji.demo.entities.Payment;
import net.hajji.demo.entities.PaymentStatus;
import net.hajji.demo.entities.PaymentType;
import net.hajji.demo.entities.Student;
import net.hajji.demo.repository.PaymentRepository;
import net.hajji.demo.repository.StudentRepository;
import net.hajji.demo.services.PaymentService;


@RestController
@CrossOrigin("*")
public class PaymentRestController {

    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }


    @GetMapping(path = "/payments")
    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping(path = "/students/{code}/payments")
    public List<Payment> getPaymentsByStudentCode(@PathVariable String code) {
        return paymentRepository.findByStudentCode(code);
    }

    // @GetMapping(path = "/payments")
    // public List<Payment> getPaymentsByStatus(@RequestParam PaymentStatus status) {
    //     return paymentRepository.findByStatus(status);
    // }

    // @GetMapping(path = "/payments")
    // public List<Payment> getPaymentsByType(@RequestParam PaymentType type) {
    //     return paymentRepository.findByType(type.name());
    // }

    @GetMapping(path = "/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @GetMapping(path = "/students")
    public List<Student> allStudents() {
        return studentRepository.findAll();
    }

    @GetMapping(path = "/students/{code}")
    public Student getStudentByCode(@PathVariable String code) {
        return studentRepository.findByCode(code);
    }

    @GetMapping(path = "/studentsByProgramId")
    public List<Student> getStudentsByProgramId(@RequestParam String programId){
        return studentRepository.findByProgramId(programId);
    }

    @PutMapping(path = "/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long id) {
        return paymentService.updatePaymentStatus(status, id);
    }

    @PostMapping(path = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam("file") MultipartFile file, NewPaymentDTO newPaymentDTO) throws IOException {
        
       return paymentService.savePayment(file, newPaymentDTO);
    }

    @GetMapping(path = "/payment/{id}/file", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long id) throws IOException {
        return paymentService.getPaymentFile(id);
    }




}
