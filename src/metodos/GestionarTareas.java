package metodos;

import clases.Importancia;
import clases.Tarea;
import utilidades.anotaciones.nivelDeImportancia;
import utilidades.exceptions.controlExcepciones;
import utilidades.exceptions.tareaNoEncontradaException;
import utilidades.lecturayEscritura.lecturaEscritura;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class GestionarTareas {
    private List<Tarea> tareas = new ArrayList<>();
    private int idUnico = 1;
    private final lecturaEscritura archivo = new lecturaEscritura();

    public GestionarTareas() throws IOException {
        tareas = archivo.cargaTareas();
        if (!tareas.isEmpty()) {
            idUnico = tareas.stream()
                    .mapToInt(Tarea::getId)
                    .max()
                    .getAsInt() + 1;
        }
    }

    public void EjecutaTarea(int op, Scanner sc) throws tareaNoEncontradaException {
        switch (op){
            case 1 -> agregarTarea(sc);
            case 2 -> verTarea();
            case 3 -> buscarTarea(sc);
            case 4 -> modificarTarea(sc);
            case 5 -> eliminarTarea(sc);
            case 6 -> mostrarTareasCompletadas();
            case 7 -> mostrarTareasPendientes();
            case 8 -> mostrarPorFechaVencimiento();
            case 9 -> buscarPorPalabraClave(sc);
            case 10-> mostrarSoloDescripcion();
            case 0 -> System.out.println("Saliendo del prgrama");
            default -> System.out.println("Opcion erronea");
        }
    }

    public void agregarTarea(Scanner sc){
        System.out.println("Luego de ingresar los datos segun su campo presione la tecla 'Enter'");
        System.out.println("Descripcion: ");
        String desc = sc.nextLine();
        LocalDate  fecha = null;
        boolean fechaValida = false;
        while (!fechaValida){
            System.out.println("Ingrese fecha de Vencimiento: (YYYY-MM-DD)");
            try{
                fecha = LocalDate.parse(sc.nextLine());
                if(fecha.isBefore(LocalDate.now())){
                    throw new controlExcepciones("Ingresa una fecha Igual o mayor a la actual");
                }else {
                    fechaValida=true;
                }
            }catch (controlExcepciones ex){
                System.out.println(ex.getMessage());
            }

        }
        Tarea tarea = new Tarea(idUnico++,desc, fecha, false);
        tareas.add(tarea);

        archivo.guardarTareas(tareas);
    }

    public void verTarea(){
        if (tareas.isEmpty()) {
            separador("No Existen Tareas");
            return;
        }
        System.out.println("|------------------------------------------------------------------------------------------|");
        tareas.forEach(System.out::println);
        System.out.println("|------------------------------------------------------------------------------------------|");
    }

    public void modificarTarea(Scanner sc){
        System.out.println("Ingresar el ´ID´ de la tarea a Modificar");
        int idTarea = sc.nextInt();
        sc.nextLine();

        Tarea buscada = tareas.stream()
                .filter(x -> x.getId() == idTarea)
                .findFirst()
                .orElse(null);

        if (buscada != null){
            System.out.println(buscada);
        }else {
            System.out.println("No se encontro la tarea con ese id");
            System.out.println("Las tareas que existen son las siguiente");
            verTarea();
        }

        System.out.println("Que parametro quieres modificar");
        System.out.println("1.- Descripcion de la tarea");
        System.out.println("2.- Estado de la tarea");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option){
            case 1 -> {
                System.out.println("Se Modificara la Descripcion del producto");
                buscada.setDescripcion(sc.nextLine());
            }
            case 2 -> {
                System.out.println("Se Modificara el Estado de la tarea");
                if(!buscada.isCompletado()){
                    buscada.setCompletado(true);
                }else{
                    buscada.setCompletado(false);
                }
            }
            default -> System.out.println("Opcion erronea");
        }

        archivo.guardarTareas(tareas);

    }

    public void buscarTarea(Scanner sc){
        System.out.println("Ingresar el ´ID´ de la tarea a Buscar");
        int idBuscar = sc.nextInt();
        sc.nextLine();
        Tarea buscada = tareas.stream()
                .filter(x -> x.getId() == idBuscar)
                .findFirst()
                .orElse(null);

        if (buscada != null){
            System.out.println(buscada);
        }else {
            System.out.println("No se encontro la tarea con ese id");
            System.out.println("Las tareas que existen son las siguiente");
            verTarea();
        }
    }

    public void eliminarTarea(Scanner sc) throws tareaNoEncontradaException {
        System.out.println("Ingresar el ´ID´ de la tarea a eliminar");
        int d = sc.nextInt();
        sc.nextLine();

        Tarea tareaAEliminar =tareas.stream().filter(x -> x.getId() == d).findFirst().orElse(null);

        if (tareaAEliminar == null){
            throw new tareaNoEncontradaException("No se encontro tarea con el id: "+d);
        }

        if (tareaAEliminar.getId() == d){
            tareas.remove(tareaAEliminar);
            System.out.println("Se elimino la tarea de manera satisfactoria");
        }

        archivo.guardarTareas(tareas);
    }

    public void mostrarTareasCompletadas(){
       if(tareas.stream().anyMatch(Tarea::isCompletado)) {
           separador("Las TareasCompletadas Son");
           tareas.stream().filter(Tarea::isCompletado).forEach(System.out::println);
           System.out.println("|-----------------------------------------------------|");
       }else{
           separador("No hay tareas Completadas");
       }
    }
    public void mostrarTareasPendientes(){
        if(tareas.stream().anyMatch(x -> !x.isCompletado())){
            separador("Las Tareas Incompletas son");
            tareas.stream().filter(x -> !x.isCompletado()).forEach(System.out::println);
            System.out.println("|-----------------------------------------------------|");
        }else {
            separador("Ho hay Tareas Implentas");
        }
    }
    public void mostrarPorFechaVencimiento(){
        separador("Las Fechas Ordenasdas son");
        tareas.stream().sorted(Comparator.comparing(Tarea::getFechaVencimiento)).forEach(System.out::println);
        System.out.println("|-----------------------------------------------------|");
    }

    public void buscarPorPalabraClave(Scanner sc){
        System.out.println("Ingresa la palabra a buscar:");
        String palabra =sc.next();
        sc.nextLine();

        if (tareas.stream().anyMatch(x -> x.getDescripcion().toLowerCase().contains(palabra.toLowerCase()))) {
            separador("La palabra clave se encuentra en las siguientes descripciones");
            tareas.stream()
                    .filter(x -> x.getDescripcion().toLowerCase().contains(palabra.toLowerCase()))
                    .forEach(System.out::println);
            System.out.println("|-----------------------------------------------------|");
        }else{
            System.out.println("No se encontro tarea con la palabra señalada");
        }
    }

    public void mostrarSoloDescripcion(){
        separador("Las Descripciones son:");
        tareas.stream().map(Tarea::getDescripcion).filter(x -> x != null && !x.isEmpty()).forEach(System.out::println);
        System.out.println("|-----------------------------------------------------|");
    }


    private void separador(String titulo) {
        System.out.println("|------------------------------------------------------------------------------------------|");
        System.out.println("|**********************************"+titulo+".******************************************|");
        System.out.println("|------------------------------------------------------------------------------------------|");
    }
}
