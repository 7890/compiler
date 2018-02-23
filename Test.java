import java.lang.reflect.Method;
import java.util.Map;

import com.itranswarp.compiler.*;

//tb/1802

public class Test
{
	//f(x) : float calc(float x)
	static String f_x="Math.pow(x+.1f, 1.23)";

	static String JAVA_SRC = ""
//		+ "package foo;"
//		+ "import java.util.*;"
		+ "public class CalcImpl implements Calc {"
		+ "	public float calc(float x){return (float)"+f_x+";}"
		+ "}"
		+ "";

	public static void main(String[] args) throws Exception
	{
		System.err.println(""+System.currentTimeMillis());
		JavaStringCompiler compiler;
		compiler = new JavaStringCompiler();

		System.err.println(""+System.currentTimeMillis());
		Map<String, byte[]> results = compiler.compile("CalcImpl.java", JAVA_SRC);
		Class<?> impl = compiler.loadClass("CalcImpl", results);

		System.err.println(""+System.currentTimeMillis());
		Object obj = impl.newInstance();

		System.err.println(""+System.currentTimeMillis());
		float f=0;
		int i=0;
		for(i=0;i<100000000;i++)
		{
			f=((Calc)obj).calc(i/1000f);
			if(i%100000==0)
			{
				System.out.println(i+" "+f);
			}
		}//end loop
		System.out.println(""+i);
		System.out.println("done calculating 100000000 floats with on-the-fly method.");
		System.err.println(""+System.currentTimeMillis());
	}//end main()
}//end Test.java
//EOF
