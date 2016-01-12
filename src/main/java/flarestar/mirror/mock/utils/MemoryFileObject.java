package flarestar.mirror.mock.utils;

import javax.tools.FileObject;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.Scanner;

/**
 * TODO
 */
public class MemoryFileObject implements FileObject {

    private String path;

    private static class ByteArrayOutputStreamWithAccess extends ByteArrayOutputStream {
        public byte[] getBuffer() {
            return buf;
        }
    }

    private ByteArrayOutputStreamWithAccess stream = new ByteArrayOutputStreamWithAccess();

    public MemoryFileObject(CharSequence path) {
        this.path = path.toString();
    }

    public URI toUri() {
        return new File(path).toURI();
    }

    public String getName() {
        return path;
    }

    public InputStream openInputStream() throws IOException {
        return new ByteArrayInputStream(stream.getBuffer());
    }

    public OutputStream openOutputStream() throws IOException {
        return stream;
    }

    public Reader openReader(boolean ignoreEncodingErrors) throws IOException { // TODO: code redundancy w/ ResourceFileObject
        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
        if (ignoreEncodingErrors) {
            decoder.onMalformedInput(CodingErrorAction.IGNORE);
        }

        return new BufferedReader(new InputStreamReader(openInputStream(), decoder));
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException { // TODO: code redundancy w/ ResourceFileObject
        Reader reader = openReader(ignoreEncodingErrors);
        try {
            Scanner scanner = new Scanner(reader).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } finally {
            reader.close();
        }
    }

    public Writer openWriter() throws IOException {
        return new OutputStreamWriter(stream);
    }

    public long getLastModified() {
        return 0; // unsupported
    }

    public boolean delete() {
        return false; // unsupported
    }
}
