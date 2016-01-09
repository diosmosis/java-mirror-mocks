package flarestar.mirror.mock.type;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;

/**
 * TODO
 */
public class TypeVarMirror implements TypeVariable {
    private java.lang.reflect.TypeVariable<?> typeVariable;
    private TypeParameterElement element;
    private TypeMirror upperBounds = null;

    public TypeVarMirror(java.lang.reflect.TypeVariable<?> typeVariable, TypeParameterElement element) {
        this.typeVariable = typeVariable;
        this.element = element;
    }

    @Override
    public Element asElement() {
        return element;
    }

    @Override
    public TypeMirror getUpperBound() {
        if (upperBounds == null) {
            this.upperBounds = TypeMirrorFactory.makeIntersected(typeVariable.getBounds(), element);
        }
        return upperBounds;
    }

    @Override
    public TypeMirror getLowerBound() {
        return TypeMirrorFactory.NULL;
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.TYPEVAR;
    }

    @Override
    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return typeVisitor.visitTypeVariable(this, p);
    }
}
