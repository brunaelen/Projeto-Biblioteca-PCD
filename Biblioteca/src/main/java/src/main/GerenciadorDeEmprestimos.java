/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.main;

import java.util.HashMap;
import java.util.Map;

public class GerenciadorDeEmprestimos {
    private Map<String, Integer> emprestimos = new HashMap<>();

    public void registrarEmprestimo(String titulo) {
        emprestimos.put(titulo, emprestimos.getOrDefault(titulo, 0) + 1);
    }

    public boolean podeDevolver(String titulo) {
        return emprestimos.getOrDefault(titulo, 0) > 0;
    }

    public void registrarDevolucao(String titulo) {
        if (emprestimos.containsKey(titulo)) {
            int count = emprestimos.get(titulo);
            if (count > 1) {
                emprestimos.put(titulo, count - 1);
            } else {
                emprestimos.remove(titulo);
            }
        }
    }
}
