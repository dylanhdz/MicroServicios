import { Component, OnInit } from '@angular/core';
import { StudentService } from '../../services/student.service';
import { CourseService } from '../../services/course.service';
import { Student } from '../../models/student.model';
import { Course } from '../../models/course.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-student',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './student.component.html',
  styleUrl: './student.component.css'
})
export class StudentComponent implements OnInit{
  students: Student[] = [];
  nombreUsuario: string = '';
  email: string = '';
  password: string = '';
  courses: Course[] = [];

  constructor(private studentService: StudentService, private courseService: CourseService) {}

  ngOnInit(): void {
    this.loadStudents();
    this.loadCourses();
  }

  loadStudents(): void {
    this.studentService.getStudents().subscribe((data: Student[]) => {
      this.students = data;
    });
  }

  loadCourses(): void {
    this.courseService.getCourses().subscribe((data: Course[]) => {
      this.courses = data;
    });
  }

  addStudent(): void {
    const student: Student = { 
      nombre: this.nombreUsuario,
      email: this.email,
      password: this.password
    };
    this.studentService.addStudent(student).subscribe({
      next: (response) => {
        // Handle successful student creation
        alert('Se ha creado el estudiante ' + this.nombreUsuario);
        this.loadStudents();
      },
      error: (error) => {
        // Handle error
        alert('Error al crear el estudiante');
      }
    });
  }

  enrollStudentInCourse(student: Student, event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const courseId = parseInt(selectElement.value.split('-')[0]);
    this.courseService.enrollStudentInCourse(student, courseId).subscribe({
      next: (response) => {
        // Handle successful enrollment
        alert('Se ha agregado correctamente al estudiante ' + student.nombre + ' al curso "' + selectElement.value.split('-')[1] + '"');
      },
      error: (error) => {
        // Handle error
        alert("El usuario ya está inscrito en un curso.");
      }
    });
  }
}
