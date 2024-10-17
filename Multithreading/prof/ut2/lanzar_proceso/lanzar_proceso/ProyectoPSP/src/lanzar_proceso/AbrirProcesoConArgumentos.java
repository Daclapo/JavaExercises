package org.ejercicios.prof.ut2.lanzar_proceso.lanzar_proceso.ProyectoPSP.src.lanzar_proceso;

import java.io.IOException;
import java.nio.file.Path;

public class AbrirProcesoConArgumentos {
    public static void main(String[] args) throws IOException, InterruptedException {
        String programPath = "C:\\Program Files (x86)\\Adobe\\Acrobat Reader DC\\Reader\\AcroRd32.exe";
        String filePath = Path.of(System.getProperty("user.dir"), "ProcessBuilder (Java SE 17 & JDK 17).pdf").toString();
        ProcessBuilder processBuilder = new ProcessBuilder(programPath, filePath.toString());
        Process process = processBuilder.start();
        System.out.printf("Lanzado el proceso con PID %d%n", process.pid());
        process.waitFor();
        System.out.printf("El proceso con PID %d terminó con estado %d%n", process.pid(), process.exitValue());
    }
}
