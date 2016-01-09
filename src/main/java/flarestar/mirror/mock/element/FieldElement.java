package flarestar.mirror.mock.element;

import flarestar.mirror.mock.type.TypeMirrorFactory;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO
 */
public class FieldElement implements VariableElement {

    private Field field;
    private ClassElement enclosingElement;
    private List<AnnotationMirror> annotations = null;

    private Set<Modifier> modifiers = new HashSet<Modifier>();

    private Name simpleName;

    public FieldElement(Field field, ClassElement enclosingElement) {
        this.field = field;
        this.enclosingElement = enclosingElement;

        modifiers = ElementModifiers.getModifiers(field.getModifiers());

        simpleName = new Name(field.getName());
    }

    @Override
    public Object getConstantValue() {
        return null; // TODO: we can't get this from reflection
    }

    @Override
    public TypeMirror asType() {
        return TypeMirrorFactory.make(field.getType());
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.FIELD;
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        if (annotations == null) {
            annotations = new ArrayList<AnnotationMirror>();
            for (Annotation annotation : field.getAnnotations()) {
                annotations.add(ElementFactory.make(annotation, this));
            }
        }
        return annotations;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> aClass) {
        return field.getAnnotation(aClass);
    }

    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    @Override
    public Name getSimpleName() {
        return simpleName;
    }

    @Override
    public Element getEnclosingElement() {
        return enclosingElement;
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
