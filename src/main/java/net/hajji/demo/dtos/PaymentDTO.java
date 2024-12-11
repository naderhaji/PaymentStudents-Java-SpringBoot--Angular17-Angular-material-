package net.hajji.demo.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.hajji.demo.entities.PaymentStatus;
import net.hajji.demo.entities.PaymentType;

@AllArgsConstructor  
@Getter @Setter @ToString @Builder
public class PaymentDTO {

    private Long id;

    private LocalDate date;

    private double amount;

    private PaymentType type;

    private PaymentStatus status;



}
