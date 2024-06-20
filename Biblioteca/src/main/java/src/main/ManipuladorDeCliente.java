/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;


public class ManipuladorDeCliente implements Runnable {
    private Socket clienteSocket;
    private GerenciadorDeEmprestimos gerenciadorDeEmprestimos = new GerenciadorDeEmprestimos();

    public ManipuladorDeCliente(Socket clienteSocket) {
        this.clienteSocket = clienteSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true)) {

            String request;
            while ((request = in.readLine()) != null) {
                identificadorDeSolicitacao(request, out);
            }
        } catch (IOException e) {
            System.out.println("Erro ao comunicar com o cliente: " + e.getMessage());
        }
    }

    private void identificadorDeSolicitacao(String request, PrintWriter out) {
        try {
            List<Livro> livros = ArquivoJson.lerLivros();

            if (request.equalsIgnoreCase("listar")) {
                listarLivros(out, livros);
            } else if (request.startsWith("alugar")) {
                String nomeLivro = request.substring(7).trim();
                alugarLivro(nomeLivro, out, livros);
            } else if (request.startsWith("devolver")) {
                String nomeLivro = request.substring(9).trim();
                devolverLivro(nomeLivro, out, livros);
            } else if (request.startsWith("cadastrar")) {
                cadastrarLivro(request, out, livros);
            } else {
                out.println("Comando inválido.");
            }
        } catch (IOException e) {
            out.println("Erro ao processar o comando: " + e.getMessage());
        }
    }

    private static void listarLivros(PrintWriter out, List<Livro> livros) {
        out.println(livros);
    }

    private void alugarLivro(String titulo, PrintWriter out, List<Livro> livros) {
        boolean livroEncontrado = false;
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo) && livro.getExemplares() > 0) {
                livro.setExemplares(livro.getExemplares() - 1);
                livroEncontrado = true;
                gerenciadorDeEmprestimos.registrarEmprestimo(titulo);
                break;
            }
        }

        if (livroEncontrado) {
            try {
                ArquivoJson.escreverLivros(livros);
                out.println("Livro alugado com sucesso.");
            } catch (IOException e) {
                out.println("Erro ao atualizar livros: " + e.getMessage());
            }
        } else {
            out.println("Livro não encontrado ou sem exemplares disponíveis.");
        }
    }

    private void devolverLivro(String nomeLivro, PrintWriter out, List<Livro> livros) {
        if (!gerenciadorDeEmprestimos.podeDevolver(nomeLivro)) {
            out.println("O livro não foi emprestado ou já foi devolvido.");
            return;
        }

        boolean livroEncontrado = false;
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(nomeLivro)) {
                livro.setExemplares(livro.getExemplares() + 1);
                livroEncontrado = true;
                gerenciadorDeEmprestimos.registrarDevolucao(nomeLivro);
                break;
            }
        }

        if (livroEncontrado) {
            try {
                ArquivoJson.escreverLivros(livros);
                out.println("Livro devolvido com sucesso.");
            } catch (IOException e) {
                out.println("Erro ao atualizar livros: " + e.getMessage());
            }
        } else {
            out.println("Livro não encontrado.");
        }
    }

    private void cadastrarLivro(String request, PrintWriter out, List<Livro> livros) {
        try {
            String[] parts = request.substring(10).split(";\\s*");

            if (parts.length != 4) {
                out.println("Formato do comando inválido. Use: cadastrar [nome]; [autor]; [genero]; [numeroDeExemplares]");
                return;
            }

            String nome = parts[0].trim();
            String autor = parts[1].trim();
            String genero = parts[2].trim();
            int numeroDeExemplares = Integer.parseInt(parts[3].trim());

            Livro novoLivro = new Livro(autor, nome, genero, numeroDeExemplares);
            livros.add(novoLivro);

            ArquivoJson.escreverLivros(livros);
            out.println("Livro cadastrado com sucesso.");
        } catch (Exception e) {
            out.println("Erro ao cadastrar livro: " + e.getMessage());
        }
    }
}
