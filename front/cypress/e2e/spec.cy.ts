describe('Student and Course Management', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('should add a new student', () => {
    cy.get('input[name="nombreUsuario"]').type('Jose Carlos');
    cy.get('input[name="email"]').type('john.doe@example.com');
    cy.get('input[name="password"]').type('password123');
    cy.get('button').contains('Agregar Estudiante').click();
  });

  it('should add a new course', () => {
    cy.get('button').contains('Ver Cursos').click(); // Press the "Ver Cursos" button
    cy.get('input[name="nombre"]').type('FIS 101');
    cy.get('button').contains('Agregar Curso').click();
  });

  it('should enroll a student in a course', () => {
    cy.get('input[name="nombreUsuario"]').type('Jose Carlos');
    cy.get('input[name="email"]').type('john.doe@example.com');
    cy.get('input[name="password"]').type('password123');
    cy.get('button').contains('Agregar Estudiante').click();

    cy.get('button').contains('Ver Cursos').click(); // Press the "Ver Cursos" button
    cy.get('input[name="nombre"]').type('FIS 101');
    cy.get('button').contains('Agregar Curso').click();
    cy.contains('Se ha creado el curso FIS 101');

    cy.get('select[name="studentSelect"]').select('Jose Carlos');
    cy.get('select[name="courseSelect"]').select('FIS 101');
    cy.get('button').contains('Matricular').click();
  });
});