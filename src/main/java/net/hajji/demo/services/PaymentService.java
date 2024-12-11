package net.hajji.demo.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;
import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.hajji.demo.dtos.NewPaymentDTO;
import net.hajji.demo.entities.Payment;
import net.hajji.demo.entities.PaymentStatus;
import net.hajji.demo.entities.PaymentType;
import net.hajji.demo.entities.Student;
import net.hajji.demo.repository.PaymentRepository;
import net.hajji.demo.repository.StudentRepository;


@Service
@Transactional
public class PaymentService {

    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment( MultipartFile file, NewPaymentDTO newPaymentDTO ) throws IOException {
        
        Path folderPath = Paths.get(System.getProperty("user.home"), "payments");
        if(!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        String fileName= UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"), "payments", fileName+".pdf");
        Files.copy(file.getInputStream(), filePath);
        Student student = studentRepository.findByCode(newPaymentDTO.getStudentCode());
        Payment payment = Payment.builder().date(newPaymentDTO.getDate()).type(
            newPaymentDTO.getType()
        ).student(student).amount(newPaymentDTO.getAmount())
        .file(filePath.toUri().toString()).status(PaymentStatus.CREATED).build();
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(PaymentStatus status, Long id) {
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    

    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }


}
