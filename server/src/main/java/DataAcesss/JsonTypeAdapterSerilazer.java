package DataAcesss;

import chess.ChessPosition;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class JsonTypeAdapterSerilazer implements JsonSerializer<ChessPosition>
{

    @Override
    public JsonElement serialize(ChessPosition chessPosition, Type type, JsonSerializationContext jsonSerializationContext)
    {
        return new JsonPrimitive(chessPosition.toString());
    }
}
