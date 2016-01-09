package flarestar.mirror.mock.element;

import flarestar.mirror.mock.type.TypeMirrorFactory;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * TODO
 */
public class ClassElement implements TypeElement {

    private Class<?> klass;
    private TypeMirror typeMirror;
    private List<Element> enclosedElements = new ArrayList<Element>();
    private List<AnnotationMirror> annotationMirrors = new ArrayList<AnnotationMirror>();
    private List<TypeParameterElement> typeParameterElements = new ArrayList<TypeParameterElement>();
    private List<TypeMirror> interfaces = new ArrayList<TypeMirror>();
    private TypeMirror extendedType;

    private Name qualifiedName;
    private Name simpleName;

    private Set<Modifier> modifiers;

    public ClassElement(Class<?> klass) {
        this.klass = klass;
        this.typeMirror = TypeMirrorFactory.make(klass);

        for (Class<?> innerClass : klass.getDeclaredClasses()) {
            this.enclosedElements.add(new ClassElement(innerClass));
        }

        for (Field field : klass.getDeclaredFields()) {
            this.enclosedElements.add(ElementFactory.make(field, this));
        }

        for (Method method : klass.getDeclaredMethods()) {
            this.enclosedElements.add(ElementFactory.make(method, this));
        }

        for (Annotation annotation : klass.getAnnotations()) {
            this.annotationMirrors.add(ElementFactory.make(annotation, this));
        }

        for (TypeVariable element : klass.getTypeParameters()) {
            this.typeParameterElements.add(ElementFactory.make(element, this));
        }

        for (Class<?> klassInterface : klass.getInterfaces()) {
            this.interfaces.add(TypeMirrorFactory.make(klassInterface));
        }

        this.extendedType = TypeMirrorFactory.make(klass.getSuperclass());

        qualifiedName = new Name(klass.getName());
        simpleName = new Name(klass.getSimpleName());

        modifiers = ElementModifiers.getModifiers(klass.getModifiers());
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        return enclosedElements;
    }

    @Override
    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return elementVisitor.visitType(this, p);
    }

    @Override
    public NestingKind getNestingKind() {
        if (klass.isAnonymousClass()) {
            return NestingKind.ANONYMOUS;
        } else if (klass.isLocalClass()) {
            return NestingKind.LOCAL;
        } else if (klass.getDeclaringClass() != null) {
            return NestingKind.MEMBER;
        } else {
            return NestingKind.TOP_LEVEL;
        }
    }

    @Override
    public Name getQualifiedName() {
        return qualifiedName;
    }

    @Override
    public TypeMirror asType() {
        return typeMirror;
    }

    @Override
    public ElementKind getKind() {
        if (klass.isEnum()) {
            return ElementKind.ENUM;
        } else if (klass.isAnnotation()) {
            return ElementKind.ANNOTATION_TYPE;
        } else if (klass.isInterface()) {
            return ElementKind.INTERFACE;
        } else {
            return ElementKind.CLASS;
        }
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return annotationMirrors;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> aClass) {
        return klass.getAnnotation(aClass);
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
    public TypeMirror getSuperclass() {
        return extendedType;
    }

    @Override
    public List<? extends TypeMirror> getInterfaces() {
        return interfaces;
    }

    @Override
    public List<? extends TypeParameterElement> getTypeParameters() {
        return typeParameterElements;
    }

    @Override
    public Element getEnclosingElement() {
        Class<?> enclosingClass = klass.getEnclosingClass();
        if (enclosingClass != null) {
            return ElementFactory.make(enclosingClass);
        }

        return ElementFactory.make(klass.getPackage());
    }

    public Class<?> getKlass() {
        return klass;
    }
}
