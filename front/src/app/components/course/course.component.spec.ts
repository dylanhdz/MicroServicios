import { TestBed, ComponentFixture } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CourseComponent } from './course.component';
import { CourseService } from '../../services/course.service';
import { of } from 'rxjs';
import { Course } from '../../models/course.model';

describe('CourseComponent', () => {
  let component: CourseComponent;
  let fixture: ComponentFixture<CourseComponent>;
  let courseServiceMock: any;

  beforeEach(async () => {
    courseServiceMock = jasmine.createSpyObj('CourseService', ['getCourses', 'addCourse']);
    courseServiceMock.getCourses.and.returnValue(of([])); // Ensure it returns an observable
    courseServiceMock.addCourse.and.returnValue(of({})); // Ensure it returns an observable

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CourseComponent], // Import CourseComponent here
      providers: [
        { provide: CourseService, useValue: courseServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CourseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load courses on init', () => {
    const courses = [{ nombre: 'Math', cursoUsuarios: [] }];
    courseServiceMock.getCourses.and.returnValue(of(courses)); // Ensure it returns an observable

    component.ngOnInit();

    expect(component.courses).toEqual(courses);
  });

  it('should add a course', () => {
    const course = { nombre: 'Math' };
    component.nombre = 'Math';

    component.addCourse();

    expect(courseServiceMock.addCourse).toHaveBeenCalledWith(course);
  });

  it('should return students for a course', () => {
    const course: Course = {
      nombre: 'Math',
      cursoUsuarios: [{
        usuarioId: 1,
        id: 0
      }, {
        usuarioId: 2,
        id: 0
      }]
    };

    const studentIds = component.getStudentsForCourse(course);

    expect(studentIds).toEqual([1, 2]);
  });

  it('should return an empty array if cursoUsuarios is undefined', () => {
    const course: Course = {
      nombre: 'Math',
      cursoUsuarios: undefined
    };

    const studentIds = component.getStudentsForCourse(course);

    expect(studentIds).toEqual([]);
  });
});