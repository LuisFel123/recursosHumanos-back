package gm.rh.controlador;

import gm.rh.modelo.Empleado;
import gm.rh.servicio.EmpleadoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoControlador {

    private final EmpleadoServicio servicio;

    public EmpleadoControlador(EmpleadoServicio servicio) {
        this.servicio = servicio;
    }

    // Obtener todos los empleados
    @GetMapping
    public List<Empleado> listarEmpleados() {
        return servicio.listarEmpleados();
    }

    // Obtener un empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id) {
        Empleado empleado = servicio.obtenerEmpleadoPorId(id);
        if (empleado != null) {
            return ResponseEntity.ok(empleado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //obtener el empleado por id 2
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscarEmpleadoPorId(@PathVariable Long id){
        Empleado emp= servicio.obtenerEmpleadoPorId(id);
        if(emp==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(emp);

    }

    // Crear nuevo empleado en java
    @PostMapping
    public ResponseEntity<Empleado> guardarEmpleado(@RequestBody Empleado empleado) {
        //ignorando un id entrante para evitar conflictos
        empleado.setIdEmpleado(null);
        Empleado creado = servicio.guardarEmpleado(empleado);
        URI location= URI.create("/api/empleados/"+creado.getIdEmpleado());
        return ResponseEntity.created(location).body(creado);
    }

    // Actualizar empleado existente
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado datos) {
        Empleado existente = servicio.obtenerEmpleadoPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNombre(datos.getNombre());
        existente.setDepartamento(datos.getDepartamento());
        existente.setSueldo(datos.getSueldo());
        Empleado actualizado = servicio.guardarEmpleado(existente);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar empleado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        Empleado existente = servicio.obtenerEmpleadoPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        servicio.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
