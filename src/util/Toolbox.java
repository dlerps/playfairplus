package util;

/**
 *
 *
 * @author Daniel Lerps
 * @date 09/02/2014 - 11:01:20 pm
 * @version v0.1
 */
public class Toolbox
{
	/* ------------------------------------------ *
	 * -------------- Constructors -------------- *
	 * ------------------------------------------ */
	
	// no objects permitted!
	private Toolbox(){}
	
	/* -------------------------------------------- *
	 * -------------- Static Methods -------------- *
	 * -------------------------------------------- */
	
	/**
	 * Prints an array of chars to the console.
	 *
	 * @param arr Array of characters
	 */
	public static void printArray(char[] arr)
	{
		if(arr != null && arr.length > 0)
		{
			StringBuilder printOut = new StringBuilder(1 + arr.length * 2)
				.append('|');
			
			for(char c : arr)
			{
				printOut.append(c)
						.append('|');
			}
			
			System.out.println(printOut);
		}
	}
	
	/**
	 * Prints an array of char arrays to the console.
	 *
	 * @param arr Array of char arrays
	 */
	public static void printArray(char[][] arr)
	{
		if(arr != null && arr.length > 0)
		{
			for(char[] c : arr)
			{
				printArray(c);
			}
		}
	}
}