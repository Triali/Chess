package server;

import dataAccess.DataAccessException;

import java.util.ArrayList;

public interface AuthTokenDAO
{
    public AuthToken get(String authToken) throws DataAccessException;

    public void insert(String userName) throws DataAccessException;

    public void insert(AuthToken token) throws DataAccessException;

    public void delete(String authToken) throws DataAccessException;

    public void post();

    public ArrayList<AuthToken> getAll();

    public String allToString();



}
