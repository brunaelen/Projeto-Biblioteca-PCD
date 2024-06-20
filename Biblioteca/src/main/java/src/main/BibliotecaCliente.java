/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.main;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BibliotecaCliente {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado ao servidor da biblioteca.");
            String request;
            while (true) {
                System.out.println("\n==========\nMENU\n==========\n Listar\n Alugar [nome]\n Devolver [nome]\n Cadastrar [nome]; [author]; [genero]; [numeroDeExemplares]\n Sair\n\nOpção:");
                request = scanner.nextLine();
                if (request.equalsIgnoreCase("Sair")) {
                    break;
                }

                out.println(request);
                String response = in.readLine();
                System.out.println("Servidor: " + response);
            }
        } catch (IOException e) {
            System.out.println("Erro ao se comunicar com o servidor: " + e.getMessage());
        }
    }
}
