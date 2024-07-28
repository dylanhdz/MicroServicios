import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { StudentComponent } from './components/student/student.component';
import { CourseComponent } from './components/course/course.component';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

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

  constructor(private sanitizer: DomSanitizer) {}

  showStudents(): void {
    this.view = 'students';
  }

  showCourses(): void {
    this.view = 'courses';
  }

  sanitizeContent(content: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(content);
  }
}
