package modelo;

public interface metodopago {

    boolean procesarPago(double montoTotal);

    String getNombreMedio();

    String getDetallePago();
}