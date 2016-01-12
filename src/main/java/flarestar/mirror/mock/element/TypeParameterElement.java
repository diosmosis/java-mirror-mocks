package flarestar.mirror.mock.element;

import flarestar.mirror.mock.type.TypeMirrorFactory;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO
 */
public class TypeParameterElement implements javax.lang.model.element.TypeParameterElement {

    private TypeVariable typeVariable;
    private Element genericElement;
    private List<TypeMirror> bounds = null;
    private Name simpleName;

    public TypeParameterElement(TypeVariable typeVariable, Element genericElement) {
        this.typeVariable = typeVariable;
        this.genericElement = genericElement;

        simpleName = new Name(typeVariable.getName());
    }

    public Element getGenericElement() {
        return genericElement;
    }

    public List<? extends TypeMirror> getBounds() {
        if (bounds == null) {
            bounds = new ArrayList<TypeMirror>();
            for (Type bound : typeVariable.getBounds()) {
                bounds.add(TypeMirrorFactory.make(bound, genericElement));
            }
        }
        return bounds;
    }

    public TypeMirror asType() {
        return TypeMirrorFactory.make(this);
    }

    public ElementKind getKind() {
        return ElementKind.TYPE_PARAMETER;
    }

    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return new ArrayList<AnnotationMirror>();
    }

    public <A extends Annotation> A getAnnotation(Class<A> aClass) {
        return null;
    }

    public Set<Modifier> getModifiers() {
        return new HashSet<Modifier>();
    }

    public Name getSimpleName() {
        return simpleName;
    }

    public Element getEnclosingElement() {
        return getGenericElement(); // TODO: not sure if this is correct behavior
    }

    public List<? extends Element> getEnclosedElements() {
        return new ArrayList<Element>();
    }

    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return elementVisitor.visitTypeParameter(this, p);
    }

    public TypeVariable getTypeVariable() {
        return typeVariable;
    }
}
