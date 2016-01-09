package flarestar.mirror.mock.element;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 */
public class ElementModifiers {

    public static Set<javax.lang.model.element.Modifier> getModifiers(int modifiers) {
        Set<javax.lang.model.element.Modifier> result = new HashSet<javax.lang.model.element.Modifier>();

        if (Modifier.isPublic(modifiers)) {
            result.add(javax.lang.model.element.Modifier.PUBLIC);
        }

        if (Modifier.isProtected(modifiers)) {
            result.add(javax.lang.model.element.Modifier.PROTECTED);
        }

        if (Modifier.isPrivate(modifiers)) {
            result.add(javax.lang.model.element.Modifier.PRIVATE);
        }

        if (Modifier.isStrict(modifiers)) {
            result.add(javax.lang.model.element.Modifier.STRICTFP);
        }

        if (Modifier.isSynchronized(modifiers)) {
            result.add(javax.lang.model.element.Modifier.SYNCHRONIZED);
        }

        if (Modifier.isNative(modifiers)) {
            result.add(javax.lang.model.element.Modifier.NATIVE);
        }

        if (Modifier.isTransient(modifiers)) {
            result.add(javax.lang.model.element.Modifier.TRANSIENT);
        }

        if (Modifier.isVolatile(modifiers)) {
            result.add(javax.lang.model.element.Modifier.VOLATILE);
        }

        if (Modifier.isAbstract(modifiers)) {
            result.add(javax.lang.model.element.Modifier.ABSTRACT);
        }

        if (Modifier.isStatic(modifiers)) {
            result.add(javax.lang.model.element.Modifier.STATIC);
        }

        return result;
    }
}
