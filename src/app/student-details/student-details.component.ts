import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StudentsService } from '../services/students.service';
import { Payment } from '../model/students.model';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-student-details',
  templateUrl: './student-details.component.html',
  styleUrl: './student-details.component.css',
})
export class StudentDetailsComponent implements OnInit {
  paymentDetails(payment: Payment) {
    this.router.navigateByUrl(`/admin/payment-details/${payment.id}`);
  }
  newPayment() {
    this.router.navigateByUrl(`/admin/new-payment/${this.studentCode}`);
  }
  studentCode!: string;
  studentPayments!: Array<Payment>;
  PaymentDataSource!: MatTableDataSource<Payment>;
  public displayedColumns: string[] = [
    'id',
    'date',
    'amount',
    'type',
    'status',
    'firstName',
    'details',
  ];

  constructor(
    private activatedRoute: ActivatedRoute,
    private studentService: StudentsService,
    private router: Router
  ) {}
  ngOnInit() {
    this.studentCode = this.activatedRoute.snapshot.params['code'];
    this.studentService.getStudentPayments(this.studentCode).subscribe({
      next: (value) => {
        this.studentPayments = value;
        this.PaymentDataSource = new MatTableDataSource<Payment>(
          this.studentPayments
        );
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
}
