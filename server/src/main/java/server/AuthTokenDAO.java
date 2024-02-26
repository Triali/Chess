package server;

interface AuthTokenDAO
{
    public AuthToken get(String authToken);

    public void insert(String userName);

    public void delete(String authToken);

    public void post();



}
