package client;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

public class ClientController {
    public static void main(String[] args){
        String host = "localhost";
        String ObjLookUp = "FileServer";

        Client client = new Client(host, ObjLookUp);

        Scanner in = new Scanner(System.in);
        String[] commandList = {"read", "create", "edit", "delete", "copy", "move", "printFileInfo"};
        Method[] methods = client.getClass().getMethods();

        //user can keep to input method in loop.
        while (true){
            System.out.println("\nCommand list:");
            for (String comm: commandList){
                System.out.print(comm + " ");
            }
            System.out.println("\nPlease Input the command (input \"quit\" to exit):");

            String commandLine = in.nextLine();
            String [] commArr = commandLine.split("\\s+");

            // lead to no such method when input "   ". Without this, it may occurs error.
            if (commArr.length == 0){
                commArr = new String[]{"noInvoke"};
            }

            if (commArr[0].equals("quit")){
                System.out.println("Quitting...");
                break;
            }

            boolean findCorMethod = false;
            for (Method m: methods){
                // Find method
                if (m.getName().equals(commArr[0])){
                    findCorMethod = true;
                    int numOfArg = m.getParameterCount();
                    try {
                        // invoke method by reflection.
                        String[] arguments = Arrays.copyOfRange(commArr, 1, numOfArg + 1);
                        m.invoke(client, arguments);
                    }catch (Exception e){
                        System.out.println("Error occurs at invoking the method.");
                        for (String item: commArr){
                            System.out.println(item);
                        }
                        e.printStackTrace();
                    }
                }
            }

            if (!findCorMethod){
                System.out.println("Command not Found.");
            }

        }

    }
}
