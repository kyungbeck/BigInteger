import java.io.*;
import java.util.StringTokenizer;

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
				input = input.replace(" ", "");	// delete all blanks
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
		// reference: http://sibnt.net/m/post/view/id/173
		StringTokenizer stok = new StringTokenizer ( input, "/+/-/*", true ); 
		while( stok.hasMoreTokens() )
		{
			System.out.println( stok.nextToken() );
		}
		// System.out.println("<< calculate 함수에서" + input + "을 계산할 예정입니다 >>");
	}
}