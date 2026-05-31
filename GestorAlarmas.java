import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GestorAlarmas {
    private final List<Alarma> listaAlarmas;
    private boolean modoVacaciones; // Funcionalidad Avanzada 1

    public GestorAlarmas() {
        this.listaAlarmas = new ArrayList<>();
        this.modoVacaciones = false;
    }

    public void agregarAlarma(Alarma alarma) {
        listaAlarmas.add(alarma);
        System.out.println("✅ Alarma creada con éxito.");
    }

    public boolean eliminarAlarma(int id) {
        return listaAlarmas.removeIf(a -> a.getId() == id);
    }

    public Alarma buscarPorId(int id) {
        return listaAlarmas.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    // Consultar próximas alarmas activas
    public void listarAlarmasActivas() {
        if (modoVacaciones) {
            System.out.println("🌴 MODO VACACIONES ACTIVO: Todas las alarmas están temporalmente suspendidas.");
            return;
        }

        long activas = listaAlarmas.stream().filter(Alarma::isActiva).count();
        if (activas == 0) {
            System.out.println("📭 No hay próximas alarmas activas configuradas.");
            return;
        }

        System.out.println("\n--- Próximas Alarmas Activas ---");
        listaAlarmas.stream()
                .filter(Alarma::isActiva)
                .sorted((a1, a2) -> a1.getHora().compareTo(a2.getHora())) // Ordenadas por hora
                .forEach(System.out::println);
    }

    public void listarTodasLasAlarmas() {
        if (listaAlarmas.isEmpty()) {
            System.out.println("No hay alarmas registradas.");
            return;
        }
        listaAlarmas.forEach(System.out::println);
    }

    // Funcionalidad Avanzada 1: Modo Vacaciones
    public void setModoVacaciones(boolean activo) {
        this.modoVacaciones = activo;
        System.out.println(activo ? "🌴 Modo Vacaciones ACTIVADO. Las alarmas no sonarán." 
                                  : "⏰ Modo Vacaciones DESACTIVADO. Calendario normal restaurado.");
    }

    public boolean isModoVacaciones() { return modoVacaciones; }

    // Funcionalidad Avanzada 2: Reto Matemático para Apagar
    public void dispararAlarma(Alarma alarma, Scanner scanner) {
        if (modoVacaciones || !alarma.isActiva()) {
            System.out.println("La alarma no puede sonar (Modo vacaciones o alarma desactivada).");
            return;
        }

        System.out.println("\n💥 🔔 !!! RIIIIING !!! 🔔 💥");
        System.out.println("Alarma: " + alarma.getEtiqueta() + " | Categoría: " + alarma.getCategoria());
        System.out.println("Reproduciendo: '" + alarma.getSonido() + "' al " + alarma.getVolumen() + "% de volumen.");
        System.out.println("--------------------------------------------------");
        
        // Ejecución del Reto Matemático Obligatorio para desactivar
        ejecutarRetoMatematico(scanner);

        // Menú de post-desactivación de sonido
        System.out.println("\nReto superado. ¿Qué deseas hacer con la alarma?");
        System.out.println("1. Detener definitivamente");
        System.out.println("2. Posponer (Snooze)");
        System.out.print("Selección: ");
        int opcion = scanner.nextInt();

        if (opcion == 2) {
            alarma.posponer();
        } else {
            alarma.detener();
        }
    }

    private void ejecutarRetoMatematico(Scanner scanner) {
        Random random = new Random();
        int a = random.nextInt(12) + 4; // Números entre 4 y 15
        int b = random.nextInt(12) + 4;
        int resultadoCorrecto = a * b;

        System.out.println("🧠 [RETO MATEMÁTICO ACTIVADO] Para demostrar que estás despierto, resuelve:");
        while (true) {
            System.out.print(a + " x " + b + " = ");
            if (scanner.hasNextInt()) {
                int respuesta = scanner.nextInt();
                if (respuesta == resultadoCorrecto) {
                    System.out.println("🎯 ¡Correcto! Identidad humana verificada.");
                    break;
                } else {
                    System.out.println("❌ Incorrecto. ¡Sigue intentándolo para apagar el sonido!");
                }
            } else {
                System.out.println("Por favor, introduce un número válido.");
                scanner.next(); // Limpiar basura del buffer
            }
        }
    }
}