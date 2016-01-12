package flarestar.mirror.mock.type;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.WildcardType;

/**
 * TODO
 */
public class WildcardMirror implements WildcardType {
    private java.lang.reflect.WildcardType type;

    public WildcardMirror(java.lang.reflect.WildcardType type) {
        this.type = type;
    }

    public TypeMirror getExtendsBound() {
        return null; // TODO: unimplemented
    }

    public TypeMirror getSuperBound() {
        return null; // TODO: unimplemented
    }

    public TypeKind getKind() {
        return TypeKind.WILDCARD;
    }

    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return typeVisitor.visitWildcard(this, p);
    }
}
