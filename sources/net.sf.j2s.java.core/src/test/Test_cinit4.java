package test;

class Test_cinit4 {
	
	static {
		System.out.println("Test_Cinit4 static init");
	}
	{
		System.out.println("Test_Cinit4 nonstatic init");
	}

	static int j = Test_Cinit.p;
	static Test_Cinit a = new Test_Cinit(j);
	Test_cinit4(int p) {
		System.out.println("j is " + j);
		assert (j == 0);
		System.out.println("p is " + Test_Cinit.p + " "  + p);
		assert(Test_Cinit.p == p);
	}

}