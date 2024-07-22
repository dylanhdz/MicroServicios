import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';
import { Course } from '../../models/course.model';
import { FormsModule } from '@angular/forms';
import { Student } from '../../models/student.model';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-course',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './course.component.html',
  styleUrl: './course.component.css'
})
export class CourseComponent implements OnInit {
  nombre: string = '';
  courses: Course[] = [];
  students: Student[] = [];

  constructor(private courseService: CourseService, private studentService: StudentService) {}

  ngOnInit(): void {
    this.loadCourses();
    this.loadStudents();
  }

  loadCourses(): void {
    this.courseService.getCourses().subscribe((data: Course[]) => {
      this.courses = data;
    });
  }

  loadStudents(): void {
    this.studentService.getStudents().subscribe((data: Student[]) => {
      this.students = data;
    });
  }

  addCourse(): void {
    const course: Course = { 
      nombre: this.nombre
    };
    this.courseService.addCourse(course).subscribe({
      next: (response) => {
        // Handle successful course creation
        alert('Se ha creado el curso ' + this.nombre);
        this.loadCourses();
      },
      error: (error) => {
        // Handle error
        alert('Error al crear el curso');
      }
    });
  }

  deleteCourse(course: Course): void {
    if (course.id !== undefined) {
      this.courseService.deleteCourse(course.id).subscribe({
        next: (response) => {
          alert("Se ha eliminado el curso '" + course.nombre + "'");
          this.loadCourses();
        },
        error: (error) => {
          alert('Error al eliminar el curso');
        }
      });
    } else {
      alert('Error: Este curso no existe.');
    }
  }

  getStudentsForCourse(course: Course): number[] {
    if (course.cursoUsuarios) {
      return course.cursoUsuarios.map(cu => cu.usuarioId);
    }
    return [];
  }

  getStudentNameFromId(studentId: number): string {
    const student = this.students.find(s => s.id === studentId);
    return student ? student.nombre : '';
  }

  getStudentsNamesForCourse(studentIds: number[]): string[] {
    return studentIds.map(id => this.getStudentNameFromId(id));
  }
}
