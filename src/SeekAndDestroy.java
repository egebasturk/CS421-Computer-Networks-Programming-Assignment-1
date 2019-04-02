import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    Socket clientDataSocket;

    public static void main(String[] args)
    {
        SeekAndDestroy sad = new SeekAndDestroy();
        try
        {
            sad.sendUserName("bilkent");
            sad.sendPass("cs421");
            sad.sendPort(dataPort);
            sad.clientDataSocket = new Socket(host, dataPort);
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
    private void sendStringToPort(String str)
    {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(clientSocket.getOutputStream()
                            , "UTF-8");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(clientSocket.getInputStream());
            System.out.println(inputStreamReader);
            outputStreamWriter.write(str, 0, str.length());
            outputStreamWriter.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
