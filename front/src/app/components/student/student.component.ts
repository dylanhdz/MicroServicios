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
  selectedCourseId: number = 0;
  selectedCourseName: string = '';
  selectedStudentId: number | null = null;

  constructor(private studentService: StudentService, private courseService: CourseService) {}

  ngOnInit(): void {
    this.loadStudents();
    this.loadCourses();
  }

  loadStudents(): void {
    this.studentService.getStudents().subscribe((data: Student[]) => {
      this.students = data;
      this.updateSelectedStudent(this.students[0].id?.toString() || '');
    });
  }

  loadCourses(): void {
    this.courseService.getCourses().subscribe((data: Course[]) => {
      this.courses = data;
      this.updateSelectedCourse(this.courses[0].id + ' - ' + this.courses[0].nombre);
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

  updateSelectedCourse(value: string): void {
    const parts = value.split(' - ');
    this.selectedCourseId = parseInt(parts[0]);
    this.selectedCourseName = parts[1];
  }

  updateSelectedStudent(value: string): void {
    this.selectedStudentId = parseInt(value);
    this.loadSelectedStudent();
  }
  
  enrollStudentInCourse(student: Student): void {
    this.courseService.enrollStudentInCourse(student, this.selectedCourseId).subscribe({
      next: (response) => {
        // Handle successful enrollment
        alert('Se ha agregado correctamente al estudiante ' + student.nombre + ' al curso "' + this.selectedCourseName + '"');
      },
      error: (error) => {
        // Handle error
        alert("El usuario ya está inscrito en un curso.");
      }
    });
  }

  deleteStudent(student: Student): void {
    if (student.id !== undefined) {
      this.studentService.deleteStudent(student.id).subscribe({
        next: (response) => {
          alert('Se ha eliminado el estudiante ' + student.nombre);
          this.loadStudents();
        },
        error: (error) => {
          alert('Error al eliminar el estudiante');
        }
      });
    } else {
      alert('Error: student.id is undefined');
    }
  }
  trackByStudentId(index: number, student: Student): number | undefined {
    return student.id;
  }

  loadSelectedStudent(): void {
    if (this.selectedStudentId !== null) {
      this.studentService.getStudent(this.selectedStudentId).subscribe((student: Student) => {
        this.nombreUsuario = student.nombre;
        this.email = student.email;
        this.password = ''; // Passwords are usually not retrieved for editing
      });
    }
  }

  updateStudent(): void {
    if (this.selectedStudentId) {
      const student: Student = {
        id: this.selectedStudentId,
        nombre: this.nombreUsuario,
        email: this.email,
        password: this.password
      };
      this.studentService.updateStudent(student).subscribe({
        next: (response) => {
          alert('Se ha editado el estudiante ' + student.nombre);
          this.loadStudents();
        },
        error: (error) => {
          alert('Error al editar el estudiante');
        }
      });
    } else {
      alert('Seleccione un estudiante para editar');
    }
  }
}
