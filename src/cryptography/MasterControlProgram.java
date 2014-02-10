package cryptography;

/**
 *
 *
 * @author Daniel Lerps
 * @date 10/02/2014 - 12:14:14 am
 * @version v1.0
 */
public class MasterControlProgram
{
	/* ---------------------------------------------- *
	 * -------------- Static Constants -------------- *
	 * ---------------------------------------------- */
	
	private final static String MODE_DECRYPT_SHORT = "-d";
	private final static String MODE_DECRYPT = "--decrypt";
	private final static String MODE_ENCRYPT_SHORT = "-e";
	private final static String MODE_ENCRYPT = "--encrypt";
	private final static String MODE_HELP_SHORT = "-h";
	private final static String MODE_HELP = "--help";
	private final static String MODE_HELP_ALT = "-?";
	
	private final static String HELP_TXT = "Playfair Plus Encryption System\n"
			+ "-================================================-\n\n"
			+ "--encrypt [OPTION] [KEY] [MESSAGE]\n\n"
			+ "-e, --encrypt\t\tEncrypt a plain text message with Playfair Plus\n"
			+ "-d, --decrypt\t\tDecrypt a cipher text message with Playfair PLus";
	
	private final static String ERROR_TXT = "Please provide 3 arguments (Mode + Key + Message).\n"
			+ "Use option --help for more help!";
	
	/* -------------------------------------------- *
	 * -------------- Static Methods -------------- *
	 * -------------------------------------------- */
	
	public static void main(String[] args)
	{
		if(args.length == 3)
		{
			if(args[0].equals(MODE_DECRYPT)  || args[0].equals(MODE_DECRYPT_SHORT))
			{
				EncryptionEngine crypto = new PlayfairPlusEngine();
				crypto.setEncryptionKey(args[1].toCharArray());
				
				System.out.println("\n" + crypto.decrypt(args[2]));
			}
			
			if(args[0].equals(MODE_ENCRYPT) || args[0].equals(MODE_ENCRYPT_SHORT))
			{
				EncryptionEngine crypto = new PlayfairPlusEngine();
				crypto.setEncryptionKey(args[1].toCharArray());
				
				System.out.println("\n" + crypto.encrypt(args[2]));
			}
		}
		else if(args.length == 1 && (args[0].equals(MODE_HELP) || args[0].equals(MODE_HELP_SHORT) ||
				args[0].equals(MODE_HELP_ALT)))
		{
			System.out.println(HELP_TXT);
		}
		else
		{
			System.out.println(ERROR_TXT);
		}
	}
}