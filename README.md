# Servidor de Biblioteca com Sockets

Este projeto implementa um sistema de servidor-cliente em Java 17 para controlar um registro de livros de uma biblioteca. O servidor gerencia as seguintes funcionalidades através de comunicação via sockets:

- Listagem dos livros cadastrados na biblioteca.
- Aluguel e devolução de livros.
- Cadastro de novos livros.

## Estrutura do Projeto

O projeto está estruturado da seguinte forma:

- `src/`: Contém o código-fonte Java.
  - `main/`: Pacote principal.
    - `BibliotecaCliente.java`: Cliente que se conecta ao servidor.
    - `BibliotecaServer.java`: Servidor que gerencia as requisições dos clientes.
    - `GerenciadorDeEmprestimos.java`: Classe para gerenciar empréstimos e devoluções.
    - `ManipuladorDeCliente.java`: Thread para manipular cada conexão de cliente no servidor.
    - `ArquivoJson.java`: Classe para leitura e escrita de livros em formato JSON.
    - `Livro.java`: Classe que representa um livro com seus atributos.

## Tecnologias Utilizadas

- Java 17
- JSON (para armazenamento dos livros)
- Sockets (para comunicação cliente-servidor)

## Funcionamento do Projeto

1. **Execução do Servidor:**
   - O servidor (`BibliotecaServer.java`) é iniciado na porta especificada (`12345` por padrão).
   - Aguarda conexões de clientes para manipular suas requisições em threads separadas.

2. **Execução do Cliente:**
   - O cliente (`BibliotecaCliente.java`) se conecta ao servidor (`localhost:12345`).
   - Oferece um menu interativo para o usuário realizar operações como listar, cadastrar, alugar e devolver livros.

3. **Persistência de Dados:**
   - Os livros são armazenados em um arquivo JSON (`livros.json`), que serve como base de dados da biblioteca.
   - As operações de cadastro, aluguel e devolução são refletidas diretamente neste arquivo JSON.
