package flarestar.mirror.mock.element;

import flarestar.mirror.mock.type.TypeMirrorFactory;

import javax.lang.model.element.*;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * TODO
 */
public class MethodElement implements ExecutableElement {

    private Method method;
    private ClassElement enclosingElement;

    private List<TypeParameterElement> typeParameters = new ArrayList<TypeParameterElement>();
    private List<VariableElement> parameters = new ArrayList<VariableElement>();
    private List<TypeMirror> thrownTypes = new ArrayList<TypeMirror>();
    private AnnotationValue defaultValue = null;

    private List<AnnotationMirror> annotationMirrors = new ArrayList<AnnotationMirror>();

    private Set<Modifier> modifiers;

    private Name simpleName;

    public MethodElement(Method method, ClassElement enclosingElement) {
        this.method = method;
        this.enclosingElement = enclosingElement;

        modifiers = ElementModifiers.getModifiers(method.getModifiers());

        simpleName = new Name(method.getName());

        for (TypeVariable<Method> typeVariable : method.getTypeParameters()) {
            typeParameters.add(ElementFactory.make(typeVariable, this));
        }

        int i = 0;
        for (Class<?> parameterType : method.getParameterTypes()) {
            parameters.add(ElementFactory.makeParameter(parameterType, i, this));

            ++i;
        }

        for (Class<?> thrownType : method.getExceptionTypes()) {
            thrownTypes.add(TypeMirrorFactory.make(thrownType));
        }

        for (Annotation annotation : method.getAnnotations()) {
            this.annotationMirrors.add(ElementFactory.make(annotation, this));
        }

        Object defaultValue = method.getDefaultValue();
        if (defaultValue != null) {
            this.defaultValue = new AnnotationValueImpl(defaultValue);
        }
    }

    @Override
    public List<? extends TypeParameterElement> getTypeParameters() {
        return typeParameters;
    }

    @Override
    public TypeMirror getReturnType() {
        return TypeMirrorFactory.make(method.getReturnType());
    }

    @Override
    public List<? extends VariableElement> getParameters() {
        return parameters;
    }

    @Override
    public boolean isVarArgs() {
        return method.isVarArgs();
    }

    @Override
    public List<? extends TypeMirror> getThrownTypes() {
        return thrownTypes;
    }

    @Override
    public AnnotationValue getDefaultValue() {
        return defaultValue;
    }

    @Override
    public TypeMirror asType() {
        return TypeMirrorFactory.make(this);
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.METHOD;
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return annotationMirrors;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> aClass) {
        return method.getAnnotation(aClass);
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
        return new ArrayList<Element>(); // TODO: should return local types & such, but don't think it's possible to get them
    }

    @Override
    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return elementVisitor.visitExecutable(this, p);
    }

    public Method getMethod() {
        return method;
    }
}
