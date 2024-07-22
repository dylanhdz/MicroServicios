import { TestBed, ComponentFixture } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { StudentComponent } from './student.component';
import { StudentService } from '../../services/student.service';
import { CourseService } from '../../services/course.service';

import { of } from 'rxjs';

describe('StudentComponent', () => {
  let component: StudentComponent;
  let fixture: ComponentFixture<StudentComponent>;
  let studentServiceMock: any;
  let courseServiceMock: any;

  beforeEach(async () => {
    studentServiceMock = jasmine.createSpyObj('StudentService', ['getStudents', 'addStudent', 'getStudent']);
    studentServiceMock.getStudents.and.returnValue(of([])); // Existing mock return
    studentServiceMock.addStudent.and.returnValue(of({})); // Existing mock return
    
    studentServiceMock.getStudent.and.returnValue(of({ id: 1, nombre: 'Test Student', email: 'test@example.com', password: 'test' })); // Mock return for getStudent
  
    courseServiceMock = jasmine.createSpyObj('CourseService', ['getCourses', 'enrollStudentInCourse']);
    courseServiceMock.getCourses.and.returnValue(of([])); // Existing mock return
  
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, StudentComponent], // Import StudentComponent here
      providers: [
        { provide: StudentService, useValue: studentServiceMock },
        { provide: CourseService, useValue: courseServiceMock }
      ]
    }).compileComponents();
  
    fixture = TestBed.createComponent(StudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add a student', () => {
    const student = { nombre: 'John', email: 'john@example.com', password: '1234' };
    component.nombreUsuario = 'John';
    component.email = 'john@example.com';
    component.password = '1234';

    component.addStudent();

    expect(studentServiceMock.addStudent).toHaveBeenCalledWith(student);
  });

  it('should enroll a student in a course', () => {
    const student = { nombre: 'John', email: 'john@example.com', password: '1234' };
    // Simulate selecting a course by setting selectedCourseId directly
    component.selectedCourseId = 1;
  
    courseServiceMock.enrollStudentInCourse.and.returnValue(of({}));
  
    component.enrollStudentInCourse(student);
  
    expect(courseServiceMock.enrollStudentInCourse).toHaveBeenCalledWith(student, 1);
  });
});