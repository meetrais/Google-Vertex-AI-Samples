package palm.workshop;

import dev.langchain4j.model.vertexai.VertexAiChatModel;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;

public class ChatPrompts {
    public static void main(String[] args){
        //SimpleChat();

        //PromptAndChat();

        PromptEngineering();
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

    static class Person {
        String name;
        int age;
        String education;
        String interest;
        String achievement;
    }

    interface PersonExtractor {
        @UserMessage("""
            Extract the name and age of the person described below.
            Return a JSON document with a "name", "age", "education", 
            "interest", "achievement" properties, \
            following this structure: {"name": "John Doe", "age": 34,
            "education":"Computer Science", "interest":"reading books",
            "achievement":"award in the field"}
            Return only JSON, without any markdown markup surrounding it.
            Here is the document describing the person:
            ---
            {{it}}
            ---
            JSON: 
            """)
            Person extractPerson(String text);
    }

    private static void PromptEngineering()
    {
        VertexAiChatModel model = VertexAiChatModel.builder()
        .endpoint("us-central1-aiplatform.googleapis.com:443")
        .project("palm-workshop-project")
        .location("us-central1")
        .publisher("google")
        .modelName("chat-bison@001")
        .maxOutputTokens(300)
        .maxRetries(3)
        .build();

        PersonExtractor extractor = AiServices.create(PersonExtractor.class, model);

        Person person = extractor.extractPerson("""
            Anna is a 23 year old artist based in Brooklyn, New York. She was born and 
            raised in the suburbs of Chicago, where she developed a love for art at a 
            young age. She attended the School of the Art Institute of Chicago, where 
            she studied painting and drawing. After graduating, she moved to New York 
            City to pursue her art career. Anna's work is inspired by her personal 
            experiences and observations of the world around her. She often uses bright 
            colors and bold lines to create vibrant and energetic paintings. Her work 
            has been exhibited in galleries and museums in New York City and Chicago.    
            """
        );

        System.out.println("Name: " + person.name);
        System.out.println("Age: " + person.age);
        System.out.println("Education:" + person.education);
        System.out.println("Interest: " + person.interest);
        System.out.println("Achievement: " + person.achievement);




    }


}

