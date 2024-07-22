import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { StudentComponent } from './components/student/student.component';
import { CourseComponent } from './components/course/course.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, StudentComponent, CourseComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'crud-msvc';
  view: string = 'students';

  showStudents(): void {
    this.view = 'students';
  }

  showCourses(): void {
    this.view = 'courses';
  }
}
