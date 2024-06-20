/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class BibliotecaServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);
            while (true) {
                try {
                    Socket clienteSocket = serverSocket.accept();
                    new Thread(new ManipuladorDeCliente(clienteSocket)).start();
                } catch (IOException e) {
                    System.out.println("Erro ao conectar com o cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }
}
