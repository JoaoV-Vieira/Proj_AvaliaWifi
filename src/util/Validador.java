package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validador {

    public static boolean validarNivelSinal(int nivelSinal) {
        return nivelSinal >= -100 && nivelSinal <= 0;
    }

    public static boolean validarVelocidade(double velocidade) {
        return velocidade >= 0 && velocidade <= 1000; // até 1 Gbps
    }

    public static boolean validarBanda(String banda) {
        return banda != null && (banda.equals("2.4GHz") || banda.equals("5GHz"));
    }

    public static boolean validarTextoNaoVazio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static LocalDateTime parseDataHora(String dataHoraStr) throws DateTimeParseException {
        if (dataHoraStr == null || dataHoraStr.trim().isEmpty()) {
            return LocalDateTime.now();
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(dataHoraStr, formatter);
    }

    public static String formatarNivelSinal(int nivelSinal) {
        if (nivelSinal >= -50) {
            return nivelSinal + " dBm (Excelente)";
        } else if (nivelSinal >= -60) {
            return nivelSinal + " dBm (Bom)";
        } else if (nivelSinal >= -70) {
            return nivelSinal + " dBm (Regular)";
        } else {
            return nivelSinal + " dBm (Fraco)";
        }
    }
}
