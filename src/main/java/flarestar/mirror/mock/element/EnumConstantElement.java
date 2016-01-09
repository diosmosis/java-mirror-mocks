package flarestar.mirror.mock.element;

import flarestar.mirror.mock.type.TypeMirrorFactory;

import javax.lang.model.element.*;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO
 */
public class EnumConstantElement implements VariableElement {
    private Object value;

    public EnumConstantElement(Object value) {
        this.value = value;
    }

    @Override
    public Object getConstantValue() {
        return value;
    }

    @Override
    public TypeMirror asType() {
        return TypeMirrorFactory.make(value.getClass());
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.ENUM_CONSTANT;
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
        return null;
    }

    @Override
    public Element getEnclosingElement() {
        return null; // TODO: should we return something here?
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        return new ArrayList<Element>();
    }

    @Override
    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return elementVisitor.visitVariable(this, p);
    }
}
