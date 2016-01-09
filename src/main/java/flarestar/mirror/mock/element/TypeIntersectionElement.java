package flarestar.mirror.mock.element;

import flarestar.mirror.mock.type.TypeMirrorFactory;

import javax.lang.model.element.*;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO
 */
public class TypeIntersectionElement implements TypeElement {
    private final TypeMirror fakeSuperclass;
    private final List<TypeMirror> fakeInterfaces;
    private Element genericElement;

    public TypeIntersectionElement(TypeMirror fakeSuperclass, List<TypeMirror> fakeInterfaces, Element genericElement) {
        this.fakeSuperclass = fakeSuperclass;
        this.fakeInterfaces = fakeInterfaces;
        this.genericElement = genericElement;
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        return new ArrayList<Element>();
    }

    @Override
    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return elementVisitor.visitType(this, p);
    }

    @Override
    public NestingKind getNestingKind() {
        throw new UnsupportedOperationException("Intersected types have no nesting."); // TODO: what is normal mirror API behavior?
    }

    @Override
    public Name getQualifiedName() {
        return null; // TODO: should intersected types have names?
    }

    @Override
    public TypeMirror asType() {
        return TypeMirrorFactory.make(this);
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.OTHER; // TODO: again, no idea if this is correct behavior
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return new ArrayList<AnnotationMirror>();
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> aClass) {
        return null;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return new HashSet<Modifier>();
    }

    @Override
    public Name getSimpleName() {
        return null; // TODO: correct behavior?
    }

    @Override
    public TypeMirror getSuperclass() {
        return fakeSuperclass;
    }

    @Override
    public List<? extends TypeMirror> getInterfaces() {
        return fakeInterfaces;
    }

    @Override
    public List<? extends TypeParameterElement> getTypeParameters() {
        return new ArrayList<TypeParameterElement>();
    }

    @Override
    public Element getEnclosingElement() {
        return genericElement;
    }
}
