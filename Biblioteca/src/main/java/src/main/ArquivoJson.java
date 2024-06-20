/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ArquivoJson {
    private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static final String ARQUIVO = "src/main/resources/livros.json";

    public static List<Livro> lerLivros() throws IOException {

        File file = new File(ARQUIVO);

        LivrosContainer livrosContainer = objectMapper.readValue(file, LivrosContainer.class);

        return livrosContainer.getLivros();
    }

    public static void escreverLivros(List<Livro> livros) throws IOException {

        LivrosContainer livrosContainer = new LivrosContainer();
        livrosContainer.setLivros(livros);

        objectMapper.writeValue(new File(ARQUIVO), livrosContainer);
    }

    static class LivrosContainer {
        private List<Livro> livros;

        public List<Livro> getLivros() {
            return livros;
        }

        public void setLivros(List<Livro> livros) {
            this.livros = livros;
        }
    }
}
