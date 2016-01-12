package flarestar.mirror.mock.type;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.NullType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;

/**
 * TODO
 */
public class NullMirror implements NullType {
    public TypeKind getKind() {
        return TypeKind.NULL;
    }

    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return typeVisitor.visitNull(this, p);
    }
}
