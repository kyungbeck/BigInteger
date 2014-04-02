
import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

public class BigInteger
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			try
			{
				String input = br.readLine();
				input = input.replace("\t", ""); // remove tab
				input = input.replace(" ", "");	// remove all blanks
				if (input.compareTo("quit") == 0)
				{
					break;
				}
				calculate(input);
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void calculate(String input)
	{
		int state = 0;
		String operand1_sign = "";
		String operand1_value = "";
		String operator = "";
		String operand2_sign = "";
		String operand2_value = "";
		
		// Start of parsing using FSM.
		// StringTokenizer : http://sibnt.net/m/post/view/id/173
		StringTokenizer stok = new StringTokenizer ( input, "/+/-/*", true ); 
		while( stok.hasMoreTokens() )
		{
			String s = stok.nextToken();
			if ( state == 0 && s.matches("[+-]") )
			{
				state = 1;
				operand1_sign = s;
				continue;
			}
			if ( state == 0 && s.matches("[0-9]+") )
			{
				state = 2;
				operand1_sign = "+";
				operand1_value = s;
				continue;
			}			
			// matches : http://pupustory.tistory.com/132
			if ( state == 1 && s.matches ("[0-9]+") )
			{
				state = 3;
				operand1_value = s;
				continue;
			}
			if ( (state == 2 || state == 3) && s.matches("[*+-]") )
			{
				state = 4;
				operator = s;
				continue;
			}
			if ( state == 4 && s.matches("[+-]") )
			{
				state = 5;
				operand2_sign = s;
				continue;
			}
			if ( state == 4 && s.matches("[0-9]+") )
			{
				state = 6;
				operand2_sign = "+";
				operand2_value = s;
				continue;
			}			
			if ( state == 5 && s.matches ("[0-9]+") )
			{
				state = 7;
				operand2_value = s;
				continue;
			}			
		}
		System.out.println( operand1_sign + " " + operand1_value + " " + operator + " " + operand2_sign + " " + operand2_value );
		// End of parsing using FSM.
	}
}