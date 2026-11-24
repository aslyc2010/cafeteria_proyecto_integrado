package modelo;

public class pagotarjeta implements metodopago {

    private String ultimosCuatroDigitos;
    private boolean aprobado;

    public pagotarjeta(String ultimosCuatroDigitos) {
        setUltimosCuatroDigitos(ultimosCuatroDigitos);
        this.aprobado = false;
    }

    public String getUltimosCuatroDigitos() {
        return ultimosCuatroDigitos;
    }

    public void setUltimosCuatroDigitos(String ultimosCuatroDigitos) {
        if (ultimosCuatroDigitos != null && ultimosCuatroDigitos.trim().matches("\\d{4}")) {
            this.ultimosCuatroDigitos = ultimosCuatroDigitos.trim();
        }
    }

    @Override
    public boolean procesarPago(double montoTotal) {
        if (montoTotal >= 0 && this.ultimosCuatroDigitos != null) {
            this.aprobado = true;
            return true;
        }
        this.aprobado = false;
        return false;
    }

    @Override
    public String getNombreMedio() {
        return "Tarjeta";
    }

    @Override
    public String getDetallePago() {
        String digitos = (ultimosCuatroDigitos != null) ? ultimosCuatroDigitos : "N/A";
        String estado = aprobado ? "Aprobado" : "Rechazado";
        return String.format("Tarjeta ****%s (%s)", digitos, estado);
    }
}