import java.util.Arrays;
import java.util.List;

public enum DiaSemana {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO;

    public static List<DiaSemana> getFinDeSemana() {
        return Arrays.asList(SABADO, DOMINGO);
    }

    public static List<DiaSemana> getDiasLaborables() {
        return Arrays.asList(LUNES, MARTES, MIERCOLES, JUEVES, VIERNES);
    }
}