package gp;

public class ProgramClassLoader extends ClassLoader {

    public ProgramClassSimpleJavaFileObject programClassSimpleJavaFileObject = null;

    public ProgramClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        if(programClassSimpleJavaFileObject != null) {
        	byte[] byteCode = programClassSimpleJavaFileObject.byteArrayOutputStream.toByteArray();
            return defineClass(className, byteCode, 0, byteCode.length);
        } else {
            return super.findClass(className);
        }
    }
}
