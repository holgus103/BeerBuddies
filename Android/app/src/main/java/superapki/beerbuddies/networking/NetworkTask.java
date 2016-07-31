package superapki.beerbuddies.networking;

import org.json.JSONArray;

/**
 * Created by Kuba on 31/07/2016.
*/
public abstract class NetworkTask<T>{
    public enum TaskType{
        REGISTER
    }
    protected TaskType type;
    public abstract T callbackMethod(JSONArray arr);
    public TaskType getType(){
        return this.type;
    }
}