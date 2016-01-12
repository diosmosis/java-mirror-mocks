package flarestar.mirror.mock.type;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;

/**
 * TODO
 */
public class PrimitiveMirror implements PrimitiveType, ReflectedClassMirror {
    private Class<?> klass;

    public Class<?> getKlass() {
        return klass;
    }

    public PrimitiveMirror(Class<?> klass) {
        this.klass = klass;
    }

    public TypeKind getKind() {
        if (klass == Boolean.class) {
            return TypeKind.BOOLEAN;
        } else if (klass == Byte.class) {
            return TypeKind.BYTE;
        } else if (klass == Short.class) {
            return TypeKind.SHORT;
        } else if (klass == Integer.class) {
            return TypeKind.INT;
        } else if (klass == Long.class) {
            return TypeKind.LONG;
        } else if (klass == Character.class) {
            return TypeKind.CHAR;
        } else if (klass == Float.class) {
            return TypeKind.FLOAT;
        } else if (klass == Double.class) {
            return TypeKind.DOUBLE;
        } else if (klass == Void.class) {
            return TypeKind.VOID;
        } else {
            throw new IllegalStateException("Invalid class in PrimitiveMirror: " + klass.getName());
        }
    }

    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return typeVisitor.visitPrimitive(this, p);
    }
}
