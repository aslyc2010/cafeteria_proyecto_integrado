package modelo;

public class cliente {

    private String documento;
    private String nombre;
    private String telefono;
    
    public cliente(String documento, String nombre, String telefono) {
        setDocumento(documento);
        setNombre(nombre);
        setTelefono(telefono);
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        if (documento != null && !documento.trim().isEmpty()) {
            this.documento = documento.trim();
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono != null && !telefono.trim().isEmpty()) {
            this.telefono = telefono.trim();
        }
    }

    @Override
    public String toString() {
        return String.format("Doc: %s | Nombre: %s | Tel: %s", documento, nombre, telefono);
    }
}
