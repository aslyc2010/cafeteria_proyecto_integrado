package modelo;

public class producto {

    private String codigo;
    private String nombre;
    private double precioBase;
    private int existencias;
    private int stockMinimo;

    public producto(String codigo, String nombre, double precioBase, int existencias, int stockMinimo) {
        this.codigo = codigo;
        this.nombre = nombre;
        setPrecioBase(precioBase);
        setExistencias(existencias);
        setStockMinimo(stockMinimo);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        if (precioBase >= 0) {
            this.precioBase = precioBase;
        }
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        if (existencias >= 0) {
            this.existencias = existencias;
        }
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        if (stockMinimo >= 0) {
            this.stockMinimo = stockMinimo;
        }
    }

    public boolean tieneExistenciaBaja() {
        return this.existencias < this.stockMinimo;
    }

    public double calcularPrecioVenta() {
        return this.precioBase;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Base: $%.2f | Venta: $%.2f | Stock: %d (Min: %d)",
                codigo, nombre, precioBase, calcularPrecioVenta(), existencias, stockMinimo);
    }
}