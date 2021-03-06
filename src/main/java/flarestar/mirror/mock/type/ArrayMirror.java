package flarestar.mirror.mock.type;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

/**
 * TODO
 */
public class ArrayMirror implements ArrayType {
    private Class<?> klass;

    public ArrayMirror(Class<?> klass) {
        this.klass = klass;
    }

    public TypeMirror getComponentType() {
        return TypeMirrorFactory.make(klass.getComponentType());
    }

    public TypeKind getKind() {
        return TypeKind.ARRAY;
    }

    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return typeVisitor.visitArray(this, p);
    }
}
