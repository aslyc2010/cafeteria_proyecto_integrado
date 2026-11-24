package modelo;

public class productocombo extends producto {

    private double recargoAdicional;

    public productocombo(String codigo, String nombre, double precioBase, int existencias,
                         int stockMinimo, double recargoAdicional) {
        super(codigo, nombre, precioBase, existencias, stockMinimo);
        setRecargoAdicional(recargoAdicional);
    }

    public double getRecargoAdicional() {
        return recargoAdicional;
    }

    public void setRecargoAdicional(double recargoAdicional) {
        if (recargoAdicional >= 0) {
            this.recargoAdicional = recargoAdicional;
        }
    }

    @Override
    public double calcularPrecioVenta() {
        return getPrecioBase() + this.recargoAdicional;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Tipo: Combo (Recargo: +$%.2f)", recargoAdicional);
    }
}
