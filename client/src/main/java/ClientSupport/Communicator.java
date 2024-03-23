package ClientSupport;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.util.Scanner;

public class Communicator
{

    public String doGet(String urlString,String token) throws IOException
    {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");

        // Set HTTP request headers, if necessary
         connection.addRequestProperty("Accept", "text/html");
         connection.addRequestProperty("authorization",token);

        connection.connect();

        if (isSuccessful(connection.getResponseCode()))
        {

            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");

            InputStream responseBody = connection.getInputStream();
            return inputStreamtoString(responseBody);
            // Read and process response body from InputStream ...
        } else
        {
            // SERVER RETURNED AN HTTP ERROR

            InputStream responseBody = connection.getErrorStream();
            return inputStreamtoString(responseBody);
            // Read and process error response body from InputStream ...
        }
    }

    public String doPost(String urlString,String req,String token) throws IOException
    {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.addRequestProperty("Content-Type","application/json");
        connection.setDoOutput(true);

        // Set HTTP request headers, if necessary
         connection.addRequestProperty("Accept", "text/html");

        if(token !=null){
            connection.addRequestProperty("authorization", token);
        }

        if (req != null)
        {
            try (OutputStream requestBody = connection.getOutputStream())
            {
                requestBody.write(req.getBytes());
            }
        }

        connection.connect();
        if (isSuccessful(connection.getResponseCode()))
        {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");

            InputStream responseBody = connection.getInputStream();
            return inputStreamtoString(responseBody);
            // Read response body from InputStream ...
        } else
        {
            // SERVER RETURNED AN HTTP ERROR

            InputStream responseBody = connection.getErrorStream();
            return inputStreamtoString(responseBody);
            // Read and process error response body from InputStream ...
        }
    }

    public String doDelete(String urlString,String token) throws IOException
    {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);

        connection.setRequestMethod("DELETE");

        if(token !=null){
            connection.addRequestProperty("authorization", token);
        }

        connection.connect();

        if (isSuccessful(connection.getResponseCode()))
        {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");

            InputStream responseBody = connection.getInputStream();
            return inputStreamtoString(responseBody);
            // Read response body from InputStream ...
        } else
        {
            // SERVER RETURNED AN HTTP ERROR

            InputStream responseBody = connection.getErrorStream();
            return inputStreamtoString(responseBody);
            // Read and process error response body from InputStream ...
        }
    }

    public String doPut(String urlString,String req,String token) throws IOException
    {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("PUT");
        connection.addRequestProperty("Content-Type","application/json");
        connection.setDoOutput(true);

        // Set HTTP request headers, if necessary
        connection.addRequestProperty("Accept", "text/html");
        if(token !=null){
            connection.addRequestProperty("authorization", token);
        }


        if (req != null)
        {
            try (OutputStream requestBody = connection.getOutputStream();)
            {
                requestBody.write(req.getBytes());
            }
        }
        connection.connect();
        if (isSuccessful(connection.getResponseCode()))
        {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");

            InputStream responseBody = connection.getInputStream();
            return inputStreamtoString(responseBody);
            // Read response body from InputStream ...
        } else
        {
            // SERVER RETURNED AN HTTP ERROR

            InputStream responseBody = connection.getErrorStream();
            return inputStreamtoString(responseBody);
            // Read and process error response body from InputStream ...
        }
    }

    private String inputStreamtoString(InputStream is){
        Scanner sc = new Scanner(is);
        StringBuffer sb = new StringBuffer();
        //Appending each line to the buffer
        while(sc.hasNext()) {
            sb.append(" "+sc.nextLine());
        }
        return sb.toString();
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}
