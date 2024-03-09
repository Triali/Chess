package DataAcesss;

import chess.ChessPosition;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class JsonTypeAdapterDeserializer implements JsonDeserializer<ChessPosition>
{

    @Override
    public ChessPosition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        String string = jsonElement.getAsString();
//        System.out.println("string "+jsonElement.getAsString());
        return new ChessPosition(string.charAt(1),string.charAt(3));
//        return null;
    }
}
