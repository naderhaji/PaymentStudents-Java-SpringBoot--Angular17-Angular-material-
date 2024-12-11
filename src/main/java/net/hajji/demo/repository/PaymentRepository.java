package net.hajji.demo.repository;

import net.hajji.demo.entities.Payment;
import net.hajji.demo.entities.PaymentStatus;
import net.hajji.demo.entities.PaymentType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

    List<Payment> findByStudentCode(String code);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByType(PaymentType type);


}
