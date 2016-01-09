package flarestar.mirror.mock.type;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.UnionType;
import java.util.List;

/**
 * TODO
 */
public class UnionMirror implements UnionType {
    // TODO: all list types should be unmodifiable

    private List<TypeMirror> unionTypes;

    public UnionMirror(List<TypeMirror> unionTypes) {
        this.unionTypes = unionTypes;
    }

    @Override
    public List<? extends TypeMirror> getAlternatives() {
        return unionTypes;
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.UNION;
    }

    @Override
    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return typeVisitor.visitUnion(this, p);
    }
}
