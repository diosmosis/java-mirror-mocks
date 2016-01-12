package flarestar.mirror.mock.type;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;

/**
 * TODO
 */
public class NoTypeMirror implements NoType {
    public TypeKind getKind() {
        return TypeKind.NONE;
    }

    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return typeVisitor.visitNoType(this, p);
    }
}
