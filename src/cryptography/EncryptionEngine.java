package cryptography;

/**
 * Specifications of an Encryption Engine.
 *
 * @author Daniel Lerps
 * @date 09/02/2014 - 5:52:45 pm
 * @version v1.0
 */
public abstract class EncryptionEngine
{
	/* ---------------------------------------------- *
	 * -------------- Static Constants -------------- *
	 * ---------------------------------------------- */
	
	/**
	 * Alphabet of permitted characters for any encryption system.
	 */
	public static final char[] ALPHABET = 
		{'A', '7', 'b', 'V', 'C', '+', 'd', '.', 'E', '4',
		 'ä', 'f', '0', 'G', ' ', 'h', '[', 'I', '{', 'j',
		 'K', 'ü', 'l', '(', 'M', '?', 'n', 'w', 'O', '*',
		 '5', 'p', 'Ö', 'Q', ',', 'r', '£', 'S', '\'', 't',
		 'U', 'X', 'v', '8', 'W', ';', 'Z', '/', 'Y', '%', 
		 '!', 'z', '-', 'Ä', '3', 'ö', '}', 'Ü', '"', 'a', 
		 'B', '$', 'c', '^', 'D', '>', 'e', '1', 'F', ':', 
		 '6', 'g', '\\', 'H', '@', 'i', '_', 'J', 'y', 'k',
		 'L', 'ß', 'm', '=', 'N', '<', 'o', '9', 'P', '&',
		 '€', 'q', 'z', 'R', ')', 's', '2', 'T', ']', 'u' };
	
	/* ---------------------------------------- *
	 * -------------- Attributes -------------- *
	 * ---------------------------------------- */
	
	private char[] encryptionKey;
	
	/* ------------------------------------------ *
	 * -------------- Constructors -------------- *
	 * ------------------------------------------ */
	
	/**
	 * 
	 * <p>Initialises a new instance of EncryptionEngine with a given key.</p>
	 * 
	 * <p>The key has to be valid. Use validate(char[]) to check.</p>
	 * 
	 * @param key The initial encryption key.
	 * @see EncryptionEngine#validate(char[])
	 */
	public EncryptionEngine(char[] key)
	{
		encryptionKey = validate(key) ? key : new char[0];
	}
	
	/**
	 * 
	 * Initialises a new instance of EncryptionEngine without a key.
	 */
	public EncryptionEngine()
	{
		encryptionKey = new char[0];
	}
	
	/* ------------------------------------------- *
	 * -------------- Final Methods -------------- *
	 * ------------------------------------------- */
	
	/**
	 * Checks if a String is made of valid characters.
	 *
	 * @param toCheck String which will be checked
	 * @return is toCheck valid?
	 */
	public final boolean validate(String toCheck)
	{
		return (toCheck == null) ? false : validate(toCheck.toCharArray());
	}
	
	/**
	 * Checks if a char array is made of valid character values.
	 *
	 * @param toCheck Array which will be checked
	 * @return is toCheck valid?
	 */
	public final boolean validate(char[] toCheck)
	{
		boolean valid = toCheck != null;
		
		if(valid)
		{
			for(int i = 0; i < toCheck.length; i++)
			{
				valid &= isValidChar(toCheck[i]);
			}
		}
		
		return valid;
	}
	
	/* -------------------------------------------- *
	 * -------------- Public Methods -------------- *
	 * -------------------------------------------- */
	
	/**
	 * Checks if a key is set.
	 *
	 * @return Is a key set?
	 */
	public boolean isKeySet()
	{
		return encryptionKey != null && encryptionKey.length > 0;
	}
	
	/**
	 * <p>Changes the key which is used to de- and encrypt messages.</p>
	 * 
	 * <p>Key has to be made of valid characters.</p>
	 *
	 * @param key New encryption key
	 */
	public void setEncryptionKey(char[] key)
	{
		encryptionKey = validate(key) ? key : encryptionKey;
	}
	
	/* ---------------------------------------------- *
	 * -------------- Abstract Methods -------------- *
	 * ---------------------------------------------- */
	
	/**
	 * <p>Encrypts a given plain text message with the current key.</p>
	 *
	 * <p>Only possible if the plain text consists exclusively of valid characters.
	 * Use EncryptionEngine.validate(String) to check.</p>
	 *
	 * @param plainText The unencrypted message
	 * @return cipher
	 * @see EncryptionEngine#validate(String)
	 */
	public abstract String encrypt(String plainText);
	
	/**
	 * <p>Decrypts a given cipher messages with the current key.</p>
	 * 
	 * <p>Only possible if the cipher text consists exclusively of valid 
	 * characters. Use EncryptionEngine.validate(String) to check.</p>
	 *
	 * @param cipherText The encrypted message
	 * @return plain
	 * @see EncryptionEngine#validate(String)
	 */
	public abstract String decrypt(String cipherText);
	
	/* ----------------------------------------------- *
	 * -------------- Protected Methods -------------- *
	 * ----------------------------------------------- */
	
	protected char[] getEncryptionKey()
	{
		return encryptionKey;
	}
	
	/* --------------------------------------------- *
	 * -------------- Private Methods -------------- *
	 * --------------------------------------------- */
	
	private boolean isValidChar(char c)
	{
		boolean valid = false;
		
		for(int i = 0; i < ALPHABET.length; i++)
		{
			//System.out.println(ALPHABET[i] + " vs " + c);
			valid |= ALPHABET[i] == c;
			
			if(valid)
			{
				break;
			}
		}
		
		return valid;
	}
}