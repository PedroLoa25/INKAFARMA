import java.util.Scanner;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class INKAFARMA{

    static Scanner scanner = new Scanner(System.in);

    static String correoRegistrado = "";
    static String contraseñaRegistrada = "";
    static String dniRegistrado = "";
    static String tarjetaRegistrada = "";
    static String claveTarjeta = "";
    static String nombreRegistrado = "";

    static int totalComprasRealizadas = 0;
    static boolean sesionIniciada = false;

    static String[] nombresProductos = {
            "Pañales Talla M", "Pañales Talla G",
            "Toallitas Húmedas Regular", "Toallitas Húmedas Sensitive",
            "Leche NAN Pro 1 Pequeña", "Leche NAN Pro 1 Grande",
            "Shampoo para bebé Suave", "Crema Corporal Bebé Humectante"
    };

    static String[] marcasProductos = {
            "Huggies", "Huggies",
            "Pampers", "Pampers",
            "Ninet", "Ninet",
            "Johnson’s", "Johnson’s"
    };

    static double[] preciosProductos = {
            45.90, 55.00,
            6.50, 8.00,
            89.00, 150.00,
            12.90, 25.00
    };

    static final int MAMA_BEBE_INICIO = 0;
    static final int MAMA_BEBE_FIN = 7;

    static final int MAX_ITEMS_CARRITO = 20;

    static String[] carritoNombres = new String[MAX_ITEMS_CARRITO];
    static String[] carritoMarcas = new String[MAX_ITEMS_CARRITO];
    static double[] carritoPreciosUnitarios = new double[MAX_ITEMS_CARRITO];
    static int[] carritoCantidades = new int[MAX_ITEMS_CARRITO];

    static int numeroTiposUnicosEnCarrito = 0;

    public static void main(String[] args) {
        System.out.println("=== ¡Bienvenido a INKAFARMA! ===");
        registrarUsuario();
        if (iniciarSesion()) {
            sesionIniciada = true;
            System.out.println("✅ Sesión iniciada correctamente. ¡Bienvenido/a " + nombreRegistrado + "!");
            mostrarOfertas();
            menuPrincipal();
        } else {
            System.out.println("Demasiados intentos fallidos. Saliendo de la aplicación.");
        }
        scanner.close();
    }

    public static void registrarUsuario() {
        System.out.println("\n=== Registro de Usuario ===");
        System.out.print("Ingrese su nombre completo: ");
        nombreRegistrado = scanner.nextLine();

        while (true) {
            System.out.print("Ingrese su correo electrónico: ");
            correoRegistrado = scanner.nextLine().trim();
            Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
            Matcher emailMatcher = emailPattern.matcher(correoRegistrado);
            if (emailMatcher.matches()) {
                break;
            }
            System.out.println("❌ Formato de correo inválido. Intente nuevamente (ej. usuario@dominio.com).");
        }

        while (true) {
            System.out.print("Ingrese una contraseña (mínimo 8 caracteres, al menos una mayúscula, un número y un símbolo): ");
            contraseñaRegistrada = scanner.nextLine();
            Pattern passwordPattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&()_+\\-={}\\[\\]|:;\"'<>,.?/]).{8,}$");
            Matcher passwordMatcher = passwordPattern.matcher(contraseñaRegistrada);
            if (passwordMatcher.matches()) {
                break;
            }
            System.out.println("❌ Contraseña insegura. Debe tener al menos 8 caracteres, una mayúscula, un número y un símbolo.");
        }

        while (true) {
            System.out.print("Ingrese DNI de 8 dígitos: ");
            dniRegistrado = scanner.nextLine();
            Pattern dniPattern = Pattern.compile("^\\d{8}$");
            Matcher dniMatcher = dniPattern.matcher(dniRegistrado);
            if (dniMatcher.matches()) {
                break;
            }
            System.out.println("❌ El DNI debe tener exactamente 8 dígitos numéricos.");
        }

        while (true) {
            System.out.print("Ingrese número de tarjeta (16 dígitos): ");
            tarjetaRegistrada = scanner.nextLine();
            Pattern tarjetaPattern = Pattern.compile("^\\d{16}$");
            Matcher tarjetaMatcher = tarjetaPattern.matcher(tarjetaRegistrada);
            if (tarjetaMatcher.matches()) {
                break;
            }
            System.out.println("❌ La tarjeta debe tener exactamente 16 dígitos numéricos.");
        }

        while (true) {
            System.out.print("Cree una clave para su tarjeta (4 dígitos): ");
            claveTarjeta = scanner.nextLine();
            Pattern clavePattern = Pattern.compile("^\\d{4}$");
            Matcher claveMatcher = clavePattern.matcher(claveTarjeta);
            if (claveMatcher.matches()) {
                break;
            }
            System.out.println("❌ La clave debe tener exactamente 4 dígitos numéricos.");
        }
        System.out.println("✅ Registro exitoso.\n");
    }

    static boolean iniciarSesion() {
        System.out.println("\n=== Inicio de Sesión ===");
        int intentos = 0;
        do {
            System.out.print("Correo: ");
            String correo = scanner.nextLine().trim();
            System.out.print("Contraseña: ");
            String contraseña = scanner.nextLine();

            if (correo.equals(correoRegistrado) && contraseña.equals(contraseñaRegistrada)) {
                return true;
            } else {
                intentos++;
                System.out.println("❌ Correo o contraseña incorrectos. Intento " + intentos + " de 3.");
            }
        } while (intentos < 3);
        return false;
    }

    public static void mostrarOfertas() {
        System.out.println("\n=== OFERTAS DEL DÍA ===");
        Random random = new Random();
        int indiceOferta = random.nextInt(MAMA_BEBE_FIN - MAMA_BEBE_INICIO + 1) + MAMA_BEBE_INICIO;

        String nombreOferta = nombresProductos[indiceOferta];
        String marcaOferta = marcasProductos[indiceOferta];
        double precioRegular = preciosProductos[indiceOferta];
        double precioOferta = precioRegular * 0.80;

        System.out.println("🎉 ¡No te pierdas nuestra Oferta del Día! 🎉");
        System.out.println("Producto: " + nombreOferta + " (" + marcaOferta + ")");
        System.out.println("Precio regular: S/ " + String.format("%.2f", precioRegular));
        System.out.println("¡Precio de OFERTA: S/ " + String.format("%.2f", precioOferta) + "!");
        System.out.println("------------------------------------");
        System.out.println("Esta oferta es solo una muestra. Puedes encontrar más productos en las categorías.");
    }

    public static void menuPrincipal() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Ver Categorías");
            System.out.println("2. Salir");
            System.out.print("Ingrese su opción: ");
            opcion = Integer.parseInt(scanner.nextLine().trim());

            switch (opcion) {
                case 1:
                    menuCategorias();
                    break;
                case 2:
                    System.out.println("Gracias por su preferencia. ¡Vuelva pronto!");
                    break;
                default:
                    System.out.println("❌ Opción inválida. Por favor, intente de nuevo.");
            }
        } while (opcion != 2);
    }

    public static void menuCategorias() {
        int opcion;
        do {
            System.out.println("\n=== CATEGORÍAS ===");
            System.out.println("1. Salud");
            System.out.println("2. Productos Naturales");
            System.out.println("3. Nutrición para Todos");
            System.out.println("4. Mamá y Bebé");
            System.out.println("5. Atrás (Volver al Menú Principal)");
            System.out.print("Ingrese su opción: ");
            opcion = Integer.parseInt(scanner.nextLine().trim());

            switch (opcion) {
                case 1:
                    System.out.println("\n=== Categoría: Salud ===");
                    System.out.println("Esta categoría está actualmente en desarrollo. ¡Pronto tendremos más productos!");
                    break;
                case 2:
                    System.out.println("\n=== Categoría: Productos Naturales ===");
                    System.out.println("Esta categoría está actualmente en desarrollo. ¡Pronto tendremos más productos!");
                    break;
                case 3:
                    System.out.println("\n=== Categoría: Nutrición para Todos ===");
                    System.out.println("Esta categoría está actualmente en desarrollo. ¡Pronto tendremos más productos!");
                    break;
                case 4:
                    menuMamaBebe();
                    break;
                case 5:
                    System.out.println("Volviendo a Categorías...");
                    break;
                default:
                    System.out.println("❌ Opción inválida. Por favor, intente de nuevo.");
            }
        } while (opcion != 5);
    }

    public static void menuMamaBebe() {
        int opcion;
        do {
            System.out.println("\n=== CATEGORÍA: MAMÁ Y BEBÉ ===");
            System.out.println("1. Ver todos los productos");
            System.out.println("2. Filtrar por marca");
            System.out.println("3. Filtrar por precio");
            System.out.println("4. Ver carrito");
            System.out.println("5. Finalizar compra");
            System.out.println("6. Atrás (Volver a Categorías)");
            System.out.print("Ingrese su opción: ");
            opcion = Integer.parseInt(scanner.nextLine().trim());

            switch (opcion) {
                case 1:
                    mostrarProductosDeCategoria("Mamá y Bebé", MAMA_BEBE_INICIO, MAMA_BEBE_FIN);
                    break;
                case 2:
                    filtrarPorMarca("Mamá y Bebé", MAMA_BEBE_INICIO, MAMA_BEBE_FIN);
                    break;
                case 3:
                    filtrarPorPrecio("Mamá y Bebé", MAMA_BEBE_INICIO, MAMA_BEBE_FIN);
                    break;
                case 4:
                    verCarrito();
                    break;
                case 5:
                    finalizarCompra();
                    break;
                case 6:
                    System.out.println("Volviendo a Categorías...");
                    break;
                default:
                    System.out.println("❌ Opción inválida. Por favor, intente de nuevo.");
            }
        } while (opcion != 6);
    }

    public static void mostrarProductoParaCompra(String nombre, String marca, double precio) {
        System.out.println("------------------------------------");
        System.out.println("Producto: " + nombre + " (" + marca + ")");
        System.out.println("Precio: S/ " + String.format("%.2f", precio));
        System.out.println("------------------------------------");
        System.out.print("¿Desea añadir " + nombre + " a su carrito? (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        if (respuesta.equals("s")) {
            System.out.print("Ingrese la cantidad que desea comprar de " + nombre + ": ");
            int cantidad = Integer.parseInt(scanner.nextLine().trim());
            if (cantidad > 0) {
                añadirProductoAlCarrito(nombre, marca, precio, cantidad);
                System.out.println("✅ " + cantidad + " unidad(es) de " + nombre + " añadidas al carrito.");
            } else {
                System.out.println("❌ Cantidad inválida. No se añadió el producto.");
            }
        } else {
            System.out.println("De acuerdo, no se añadió el producto.");
        }
    }

    public static void mostrarProductosDeCategoria(String categoriaNombre, int inicioIndice, int finIndice) {
        System.out.println("\n=== Productos en " + categoriaNombre + " ===");
        if (inicioIndice == -1 || finIndice == -1 || inicioIndice > finIndice || inicioIndice < 0 || finIndice >= nombresProductos.length) {
            System.out.println("No hay productos en esta categoría o los índices son inválidos.");
            return;
        }

        for (int i = inicioIndice; i <= finIndice; i++) {
            System.out.println((i - inicioIndice + 1) + ". " + nombresProductos[i] + " (" + marcasProductos[i] + ") - S/ " + String.format("%.2f", preciosProductos[i]));
        }

        System.out.print("Seleccione el número del producto que desea añadir al carrito (0 para volver): ");
        int opcionProducto = Integer.parseInt(scanner.nextLine().trim());

        if (opcionProducto >= 1 && opcionProducto <= (finIndice - inicioIndice + 1)) {
            int indiceReal = inicioIndice + (opcionProducto - 1);
            System.out.print("Ingrese la cantidad que desea comprar de " + nombresProductos[indiceReal] + ": ");
            int cantidad = Integer.parseInt(scanner.nextLine().trim());
            if (cantidad > 0) {
                añadirProductoAlCarrito(nombresProductos[indiceReal], marcasProductos[indiceReal], preciosProductos[indiceReal], cantidad);
                System.out.println("✅ " + cantidad + " unidad(es) de " + nombresProductos[indiceReal] + " añadidas al carrito.");
            } else {
                System.out.println("❌ Cantidad inválida. No se añadió el producto.");
            }
        } else if (opcionProducto != 0) {
            System.out.println("❌ Opción de producto inválida.");
        }
    }

    public static void añadirProductoAlCarrito(String nombre, String marca, double precio, int cantidad) {
        boolean encontrado = false;
        for (int i = 0; i < numeroTiposUnicosEnCarrito; i++) {
            if (carritoNombres[i] != null && carritoNombres[i].equalsIgnoreCase(nombre) &&
                    carritoMarcas[i] != null && carritoMarcas[i].equalsIgnoreCase(marca)) {
                carritoCantidades[i] += cantidad;
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            if (numeroTiposUnicosEnCarrito < MAX_ITEMS_CARRITO) {
                carritoNombres[numeroTiposUnicosEnCarrito] = nombre;
                carritoMarcas[numeroTiposUnicosEnCarrito] = marca;
                carritoPreciosUnitarios[numeroTiposUnicosEnCarrito] = precio;
                carritoCantidades[numeroTiposUnicosEnCarrito] = cantidad;
                numeroTiposUnicosEnCarrito++;
            } else {
                System.out.println("❌ El carrito está lleno, no se pueden añadir más tipos de productos.");
            }
        }
    }

    public static void verCarrito() {
        System.out.println("\n=== SU CARRITO DE COMPRAS ===");
        if (numeroTiposUnicosEnCarrito == 0) {
            System.out.println("El carrito está vacío.");
            return;
        }

        double subtotalCarrito = 0.0;
        for (int i = 0; i < numeroTiposUnicosEnCarrito; i++) {
            if (carritoNombres[i] != null && carritoCantidades[i] > 0) {
                double totalPorProducto = carritoCantidades[i] * carritoPreciosUnitarios[i];
                System.out.println("- " + carritoNombres[i] + " (" + carritoMarcas[i] + ")");
                System.out.println("  Cantidad: " + carritoCantidades[i] + " x S/ " + String.format("%.2f", carritoPreciosUnitarios[i]) + " = S/ " + String.format("%.2f", totalPorProducto));
                subtotalCarrito += totalPorProducto;
            }
        }
        System.out.println("------------------------------------");
        System.out.println("Subtotal del carrito: S/ " + String.format("%.2f", subtotalCarrito));
    }

    public static void filtrarPorMarca(String categoriaNombre, int inicioIndice, int finIndice) {
        System.out.println("\n=== Filtrar por Marca en " + categoriaNombre + " ===");

        if (inicioIndice == -1 || finIndice == -1 || inicioIndice > finIndice) {
            System.out.println("No hay productos para filtrar por marca en esta categoría.");
            return;
        }

        System.out.println("Marcas disponibles en esta categoría:");
        String[] marcasUnicas = new String[finIndice - inicioIndice + 1];
        int countUnicas = 0;

        for (int i = inicioIndice; i <= finIndice; i++) {
            boolean yaExiste = false;
            for (int j = 0; j < countUnicas; j++) {
                if (marcasUnicas[j].equalsIgnoreCase(marcasProductos[i])) {
                    yaExiste = true;
                    break;
                }
            }
            if (!yaExiste) {
                marcasUnicas[countUnicas] = marcasProductos[i];
                countUnicas++;
            }
        }

        if (countUnicas == 0) {
            System.out.println("No hay marcas disponibles para mostrar.");
        } else {
            for (int i = 0; i < countUnicas; i++) {
                System.out.println("- " + marcasUnicas[i]);
            }
        }
        System.out.println("------------------------------------");

        System.out.print("Ingrese la marca a buscar: ");
        String marcaBuscada = scanner.nextLine().trim();

        System.out.println("\n--- Productos de la marca '" + marcaBuscada + "' ---");
        boolean encontrado = false;
        int[] indicesFiltrados = new int[finIndice - inicioIndice + 1];
        int productosFiltradosCount = 0;

        for (int i = inicioIndice; i <= finIndice; i++) {
            if (marcasProductos[i].equalsIgnoreCase(marcaBuscada)) {
                System.out.println((productosFiltradosCount + 1) + ". " + nombresProductos[i] + " (" + marcasProductos[i] + ") - S/ " + String.format("%.2f", preciosProductos[i]));
                indicesFiltrados[productosFiltradosCount] = i;
                productosFiltradosCount++;
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron productos con la marca '" + marcaBuscada + "' en esta categoría.");
        } else {
            System.out.print("Seleccione el número del producto que desea añadir al carrito (0 para volver): ");
            int opcionProducto = Integer.parseInt(scanner.nextLine().trim());
            if (opcionProducto >= 1 && opcionProducto <= productosFiltradosCount) {
                int indiceReal = indicesFiltrados[opcionProducto - 1];
                System.out.print("Ingrese la cantidad que desea comprar de " + nombresProductos[indiceReal] + ": ");
                int cantidad = Integer.parseInt(scanner.nextLine().trim());
                if (cantidad > 0) {
                    añadirProductoAlCarrito(nombresProductos[indiceReal], marcasProductos[indiceReal], preciosProductos[indiceReal], cantidad);
                    System.out.println("✅ " + cantidad + " unidad(es) de " + nombresProductos[indiceReal] + " añadidas al carrito.");
                } else {
                    System.out.println("❌ Cantidad inválida. No se añadió el producto.");
                }
            } else if (opcionProducto != 0) {
                System.out.println("❌ Opción de producto inválida.");
            }
        }
    }

    public static void filtrarPorPrecio(String categoriaNombre, int inicioIndice, int finIndice) {
        System.out.println("\n=== Filtrar por Precio en " + categoriaNombre + " ===");

        if (inicioIndice == -1 || finIndice == -1 || inicioIndice > finIndice) {
            System.out.println("No hay productos para filtrar por precio en esta categoría.");
            return;
        }

        System.out.print("Ingrese el precio mínimo: S/ ");
        double precioMin = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Ingrese el precio máximo: S/ ");
        double precioMax = Double.parseDouble(scanner.nextLine().trim());

        if (precioMin > precioMax) {
            System.out.println("❌ El precio mínimo no puede ser mayor que el precio máximo.");
            return;
        }

        System.out.println("\n--- Productos entre S/ " + String.format("%.2f", precioMin) + " y S/ " + String.format("%.2f", precioMax) + " ---");
        boolean encontrado = false;
        int productosFiltradosCount = 0;
        int[] indicesFiltrados = new int[finIndice - inicioIndice + 1];

        for (int i = inicioIndice; i <= finIndice; i++) {
            if (preciosProductos[i] >= precioMin && preciosProductos[i] <= precioMax) {
                System.out.println((productosFiltradosCount + 1) + ". " + nombresProductos[i] + " (" + marcasProductos[i] + ") - S/ " + String.format("%.2f", preciosProductos[i]));
                indicesFiltrados[productosFiltradosCount] = i;
                productosFiltradosCount++;
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron productos en este rango de precios en esta categoría.");
        } else {
            System.out.print("Seleccione el número del producto que desea añadir al carrito (0 para volver): ");
            int opcionProducto = Integer.parseInt(scanner.nextLine().trim());
            if (opcionProducto >= 1 && opcionProducto <= productosFiltradosCount) {
                int indiceReal = indicesFiltrados[opcionProducto - 1];
                System.out.print("Ingrese la cantidad que desea comprar de " + nombresProductos[indiceReal] + ": ");
                int cantidad = Integer.parseInt(scanner.nextLine().trim());
                if (cantidad > 0) {
                    añadirProductoAlCarrito(nombresProductos[indiceReal], marcasProductos[indiceReal], preciosProductos[indiceReal], cantidad);
                    System.out.println("✅ " + cantidad + " unidad(es) de " + nombresProductos[indiceReal] + " añadidas al carrito.");
                } else {
                    System.out.println("❌ Cantidad inválida. No se añadió el producto.");
                }
            } else if (opcionProducto != 0) {
                System.out.println("❌ Opción de producto inválida.");
            }
        }
    }

    public static void finalizarCompra() {
        System.out.println("\n=== FINALIZAR COMPRA ===");
        if (numeroTiposUnicosEnCarrito == 0) {
            System.out.println("El carrito está vacío. No se puede finalizar la compra.");
            return;
        }

        double subtotalGeneral = 0.0;
        System.out.println("\n---- DETALLE DE SU PEDIDO ----");
        for (int i = 0; i < numeroTiposUnicosEnCarrito; i++) {
            if (carritoNombres[i] != null && carritoCantidades[i] > 0) {
                double totalPorProducto = carritoCantidades[i] * carritoPreciosUnitarios[i];
                System.out.println("- " + carritoNombres[i] + " (" + carritoMarcas[i] + ")");
                System.out.println("  Cant: " + carritoCantidades[i] + " x S/ " + String.format("%.2f", carritoPreciosUnitarios[i]) + " = S/ " + String.format("%.2f", totalPorProducto));
                subtotalGeneral += totalPorProducto;
            }
        }

        double igv = subtotalGeneral * 0.18;
        double totalAPagar = subtotalGeneral + igv;

        System.out.println("------------------------------------");
        System.out.println("Subtotal: S/ " + String.format("%.2f", subtotalGeneral));
        System.out.println("IGV (18%): S/ " + String.format("%.2f", igv));
        System.out.println("TOTAL A PAGAR: S/ " + String.format("%.2f", totalAPagar));
        System.out.println("------------------------------------");

        System.out.print("¿Desea envío a domicilio? (s/n): ");
        String quiereDelivery = scanner.nextLine().trim().toLowerCase();
        if (quiereDelivery.equals("s")) {
            System.out.print("Ingrese su dirección de envío: ");
            String direccion = scanner.nextLine();
            System.out.println("✅ Envío a domicilio a '" + direccion + "' programado. Costo adicional se calculará en la entrega.");
        }

        System.out.println("\n--- Método de Pago ---");
        System.out.println("1. Tarjeta");
        System.out.println("2. Efectivo");
        System.out.print("Seleccione su método de pago: ");
        int opcionPago = Integer.parseInt(scanner.nextLine().trim());

        double montoPagado = 0.0;
        double vuelto = 0.0;
        boolean pagoExitoso = false;

        switch (opcionPago) {
            case 1:
                System.out.print("Ingrese la clave de su tarjeta (4 dígitos): ");
                String claveIngresada = scanner.nextLine();
                if (claveIngresada.equals(claveTarjeta)) {
                    System.out.println("✅ Pago con tarjeta exitoso.");
                    pagoExitoso = true;
                    montoPagado = totalAPagar;
                    vuelto = 0.0;
                } else {
                    System.out.println("❌ Clave de tarjeta incorrecta. Pago cancelado.");
                }
                break;
            case 2:
                System.out.print("Ingrese el monto en efectivo con el que va a pagar: S/ ");
                montoPagado = Double.parseDouble(scanner.nextLine().trim());
                if (montoPagado < totalAPagar) {
                    System.out.println("❌ Monto insuficiente. El monto debe ser mayor o igual a S/ " + String.format("%.2f", totalAPagar));
                    // Aquí no hay bucle para reintentar si el monto es insuficiente
                    // Si el monto es incorrecto, el pago fallará
                } else {
                    vuelto = montoPagado - totalAPagar;
                    System.out.println("✅ Pago en efectivo exitoso.");
                    pagoExitoso = true;
                }
                break;
            default:
                System.out.println("❌ Opción de pago inválida. Pago cancelado.");
        }

        if (pagoExitoso) {
            System.out.println("\n---- BOLETA DE VENTA ELECTRÓNICA ----");
            System.out.println("Cliente: " + nombreRegistrado + " (DNI: " + dniRegistrado + ")");
            System.out.println("------------------------------------");
            for (int i = 0; i < numeroTiposUnicosEnCarrito; i++) {
                if (carritoNombres[i] != null && carritoCantidades[i] > 0) {
                    double totalPorProducto = carritoCantidades[i] * carritoPreciosUnitarios[i];
                    System.out.println("- " + carritoNombres[i] + " (" + carritoMarcas[i] + ")");
                    System.out.println("  Cant: " + carritoCantidades[i] + " x S/ " + String.format("%.2f", carritoPreciosUnitarios[i]) + " = S/ " + String.format("%.2f", totalPorProducto));
                }
            }
            System.out.println("------------------------------------");
            System.out.println("Subtotal: S/ " + String.format("%.2f", subtotalGeneral));
            System.out.println("IGV (18%): S/ " + String.format("%.2f", igv));
            System.out.println("TOTAL: S/ " + String.format("%.2f", totalAPagar));
            System.out.println("Monto Pagado: S/ " + String.format("%.2f", montoPagado));
            System.out.println("Vuelto: S/ " + String.format("%.2f", vuelto));
            System.out.println("------------------------------------");
            System.out.println("✅ ¡Compra finalizada exitosamente! Gracias por su preferencia.");
            totalComprasRealizadas++;
            numeroTiposUnicosEnCarrito = 0;
            for (int i = 0; i < MAX_ITEMS_CARRITO; i++) {
                carritoNombres[i] = null;
                carritoMarcas[i] = null;
                carritoPreciosUnitarios[i] = 0.0;
                carritoCantidades[i] = 0;
            }
        } else {
            System.out.println("La compra ha sido cancelada.");
        }
    }
}
