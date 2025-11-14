import metodos.GestionarTareas;
import utilidades.exceptions.tareaNoEncontradaException;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        GestionarTareas gestor = new GestionarTareas();
        Scanner sc = new Scanner(System.in);
        int op;

        do {
            System.out.println("Gestor de tareas");
            System.out.println("1.- Agregar Tarea");
            System.out.println("2.- Ver Tareas");
            System.out.println("3.- Buscar Tarea");
            System.out.println("4.- Modificar Tarea");
            System.out.println("5.- Eliminar Tarea");
            System.out.println("6.- Mostrar Tareas Completadas");
            System.out.println("7.- Mostrar Tareas Pendientes");
            System.out.println("8.- Mostrar Tareas por Fecha de Vencimiento");
            System.out.println("9.- Buscar por Palabra Clave en la descripcion");
            System.out.println("10.- Mostrar solo la Descripcion");
            System.out.println("0.- Salir");
            System.out.println("Seleccione una opcion:");

            try {
                op = sc.nextInt();
                sc.nextLine();
                gestor.EjecutaTarea(op, sc);
            }catch (InputMismatchException | tareaNoEncontradaException e){
                System.out.println("Menu erroneo por favor seleccione la opcion de forma numerica");
                sc.nextLine();
                op = 0;
            }
        }while (op !=0);
    }
}