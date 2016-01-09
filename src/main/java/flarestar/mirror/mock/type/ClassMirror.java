package flarestar.mirror.mock.type;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class ClassMirror implements DeclaredType {
    private TypeElement element;
    private List<TypeMirror> typeParameters = null;

    public ClassMirror(TypeElement element) {
        this.element = element;
    }

    @Override
    public Element asElement() {
        return element;
    }

    @Override
    public TypeMirror getEnclosingType() {
        return element.getEnclosingElement().asType();
    }

    @Override
    public List<? extends TypeMirror> getTypeArguments() {
        if (typeParameters == null) {
            typeParameters = new ArrayList<TypeMirror>();
            for (TypeParameterElement argument : element.getTypeParameters()) {
                typeParameters.add(argument.asType());
            }
        }
        return typeParameters;
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.DECLARED;
    }

    @Override
    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return typeVisitor.visitDeclared(this, p);
    }
}
