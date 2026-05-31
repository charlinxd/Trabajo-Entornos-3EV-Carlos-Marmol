import java.time.LocalTime;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        GestorAlarmas gestor = new GestorAlarmas();
        Scanner scanner = new Scanner(System.in);
        
        // Datos de prueba iniciales
        Set<DiaSemana> diasTrabajo = new HashSet<>(DiaSemana.getDiasLaborables());
        gestor.agregarAlarma(new Alarma(LocalTime.of(7, 0), "Despertador Diario", diasTrabajo, "Sonido_Naturaleza.mp3", 80, Categoria.TRABAJO));
        gestor.agregarAlarma(new Alarma(LocalTime.of(9, 30), "Entrenamiento", new HashSet<>(DiaSemana.getFinDeSemana()), "Rock_Enérgico.mp3", 90, Categoria.DEPORTE));

        int opcion;
        do {
            System.out.println("\n=====================================");
            System.out.println("      ⏰ SISTEMA DE ALARMAS OOP ⏰      ");
            System.out.println("=====================================");
            System.out.println("1. Crear nueva Alarma");
            System.out.println("2. Eliminar Alarma");
            System.out.println("3. Activar / Desactivar Alarma");
            System.out.println("4. Consultar Próximas Alarmas Activas");
            System.out.println("5. Ver Todas las Alarmas");
            System.out.println("6. CONMUTAR MODO VACACIONES (Actual: " + (gestor.isModoVacaciones() ? "ON" : "OFF") + ")");
            System.out.println("7. SIMULAR DISPARO DE ALARMA (Prueba de Reto Matemático)");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Introduce hora (0-23): ");
                    int hora = scanner.nextInt();
                    System.out.print("Introduce minuto (0-59): ");
                    int minuto = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Etiqueta / Nombre: ");
                    String etiqueta = scanner.nextLine();

                    System.out.print("Nombre del archivo de sonido: ");
                    String sonido = scanner.nextLine();

                    System.out.print("Volumen (0-100): ");
                    int volumen = scanner.nextInt();

                    System.out.println("Selecciona Categoría (1.TRABAJO, 2.ESTUDIO, 3.DEPORTE, 4.MEDICINA, 5.GENERAL): ");
                    int catOp = scanner.nextInt();
                    Categoria cat = switch(catOp) {
                        case 1 -> Categoria.TRABAJO;
                        case 2 -> Categoria.ESTUDIO;
                        case 3 -> Categoria.DEPORTE;
                        case 4 -> Categoria.MEDICINA;
                        default -> Categoria.GENERAL;
                    };

                    System.out.println("Repetición: 1. Diario | 2. Días Laborables | 3. Fines de Semana | 4. Solo hoy");
                    int repOp = scanner.nextInt();
                    Set<DiaSemana> dias = new HashSet<>();
                    if (repOp == 1) dias.addAll(java.util.Arrays.asList(DiaSemana.values()));
                    else if (repOp == 2) dias.addAll(DiaSemana.getDiasLaborables());
                    else if (repOp == 3) dias.addAll(DiaSemana.getFinDeSemana());

                    gestor.agregarAlarma(new Alarma(LocalTime.of(hora, minuto), etiqueta, dias, sonido, volumen, cat));
                    break;

                case 2:
                    gestor.listarTodasLasAlarmas();
                    System.out.print("Introduce el ID de la alarma a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    if (gestor.eliminarAlarma(idEliminar)) {
                        System.out.println("❌ Alarma eliminada.");
                    } else {
                        System.out.println("No se encontró ninguna alarma con ese ID.");
                    }
                    break;

                case 3:
                    gestor.listarTodasLasAlarmas();
                    System.out.print("Introduce el ID de la alarma a Modificar Estado: ");
                    int idMod = scanner.nextInt();
                    Alarma aMod = gestor.buscarPorId(idMod);
                    if (aMod != null) {
                        aMod.setActiva(!aMod.isActiva());
                        System.out.println("Estado cambiado. Ahora la alarma está: " + (aMod.isActiva() ? "ACTIVA" : "INACTIVA"));
                    } else {
                        System.out.println("ID no encontrado.");
                    }
                    break;

                case 4:
                    gestor.listarAlarmasActivas();
                    break;

                case 5:
                    System.out.println("\n--- Todas las Alarmas en el Sistema ---");
                    gestor.listarTodasLasAlarmas();
                    break;

                case 6:
                    gestor.setModoVacaciones(!gestor.isModoVacaciones());
                    break;

                case 7:
                    gestor.listarTodasLasAlarmas();
                    System.out.print("Introduce el ID de la alarma que quieres simular que está sonando HOY: ");
                    int idSim = scanner.nextInt();
                    Alarma aSim = gestor.buscarPorId(idSim);
                    if (aSim != null) {
                        gestor.dispararAlarma(aSim, scanner);
                    } else {
                        System.out.println("ID inválido.");
                    }
                    break;

                case 0:
                    System.out.println("Saliendo de la aplicación... ¡Buenos días!");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}