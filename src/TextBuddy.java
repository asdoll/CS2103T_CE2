import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextBuddy {

	static List<String> text = new ArrayList<String>();
	static String fileName = null;
	static Scanner sc = new Scanner(System.in);

	private static final String WRONG_FORMAT_OF_FILE_NAME = "\\/:*?<>|\"";
	private static final String ILLEGAL_FILE_MESSEGE = "pls give correct file name";
	private static final String CREATE_NEW_FILE_MESSAGE = "file not exist. create a new file?(y/n)";
	private static final String CREATE_NEW_FILE_TRUE = "y";
	private static final String CREATE_NEW_FILE_FALSE = "n";
	private static final String CREATE_NEW_FILE_WRONG_COMMAND_MESSAGE = "wrong command";
	private static final String WRONG_FORMAT_OF_COMMAND = "wrong form of instruction";

	private static final String CMD_CONTENT = "command:";

	private static final String CMD_ADD_MESSAGE1 = "added to ";
	private static final String CMD_ADD_MESSAGE2 = " :\"";
	private static final String CMD_ADD_CONTENT = "add";

	private static final String CMD_DELETE_MESSAGE1 = "deleted from ";
	private static final String CMD_DELETE_MESSAGE2 = " :\"";
	private static final String CMD_DELETE_CONTENT = "delete";
	private static final String CMD_DELETE_NULL_INDEX = "null index";

	private static final String CMD_DISPLAY_MESSAGE_EMPTY = " is empty";
	private static final String CMD_DISPLAY_CONTENT = "display";

	private static final String CMD_CLEAR_MESSAGE = "all contents deleted from ";
	private static final String CMD_CLEAR_CONTENT = "clear";

	private static final String CMD_SORT_MESSAGE = " is sorted:";
	private static final String CMD_SORT_CONTENT = "sort";

	private static final String CMD_SEARCH_MESSAGE1 = "the following are found from ";
	private static final String CMD_SEARCH_MESSAGE2 = ":";
	private static final String CMD_SEARCH_NULL_MESSAGE = " is not found";
	private static final String CMD_SEARCH_CONTENT = "search";

	private static final String CMD_EXIT_CONTENT = "exit";

	/**
	 * Check for correctness of file name Print "pls give correct file name" if
	 * file is invalid Give new file name if not valid
	 * 
	 * @param args
	 */

	public static void fileBegin(String[] args) {
		String name = null;
		if (args.length == 0 || args[0].trim().length() == 0
				|| !rightFormatOfName(args[0])) {
			System.out.println(ILLEGAL_FILE_MESSEGE);
			name = sc.nextLine().trim();
			while (name.length() == 0 || !rightFormatOfName(name)) {
				System.out.println(ILLEGAL_FILE_MESSEGE);
				name = sc.nextLine().trim();
			}
		} else
			name = args[0];
		fileName = name;
	}

	/**
	 * Check for the correctness of input file name
	 * 
	 * @param name
	 * @return whether the format is correct
	 */

	public static Boolean rightFormatOfName(String name) {
		if (name.length() == 0)
			return false;
		for (int i = 0; i < name.length(); i++) {
			if (WRONG_FORMAT_OF_FILE_NAME.contains(Character.valueOf(
					name.charAt(i)).toString()))
				return false;
		}
		return true;
	}

	/**
	 * Begin to read the file Read until the end of file and save to List text
	 * 
	 * @param fileName
	 * @throw IOexception
	 */

	public static void readFile(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null)
				text.add(tempString);
			reader.close();
		} catch (IOException e) {
			e.getStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * Check for existence of file given Ask for new the file if not find
	 * 
	 * @throw IOexception
	 * @return whether file is exist after process
	 */

	public static boolean fileExist() {
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println(CREATE_NEW_FILE_MESSAGE);
			String answer = sc.nextLine().trim();
			while ((!answer.toLowerCase().equals(CREATE_NEW_FILE_TRUE))
					&& (!answer.toLowerCase().equals(CREATE_NEW_FILE_FALSE))) {
				System.out.println(CREATE_NEW_FILE_WRONG_COMMAND_MESSAGE);
				answer = sc.nextLine().trim();
			}
			if (answer.toLowerCase().equals(CREATE_NEW_FILE_TRUE)) {
				try {
					file.createNewFile();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return false;
		}
		return true;
	}

	/**
	 * check if the file exists and ask for creating Read the commands Check the
	 * correctness of command and process Save after each command
	 */

	public static void processing() {
		String command = null;
		boolean continues = fileExist();
		do {
			System.out.print(CMD_CONTENT);
			command = sc.nextLine().trim();
			continues = processCommand(command);
			save();
		} while (continues);
	}

	/**
	 * Check the correctness of command process command if correct Print
	 * "wrong form of instruction" if not correct
	 * 
	 * @param cmd
	 * @return correctness of command
	 */

	public static boolean processCommand(String cmd) {
		String[] command = cmd.trim().split(" ", 2);
		int size = command.length;
		switch (command[0].trim().toLowerCase()) {
		case CMD_ADD_CONTENT: {
			if (size != 2)
				System.out.println(WRONG_FORMAT_OF_COMMAND);
			else
				add(command[1].trim());
			break;
		}
		case CMD_DELETE_CONTENT: {
			if (size != 2 || !deleteCheck(command[1].trim()))
				System.out.println(WRONG_FORMAT_OF_COMMAND);
			else
				delete(Integer.valueOf(command[1].trim()));
			break;
		}
		case CMD_DISPLAY_CONTENT: {
			if (size != 1)
				System.out.println(WRONG_FORMAT_OF_COMMAND);
			else
				display(text);
			break;
		}
		case CMD_CLEAR_CONTENT: {
			if (size != 1)
				System.out.println(WRONG_FORMAT_OF_COMMAND);
			else
				clear();
			break;
		}
		case CMD_SORT_CONTENT: {
			if (size != 1)
				System.out.println(WRONG_FORMAT_OF_COMMAND);
			else
				sort();
			break;
		}
		case CMD_SEARCH_CONTENT: {
			if (size != 2)
				System.out.println(WRONG_FORMAT_OF_COMMAND);
			else
				search(command[1].trim());
			break;
		}
		case CMD_EXIT_CONTENT: {
			if (size != 1)
				System.out.println(WRONG_FORMAT_OF_COMMAND);
			else
				return false;
			break;
		}
		default:
			System.out.println(WRONG_FORMAT_OF_COMMAND);
		}
		return true;
	}

	/**
	 * Check if the one to delete is correct format or exist
	 * 
	 * @param index
	 * @return correctness of command
	 */

	private static boolean deleteCheck(String index) {
		int length = index.length();
		for (int i = 0; i < length; i++)
			if (!Character.isDigit(index.charAt(i)))
				return false;
		if (Integer.valueOf(index) == 0)
			return false;
		return true;
	}

	/**
	 * Add things to text print message after added
	 * 
	 * @param thing
	 */

	public static void add(String thing) {
		text.add(thing);
		System.out.println(CMD_ADD_MESSAGE1 + fileName + CMD_ADD_MESSAGE2
				+ thing + "\"");
	}

	/**
	 * Display list by print
	 * 
	 * @param displayFile
	 */

	public static void display(List<String> displayFile) {
		if (displayFile.size() == 0)
			System.out.println(fileName + CMD_DISPLAY_MESSAGE_EMPTY);
		else {
			int index = displayFile.size();
			for (int i = 0; i < index; i++)
				System.out.print(i + 1 + "." + displayFile.get(i) + "\n");
		}
	}

	/**
	 * Delete selected number from text print null if not found print selected
	 * message which are deleted
	 * 
	 * @param number
	 */
	public static void delete(int number) {
		if (number > text.size())
			System.out.println(CMD_DELETE_NULL_INDEX);
		else {
			String temp = text.remove(number - 1);
			System.out.println(CMD_DELETE_MESSAGE1 + fileName
					+ CMD_DELETE_MESSAGE2 + temp + "\"");
		}
	}

	/**
	 * Delete all from text print cleared message
	 * 
	 */

	public static void clear() {
		text.clear();
		System.out.println(CMD_CLEAR_MESSAGE + fileName);
	}

	/**
	 * sort the elements print empty if empty print list if not empty
	 */

	public static void sort() {
		if (text.isEmpty())
			System.out.println(fileName + CMD_DISPLAY_MESSAGE_EMPTY);
		else {
			java.util.Collections.sort(text);
			System.out.println(fileName + CMD_SORT_MESSAGE);
			display(text);
		}
	}

	/**
	 * search the elements in list print list if not empty print not find if not
	 * find
	 * 
	 * @param keyword
	 */

	public static void search(String keyword) {
		List<String> findList = find(keyword);
		if (findList.size() != 0) {
			System.out.println(CMD_SEARCH_MESSAGE1 + fileName
					+ CMD_SEARCH_MESSAGE2);
			display(findList);
		} else
			System.out.println(keyword + CMD_SEARCH_NULL_MESSAGE);
	}

	/**
	 * find the elements in list to put in a list
	 *
	 * @param keyword
	 * @return all elements found in list(null if not found)
	 */

	public static List<String> find(String keyword) {
		List<String> findList = new ArrayList<String>();
		for (int i = 0; i < text.size(); i++) {
			if (text.get(i).contains(keyword)) {
				findList.add(text.get(i));
			}
		}
		return findList;
	}

	/**
	 * autoSave to file after each command
	 * 
	 * @throws IOException
	 */

	public static void save() {
		try {
			FileWriter fileWriter = new FileWriter(fileName, false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWriter);
			for (int i = 0; i < text.size(); i++)
				bufferWritter.write(text.get(i) + "\n");
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 *            check for correctness of file name, then read file Read all
	 *            inputs and process
	 */
	public static void main(String[] args) {
		fileBegin(args);
		readFile(fileName);
		processing();
	}
}
