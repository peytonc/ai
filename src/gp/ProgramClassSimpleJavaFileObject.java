package gp;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class ProgramClassSimpleJavaFileObject extends SimpleJavaFileObject {
	public ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	public ProgramClassSimpleJavaFileObject(String className) throws Exception {
		super(new URI(className), Kind.CLASS);
	}

	@Override
	public OutputStream openOutputStream() throws IOException {
		return byteArrayOutputStream;
	}
}
