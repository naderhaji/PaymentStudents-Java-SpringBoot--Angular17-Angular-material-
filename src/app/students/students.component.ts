import { Student } from './../model/students.model';
import { Component, OnInit } from '@angular/core';
import { StudentsService } from '../services/students.service';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { $locationShim } from '@angular/common/upgrade';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrl: './students.component.css',
})
export class StudentsComponent implements OnInit {
  students!: Array<Student>;
  StudentDataSource!: MatTableDataSource<Student>;
  displayedColumns: string[] = [
    'id',
    'code',
    'firstName',
    'lastName',
    'programId',
    'payments',
  ];
  constructor(
    private studentsService: StudentsService,
    private router: Router
  ) {}

  ngOnInit() {
    this.studentsService.getStudents().subscribe({
      next: (data: Student[]) => {
        this.students = data;
        this.StudentDataSource = new MatTableDataSource<Student>(this.students);
      },
      error: (err: any) => {
        console.log(err);
      },
    });
  }

  studentPayments(student: Student) {
    this.router.navigateByUrl(`/admin/student-details/${student.code}`);
  }
}
