import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SeekAndDestroy
{
    public static String CR = "\r";
    public static String LF = "\n";
    public static int controlPort = 6000;
    public static int dataPort = 53462;
    public static String host = "localhost";
    String command;
    Socket clientSocket;
    ServerSocket clientDataSocket;

    public static void main(String[] args)
    {
        SeekAndDestroy sad = new SeekAndDestroy();
        try
        {
            sad.sendUserName("bilkent");
            sad.sendPass("cs421");
            sad.clientDataSocket = new ServerSocket(dataPort);
            sad.sendPort(dataPort);
            //sad.nlst();
            sad.quit();

        }
        catch (Exception ioe)
        {
            ioe.printStackTrace();
            System.out.println("Connection Failure");
        }
    }
    // Default Constructor
    public SeekAndDestroy()
    {
        try {
            clientSocket = new Socket(host, controlPort);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Commands
     * */
    private void sendUserName(String username)
    {
        command = "USER" + " " + username + CR + LF;
        sendStringToPort(command);
    }
    private void sendPass(String password)
    {
        command = "PASS" + " " + password + CR + LF;
        sendStringToPort(command);
    }
    private void sendPort(int port)
    {
        command = "PORT" + " " + port + CR + LF;
        sendStringToPort(command);
    }
    private void nlst()
    {
        command = "NLST" + CR + LF;
        sendStringToPort(command);
    }
    private void cwd(String child)
    {
        command = "CWD" + child + CR + LF;
        sendStringToPort(command);
    }
    private void cdup()
    {
        command = "CDUP" + CR + LF;
        sendStringToPort(command);
    }
    private void retry(String filename)
    {
        command = "RETR" + filename + CR + LF;
        sendStringToPort(command);
    }
    private void delete(String filename)
    {
        command = "DELE" + filename + CR + LF;
        sendStringToPort(command);
    }
    private void quit()
    {
        command = "QUIT" + CR + LF;
        sendStringToPort(command);
    }
    private InputStream sendStringToPort(String str)
    {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(clientSocket.getOutputStream()
                            , "UTF-8");
            outputStreamWriter.write(str, 0, str.length()); // Send command
            outputStreamWriter.flush();

            return clientSocket.getInputStream();
            /*InputStreamReader inputStreamReader =
                    new InputStreamReader(clientSocket.getInputStream());
            BufferedReader bufferedReader =
                    new BufferedReader(inputStreamReader);

            System.out.println(bufferedReader.readLine());*/

        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
