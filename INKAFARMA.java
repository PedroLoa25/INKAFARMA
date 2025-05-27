import java.util.*;
import java.util.Random;

public class EXA {

    static Scanner papu = new Scanner(System.in);
    static String correoRegistrado = "";
    static String contraseñaRegistrada = "";
    static String dniRegistrado = "";
    static String tarjetaRegistrada = "";
    static String claveTarjeta = "";
    static String nombreRegistrado = "";

    static class Producto {
        String nombre;
        String marca;
        double precio;
        int stock;

        Producto(String nombre, String marca, double precio, int stock) {
            this.nombre = nombre;
            this.marca = marca;
            this.precio = precio;
            this.stock = stock;
        }

        boolean esIgual(Producto otro) {
            if (otro == null) return false;
            return this.nombre.equals(otro.nombre) && this.marca.equals(otro.marca);
        }
    }

    static Producto[] productosMamaBebe = {
            new Producto("Pañales Talla M", "Huggies", 45.90, 10),
            new Producto("Toallitas húmedas", "Pampers", 6.50, 15),
            new Producto("Leche NAN Pro 1", "Ninet", 89.00, 8),
            new Producto("Shampoo para bebé", "Johnson’s", 12.90, 20),
            new Producto("Pañales Talla S", "Babysec", 38.90, 10),
            new Producto("Jabón líquido", "Johnson’s", 15.50, 15),
            new Producto("Pañales XG", "Pampers", 59.00, 5),
            new Producto("Toallitas húmedas extra", "Babysec", 7.80, 12),
            new Producto("Crema protectora", "Huggies", 13.40, 8),
            new Producto("Leche infantil 2+", "Ninet", 94.90, 6)
    };

    static Producto[] productosEnOferta = {
            new Producto("Fórmula Etapa 1 Lata", "Similac", 75.50, 5),
            new Producto("Biberón Anticólicos", "Avent", 25.00, 10),
            new Producto("Cereal Infantil Trigo y Miel", "Nestum", 18.90, 8),
            new Producto("Colonia para Bebé Hipoalergénica", "Mustela", 35.20, 12),
            new Producto("Chupón Anatómico 0-6m", "Nuk", 15.00, 20)
    };


    static final int MAX_CARRITO_ITEMS = 50;
    static Producto[] carrito = new Producto[MAX_CARRITO_ITEMS];
    static int numeroItemsEnCarrito = 0;

    static int leerEnteroSimple(String prompt) {
        String entrada;
        while (true) {
            System.out.print(prompt);
            entrada = papu.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("❌ Entrada no puede estar vacía. Intente nuevamente.");
                continue;
            }

            boolean esValido = true;
            boolean esNegativo = false;
            int indiceInicio = 0;

            if (entrada.charAt(0) == '-') {
                if (entrada.length() == 1) {
                    esValido = false;
                } else {
                    esNegativo = true;
                    indiceInicio = 1;
                }
            } else if (entrada.charAt(0) == '+') {
                if (entrada.length() == 1) {
                    esValido = false;
                } else {
                    indiceInicio = 1;
                }
            }

            if (!esValido) {
                System.out.println("❌ Entrada inválida. Ingrese un número válido.");
                continue;
            }

            long numeroLargo = 0;
            for (int i = indiceInicio; i < entrada.length(); i++) {
                char c = entrada.charAt(i);
                if (c < '0' || c > '9') {
                    esValido = false;
                    break;
                }
                numeroLargo = numeroLargo * 10 + (c - '0');
                if (esNegativo && -numeroLargo < Integer.MIN_VALUE) {
                    esValido = false; break;
                }
                if (!esNegativo && numeroLargo > Integer.MAX_VALUE) {
                    esValido = false; break;
                }
            }

            if (esValido) {
                if (esNegativo) {
                    numeroLargo = -numeroLargo;
                }
                if (numeroLargo > Integer.MAX_VALUE || numeroLargo < Integer.MIN_VALUE) {
                    System.out.println("❌ Número fuera del rango permitido (int). Intente nuevamente.");
                    continue;
                }
                return (int) numeroLargo;
            } else {
                System.out.println("❌ Entrada inválida. Ingrese solo dígitos numéricos (opcionalmente un signo al inicio) o número demasiado grande/pequeño.");
            }
        }
    }

    static double leerDoubleSimple(String prompt) {
        String entrada;
        while (true) {
            System.out.print(prompt);
            entrada = papu.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("❌ Entrada no puede estar vacía. Intente nuevamente.");
                continue;
            }

            boolean esValido = true;
            boolean puntoEncontrado = false;
            boolean esNegativo = false;
            int indiceInicio = 0;

            if (entrada.charAt(0) == '-') {
                if (entrada.length() == 1) { esValido = false; }
                else { esNegativo = true; indiceInicio = 1; }
            } else if (entrada.charAt(0) == '+') {
                if (entrada.length() == 1) { esValido = false; }
                else { indiceInicio = 1; }
            }

            if (!esValido) {
                System.out.println("❌ Formato numérico inválido. Intente nuevamente.");
                continue;
            }


            for (int i = indiceInicio; i < entrada.length(); i++) {
                char c = entrada.charAt(i);
                if (c >= '0' && c <= '9') {
                } else if (c == '.') {
                    if (puntoEncontrado || i == indiceInicio || i == entrada.length() - 1) {
                        esValido = false;
                        break;
                    }
                    puntoEncontrado = true;
                } else {
                    esValido = false;
                    break;
                }
            }
            if (entrada.substring(indiceInicio).equals(".")) {
                esValido = false;
            }


            if (esValido) {
                double resultado = 0.0;
                double parteEntera = 0.0;
                double parteDecimal = 0.0;
                int j = indiceInicio;

                while (j < entrada.length() && entrada.charAt(j) != '.') {
                    parteEntera = parteEntera * 10 + (entrada.charAt(j) - '0');
                    j++;
                }
                resultado = parteEntera;

                if (j < entrada.length() && entrada.charAt(j) == '.') {
                    j++;
                    double factor = 0.1;
                    while (j < entrada.length()) {
                        parteDecimal = parteDecimal + (entrada.charAt(j) - '0') * factor;
                        factor /= 10;
                        j++;
                    }
                    resultado += parteDecimal;
                }
                if (esNegativo) {
                    resultado = -resultado;
                }
                return resultado;

            } else {
                System.out.println("❌ Formato numérico inválido. Use números como 123 o 12.34 (opcionalmente con signo).");
            }
        }
    }


    public static void main(String[] args) {
        registrarUsuario();
        if (iniciarSesion()) {
            mostrarOfertas();
            menuPrincipal();
        }
    }

    public static void registrarUsuario() {
        System.out.println("=== Registro de Usuario ===");
        System.out.print("Ingrese su nombre completo: ");
        nombreRegistrado = papu.nextLine();

        while (true) {
            System.out.print("Ingrese su correo electrónico: ");
            correoRegistrado = papu.nextLine();
            if (correoRegistrado.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) break;
            System.out.println("❌ Correo inválido. Intente nuevamente.");
        }

        while (true) {
            System.out.print("Ingrese una contraseña (mínimo 8 caracteres, al menos una mayúscula, un número y un símbolo): ");
            contraseñaRegistrada = papu.nextLine();
            if (contraseñaRegistrada.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&()_+\\-={}\\[\\]|:;\"'<>,.?/]).{8,}$")) break;
            System.out.println("❌ Contraseña insegura. Intente nuevamente.");
        }

        while (true) {
            System.out.print("Ingrese DNI de 8 dígitos: ");
            dniRegistrado = papu.nextLine();
            if (dniRegistrado.matches("^\\d{8}$")) break;
            System.out.println("❌ El DNI debe tener exactamente 8 dígitos numéricos.");
        }

        while (true) {
            System.out.print("Ingrese número de tarjeta (16 dígitos): ");
            tarjetaRegistrada = papu.nextLine();
            if (tarjetaRegistrada.matches("^\\d{16}$")) break;
            System.out.println("❌ La tarjeta debe tener exactamente 16 dígitos.");
        }

        while (true) {
            System.out.print("Cree una clave para su tarjeta (4 dígitos): ");
            claveTarjeta = papu.nextLine();
            if (claveTarjeta.matches("^\\d{4}$")) break;
            System.out.println("❌ La clave debe tener exactamente 4 dígitos.");
        }
        System.out.println("✅ Registro exitoso.\n");
    }

    static boolean iniciarSesion() {
        System.out.println("\nInicio de sesión");
        for (int intentos = 0; intentos < 3; intentos++) {
            System.out.print("Correo: ");
            String correo = papu.nextLine();
            System.out.print("Contraseña: ");
            String contraseña = papu.nextLine();

            if (correo.equals(correoRegistrado) && contraseña.equals(contraseñaRegistrada)) {
                return true;
            } else {
                System.out.println("Correo o contraseña incorrectos. Intento " + (intentos + 1) + " de 3.");
            }
        }
        return false;
    }

    public static void mostrarOfertas() {
        System.out.println("🎉 ¡Bienvenido/a a Inkafarma! Ofertas especiales disponibles.");

        if (productosEnOferta != null && productosEnOferta.length > 0) {
            Random random = new Random();
            int indiceAleatorio = random.nextInt(productosEnOferta.length); 
            Producto ofertaDelDia = productosEnOferta[indiceAleatorio];

            System.out.println("\n✨ ¡OFERTA DEL DÍA! ✨");
            System.out.println("------------------------------------");
            System.out.println("Producto: " + ofertaDelDia.nombre);
            System.out.println("Marca: " + ofertaDelDia.marca);
            System.out.println("Precio Especial: S/ " + String.format("%.2f", ofertaDelDia.precio));
            System.out.println("Stock limitado: " + ofertaDelDia.stock + " unidades");
            System.out.println("------------------------------------");
        }
        else {
            System.out.println("No hay ofertas especiales por el momento.");
        }
    }

    public static void menuPrincipal() {
        while (true) {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Categorías");
            System.out.println("2. Ver carrito");
            System.out.println("3. Finalizar compra");
            System.out.println("4. Salir");

            int opcion = leerEnteroSimple("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    menuCategorias();
                    break;
                case 2:
                    verCarrito();
                    break;
                case 3:
                    finalizarCompra();
                    break;
                case 4:
                    System.out.println("Gracias por usar Inkafarma App. ¡Hasta luego!");
                    return;
                default:
                    System.out.println("❌ Opción inválida.");
            }
        }
    }

    public static void menuCategorias() {
        while (true) {
            System.out.println("\n--- Categorías ---");
            System.out.println("1. Mamá y Bebé");
            System.out.println("2. Inka Packs (no disponible)");
            System.out.println("3. Salud (no disponible)");
            System.out.println("4. Productos Naturales (no disponible)");
            System.out.println("5. Nutrición para Todos (no disponible)");
            System.out.println("6. Volver al menú principal");

            int opcion = leerEnteroSimple("Seleccione una categoría: ");

            if (opcion == 1) mostrarFiltradoMamaBebe();
            else if (opcion == 6) break;
            else if (opcion >=2 && opcion <=5) System.out.println("❌ Categoría aún no disponible.");
            else System.out.println("❌ Opción inválida.");
        }
    }

    public static void mostrarFiltradoMamaBebe() {
        while (true) {
            System.out.println("\n--- Mamá y Bebé ---");
            System.out.println("1. Ver todos");
            System.out.println("2. Filtrar por marca");
            System.out.println("3. Filtrar por precio (intervalo)");
            System.out.println("4. Volver");

            int opcion = leerEnteroSimple("Seleccione opción: ");

            if (opcion == 1) mostrarYAgregar(productosMamaBebe, productosMamaBebe.length);
            else if (opcion == 2) filtrarPorMarca();
            else if (opcion == 3) filtrarPorPrecio();
            else if (opcion == 4) break;
            else System.out.println("❌ Opción inválida.");
        }
    }

    public static void filtrarPorMarca() {
        String[] marcasUnicas = new String[productosMamaBebe.length];
        int contadorMarcasUnicas = 0;

        for (int i = 0; i < productosMamaBebe.length; i++) {
            boolean encontrada = false;
            for (int j = 0; j < contadorMarcasUnicas; j++) {
                if (productosMamaBebe[i].marca.equals(marcasUnicas[j])) {
                    encontrada = true;
                    break;
                }
            }
            if (!encontrada) {
                marcasUnicas[contadorMarcasUnicas] = productosMamaBebe[i].marca;
                contadorMarcasUnicas++;
            }
        }

        System.out.println("\nMarcas disponibles:");
        for (int i = 0; i < contadorMarcasUnicas; i++) {
            System.out.println((i + 1) + ". " + marcasUnicas[i]);
        }
        if (contadorMarcasUnicas == 0) {
            System.out.println("No hay marcas disponibles.");
            return;
        }

        int seleccionNum = leerEnteroSimple("Seleccione la marca (número): ");

        if (seleccionNum >= 1 && seleccionNum <= contadorMarcasUnicas) {
            String marcaElegida = marcasUnicas[seleccionNum - 1];
            int contadorFiltrados = 0;
            for(int i=0; i < productosMamaBebe.length; i++){
                if(productosMamaBebe[i].marca.equalsIgnoreCase(marcaElegida)){
                    contadorFiltrados++;
                }
            }

            if(contadorFiltrados == 0){
                System.out.println("No hay productos de la marca " + marcaElegida);
                return;
            }

            Producto[] productosFiltrados = new Producto[contadorFiltrados];
            int indiceFiltrados = 0;
            for (int i = 0; i < productosMamaBebe.length; i++) {
                if (productosMamaBebe[i].marca.equalsIgnoreCase(marcaElegida)) {
                    productosFiltrados[indiceFiltrados] = productosMamaBebe[i];
                    indiceFiltrados++;
                }
            }
            mostrarYAgregar(productosFiltrados, indiceFiltrados);
        } else {
            System.out.println("❌ Opción inválida.");
        }
    }

    public static void filtrarPorPrecio() {
        double min, max;
        min = leerDoubleSimple("Ingrese precio mínimo: S/ ");
        max = leerDoubleSimple("Ingrese precio máximo: S/ ");

        if(min < 0 || max < 0 || min > max){
            System.out.println("❌ Rango de precios inválido.");
            return;
        }

        int contadorFiltrados = 0;
        for(int i=0; i < productosMamaBebe.length; i++){
            if(productosMamaBebe[i].precio >= min && productosMamaBebe[i].precio <= max){
                contadorFiltrados++;
            }
        }

        if (contadorFiltrados == 0) {
            System.out.println("❌ No se encontraron productos en ese rango de precios.");
            return;
        }

        Producto[] productosFiltrados = new Producto[contadorFiltrados];
        int indiceFiltrados = 0;
        for (int i = 0; i < productosMamaBebe.length; i++) {
            if (productosMamaBebe[i].precio >= min && productosMamaBebe[i].precio <= max) {
                productosFiltrados[indiceFiltrados] = productosMamaBebe[i];
                indiceFiltrados++;
            }
        }
        mostrarYAgregar(productosFiltrados, indiceFiltrados);
    }

    public static void mostrarYAgregar(Producto[] productosAMostrar, int cantidadAMostrar) {
        if (cantidadAMostrar == 0) {
            return;
        }
        for (int i = 0; i < cantidadAMostrar; i++) {
            Producto p = productosAMostrar[i];
            System.out.println((i + 1) + ". " + p.nombre + " (" + p.marca + ") - S/ " + String.format("%.2f", p.precio) + " - Stock: " + p.stock);
        }

        int eleccionNum = leerEnteroSimple("Seleccione producto para añadir al carrito (0 para cancelar): ");

        if (eleccionNum > 0 && eleccionNum <= cantidadAMostrar) {
            Producto productoOriginalSeleccionado = productosAMostrar[eleccionNum - 1];

            if (productoOriginalSeleccionado.stock > 0) {
                int cantidadAComprar = leerEnteroSimple("Ingrese cantidad: ");

                if (cantidadAComprar > 0 && cantidadAComprar <= productoOriginalSeleccionado.stock) {
                    if (numeroItemsEnCarrito + cantidadAComprar <= MAX_CARRITO_ITEMS) {
                        for (int i = 0; i < cantidadAComprar; i++) {
                            carrito[numeroItemsEnCarrito] = new Producto(productoOriginalSeleccionado.nombre, productoOriginalSeleccionado.marca, productoOriginalSeleccionado.precio, 1);
                            numeroItemsEnCarrito++;
                        }
                        productoOriginalSeleccionado.stock -= cantidadAComprar;
                        System.out.println("✅ " + cantidadAComprar + " unidades de '" + productoOriginalSeleccionado.nombre + "' añadidas al carrito.");
                    } else {
                        System.out.println("❌ No hay suficiente espacio en el carrito para " + cantidadAComprar + " unidades.");
                    }
                } else if (cantidadAComprar <=0) {
                    System.out.println("❌ La cantidad debe ser mayor a cero.");
                }
                else {
                    System.out.println("❌ Cantidad solicitada excede el stock disponible (" + productoOriginalSeleccionado.stock + ").");
                }
            } else {
                System.out.println("❌ Producto sin stock.");
            }
        } else if (eleccionNum != 0) {
            System.out.println("❌ Selección inválida.");
        }
    }

    public static void verCarrito() {
        if (numeroItemsEnCarrito == 0) {
            System.out.println("🛒 El carrito está vacío.");
            return;
        }
        System.out.println("\n=== Carrito de Compras ===");

        Producto[] productosUnicos = new Producto[numeroItemsEnCarrito];
        int[] cantidadesUnicas = new int[numeroItemsEnCarrito];
        int contadorUnicos = 0;
        boolean[] procesado = new boolean[numeroItemsEnCarrito];

        for (int i = 0; i < numeroItemsEnCarrito; i++) {
            if (procesado[i]) continue;

            Producto actual = carrito[i];
            int cantidadActual = 0;
            for (int j = 0; j < numeroItemsEnCarrito; j++) {
                if (carrito[j].esIgual(actual)) {
                    cantidadActual++;
                    procesado[j] = true;
                }
            }
            productosUnicos[contadorUnicos] = actual;
            cantidadesUnicas[contadorUnicos] = cantidadActual;
            contadorUnicos++;
        }

        double subtotalCarrito = 0;
        for (int i = 0; i < contadorUnicos; i++) {
            Producto p = productosUnicos[i];
            int cantidad = cantidadesUnicas[i];
            double totalProducto = p.precio * cantidad;
            subtotalCarrito += totalProducto;
            System.out.println(p.nombre + " (" + p.marca + ") - S/ " + String.format("%.2f", p.precio) + " x" + cantidad + " = S/ " + String.format("%.2f", totalProducto));
        }
        System.out.println("------------------------------------");
        System.out.println("Subtotal del Carrito: S/ " + String.format("%.2f", subtotalCarrito));
    }

    public static void finalizarCompra() {
        if (numeroItemsEnCarrito == 0) {
            System.out.println("❌ El carrito está vacío. Nada que finalizar.");
            return;
        }

        Producto[] productosUnicosBoleta = new Producto[numeroItemsEnCarrito];
        int[] cantidadesUnicasBoleta = new int[numeroItemsEnCarrito];
        int contadorUnicosBoleta = 0;
        boolean[] procesadoBoleta = new boolean[numeroItemsEnCarrito];

        double subtotal = 0;
        for(int i=0; i < numeroItemsEnCarrito; i++){
            subtotal += carrito[i].precio;
        }

        for (int i = 0; i < numeroItemsEnCarrito; i++) {
            if (procesadoBoleta[i]) continue;
            Producto actual = carrito[i];
            int cantidadActual = 0;
            for (int j = 0; j < numeroItemsEnCarrito; j++) {
                if (carrito[j].esIgual(actual)) {
                    cantidadActual++;
                    procesadoBoleta[j] = true;
                }
            }
            productosUnicosBoleta[contadorUnicosBoleta] = actual;
            cantidadesUnicasBoleta[contadorUnicosBoleta] = cantidadActual;
            contadorUnicosBoleta++;
        }

        double igv = subtotal * 0.18;
        double totalPagar = subtotal + igv;

        System.out.println("\n--- Resumen de Compra ---");
        for (int i = 0; i < contadorUnicosBoleta; i++) {
            Producto p = productosUnicosBoleta[i];
            int cantidad = cantidadesUnicasBoleta[i];
            System.out.println(p.nombre + " (" + p.marca + ") - S/ " + String.format("%.2f", p.precio) + " x" + cantidad);
        }
        System.out.println("---------------------------");
        System.out.println("Subtotal: S/ " + String.format("%.2f", subtotal));
        System.out.println("IGV (18%): S/ " + String.format("%.2f", igv));
        System.out.println("TOTAL A PAGAR: S/ " + String.format("%.2f", totalPagar));

        System.out.print("\n¿Desea delivery? (s/n): ");
        String delivery = papu.nextLine();
        if (delivery.equalsIgnoreCase("s")) {
            System.out.print("Ingrese dirección de entrega: ");
            String direccion = papu.nextLine();
            System.out.println("Se enviará a: " + direccion);
        }

        System.out.print("¿Pagar con tarjeta? (s/n): ");
        String pagoTarjeta = papu.nextLine();
        double vuelto = 0;
        double efectivoRecibido = 0;

        if (pagoTarjeta.equalsIgnoreCase("s")) {
            System.out.print("Ingrese clave de tarjeta ("+ claveTarjeta.substring(0,1) +"***" +"): ");
            String claveIngresada = papu.nextLine();
            if (!claveIngresada.equals(claveTarjeta)) {
                System.out.println("❌ Clave de tarjeta incorrecta. Compra cancelada.");
                return;
            }
            System.out.println("Pago con tarjeta exitoso.");
            vuelto = 0;
        } else {
            efectivoRecibido = leerDoubleSimple("Ingrese monto en efectivo: S/ ");

            if (efectivoRecibido < totalPagar) {
                System.out.println("❌ Monto insuficiente. Compra cancelada.");
                return;
            }
            vuelto = efectivoRecibido - totalPagar;
            System.out.println("Pago en efectivo. Su vuelto es S/ " + String.format("%.2f", vuelto));
        }

        System.out.println("\n🧾 BOLETA DE VENTA ELECTRÓNICA");
        System.out.println("------------------------------------");
        System.out.println("Cliente: " + nombreRegistrado);
        System.out.println("DNI: " + dniRegistrado);
        System.out.println("------------------------------------");
        System.out.println("Productos:");
        for (int i = 0; i < contadorUnicosBoleta; i++) {
            Producto p = productosUnicosBoleta[i];
            int cantidad = cantidadesUnicasBoleta[i];
            double totalPorProducto = p.precio * cantidad;
            System.out.println("- " + p.nombre + " (" + p.marca + ")");
            System.out.println("  Cant: " + cantidad + " x S/ " + String.format("%.2f", p.precio) + " = S/ " + String.format("%.2f", totalPorProducto));
        }
        System.out.println("------------------------------------");
        System.out.println("Subtotal: S/ " + String.format("%.2f", subtotal));
        System.out.println("IGV (18%): S/ " + String.format("%.2f", igv));
        System.out.println("TOTAL: S/ " + String.format("%.2f", totalPagar));
        if (!pagoTarjeta.equalsIgnoreCase("s")) {
            System.out.println("Efectivo recibido: S/ " + String.format("%.2f", efectivoRecibido));
            System.out.println("Vuelto: S/ " + String.format("%.2f", vuelto));
        }
        System.out.println("------------------------------------");
        System.out.println("✅ ¡Compra finalizada exitosamente!");
        System.out.println("Gracias por su preferencia, " + nombreRegistrado + ".");

        for(int i=0; i < numeroItemsEnCarrito; i++){
            carrito[i] = null;
        }
        numeroItemsEnCarrito = 0;
    }
}