package flarestar.mirror.mock.utils;

import flarestar.mirror.mock.element.ElementFactory;
import flarestar.mirror.mock.type.ClassMirror;
import flarestar.mirror.mock.type.PrimitiveMirror;
import flarestar.mirror.mock.type.ReflectedClassMirror;
import flarestar.mirror.mock.type.TypeMirrorFactory;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.*;
import javax.lang.model.util.Types;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class MockTypes implements Types {
    // TODO: make mirror/element constructors package private
    public Element asElement(TypeMirror typeMirror) {
        if (typeMirror instanceof DeclaredType) {
            return ((DeclaredType)typeMirror).asElement();
        }

        throw new IllegalArgumentException("Don't know how to get the associated element for " + typeMirror.getClass().getName());
    }

    public boolean isSameType(TypeMirror typeMirror1, TypeMirror typeMirror2) {
        // there should only be one instance per type since mirror/element creation should go through
        // TypeMirrorFactory/ElementFactory
        return typeMirror1 == typeMirror2;
    }

    public boolean isSubtype(TypeMirror subType, TypeMirror superType) {
        // TODO: this is not accurate, real rules are here: https://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.10
        // I definitely don't want to go through all of those rules unless necessary, though...
        return isAssignable(subType, superType);
    }

    public boolean isAssignable(TypeMirror type, TypeMirror isAssignableTo) {
        if (!(type instanceof ReflectedClassMirror) || !(isAssignableTo instanceof ReflectedClassMirror)) {
            return false;
        }

        Class<?> typeClass = ((ReflectedClassMirror) type).getKlass();
        Class<?> isAssignableToClass = ((ReflectedClassMirror) isAssignableTo).getKlass();

        if (typeClass == null || isAssignableToClass == null) {
            return false;
        }

        return isAssignableToClass.isAssignableFrom(typeClass);
    }

    public boolean contains(TypeMirror typeMirror, TypeMirror typeMirror1) {
        // TODO: https://docs.oracle.com/javase/specs/jls/se6/html/typesValues.html#113460
        throw new UnsupportedOperationException("MockTypes.contains() is unimplemented.");
    }

    public boolean isSubsignature(ExecutableType executableType, ExecutableType executableType1) {
        // TODO: https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.2
        throw new UnsupportedOperationException("MockTypes.contains() is unimplemented.");
    }

    public List<? extends TypeMirror> directSupertypes(TypeMirror typeMirror) {
        if (!(typeMirror instanceof ReflectedClassMirror)) {
            throw new IllegalArgumentException("cannot get direct supertypes of " + typeMirror.getClass());
        }

        Class<?> klass = ((ReflectedClassMirror) typeMirror).getKlass();

        List<TypeMirror> result = new ArrayList<TypeMirror>();

        Class<?> superKlass = klass.getSuperclass();
        if (superKlass != null) {
            result.add(TypeMirrorFactory.make(superKlass));
        }

        for (Class<?> interfaceKlass : klass.getInterfaces()) {
            result.add(TypeMirrorFactory.make(interfaceKlass));
        }

        return result;
    }

    public TypeMirror erasure(TypeMirror typeMirror) {
        // TODO: https://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html#jls-4.6
        throw new UnsupportedOperationException("Types.erasure() is not implemented.");
    }

    public TypeElement boxedClass(PrimitiveType primitiveType) {
        return ElementFactory.make(((PrimitiveMirror)primitiveType).getKlass());
    }

    public PrimitiveType unboxedType(TypeMirror typeMirror) {
        // TODO: there's no separation between primitive types & primitive classes. this may become an issue.
        return (PrimitiveType)typeMirror;
    }

    public TypeMirror capture(TypeMirror typeMirror) {
        // TODO: https://docs.oracle.com/javase/specs/jls/se8/html/jls-5.html#jls-5.1.10
        throw new UnsupportedOperationException("Types.capture() is not implemented.");
    }

    public PrimitiveType getPrimitiveType(TypeKind typeKind) {
        PrimitiveType result = TypeMirrorFactory.makePrimitive(typeKind);
        if (result == null) {
            throw new IllegalArgumentException("TypeKind " + typeKind + " is not a primitive type.");
        }

        return result;
    }

    public NullType getNullType() {
        return (NullType)TypeMirrorFactory.NULL;
    }

    public NoType getNoType(TypeKind typeKind) {
        return (NoType)TypeMirrorFactory.NO_TYPE;
    }

    public ArrayType getArrayType(TypeMirror typeMirror) {
        // TODO: won't work w/ type parameters and other classes like that, but probably should
        if (!(typeMirror instanceof ReflectedClassMirror)) {
            throw new IllegalArgumentException("Don't know how to get ArrayType for " + typeMirror.getClass().getName());
        }

        Class<?> klass = ((ReflectedClassMirror) typeMirror).getKlass();
        Class<?> arrayKlass = Array.newInstance(klass, 0).getClass();

        return (ArrayType)TypeMirrorFactory.make(arrayKlass);
    }

    public WildcardType getWildcardType(TypeMirror extendsBounds, TypeMirror superBound) {
        throw new UnsupportedOperationException("Types.getWildcardType() not implemented.");
    }

    public DeclaredType getDeclaredType(TypeElement typeElement, TypeMirror... typeMirrors) {
        throw new UnsupportedOperationException("Types.getDeclaredType() not implemented.");
    }

    public DeclaredType getDeclaredType(DeclaredType declaredType, TypeElement typeElement, TypeMirror... typeMirrors) {
        throw new UnsupportedOperationException("Types.getDeclaredType() not implemented.");
    }

    public TypeMirror asMemberOf(DeclaredType declaredType, Element element) {
        throw new UnsupportedOperationException("Types.asMemberOf() not implemented.");
    }
}
