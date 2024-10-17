package lanzar_proceso;

import java.io.IOException;

public class AbrirProceso {
    public static void main(String[] args) throws IOException, InterruptedException {
    	String path = "C:\\Program Files\\notepad++\\notepad++.exe";
        ProcessBuilder processBuilder = new ProcessBuilder(path);
        Process process = processBuilder.start();
        System.out.printf("Lanzado el proceso con PID %d%n", process.pid());
        process.waitFor();
        System.out.printf("El proceso con PID %d terminó con estado %d%n", process.pid(), process.exitValue());
    }
}