describe('Student and Course Management', () => {
  beforeEach(() => {
    cy.visit('/');
    cy.get('input[name="nombreUsuario"]').clear();
    cy.get('input[name="email"]').clear();
    cy.get('input[name="password"]').clear();
  });

  it('agregar estudiante', () => {
    cy.get('input[name="nombreUsuario"]').type('Juan Reyes S.');
    cy.get('input[name="email"]').type('jfreyes4@espe.edu.ec');
    cy.get('input[name="password"]').type('root');
    cy.get('button').contains('Agregar Estudiante').click();
  });



  it('matricular estudiante en un curso', () => {
    cy.visit('/');
    cy.contains('li', 'Juan Reyes S.').within(() => {
      cy.get('select').select('2 - Aplicaciones Distribuidas');
    });
  });

  it('eliminar estudiante', () => {
    cy.visit('/');
    cy.contains('li', 'Juan Reyes S.').within(() => {
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
      cy.get('select').select('2 - Aplicaciones Distribuidas');
    });
  });

  it('verificar matricula', () => {
    cy.visit('/');
    cy.get('button').contains('Ver Cursos').click(); 
  });

});