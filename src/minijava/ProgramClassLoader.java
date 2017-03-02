package minijava;

import java.util.HashMap;
import java.util.Map;

public class ProgramClassLoader extends ClassLoader {

    public Map<String, ProgramClassSimpleJavaFileObject> mapProgramClass = new HashMap<>();

    public ProgramClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        ProgramClassSimpleJavaFileObject programClassSimpleJavaFileObject = mapProgramClass.get(className);
        if(programClassSimpleJavaFileObject != null) {
        	byte[] byteCode = programClassSimpleJavaFileObject.byteArrayOutputStream.toByteArray();
            return defineClass(className, byteCode, 0, byteCode.length);
        } else {
            return super.findClass(className);
        }
    }
}
