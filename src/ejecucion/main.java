package ejecucion;

import java.util.ArrayList;
import java.util.Scanner;
import logica.gestorpos;
import modelo.cliente;
import modelo.metodopago;
import modelo.pagoefectivo;
import modelo.pagotarjeta;
import modelo.producto;
import modelo.productocombo;
import modelo.productounidad;
import modelo.venta;

public class main {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        gestorpos gestor = new gestorpos();
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    listarProductos(gestor);
                    break;
                case 2:
                    registrarProductoMenu(gestor);
                    break;
                case 3:
                    buscarProductoMenu(gestor);
                    break;
                case 4:
                    actualizarProductoMenu(gestor);
                    break;
                case 5:
                    listarStockBajo(gestor);
                    break;
                case 6:
                    registrarClienteMenu(gestor);
                    break;
                case 7:
                    listarClientes(gestor);
                    break;
                case 8:
                    buscarClienteMenu(gestor);
                    break;
                case 9:
                    realizarVentaMenu(gestor);
                    break;
                case 10:
                    mostrarReporteVentas(gestor);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema POS. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opcion inexistente. Intente nuevamente.");
                    break;
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n================ POS CAFETERIA Y PANADERIA ================");
        System.out.println("1. Listar productos");
        System.out.println("2. Registrar producto (Unidad o Combo)");
        System.out.println("3. Buscar producto por codigo");
        System.out.println("4. Actualizar precio y existencias de producto");
        System.out.println("5. Listar productos con existencias bajas");
        System.out.println("6. Registrar cliente");
        System.out.println("7. Listar clientes");
        System.out.println("8. Buscar cliente por documento");
        System.out.println("9. Realizar venta y generar comprobante");
        System.out.println("10. Ver reporte de ventas");
        System.out.println("0. Salir");
        System.out.println("===========================================================");
    }

    private static void listarProductos(gestorpos gestor) {
        ArrayList<producto> lista = gestor.getProductos();
        System.out.println("\n--- CATALOGO DE PRODUCTOS ---");
        if (lista.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (producto p : lista) {
            System.out.println(p.toString());
        }
        System.out.printf("Valor potencial del inventario: $%.2f%n", gestor.calcularValorPotencialInventario());
    }

    private static void registrarProductoMenu(gestorpos gestor) {
        System.out.println("\n--- REGISTRAR PRODUCTO ---");
        System.out.println("1. Producto por Unidad (con descuento %)");
        System.out.println("2. Producto Combo (con recargo adicional)");
        int tipo = leerEntero("Elija el tipo: ");

        String codigo = leerTexto("Codigo: ");
        String nombre = leerTexto("Nombre: ");
        double precioBase = leerDouble("Precio base: ");
        int existencias = leerEntero("Existencias iniciales: ");
        int stockMinimo = leerEntero("Stock minimo: ");

        if (precioBase < 0 || existencias < 0 || stockMinimo < 0) {
            System.out.println("Error: precio, existencias y stock minimo no pueden ser negativos.");
            return;
        }

        producto nuevo = null;
        if (tipo == 1) {
            double desc = leerDouble("Descuento porcentaje (0 a 100): ");
            if (desc < 0 || desc > 100) {
                System.out.println("Error: el descuento debe estar entre 0 y 100.");
                return;
            }
            nuevo = new productounidad(codigo, nombre, precioBase, existencias, stockMinimo, desc);
        } else if (tipo == 2) {
            double recargo = leerDouble("Recargo adicional: ");
            if (recargo < 0) {
                System.out.println("Error: el recargo no puede ser negativo.");
                return;
            }
            nuevo = new productocombo(codigo, nombre, precioBase, existencias, stockMinimo, recargo);
        } else {
            System.out.println("Tipo de producto invalido.");
            return;
        }

        if (gestor.registrarProducto(nuevo)) {
            System.out.println("Producto registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar. Verifique que el codigo no este duplicado.");
        }
    }

    private static void buscarProductoMenu(gestorpos gestor) {
        String codigo = leerTexto("\nIngrese el codigo del producto: ");
        producto p = gestor.buscarProductoPorCodigo(codigo);
        if (p == null) {
            System.out.println("Producto no encontrado con el codigo: " + codigo);
        } else {
            System.out.println("Producto encontrado: " + p.toString());
        }
    }

    private static void actualizarProductoMenu(gestorpos gestor) {
        String codigo = leerTexto("\nCodigo del producto a actualizar: ");
        producto p = gestor.buscarProductoPorCodigo(codigo);
        if (p == null) {
            System.out.println("No existe un producto con el codigo: " + codigo);
            return;
        }
        double nuevoPrecio = leerDouble("Nuevo precio base: ");
        int nuevasExistencias = leerEntero("Nuevas existencias: ");

        if (gestor.actualizarProducto(codigo, nuevoPrecio, nuevasExistencias)) {
            System.out.println("Producto actualizado correctamente.");
        } else {
            System.out.println("Error al actualizar. Verifique que los valores no sean negativos.");
        }
    }

    private static void listarStockBajo(gestorpos gestor) {
        ArrayList<producto> bajos = gestor.obtenerProductosStockBajo();
        System.out.println("\n--- PRODUCTOS CON EXISTENCIAS BAJAS ---");
        if (bajos.isEmpty()) {
            System.out.println("Todos los productos estan por encima de su stock minimo.");
            return;
        }
        for (producto p : bajos) {
            System.out.println(p.toString());
        }
    }

    private static void registrarClienteMenu(gestorpos gestor) {
        System.out.println("\n--- REGISTRAR CLIENTE ---");
        String doc = leerTexto("Numero de documento: ");
        String nombre = leerTexto("Nombre completo: ");
        String tel = leerTexto("Telefono: ");

        cliente nuevoCliente = new cliente(doc, nombre, tel);
        if (gestor.registrarCliente(nuevoCliente)) {
            System.out.println("Cliente registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar. Ya existe un cliente con ese documento o datos invalidos.");
        }
    }

    private static void listarClientes(gestorpos gestor) {
        ArrayList<cliente> lista = gestor.getClientes();
        System.out.println("\n--- CLIENTES REGISTRADOS ---");
        if (lista.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (cliente c : lista) {
            System.out.println(c.toString());
        }
    }

    private static void buscarClienteMenu(gestorpos gestor) {
        String doc = leerTexto("\nIngrese el numero de documento: ");
        cliente c = gestor.buscarClientePorDocumento(doc);
        if (c == null) {
            System.out.println("Cliente no encontrado con documento: " + doc);
        } else {
            System.out.println("Cliente encontrado: " + c.toString());
        }
    }

    private static void realizarVentaMenu(gestorpos gestor) {
        System.out.println("\n--- NUEVA VENTA ---");
        System.out.println("1. Asociar a cliente registrado");
        System.out.println("2. Cliente general");
        int tipoCliente = leerEntero("Seleccione opcion de cliente: ");

        cliente clienteVenta = null;
        if (tipoCliente == 1) {
            String doc = leerTexto("Documento del cliente: ");
            clienteVenta = gestor.buscarClientePorDocumento(doc);
            if (clienteVenta == null) {
                System.out.println("Cliente no encontrado. Se continuara como Cliente General.");
            }
        }

        venta nuevaVenta = new venta(clienteVenta);
        boolean agregarMas = true;

        while (agregarMas) {
            String codigo = leerTexto("Codigo del producto (o 'FIN' para terminar de agregar): ");
            if (codigo.equalsIgnoreCase("FIN")) {
                agregarMas = false;
            } else {
                producto prod = gestor.buscarProductoPorCodigo(codigo);
                if (prod == null) {
                    System.out.println("Producto no encontrado.");
                } else {
                    int cantidad = leerEntero("Cantidad (Disponible: " + prod.getExistencias() + "): ");
                    if (cantidad <= 0) {
                        System.out.println("La cantidad debe ser mayor a cero.");
                    } else if (cantidad > prod.getExistencias()) {
                        System.out.println("Venta rechazada: cantidad solicitada (" + cantidad
                                + ") supera las existencias disponibles (" + prod.getExistencias() + ").");
                    } else {
                        if (nuevaVenta.agregarItem(prod, cantidad)) {
                            System.out.println("Item agregado correctamente.");
                        }
                    }
                }
            }
        }

        if (nuevaVenta.getItems().isEmpty()) {
            System.out.println("Venta cancelada: no se agregaron items.");
            return;
        }

        nuevaVenta.recalcularTotales();
        System.out.printf("Subtotal: $%.2f | IVA (19%%): $%.2f | Total a pagar: $%.2f%n",
                nuevaVenta.getSubtotalVenta(), nuevaVenta.getValorIva(), nuevaVenta.getTotalVenta());

        System.out.println("Medio de pago:");
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta");
        int opPago = leerEntero("Elija medio de pago: ");

        metodopago medioPago = null;
        if (opPago == 1) {
            double recibido = leerDouble("Monto recibido en efectivo: ");
            medioPago = new pagoefectivo(recibido);
        } else if (opPago == 2) {
            String ultimos4 = leerTexto("Ultimos 4 digitos de la tarjeta: ");
            medioPago = new pagotarjeta(ultimos4);
        } else {
            System.out.println("Medio de pago invalido. Venta cancelada.");
            return;
        }

        if (gestor.finalizarVenta(nuevaVenta, medioPago)) {
            System.out.println("\nVenta registrada con exito:");
            System.out.println(nuevaVenta.generarComprobante());
        } else {
            System.out.println("No se pudo procesar el pago (verifique monto recibido o datos de tarjeta).");
        }
    }

    private static void mostrarReporteVentas(gestorpos gestor) {
        ArrayList<venta> ventas = gestor.getVentas();
        System.out.println("\n--- REPORTE DE VENTAS ---");
        if (ventas.isEmpty()) {
            System.out.println("Aun no se han realizado ventas en esta ejecucion.");
            return;
        }
        for (venta v : ventas) {
            String nomCliente = (v.getCliente() != null) ? v.getCliente().getNombre() : "Cliente General";
            String medio = (v.getMedioPago() != null) ? v.getMedioPago().getNombreMedio() : "N/A";
            System.out.printf("Recibo #%04d | Cliente: %s | Medio: %s | Total: $%.2f%n",
                    v.getNumeroRecibo(), nomCliente, medio, v.getTotalVenta());
        }
        System.out.println("----------------------------------------");
        System.out.println("Numero total de ventas: " + ventas.size());
        System.out.printf("Total vendido acumulado: $%.2f%n", gestor.calcularTotalVendido());
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return SCANNER.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = SCANNER.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }
}