package test;

class Testx {
	// example of a second independent top-level class.
	// It will get its own .js file.
}

public class Test_ {

	static boolean isBatch = false;
	
	static public boolean j2sHeadless = true;

	private static int i_ = 0;

	private int t = 0;

	public void test123(int a, int b, int c) {
		
	}
	public int test_int = 3;
	
	public int showt() {
		if (true && (/**@j2sNative 1? test : */false)) {
			
		}
		System.out.println("Test_.showt() " + t);
		if (false || (/**@j2sNative test || */false)) {
			
		}
		return t;
	}
	
	public void setT(int t) {
		this.t = t;
	}

	public String t_test = "test_";

	public String showt2() {
		System.out.println("Test_.showt2() test_.t is " + t + " t_test is " + t_test);
		return t_test;
	}

	static {
		ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
	}

	class InnerClass {
		// A check that inner classes can access outer private methods
		// and fields
		public void testInner() {
			test3();
		}
	}

	private int test3() {
		// Some random tests

		{
			long j = 1;
			int i = 3;
			j &= ~(1L << i);
			j = 1L << i;
			j = 1L << 3;
			j &= 1L << i;
			j = ~i;
			j = ~(1L << i);
		}

		System.out.println("abcde".indexOf(99));
		assert ("test".contentEquals(new StringBuffer("test")));
		int i = "test\2ing".charAt(4);
		switch (i | 'd') {
		case 'f':
			assert (true);
			break;
		case '3':
		case 3:
		default:
			assert (false);
		}
		int y = (/** @j2sNative 1?'s': */
		'\t');
		assert (y == 9 || y == 's');
		int z = (/** @j2sNative 1?2: */
		909 + y);
		assert (z == 918 || z == 2);
		int x = (/** @j2sNative 1?3: */
		909);
		assert (x == 909 || x == 3);
		Object g = "testing";
		Object o = (/** @j2sNative g.mark$ ? g : */
		null);
		assert (o == null || o == g);
		return (/** @j2sNative 1?4: */
		4 + y);
	}

	public Test_() {
		System.out.println("\n\n==============\nTesting " + getClass().getName());
	}
	
	public static void main(String[] args) {

        
		int val = new Test_().test3();
		assert (val == 13 || val == 4);
		isBatch = true;
		
		// get all files loaded
		_test1(args);
		
		long t0 = System.currentTimeMillis();
		
		_test1(args);
		
		System.out.println(System.currentTimeMillis() - t0);
		
		//Test_Zipin.main(args);
		//Test_Zipout.main(args);
		System.out.println("Test_ all tests completed successfully.");
	}

	private static void _test1(String[] args) {
		Test_Anon.main(args);
		Test_Appendable.main(args);
		Test_Array.main(args);
		Test_Assert.main(args);
		Test_Boolean.main(args);
//		Test_Bugs.main(args);
//		Test_Byte.main(args);
//		Test_Bytecode.main(args);
//		Test_Bytecode1.main(args);
//		Test_Bytecode2.main(args);
//		Test_Call.main(args);
//		Test_Cast.main(args);
//		Test_Char.main(args);
//		Test_Cinit.main(args);
//		Test_Cinit2.main(args);
//		Test_Class.main(args);
//		Test_Class_1.main(args);
//		Test_ClassBase.main(args);
		Test_Clone.main(args);
		Test_DivEqual.main(args);
		Test_Double.main(args);
		Test_Enum.main(args);
		Test_Enum2.main(args);
		Test_EPS.main(args);
		Test_Extends_6.main(args);
//		Test_Extends_7.main(args);
		// Test_Extends_JButton.main(args);
		Test_Extends.main(args);
		Test_Field_fails.main(args);
		Test_Field_ok.main(args);
		//Test_File.main(args);
//		Test_Final.main(args);
		Test_Format.main(args);
		Test_Generic_Java8.main(args);
		Test_Generic_Method.main(args);
		Test_GenericAnon.main(args);
		Test_GenericEABIXY2.main(args);
		Test_GenericExt2.main(args);
		Test_Image.main(args);
//		Test_Init.main(args);
		Test_Inner.main(args);
		Test_Instance.main(args);
		Test_Interface.main(args);
		Test_Interface2.main(args);
		Test_Intern.main(args);
		Test_Interval.main(args);
		Test_Ints.main(args);
		Test_J8_Generic2.main(args);
		Test_J8_Stream.main(args);
		Test_Java8.main(args);
		Test_JAXB_Marshall.main(args);
		Test_JAXB_NONE.main(args);
		Test_JAXB_Obj.main(args);
		//failing on unicode char in "somewhatComplex" Test_JAXB_ORDERED.main(args);
//		Test_Map.main(args);
		Test_Math.main(args);
		Test_Native.main(args);
		Test_NoAssert.main(args);
		Test_NoHeadless_1.main(args);
		Test_Or.main(args);
		Test_Precision.main(args);
		Test_Print.main(args);
		Test_Printf.main(args);
		Test_Proxy.main(args);
		Test_Reflect.main(args);
		Test_Reserved.main(args);
		Test_Resource.main(args);
		Test_Sort.main(args);
		Test_Static.main(args);
		Test_String.main(args);
		Test_Var.main(args);
		Test_Vararg.main(args);
		Test_Void.main(args);
		
	}

	public String toString() {
		return "testing " + this.getClass().getName();
	}

}
