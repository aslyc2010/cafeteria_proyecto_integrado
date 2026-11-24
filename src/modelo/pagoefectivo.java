package modelo;

public class pagoefectivo implements metodopago {

    private double montoRecibido;
    private double cambio;

    public pagoefectivo(double montoRecibido) {
        setMontoRecibido(montoRecibido);
        this.cambio = 0.0;
    }

    public double getMontoRecibido() {
        return montoRecibido;
    }

    public void setMontoRecibido(double montoRecibido) {
        if (montoRecibido >= 0) {
            this.montoRecibido = montoRecibido;
        }
    }

    public double getCambio() {
        return cambio;
    }

    @Override
    public boolean procesarPago(double montoTotal) {
        if (montoTotal < 0 || this.montoRecibido < montoTotal) {
            return false;
        }
        this.cambio = this.montoRecibido - montoTotal;
        return true;
    }

    @Override
    public String getNombreMedio() {
        return "Efectivo";
    }

    @Override
    public String getDetallePago() {
        return String.format("Efectivo (Recibido: $%.2f | Cambio: $%.2f)", montoRecibido, cambio);
    }
}