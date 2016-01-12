package flarestar.mirror.mock.utils;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

/**
 * TODO
 */
public class MemoryJavaFileObject extends MemoryFileObject implements JavaFileObject {
    private Kind sourceFile;

    public MemoryJavaFileObject(CharSequence name, Kind sourceFile) {
        super(name);
        this.sourceFile = sourceFile;
    }

    public Kind getKind() {
        return sourceFile;
    }

    public boolean isNameCompatible(String s, Kind kind) {
        return true; // TODO unsupported
    }

    public NestingKind getNestingKind() {
        return NestingKind.TOP_LEVEL; // TODO: unsupported
    }

    public Modifier getAccessLevel() {
        return Modifier.PUBLIC; // TODO: unsupported
    }
}
