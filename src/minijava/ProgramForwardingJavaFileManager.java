package minijava;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import java.io.IOException;

public class ProgramForwardingJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
	private ProgramClassLoader programClassLoader;
    private ProgramClassSimpleJavaFileObject programClassSimpleJavaFileObject;

	protected ProgramForwardingJavaFileManager(JavaFileManager javaFileManager, ProgramClassSimpleJavaFileObject programClassSimpleJavaFileObject, ProgramClassLoader programClassLoader) {
    	super(javaFileManager);
    	this.programClassSimpleJavaFileObject = programClassSimpleJavaFileObject;
        this.programClassLoader = programClassLoader;
        this.programClassLoader.mapProgramClass.put(programClassSimpleJavaFileObject.getName(), programClassSimpleJavaFileObject);
	}

	@Override
	public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, Kind kind, FileObject fileObject) throws IOException {
    	return programClassSimpleJavaFileObject;
	}

	@Override
	public ClassLoader getClassLoader(Location location) {
    	return programClassLoader;
	}
}
