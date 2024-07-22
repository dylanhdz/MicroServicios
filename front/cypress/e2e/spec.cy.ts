describe('Student and Course Management', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('agregar estudiante', () => {
    cy.get('input[name="nombreUsuario"]').type('Martin Perez');
    cy.get('input[name="email"]').type('mperez@example.com');
    cy.get('input[name="password"]').type('password123');
    cy.get('button').contains('Agregar Estudiante').click();
  });



  it('matricular estudiante en un curso', () => {
    cy.visit('/');
    cy.contains('li', 'Martin Perez').within(() => {
      cy.get('select').select('1 - Matematica');
    });
  });

  it('eliminar estudiante', () => {
    cy.visit('/');
    cy.contains('li', 'Martin Perez').within(() => {
      cy.get('button').contains('Eliminar').click();
    });
  });

  it('agregar estudiante2', () => {
    cy.get('input[name="nombreUsuario"]').type('Juan kings');
    cy.get('input[name="email"]').type('jlomgs@example.com');
    cy.get('input[name="password"]').type('password123');
    cy.get('button').contains('Agregar Estudiante').click();
  });

  it('inscribir curso', () => {
    cy.visit('/');
    cy.get('button').contains('Ver Cursos').click(); 
    cy.get('input[name="nombre"]').type('Ingles');
    cy.get('button').contains('Agregar Curso').click();
  });

  it('matricular estudiante en un curso2', () => {
    cy.visit('/');
    cy.contains('li', 'Juan kings').within(() => {
      cy.get('select').select('8 - Ingles');
    });
  });

  it('verificar matricula', () => {
    cy.visit('/');
    cy.get('button').contains('Ver Cursos').click(); 
  });

});