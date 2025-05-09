# Projeto TI II EXERCICIO 4

## Pré-requisitos

- Java Development Kit (JDK) versão 24 ou superior.
- Apache Maven 3.6.0 ou superior.

## Dependências Principais

As dependências são gerenciadas pelo Maven e estão listadas no arquivo `pom.xml`. As principais incluem:

- `com.azure:azure-ai-inference`: Para interagir com serviços de inferência de IA da Azure.
- `com.azure:azure-identity`: Para autenticação com serviços Azure.
- `com.azure:azure-core`: Biblioteca principal para SDKs Azure.
- `com.azure:azure-json`: Para manipulação de JSON.
- `com.azure:azure-core-http-netty`: Cliente HTTP Netty para Azure Core.
- `org.codehaus.mojo:exec-maven-plugin`: Para executar a aplicação Java via Maven.

## Como Compilar e Executar

1.  **Compilar o projeto:**
    O Maven compila o projeto automaticamente ao executar o comando para rodar a aplicação. Se precisar compilar separadamente, use:
    ```bash
    mvn compile
    ```

2.  **Executar a aplicação:**
    Para executar a classe principal da aplicação, utilize o seguinte comando no Prompt de Comando, a partir do diretório raiz do projeto:
    ```bash
    mvn exec:java -Dexec.mainClass="com.ti2.ex4.App"
    ```
    Ou se estiver no terminal(powershell):
    ```bash
    mvn exec:java "-Dexec.mainClass=com.ti2.ex4.App"
    ```
    **Atenção:** Este comando assume que a classe principal é `com.ti2.ex4.App`. Se este arquivo foi removido ou alterado, o comando precisará ser ajustado para a nova classe principal.

## Saída do Programa

A saída da interação com a LLM (as respostas às perguntas) será:
1.  Impressa diretamente no console.
2.  Salva no arquivo `output/chat_output.txt` dentro do diretório do projeto. Uma pasta `output` será criada automaticamente se não existir.

## Observações

- Certifique-se de que as configurações de endpoint e credenciais no código estejam corretas para a sua instância do serviço de LLM.
- O console será limpo antes de cada nova saída da LLM ser impressa. 
