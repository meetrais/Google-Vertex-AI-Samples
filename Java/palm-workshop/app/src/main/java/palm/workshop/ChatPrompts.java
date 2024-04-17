package palm.workshop;

import dev.langchain4j.model.vertexai.VertexAiChatModel;
import dev.langchain4j.chain.ConversationalChain;

public class ChatPrompts {
    public static void main(String[] args){

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

}
