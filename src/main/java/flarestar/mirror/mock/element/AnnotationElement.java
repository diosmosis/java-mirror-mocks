package flarestar.mirror.mock.element;

import flarestar.mirror.mock.type.TypeMirrorFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */
public class AnnotationElement implements AnnotationMirror {

    private Annotation annotation;
    private Class<? extends Annotation> annotationClass;
    private Map<ExecutableElement, AnnotationValue> elementValues = null;
    private Map<ExecutableElement, AnnotationValue> elementValuesWithDefaults = null;

    public AnnotationElement(Annotation annotation, Class<? extends Annotation> annotationClass) {
        this.annotation = annotation;
        this.annotationClass = annotationClass;
    }

    public DeclaredType getAnnotationType() {
        return (DeclaredType)TypeMirrorFactory.make(annotationClass);
    }

    public Map<ExecutableElement, AnnotationValue> getElementValues() {
        if (elementValues == null) {
            elementValues = collectElementValues(false);
        }
        return elementValues;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public Map<ExecutableElement, AnnotationValue> getElementValuesWithDefaults() {
        if (elementValuesWithDefaults == null) {
            elementValuesWithDefaults = collectElementValues(true);
        }
        return elementValuesWithDefaults;
    }

    private Map<ExecutableElement, AnnotationValue> collectElementValues(boolean includeDefaults) {
        Map<ExecutableElement, AnnotationValue> result = new HashMap<ExecutableElement, AnnotationValue>();

        ClassElement annotationClassElement = ElementFactory.make(annotationClass);
        for (Method method : annotationClass.getDeclaredMethods()) {
            Object actualValue;
            try {
                actualValue = method.invoke(annotation);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }

            if (!includeDefaults && actualValue.equals(method.getDefaultValue())) {
                continue;
            }

            ExecutableElement methodElement = ElementFactory.make(method, annotationClassElement);
            AnnotationValue annotationValue = new AnnotationValueImpl(actualValue);

            result.put(methodElement, annotationValue);
        }

        return result;
    }
}
