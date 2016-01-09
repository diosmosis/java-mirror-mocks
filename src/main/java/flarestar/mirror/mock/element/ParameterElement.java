package flarestar.mirror.mock.element;

import flarestar.mirror.mock.type.TypeMirrorFactory;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO
 */
public class ParameterElement implements VariableElement {
    private Class<?> parameterType;
    private MethodElement enclosingElement;
    private List<AnnotationElement> annotationMirrors = null;
    private Name simpleName;

    public ParameterElement(Class<?> parameterType, int argIndex, MethodElement enclosingElement) {
        this.parameterType = parameterType;
        this.enclosingElement = enclosingElement;

        this.simpleName = new Name("arg" + argIndex);
    }

    @Override
    public Object getConstantValue() {
        return null; // TODO: this is for default value I think?
    }

    @Override
    public TypeMirror asType() {
        return TypeMirrorFactory.make(parameterType);
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.PARAMETER;
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        if (annotationMirrors == null) {
            annotationMirrors = new ArrayList<AnnotationElement>();
            for (Annotation annotation : enclosingElement.getMethod().getAnnotations()) {
                this.annotationMirrors.add(ElementFactory.make(annotation, this));
            }
        }
        return annotationMirrors;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> aClass) {
        getAnnotationMirrors(); // make sure the list is initialized

        for (AnnotationElement mirror : annotationMirrors) {
            if (aClass.isInstance(mirror.getAnnotation())) {
                return (A)mirror.getAnnotation();
            }
        }
        return null;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return new HashSet<Modifier>(); // TODO: can't get modifiers through reflection i think
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
