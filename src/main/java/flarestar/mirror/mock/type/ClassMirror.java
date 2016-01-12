package flarestar.mirror.mock.type;

import flarestar.mirror.mock.element.ClassElement;

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
public class ClassMirror implements DeclaredType, ReflectedClassMirror {
    private TypeElement element;
    private List<TypeMirror> typeParameters = null;

    public ClassMirror(TypeElement element) {
        this.element = element;
    }

    public Element asElement() {
        return element;
    }

    public TypeMirror getEnclosingType() {
        return element.getEnclosingElement().asType();
    }

    public List<? extends TypeMirror> getTypeArguments() {
        if (typeParameters == null) {
            typeParameters = new ArrayList<TypeMirror>();
            for (TypeParameterElement argument : element.getTypeParameters()) {
                typeParameters.add(argument.asType());
            }
        }
        return typeParameters;
    }

    public TypeKind getKind() {
        return TypeKind.DECLARED;
    }

    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return typeVisitor.visitDeclared(this, p);
    }

    public Class<?> getKlass() {
        if (element instanceof ClassElement) {
            return ((ClassElement) element).getKlass();
        } else {
            return null;
        }
    }
}
