package flarestar.mirror.mock.type;

import flarestar.mirror.mock.element.*;
import flarestar.mirror.mock.element.PackageElement;
import flarestar.mirror.mock.element.TypeParameterElement;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */
public class TypeMirrorFactory {
    public static final TypeMirror NULL = new NullMirror();

    private static final Map<Object, TypeMirror> typeMirrorCache = new HashMap<Object, TypeMirror>();

    static {
        typeMirrorCache.put(null, NULL);

        typeMirrorCache.put(Boolean.class, new PrimitiveMirror(Boolean.class));
        typeMirrorCache.put(Byte.class, new PrimitiveMirror(Byte.class));
        typeMirrorCache.put(Short.class, new PrimitiveMirror(Short.class));
        typeMirrorCache.put(Integer.class, new PrimitiveMirror(Integer.class));
        typeMirrorCache.put(Long.class, new PrimitiveMirror(Long.class));
        typeMirrorCache.put(Character.class, new PrimitiveMirror(Character.class));
        typeMirrorCache.put(Float.class, new PrimitiveMirror(Float.class));
        typeMirrorCache.put(Double.class, new PrimitiveMirror(Double.class));
        typeMirrorCache.put(Void.class, new PrimitiveMirror(Void.class)); // TODO: void should not be a primitive
    }

    public static TypeMirror make(Class<?> klass) {
        if (typeMirrorCache.containsKey(klass)) {
            return typeMirrorCache.get(klass);
        }

        TypeMirror result;

        // we have to handle arrays & ClassMirrors for whatever else
        if (klass.isArray()) {
            result = new ArrayMirror(klass);
        } else {
            ClassElement element = ElementFactory.make(klass);
            result = new ClassMirror(element);
        }

        typeMirrorCache.put(klass, result);

        return result;
    }

    public static TypeMirror makeIntersected(Type[] bounds, Element element) {
        TypeIntersectionElement intersected = ElementFactory.makeIntersected(bounds, element);
        return make(intersected);
    }

    public static TypeMirror make(TypeIntersectionElement intersected) {
        if (typeMirrorCache.containsKey(intersected)) {
            return typeMirrorCache.get(intersected);
        }

        TypeMirror result = new ClassMirror(intersected);

        typeMirrorCache.put(intersected, result);

        return result;
    }

    public static TypeMirror make(PackageElement packageElement) {
        if (typeMirrorCache.containsKey(packageElement)) {
            return typeMirrorCache.get(packageElement);
        }

        PackageMirror mirror = new PackageMirror(packageElement);

        typeMirrorCache.put(packageElement, mirror);

        return mirror;
    }

    public static TypeMirror make(Type bound, Element genericElement) {
        if (typeMirrorCache.containsKey(bound)) {
            return typeMirrorCache.get(bound);
        }

        TypeMirror result;

        // TODO: we're ignoring GenericArrayType & ParameterizedType
        if (bound instanceof TypeVariable) {
            TypeParameterElement element = ElementFactory.make((TypeVariable)bound, genericElement);
            result = make(element);
        } else if (bound instanceof WildcardType) {
            result = new WildcardMirror((WildcardType)bound);
        } else {
            throw new IllegalArgumentException("Cannot create TypeMirror from " + bound.getClass().getName()
                + ". Not yet supported.");
        }

        typeMirrorCache.put(bound, result);

        return result;
    }

    public static TypeMirror make(TypeParameterElement element) {
        if (typeMirrorCache.containsKey(element)) {
            return typeMirrorCache.get(element);
        }

        TypeMirror result = new TypeVarMirror(element.getTypeVariable(), element);

        typeMirrorCache.put(element, result);

        return result;
    }

    public static TypeMirror make(MethodElement methodElement) {
        if (typeMirrorCache.containsKey(methodElement)) {
            return typeMirrorCache.get(methodElement);
        }

        ExecutableMirror mirror = new ExecutableMirror(methodElement);

        typeMirrorCache.put(methodElement, mirror);

        return mirror;
    }
}
