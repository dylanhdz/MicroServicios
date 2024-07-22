import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';
import { Course } from '../../models/course.model';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-course',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './course.component.html',
  styleUrl: './course.component.css'
})
export class CourseComponent implements OnInit{
  nombre: string = '';
  courses: Course[] = [];
  

  constructor(private courseService: CourseService) {}

  ngOnInit(): void {
    this.loadCourses();
  }

  loadCourses(): void {
    this.courseService.getCourses().subscribe((data: Course[]) => {
      this.courses = data;
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

  getStudentsForCourse(course: Course): number[] {
    if (course.cursoUsuarios) {
      return course.cursoUsuarios.map(cu => cu.usuarioId);
    }
    return [];
  }
}
