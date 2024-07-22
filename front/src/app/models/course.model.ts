export interface CourseUser {
    id: number;
    usuarioId: number;
}

export interface Course {
    id?: number;
    nombre: string;
    cursoUsuarios?: CourseUser[];
}