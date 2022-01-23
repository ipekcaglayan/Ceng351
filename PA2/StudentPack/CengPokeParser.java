import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class CengPokeParser {

	public static ArrayList<CengPoke> parsePokeFile(String filename)
	{
		ArrayList<CengPoke> pokeList = new ArrayList<CengPoke>();

		// You need to parse the input file in order to use GUI tables.
		// TODO: Parse the input file, and convert them into CengPokes

		return pokeList;
	}
	
	public static void startParsingCommandLine() throws IOException
	{
		// TODO: Start listening and parsing command line -System.in-.

		Scanner sc = new Scanner(System.in);
		boolean isQuit = false;
		while (!isQuit && sc.hasNextLine()){
			String commandlineStr = sc.nextLine();
			String [] params = commandlineStr.split("\t");
			String command = params[0];
			switch(command) {
				case "add":
					CengPoke poke = new CengPoke(Integer.parseInt(params[1]), params[2], params[3], params[4]);
//					System.out.println("Created poke:");
//					System.out.println("CenkPoke("+ poke.pokeKey()+ ", "+poke.pokeName()+", "+ poke.pokePower()+", "+poke.pokeType()+")");
					CengPokeKeeper.addPoke(poke);
					break;
				case "delete":
					CengPokeKeeper.deletePoke(Integer.parseInt(params[1]));
					break;
				case "search":
					CengPokeKeeper.searchPoke(Integer.parseInt(params[1]));
					break;
				case "print":
					CengPokeKeeper.printEverything();
					break;
				default: // quit
					isQuit = true;
			}
			//add	4	Charizard	200	Fire
		}

		// There are 5 commands:
		// 1) quit : End the app. Print nothing, call nothing.
		// 2) add : Parse and create the poke, and call CengPokeKeeper.addPoke(newlyCreatedPoke).
		// 3) search : Parse the pokeKey, and call CengPokeKeeper.searchPoke(parsedKey).
		// 4) delete: Parse the pokeKey, and call CengPokeKeeper.removePoke(parsedKey).
		// 5) print : Print the whole hash table with the corresponding buckets, call CengPokeKeeper.printEverything().

		// Commands (quit, add, search, print) are case-insensitive.
	}
}
