package com.ti2.ex4;

import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.ChatCompletionsOptions;
import com.azure.ai.inference.models.ChatRequestMessage;
import com.azure.ai.inference.models.ChatRequestSystemMessage;
import com.azure.ai.inference.models.ChatRequestUserMessage;
import com.azure.ai.inference.models.StreamingChatCompletionsUpdate;
import com.azure.ai.inference.models.StreamingChatChoiceUpdate;
import com.azure.ai.inference.models.StreamingChatResponseMessageUpdate;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.CoreUtils;
import com.azure.core.util.IterableStream;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public final class App {
    public static void main(String[] args) {
        String endpoint = "https://ai-15226311334ai600581803063.services.ai.azure.com/models";
        String model = "DeepSeek-R1";

        ChatCompletionsClient client = new ChatCompletionsClientBuilder()
            .credential(new AzureKeyCredential("8SsRO3WDRLd4FZo9woDm4FFSGpdgvCgVEuQkSaesGXlJoQUNlpsWJQQJ99BEACHYHv6XJ3w3AAAAACOGkuDb"))
            .endpoint(endpoint)
            
            .buildClient();

        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage("You are a helpful assistant."));
        chatMessages.add(new ChatRequestUserMessage("Estou começando a estudar para concursos públicos. Quais são as melhores estratégias gerais para iniciar?"));
        chatMessages.add(new ChatRequestUserMessage("Quais são os melhores assuntos para estudar primeiro?"));
        chatMessages.add(new ChatRequestUserMessage("Como montar um cronograma de estudos eficiente para concursos públicos?"));
        chatMessages.add(new ChatRequestUserMessage("Quais são os erros mais comuns de quem está começando a estudar para concursos e como evitá-los?"));
        chatMessages.add(new ChatRequestUserMessage("É melhor estudar sozinho ou fazer cursinho preparatório?"));
        chatMessages.add(new ChatRequestUserMessage("Como lidar com a ansiedade e manter a motivação durante a preparação para concursos?"));
        chatMessages.add(new ChatRequestUserMessage("Quais materiais e recursos você recomenda para quem está iniciando?"));

        ChatCompletionsOptions chatCompletionsOptions = new ChatCompletionsOptions(chatMessages);
        chatCompletionsOptions.setMaxTokens(2048);
        chatCompletionsOptions.setModel(model);

        IterableStream<StreamingChatCompletionsUpdate> chatCompletionsStream = client.completeStream(
            chatCompletionsOptions);

        String outputDir = "output";
        String outputFile = outputDir + "/chat_output.txt";

        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            Path outputPath = Paths.get(outputDir);
            if (!Files.exists(outputPath)) {
                Files.createDirectories(outputPath);
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile, false))) {
                final boolean[] inThinkBlock = {false};
                chatCompletionsStream
                    .stream()
                    .forEach(chatCompletions -> {
                        if (CoreUtils.isNullOrEmpty(chatCompletions.getChoices())) {
                            return;
                        }

                        StreamingChatChoiceUpdate choice = chatCompletions.getChoices().get(0);
                        StreamingChatResponseMessageUpdate delta = choice.getDelta();

                        if (delta.getContent() != null) {
                            String currentChunk = delta.getContent();
                            StringBuilder processingBuffer = new StringBuilder(currentChunk);

                            while (processingBuffer.length() > 0) {
                                if (inThinkBlock[0]) {
                                   
                                    int thinkEndTagIndex = processingBuffer.indexOf("</think>");
                                    if (thinkEndTagIndex != -1) {
                                        inThinkBlock[0] = false;
                                        processingBuffer.delete(0, thinkEndTagIndex + "</think>".length());
                                    } else {
                                        processingBuffer.setLength(0);
                                    }
                                } else {
                                    int thinkStartTagIndex = processingBuffer.indexOf("<think>");
                                    if (thinkStartTagIndex != -1) {
                                        String contentBeforeThink = processingBuffer.substring(0, thinkStartTagIndex);
                                        if (!contentBeforeThink.isEmpty()) {
                                            writer.print(contentBeforeThink);
                                            System.out.print(contentBeforeThink);
                                        }
                                        inThinkBlock[0] = true; 
                                        processingBuffer.delete(0, thinkStartTagIndex + "<think>".length());
                                    } else {
                                        String contentToProcess = processingBuffer.toString();
                                        if (!contentToProcess.isEmpty()){
                                            writer.print(contentToProcess);
                                            System.out.print(contentToProcess);
                                        }
                                        processingBuffer.setLength(0); 
                                    }
                                }
                            }
                        }
                    });
                System.out.println("\n\nOutput saved to: " + outputFile);
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}