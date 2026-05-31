import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Alarma {
    private static int contadorId = 1;

    private final int id;
    private LocalTime hora;
    private String etiqueta;
    private boolean activa;
    private Set<DiaSemana> diasRepeticion;
    private String sonido;
    private int volumen; // 0 a 100
    private int minutosSnooze;
    private Categoria categoria;

    // Constructor principal
    public Alarma(LocalTime hora, String etiqueta, Set<DiaSemana> diasRepeticion, 
                  String sonido, int volumen, Categoria categoria) {
        this.id = contadorId++;
        this.hora = hora;
        this.etiqueta = etiqueta;
        this.activa = true; // Por defecto nacen activas
        this.diasRepeticion = diasRepeticion != null ? diasRepeticion : new HashSet<>();
        this.sonido = sonido;
        setVolumen(volumen);
        this.minutosSnooze = 5; // 5 minutos por defecto
        this.categoria = categoria != null ? categoria : Categoria.GENERAL;
    }

    // Lógica de Negocio
    public void posponer() {
        if (activa) {
            this.hora = this.hora.plusMinutes(minutosSnooze);
            System.out.println("⏰ Alarma [" + etiqueta + "] pospuesta " + minutosSnooze + " minutos. Nueva hora: " + hora);
        }
    }

    public void detener() {
        System.out.println("🛑 Alarma [" + etiqueta + "] detenida.");
        // Aquí se podría restaurar la hora original si fue pospuesta, por simplicidad la dejamos en su estado actual.
    }

    // Getters y Setters con validaciones
    public int getId() { return id; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    
    public String getEtiqueta() { return etiqueta; }
    public void setEtiqueta(String etiqueta) { this.etiqueta = etiqueta; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

    public Set<DiaSemana> getDiasRepeticion() { return diasRepeticion; }
    public void setDiasRepeticion(Set<DiaSemana> diasRepeticion) { this.diasRepeticion = diasRepeticion; }

    public String getSonido() { return sonido; }
    public void setSonido(String sonido) { this.sonido = sonido; }

    public int getVolumen() { return volumen; }
    public void setVolumen(int volumen) {
        if (volumen < 0 || volumen > 100) throw new IllegalArgumentException("El volumen debe estar entre 0 y 100");
        this.volumen = volumen;
    }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s | Estado: %s | Días: %s | Sonido: %s (Vol: %d%%) | Cat: %s",
                id, hora, etiqueta, (activa ? "ACTIVA" : "INACTIVA"), 
                diasRepeticion.isEmpty() ? "Solo hoy" : diasRepeticion, sonido, volumen, categoria);
    }
}