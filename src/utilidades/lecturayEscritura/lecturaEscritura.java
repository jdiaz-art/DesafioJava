package utilidades.lecturayEscritura;

import clases.Tarea;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class lecturaEscritura {
    private final String directorio = "archivoTarea/tareas.csv";

    public void guardarTareas(List<Tarea>tareas){
        try(BufferedWriter writer = Files.newBufferedWriter(Path.of(directorio))){
            for (Tarea t :tareas){
                writer.write(t.getId()+";"+t.getDescripcion()+";"+t.getFechaVencimiento()+";"+t.isCompletado());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al llenar el archivo: "+e);
        }
    }

    public List<Tarea> cargaTareas() throws IOException {
        List<Tarea> lista= new ArrayList<>();
        try {
            if (!Files.exists(Path.of(directorio))){
                return lista;
            }
            lista = Files.lines(Path.of(directorio)).map(x ->{
                String[] campo = x.split(";");
                return new Tarea(
                        Integer.parseInt(campo[0]),
                        campo[1],
                        LocalDate.parse(campo[2]),
                        Boolean.parseBoolean(campo[3])
                );
            })
                    .collect(Collectors.toList());
        }catch (IOException ei){
            System.out.println(ei);
        }
        return lista;
    }
}
