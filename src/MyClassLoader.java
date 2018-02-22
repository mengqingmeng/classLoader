import java.io.*;

/**
 * 自定义java类加载器来实现java类的热加载
 */
public class MyClassLoader extends ClassLoader {
    //要加载的java类的classpath
    private String classpath;

    public MyClassLoader(String classpath) {
        super(ClassLoader.getSystemClassLoader());
        this.classpath = classpath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //return super.findClass(name);
        byte[] data = this.loadClassData(name);

        return this.defineClass(name,data,0,data.length);
    }

    /**
     * 加载class文件中的内容
     * @param name
     * @return
     */
    private byte[] loadClassData(String name) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream =null;
        try {
            name = name.replace(".","//");
            fileInputStream = new FileInputStream(new File(classpath+name+".class "));
            int b = 0;
            while ((b=fileInputStream.read())!=-1){
                byteArrayOutputStream.write(b);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return byteArrayOutputStream.toByteArray();
    }
}
