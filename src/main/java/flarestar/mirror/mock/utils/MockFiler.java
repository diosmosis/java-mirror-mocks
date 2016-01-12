package flarestar.mirror.mock.utils;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */
public class MockFiler implements Filer {

    private Map<CharSequence, MemoryFileObject> createdFiles = new HashMap<CharSequence, MemoryFileObject>();
    private Map<JavaFileManager.Location, String> locations = new HashMap<JavaFileManager.Location, String>();

    public MockFiler(String sourceOutputLocation, String sourcePathLocation) {
        locations.put(StandardLocation.SOURCE_OUTPUT, sourceOutputLocation);
        locations.put(StandardLocation.SOURCE_PATH, sourcePathLocation);
    }

    public JavaFileObject createSourceFile(CharSequence name, Element... originatingElements) throws IOException {
        JavaFileObject fileObject = getFileIfAlreadyCreated(name, JavaFileObject.class);
        if (fileObject != null) {
            return fileObject;
        }

        name = getJavaFilePath(name, ".java");

        MemoryJavaFileObject javaFileObject = new MemoryJavaFileObject(name, JavaFileObject.Kind.SOURCE);
        createdFiles.put(name, javaFileObject);

        return javaFileObject;
    }

    public JavaFileObject createClassFile(CharSequence name, Element... originatingElements) throws IOException {
        JavaFileObject fileObject = getFileIfAlreadyCreated(name, JavaFileObject.class);
        if (fileObject != null) {
            return fileObject;
        }

        name = getJavaFilePath(name, ".class");

        MemoryJavaFileObject javaFileObject = new MemoryJavaFileObject(name, JavaFileObject.Kind.CLASS);
        createdFiles.put(name, javaFileObject);

        return javaFileObject;
    }

    public FileObject createResource(JavaFileManager.Location location, CharSequence pkg, CharSequence relativeName,
                                     Element... originatingElements) throws IOException {
        String path = makeResourcePath(location, pkg.toString(), relativeName.toString());

        MemoryFileObject fileObject = getFileIfAlreadyCreated(path, MemoryFileObject.class);
        if (fileObject != null) {
            return fileObject;
        }

        MemoryFileObject resourceFileObject = new MemoryFileObject(path);
        createdFiles.put(path, resourceFileObject);

        return resourceFileObject;
    }

    public FileObject getResource(JavaFileManager.Location location, CharSequence pkg, CharSequence relativeName)
            throws IOException {
        String path = makeResourcePath(location, pkg.toString(), relativeName.toString());
        return new ResourceFileObject(path);
    }

    public MemoryFileObject getCreatedFile(CharSequence name) {
        return createdFiles.get(name);
    }

    public Collection<CharSequence> getAllCreatedFileNames() {
        return createdFiles.keySet();
    }

    private <T extends FileObject> T getFileIfAlreadyCreated(CharSequence name, Class<T> expectedType) {
        MemoryFileObject fileObject = createdFiles.get(name);
        if (fileObject != null) {
            if (fileObject.getClass() != expectedType) {
                throw new IllegalStateException("File '" + name + "' was already opened as a " +
                    fileObject.getClass().getName() + ".");
            }
        }
        return (T)fileObject;
    }

    private String makeResourcePath(JavaFileManager.Location location, String pkg, String relativeName) {
        return getBasePath(location) + File.separatorChar + pkg.replace('.', File.separatorChar) + File.separatorChar + relativeName;
    }

    private String getBasePath(JavaFileManager.Location location) {
        String basePath = locations.get(location);
        if (basePath == null) {
            throw new IllegalArgumentException("Unsupported JavaFileManager.Location type: " + location.getName());
        }
        return basePath;
    }

    private CharSequence getJavaFilePath(CharSequence name, String suffix) {
        return name.toString().replace('.', File.separatorChar) + suffix;
    }
}
