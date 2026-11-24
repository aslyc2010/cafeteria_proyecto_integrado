package modelo;

public class itemventa {

    private producto producto;
    private int cantidad;
    private double subtotal;

    public itemventa(producto producto, int cantidad) {
        this.producto = producto;
        setCantidad(cantidad);
        calcularSubtotal();
    }

    public producto getProducto() {
        return producto;
    }
    
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad > 0) {
            this.cantidad = cantidad;
        }
    }

    public double calcularSubtotal() {
        if (this.producto != null) {
            this.subtotal = this.producto.calcularPrecioVenta() * this.cantidad;
        } else {
            this.subtotal = 0.0;
        }
        return this.subtotal;
    }

    public double getSubtotal() {
        return subtotal;
    }

    @Override
    public String toString() {
        String nombreProd = (producto != null) ? producto.getNombre() : "Producto no disponible";
        return String.format("- %s x%d -> Subtotal: $%.2f", nombreProd, cantidad, subtotal);
    }
}