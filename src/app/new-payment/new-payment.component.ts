import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PaymentType } from '../model/students.model';
import { StudentsService } from '../services/students.service';

@Component({
  selector: 'app-new-payment',
  templateUrl: './new-payment.component.html',
  styleUrl: './new-payment.component.css',
})
export class NewPaymentComponent implements OnInit {
  afterLoadComplete(event: any) {
    console.log(event);
  }
  savePayment() {
    this.showProgress = true;
    let date: Date = new Date(this.paymentFormGroup.value.date);
    let formattedDate =
      date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear();
    let formData: FormData = new FormData();
    formData.set('date', formattedDate);
    formData.set('amount', this.paymentFormGroup.value.amount);
    formData.set('type', this.paymentFormGroup.value.type);
    formData.set('studentCode', this.paymentFormGroup.value.studentCode);
    formData.set('file', this.paymentFormGroup.value.fileSource);

    this.studentsService.savePayment(formData).subscribe(
      (payment) => {
        this.showProgress = false;
        alert('Payment saved successfully !');
      },
      (error) => {
        console.log(error);
      }
    );
  }

  selectFile($event: any) {
    if ($event.target.files.length > 0) {
      let file = $event.target.files[0];
      this.paymentFormGroup.patchValue({
        fileSource: file,
        fileName: file.name,
      });
      this.pdfFileUrl = window.URL.createObjectURL(file);
    }
  }
  paymentFormGroup!: FormGroup;
  studentCode!: string;
  paymentType!: string[];
  pdfFileUrl!: string;
  showProgress: boolean = false;
  constructor(
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private studentsService: StudentsService
  ) {}
  ngOnInit() {
    // for (let elt in PaymentType) {
    //   this.paymentType.push(PaymentType[elt]);
    // }
    this.studentCode = this.activatedRoute.snapshot.params['studentCode'];
    this.paymentFormGroup = this.fb.group({
      amount: this.fb.control(''),
      date: this.fb.control(''),
      type: this.fb.control(''),
      studentCode: this.fb.control(this.studentCode),
      fileSource: this.fb.control(''),
      fileName: this.fb.control(''),
    });
  }
}
