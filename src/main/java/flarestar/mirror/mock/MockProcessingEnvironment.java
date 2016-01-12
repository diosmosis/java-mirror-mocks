package flarestar.mirror.mock;

import flarestar.mirror.mock.utils.MockElements;
import flarestar.mirror.mock.utils.MockFiler;
import flarestar.mirror.mock.utils.MockMessager;
import flarestar.mirror.mock.utils.MockTypes;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * TODO
 */
public class MockProcessingEnvironment implements ProcessingEnvironment {
    private MockMessager messager = new MockMessager();
    private MockFiler filer;
    private MockElements elements = new MockElements();
    private MockTypes types = new MockTypes();

    private SourceVersion sourceVersion;
    private Locale locale;

    public MockProcessingEnvironment(String testResourceRoot) {
        this(testResourceRoot, SourceVersion.latestSupported(), Locale.getDefault());
    }

    public MockProcessingEnvironment(String testResourceRoot, SourceVersion sourceVersion, Locale locale) {
        this.sourceVersion = sourceVersion;
        this.locale = locale;
        this.filer = new MockFiler(testResourceRoot + File.separatorChar + "src", testResourceRoot + File.separatorChar + "output");
    }

    public Map<String, String> getOptions() {
        return new HashMap<String, String>();
    }

    public Messager getMessager() {
        return messager;
    }

    public MockMessager getMockMessager() { return messager; }

    public Filer getFiler() {
        return filer;
    }

    public MockFiler getMockFiler() {
        return filer;
    }

    public Elements getElementUtils() {
        return elements;
    }

    public MockElements getMockElementUtils() {
        return elements;
    }

    public Types getTypeUtils() {
        return types;
    }

    public MockTypes getMockTypeUtils() {
        return types;
    }

    public SourceVersion getSourceVersion() {
        return sourceVersion;
    }

    public Locale getLocale() {
        return locale;
    }
}
