package examples.hello;

import core.application.*;

public class HelloWorld extends MagApplication {
	private String message = "";
	
	public HelloWorld () {
		this.message = "Olá Mundo!";
	}

	public void run () {
		System.out.println (this.message);
	}
}
