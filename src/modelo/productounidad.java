package modelo;

public class productounidad extends producto {

    private double descuentoPorcentaje;

    public productounidad(String codigo, String nombre, double precioBase, int existencias,
                          int stockMinimo, double descuentoPorcentaje) {
        super(codigo, nombre, precioBase, existencias, stockMinimo);
        setDescuentoPorcentaje(descuentoPorcentaje);
    }

    public double getDescuentoPorcentaje() {
        return descuentoPorcentaje;
    }

    public void setDescuentoPorcentaje(double descuentoPorcentaje) {
        if (descuentoPorcentaje >= 0 && descuentoPorcentaje <= 100) {
            this.descuentoPorcentaje = descuentoPorcentaje;
        }
    }

    @Override
    public double calcularPrecioVenta() {
        double factor = 1.0 - (this.descuentoPorcentaje / 100.0);
        return getPrecioBase() * factor;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Tipo: Unidad (Desc: %.1f%%)", descuentoPorcentaje);
    }
}