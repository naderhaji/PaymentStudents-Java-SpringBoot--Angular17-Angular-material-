package net.hajji.demo;

import java.util.Random;
import java.util.UUID;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.hajji.demo.entities.PaymentType;
import net.hajji.demo.entities.Payment;
import net.hajji.demo.entities.PaymentStatus;
import net.hajji.demo.entities.Student;
import net.hajji.demo.repository.PaymentRepository;
import net.hajji.demo.repository.StudentRepository;

@SpringBootApplication
public class EspritSpringAngApplication {

	public static void main(String[] args) {
		SpringApplication.run(EspritSpringAngApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository) {
		return args -> {
			studentRepository.save(Student.builder().id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE).firstName("nader").lastName("hajji").code("212333").programId("ESPRIT").build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE).firstName("khalil").lastName("lood").code("111758").programId("ESPRIT").build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE).firstName("louay").lastName("mpo").code("586933").programId("ESPRIT").build());
            
			PaymentType[] types=PaymentType.values();
			Random random = new Random();
			studentRepository.findAll().forEach(
				st->{
					for (int i=0; i<10; i++){
						int index = random.nextInt(types.length);
						Payment payment= Payment.builder().date(LocalDate.now()).amount(1000.0 + (Math.random() * 20000)).type(types[index]).status(PaymentStatus.CREATED).date(LocalDate.now()).student(st).build();
						paymentRepository.save(payment);
					}
				}
			);

		};
	}


}
