package palm.workshop;

import dev.langchain4j.model.vertexai.VertexAiChatModel;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

public class ChatPrompts {
    public static void main(String[] args){
        //SimpleChat();

        PromptAndChat();
    }

    private static void SimpleChat()
    {
        VertexAiChatModel model = VertexAiChatModel.builder()
        .endpoint("us-central1-aiplatform.googleapis.com:443")
        .project("palm-workshop-project")
        .location("us-central1")
        .publisher("google")
        .modelName("chat-bison@001")
        .maxOutputTokens(400)
        .maxRetries(3)
        .build();

        ConversationalChain chain = ConversationalChain.builder()
            .chatLanguageModel(model)
            .build();

            String message = "What are large language models?";
            String answer = chain.execute(message);
            System.out.println(answer);
    
            System.out.println("---------------------------");
    
            message = "What can you do with them?";
            answer = chain.execute(message);
            System.out.println(answer);
    
            System.out.println("---------------------------");
    
            message = "Can you name some of them?";
            answer = chain.execute(message);
            System.out.println(answer);
    }

    private static void PromptAndChat()
    {
        VertexAiChatModel model = VertexAiChatModel.builder()
        .endpoint("us-central1-aiplatform.googleapis.com:443")
        .project("palm-workshop-project")
        .location("us-central1")
        .publisher("google")
        .modelName("chat-bison@001")
        .maxOutputTokens(7)
        .maxRetries(3)
        .build();

        InMemoryChatMemoryStore chatMemoryStore = new InMemoryChatMemoryStore();

        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
            .chatMemoryStore(chatMemoryStore)
            .maxMessages(200)
            .build();
        
        chatMemory.add(SystemMessage.from("""
            You are an expert chess player with high ELO ranking.
            Use the PNG chess notation to reply with the best next possible move.
            """
        ));

        ConversationalChain chain = ConversationalChain.builder()
            .chatLanguageModel(model)
            .chatMemory(chatMemory)
            .build();
        
        String pgn="";
        String[] whiteMoves = { "Nf3", "c4", "Nc3", "e3", "Dc2", "Cd5"};
        for (int i = 0; i < whiteMoves.length; i++) {
            pgn += " " + (i+1) + ". " + whiteMoves[i];
            System.out.println("Playing " + whiteMoves[i]);
            pgn = chain.execute(pgn);
            System.out.println(pgn);
        }
        

    }
}
