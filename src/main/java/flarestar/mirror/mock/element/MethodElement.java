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

    private List<TypeParameterElement> typeParameters = null;
    private List<VariableElement> parameters = null;
    private List<TypeMirror> thrownTypes = null;
    private AnnotationValue defaultValue = null;

    private List<AnnotationMirror> annotationMirrors = null;

    private Set<Modifier> modifiers;

    private Name simpleName;

    public MethodElement(Method method, ClassElement enclosingElement) {
        this.method = method;
        this.enclosingElement = enclosingElement;

        modifiers = ElementModifiers.getModifiers(method.getModifiers());

        simpleName = new Name(method.getName());
    }

    public List<? extends TypeParameterElement> getTypeParameters() {
        if (typeParameters == null) {
            typeParameters = new ArrayList<TypeParameterElement>();
            for (TypeVariable<Method> typeVariable : method.getTypeParameters()) {
                typeParameters.add(ElementFactory.make(typeVariable, this));
            }

        }
        return typeParameters;
    }

    public TypeMirror getReturnType() {
        return TypeMirrorFactory.make(method.getReturnType());
    }

    public List<? extends VariableElement> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<VariableElement>();

            int i = 0;
            for (Class<?> parameterType : method.getParameterTypes()) {
                parameters.add(ElementFactory.makeParameter(parameterType, i, this));

                ++i;
            }
        }
        return parameters;
    }

    public boolean isVarArgs() {
        return method.isVarArgs();
    }

    public List<? extends TypeMirror> getThrownTypes() {
        if (thrownTypes == null) {
            thrownTypes = new ArrayList<TypeMirror>();
            for (Class<?> thrownType : method.getExceptionTypes()) {
                thrownTypes.add(TypeMirrorFactory.make(thrownType));
            }
        }
        return thrownTypes;
    }

    public AnnotationValue getDefaultValue() {
        if (defaultValue == null) {
            Object defaultValue = method.getDefaultValue();
            if (defaultValue != null) {
                this.defaultValue = new AnnotationValueImpl(defaultValue);
            }
        }
        return defaultValue;
    }

    public TypeMirror asType() {
        return TypeMirrorFactory.make(this);
    }

    public ElementKind getKind() {
        return ElementKind.METHOD;
    }

    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        if (annotationMirrors == null) {
            annotationMirrors = new ArrayList<AnnotationMirror>();
            for (Annotation annotation : method.getAnnotations()) {
                this.annotationMirrors.add(ElementFactory.make(annotation, this));
            }
        }
        return annotationMirrors;
    }

    public <A extends Annotation> A getAnnotation(Class<A> aClass) {
        return method.getAnnotation(aClass);
    }

    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    public Name getSimpleName() {
        return simpleName;
    }

    public Element getEnclosingElement() {
        return enclosingElement;
    }

    public List<? extends Element> getEnclosedElements() {
        return new ArrayList<Element>(); // TODO: should return local types & such, but don't think it's possible to get them
    }

    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return elementVisitor.visitExecutable(this, p);
    }

    public Method getMethod() {
        return method;
    }
}
