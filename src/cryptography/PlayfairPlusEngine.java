package cryptography;

import java.util.Set;
import java.util.HashSet;

/**
 *
 *
 * @author Daniel Lerps
 * @date 09/02/2014 - 10:07:44 pm
 * @version v1.1
 */
public class PlayfairPlusEngine extends EncryptionEngine
{
	/* --------------------------------------- *
	 * -------------- Constants -------------- *
	 * --------------------------------------- */
	
	private final String ERR_ILLEGAL_MSG = "Error: Illegal Cipherformat";
	
	/* ---------------------------------------- *
	 * -------------- Attributes -------------- *
	 * ---------------------------------------- */
	
	private char[][] square;
	
	/* ------------------------------------------------ *
	 * -------------- Overriding Methods -------------- *
	 * ------------------------------------------------ */
	
	@Override
	public void setEncryptionKey(char[] key)
	{
		if(validate(key))
		{
			super.setEncryptionKey(key);
			buildSquare(key);
		}
	}
	
	/* ------------------------------------------------ *
	 * -------------- Overriding Methods -------------- *
	 * ------------------------------------------------ */
	
	@Override
	public String encrypt(String plainText)
	{
		return transform(plainText, true);
	}
	
	@Override
	public String decrypt(String cipherText)
	{
		return transform(cipherText, false);
	}
	
	/* --------------------------------------------- *
	 * -------------- Private Methods -------------- *
	 * --------------------------------------------- */
	
	private String transform(String msg, boolean encrypt)
	{
		StringBuilder transformation = new StringBuilder();
		
		// 9 to avoid negative modulo
		int offset = encrypt ? 1 : 9;
		
		if(isKeySet() && (validate(msg) || !encrypt))
		{
			if(!encrypt)
			{
				// add space which might have been stripped at the beginning
				if(msg.charAt(2) != '|')
				{
					msg = " " + msg;
				}
				
				int delimiterCounter = 0;
				
				// check for delimiters
				for(char cipherChar : msg.toCharArray())
				{
					if((delimiterCounter++ % 3) == 2 && cipherChar != '|')
					{
						return ERR_ILLEGAL_MSG;
					}
				}
				
				// too short!
				if(delimiterCounter != 2)
				{
					return ERR_ILLEGAL_MSG;
				}
				
				msg = msg.replaceAll("\\|", "");
				
				if(!validate(msg))
				{
					return ERR_ILLEGAL_MSG;
				}
			}
			else
			{
				msg = msg.length() % 2 == 0 ? msg : msg + " ";
			}
			
			for(int i = 0; i < msg.length(); i += 2)
			{
				CharPosition plainPos1 = getCharacterPosition(msg.charAt(i));
				CharPosition plainPos2 = getCharacterPosition(msg.charAt(i + 1));
				CharPosition cipherPos1 = new CharPosition();
				CharPosition cipherPos2 = new CharPosition();
				
				// matching rows
				if(plainPos1.row() == plainPos2.row())
				{
					cipherPos1.setRow(plainPos1.row());
					cipherPos2.setRow(plainPos1.row());
					cipherPos1.setColumn((plainPos1.column() + offset) % 10);
					cipherPos2.setColumn((plainPos2.column() + offset) % 10);
				}
				// matching columns
				else if(plainPos1.column() == plainPos2.column())
				{
					cipherPos1.setRow((plainPos1.row() + offset) % 10);
					cipherPos2.setRow((plainPos2.row() + offset) % 10);
					cipherPos1.setColumn(plainPos1.column());
					cipherPos2.setColumn(plainPos2.column());
				}
				else
				{
					cipherPos2.setColumn(plainPos1.column());
					cipherPos1.setColumn(plainPos2.column());
					cipherPos2.setRow(plainPos2.row());
					cipherPos1.setRow(plainPos1.row());
				}
				
				if(encrypt && i > 0)
				{
					transformation.append('|');
				}
				
				transformation.append(square[cipherPos1.row()][cipherPos1.column()]);
				transformation.append(square[cipherPos2.row()][cipherPos2.column()]);
			}
		}
		
		return transformation.toString();
	}
	
	private void buildSquare(char[] key)
	{
		int keyIndex = 0;
		int alphaIndex = 0;
		
		Set<Character> charBuffer = new HashSet<Character>(key.length);
		
		square = new char[10][10];
		
		for(int row = 0; row < 10; row++)
		{
			for(int col = 0; col < 10; col++)
			{
				// take from alphabet
				if(keyIndex >= key.length - 1)
				{
					while(charBuffer.contains(ALPHABET[alphaIndex]))
					{
						alphaIndex++;
					}
					
					square[row][col] = ALPHABET[alphaIndex++];
				}
				// take from key...
				else
				{
					if(!charBuffer.contains(key[keyIndex]))
					{
						square[row][col] = key[keyIndex];
						charBuffer.add(key[keyIndex]);
					}
					// ...or not
					else
					{
						while(charBuffer.contains(ALPHABET[alphaIndex]))
						{
							alphaIndex++;
						}
						
						square[row][col] = ALPHABET[alphaIndex++];
					}
					
					keyIndex++;
				}
			}
		}
	}
	
	private CharPosition getCharacterPosition(char c)
	{
		CharPosition pos = new CharPosition();
		
		for(int row = 0; row < 10; row++)
		{
			for(int column = 0; column < 10; column++)
			{
				if(square[row][column] == c)
				{
					pos.setColumn(column).setRow(row);
					break;
				}
			}
		}
		
		return pos;
	}
	
	/* ------------------------------------------- *
	 * -------------- Inner Classes -------------- *
	 * ------------------------------------------- */
	
	private final class CharPosition
	{
		private int row;
		private int column;
		
		public CharPosition()
		{
			row = 0;
			column = 0;
		}
		
		public CharPosition setRow(int charRow)
		{
			row = (charRow >= 0) ? charRow : row;
			return this;
		}
		
		public CharPosition setColumn(int charColumn)
		{
			column = (charColumn >= 0) ? charColumn : row;
			return this;
		}
		
		public int column()
		{
			return column;
		}
		
		public int row()
		{
			return row;
		}
	}
}