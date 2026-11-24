package modelo;

import java.util.ArrayList;

public class venta {

    private static int contadorRecibos = 0;
    private static final double IVA = 0.19;

    private int numeroRecibo;
    private cliente cliente;
    private ArrayList<itemventa> items;
    private metodopago medioPago;
    private double subtotalVenta;
    private double valorIva;
    private double totalVenta;

    public venta(cliente cliente) {
        contadorRecibos++;
        this.numeroRecibo = contadorRecibos;
        this.cliente = cliente;
        this.items = new ArrayList<>();
        this.subtotalVenta = 0.0;
        this.valorIva = 0.0;
        this.totalVenta = 0.0;
    }

    public static double getIva() {
        return IVA;
    }

    public int getNumeroRecibo() {
        return numeroRecibo;
    }

    public cliente getCliente() {
        return cliente;
    }

    public ArrayList<itemventa> getItems() {
        return items;
    }

    public metodopago getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(metodopago medioPago) {
        this.medioPago = medioPago;
    }

    public boolean agregarItem(producto producto, int cantidad) {
        if (producto == null || cantidad <= 0) {
            return false;
        }
        if (cantidad > producto.getExistencias()) {
            return false;
        }
        itemventa item = new itemventa(producto, cantidad);
        this.items.add(item);
        recalcularTotales();
        return true;
    }

    public void recalcularTotales() {
        double suma = 0.0;
        for (itemventa item : this.items) {
            suma += item.calcularSubtotal();
        }
        this.subtotalVenta = suma;
        this.valorIva = this.subtotalVenta * IVA;
        this.totalVenta = this.subtotalVenta + this.valorIva;
    }

    public double getSubtotalVenta() {
        return subtotalVenta;
    }

    public double getValorIva() {
        return valorIva;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void descontarExistencias() {
        for (itemventa item : this.items) {
            producto p = item.getProducto();
            if (p != null) {
                int nuevoStock = p.getExistencias() - item.getCantidad();
                p.setExistencias(nuevoStock);
            }
        }
    }

    public String generarComprobante() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("       COMPROBANTE DE VENTA - POS       \n");
        sb.append("========================================\n");
        sb.append(String.format("Recibo Nro: #%04d%n", numeroRecibo));
        if (cliente != null) {
            sb.append(String.format("Cliente: %s (Doc: %s)%n", cliente.getNombre(), cliente.getDocumento()));
        } else {
            sb.append("Cliente: Cliente General%n");
        }
        sb.append("----------------------------------------\n");
        sb.append("Items:\n");
        for (itemventa item : items) {
            sb.append(item.toString()).append("\n");
        }
        sb.append("----------------------------------------\n");
        sb.append(String.format("Subtotal venta: $%.2f%n", subtotalVenta));
        sb.append(String.format("IVA (19%%):      $%.2f%n", valorIva));
        sb.append(String.format("TOTAL A PAGAR:  $%.2f%n", totalVenta));
        String detallePago = (medioPago != null) ? medioPago.getDetallePago() : "Sin registrar";
        sb.append(String.format("Medio de pago:  %s%n", detallePago));
        sb.append("========================================\n");
        return sb.toString();
    }
}