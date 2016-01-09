package flarestar.mirror.mock.element;

import flarestar.mirror.mock.type.TypeMirrorFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */
public class AnnotationElement implements AnnotationMirror {

    private Annotation annotation;
    private Class<? extends Annotation> annotationClass;
    private Map<ExecutableElement, AnnotationValue> elementValues = new HashMap<ExecutableElement, AnnotationValue>();

    public AnnotationElement(Annotation annotation, Class<? extends Annotation> annotationClass) {
        this.annotation = annotation;
        this.annotationClass = annotationClass;

        ClassElement annotationClassElement = ElementFactory.make(annotationClass);
        for (Method method : annotationClass.getDeclaredMethods()) {
            ExecutableElement methodElement = ElementFactory.make(method, annotationClassElement);
            AnnotationValue annotationValue = new AnnotationValueImpl(method.getDefaultValue());

            elementValues.put(methodElement, annotationValue);
        }
    }

    @Override
    public DeclaredType getAnnotationType() {
        return (DeclaredType)TypeMirrorFactory.make(annotationClass);
    }

    @Override
    public Map<ExecutableElement, AnnotationValue> getElementValues() {
        return elementValues;
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}
