package logica;

import java.util.ArrayList;
import modelo.cliente;
import modelo.metodopago;
import modelo.producto;
import modelo.productocombo;
import modelo.productounidad;
import modelo.venta;

public class gestorpos {

    private ArrayList<producto> productos;
    private ArrayList<cliente> clientes;
    private ArrayList<venta> ventas;

    public gestorpos() {
        this.productos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.ventas = new ArrayList<>();
        precargarDatosIniciales();
    }

    private void precargarDatosIniciales() {
        productos.add(new productounidad("P01", "Croissant de mantequilla", 3500.0, 20, 5, 0.0));
        productos.add(new productounidad("P02", "Pan de bono artesanal", 2800.0, 4, 6, 10.0));
        productos.add(new productounidad("P03", "Cafe frio embotellado", 6500.0, 15, 5, 5.0));

        productos.add(new productocombo("C01", "Combo desayuno manizaleno", 9500.0, 12, 4, 1500.0));
        productos.add(new productocombo("C02", "Combo tarde de cafe y pastel", 11000.0, 3, 4, 2000.0));

        clientes.add(new cliente("1053812450", "Valentina Gomez", "3004129876"));
        clientes.add(new cliente("75098321", "Camilo Restrepo", "3127894510"));
    }
    
    public boolean registrarProducto(producto producto) {
        if (producto == null) {
            return false;
        }
        if (buscarProductoPorCodigo(producto.getCodigo()) != null) {
            return false;
        }
        productos.add(producto);
        return true;
    }

    public producto buscarProductoPorCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        String buscado = codigo.trim();
        for (producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(buscado)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<producto> getProductos() {
        return productos;
    }

    public boolean actualizarProducto(String codigo, double nuevoPrecio, int nuevasExistencias) {
        producto encontrado = buscarProductoPorCodigo(codigo);
        if (encontrado == null) {
            return false;
        }
        if (nuevoPrecio < 0 || nuevasExistencias < 0) {
            return false;
        }
        encontrado.setPrecioBase(nuevoPrecio);
        encontrado.setExistencias(nuevasExistencias);
        return true;
    }

    public ArrayList<producto> obtenerProductosStockBajo() {
        ArrayList<producto> bajos = new ArrayList<>();
        for (producto p : productos) {
            if (p.tieneExistenciaBaja()) {
                bajos.add(p);
            }
        }
        return bajos;
    }

    public double calcularValorPotencialInventario() {
        double total = 0.0;
        for (producto p : productos) {
            total += p.calcularPrecioVenta() * p.getExistencias();
        }
        return total;
    }

    public boolean registrarCliente(cliente cliente) {
        if (cliente == null) {
            return false;
        }
        if (buscarClientePorDocumento(cliente.getDocumento()) != null) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    public cliente buscarClientePorDocumento(String documento) {
        if (documento == null) {
            return null;
        }
        String buscado = documento.trim();
        for (cliente c : clientes) {
            if (c.getDocumento().equalsIgnoreCase(buscado)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<cliente> getClientes() {
        return clientes;
    }

    public boolean finalizarVenta(venta venta, metodopago medioPago) {
        if (venta == null || medioPago == null || venta.getItems().isEmpty()) {
            return false;
        }
        venta.recalcularTotales();
        venta.setMedioPago(medioPago);
        boolean aprobado = medioPago.procesarPago(venta.getTotalVenta());
        if (!aprobado) {
            return false;
        }
        venta.descontarExistencias();
        ventas.add(venta);
        return true;
    }

    public ArrayList<venta> getVentas() {
        return ventas;
    }

    public double calcularTotalVendido() {
        double suma = 0.0;
        for (venta v : ventas) {
            suma += v.getTotalVenta();
        }
        return suma;
    }
}