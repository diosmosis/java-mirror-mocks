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
    private List<Element> enclosedElements = null;
    private List<AnnotationMirror> annotationMirrors = null;
    private List<TypeParameterElement> typeParameterElements = null;
    private List<TypeMirror> interfaces = null;

    private Name qualifiedName;
    private Name simpleName;

    private Set<Modifier> modifiers;

    public ClassElement(Class<?> klass) {
        this.klass = klass;

        qualifiedName = new Name(klass.getName());
        simpleName = new Name(klass.getSimpleName());

        modifiers = ElementModifiers.getModifiers(klass.getModifiers());
    }

    public List<? extends Element> getEnclosedElements() {
        if (enclosedElements == null) {
            this.enclosedElements = new ArrayList<Element>();

            for (Class<?> innerClass : klass.getDeclaredClasses()) {
                this.enclosedElements.add(ElementFactory.make(innerClass));
            }

            for (Field field : klass.getDeclaredFields()) {
                this.enclosedElements.add(ElementFactory.make(field, this));
            }

            for (Method method : klass.getDeclaredMethods()) {
                this.enclosedElements.add(ElementFactory.make(method, this));
            }
        }
        return enclosedElements;
    }

    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return elementVisitor.visitType(this, p);
    }

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

    public Name getQualifiedName() {
        return qualifiedName;
    }

    public TypeMirror asType() {
        return TypeMirrorFactory.make(klass);
    }

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

    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        if (annotationMirrors == null) {
            this.annotationMirrors = new ArrayList<AnnotationMirror>();
            for (Annotation annotation : klass.getAnnotations()) {
                this.annotationMirrors.add(ElementFactory.make(annotation, this));
            }

        }

        return annotationMirrors;
    }

    public <A extends Annotation> A getAnnotation(Class<A> aClass) {
        return klass.getAnnotation(aClass);
    }

    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    public Name getSimpleName() {
        return simpleName;
    }

    public TypeMirror getSuperclass() {
        return TypeMirrorFactory.make(klass.getSuperclass());
    }

    public List<? extends TypeMirror> getInterfaces() {
        if (interfaces == null) {
            this.interfaces = new ArrayList<TypeMirror>();
            for (Class<?> klassInterface : klass.getInterfaces()) {
                this.interfaces.add(TypeMirrorFactory.make(klassInterface));
            }
        }
        return interfaces;
    }

    public List<? extends TypeParameterElement> getTypeParameters() {
        if (typeParameterElements == null) {
            typeParameterElements = new ArrayList<TypeParameterElement>();
            for (TypeVariable element : klass.getTypeParameters()) {
                this.typeParameterElements.add(ElementFactory.make(element, this));
            }
        }
        return typeParameterElements;
    }

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
