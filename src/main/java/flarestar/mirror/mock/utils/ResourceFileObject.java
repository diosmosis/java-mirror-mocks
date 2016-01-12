package flarestar.mirror.mock.utils;

import javax.tools.FileObject;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.Scanner;

/**
 * TODO
 */
public class ResourceFileObject implements FileObject {
    private URL resourceLocator;
    private String resourceName;

    public ResourceFileObject(String name) {
        resourceName = name;
        resourceLocator = getClass().getClassLoader().getResource(name);
    }

    public URI toUri() {
        try {
            return resourceLocator.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return resourceName;
    }

    public InputStream openInputStream() throws IOException {
        return resourceLocator.openStream();
    }

    public OutputStream openOutputStream() throws IOException {
        throw new UnsupportedOperationException("Writing to a ResourceFileObject is not allowed.");
    }

    public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
        if (ignoreEncodingErrors) {
            decoder.onMalformedInput(CodingErrorAction.IGNORE);
        }

        return new BufferedReader(new InputStreamReader(openInputStream(), decoder));
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        Reader reader = openReader(ignoreEncodingErrors);
        try {
            Scanner scanner = new Scanner(reader).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } finally {
            reader.close();
        }
    }

    public Writer openWriter() throws IOException {
        throw new UnsupportedOperationException("Writing to a ResourceFileObject is not allowed.");
    }

    public long getLastModified() {
        String filename = resourceLocator.getFile().replace('/', File.separatorChar);
        try {
            filename = URLDecoder.decode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return new File(filename).lastModified();
    }

    public boolean delete() {
        return false; // unsupported
    }
}
