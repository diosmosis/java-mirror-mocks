package flarestar.mirror.mock.element;

import flarestar.mirror.mock.type.TypeMirrorFactory;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class AnnotationValueImpl implements AnnotationValue {
    private Object value;
    private List<AnnotationValue> annotationArrayValuesCache;

    public AnnotationValueImpl(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public <R, P> R accept(AnnotationValueVisitor<R, P> annotationValueVisitor, P p) {
        if (value instanceof Boolean) {
            return annotationValueVisitor.visitBoolean((Boolean) value, p);
        } else if (value instanceof Byte) {
            return annotationValueVisitor.visitByte((Byte) value, p);
        } else if (value instanceof Character) {
            return annotationValueVisitor.visitChar((Character) value, p);
        } else if (value instanceof Double) {
            return annotationValueVisitor.visitDouble((Double) value, p);
        } else if (value instanceof Float) {
            return annotationValueVisitor.visitFloat((Float) value, p);
        } else if (value instanceof Integer) {
            return annotationValueVisitor.visitInt((Integer) value, p);
        } else if (value instanceof Long) {
            return annotationValueVisitor.visitLong((Long) value, p);
        } else if (value instanceof Short) {
            return annotationValueVisitor.visitShort((Short) value, p);
        } else if (value instanceof String) {
            return annotationValueVisitor.visitString((String) value, p);
        } else if (value instanceof Class<?>) {
            return annotationValueVisitor.visitType(TypeMirrorFactory.make((Class<?>) value), p);
        } else if (value.getClass().isEnum()) {
            return annotationValueVisitor.visitEnumConstant(ElementFactory.makeEnumConstant(value), p);
        } else if (value instanceof Annotation) {
            return annotationValueVisitor.visitAnnotation(ElementFactory.make((Annotation) value, null), p);
        } else if (value.getClass().isArray()) {
            return annotationValueVisitor.visitArray(getAnnotationArrayValues(), p);
        } else {
            return annotationValueVisitor.visitUnknown(this, p);
        }
    }

    public List<? extends AnnotationValue> getAnnotationArrayValues() {
        if (annotationArrayValuesCache == null) {
            annotationArrayValuesCache = new ArrayList<AnnotationValue>();

            for (int i = 0; i != Array.getLength(value); ++i) {
                annotationArrayValuesCache.add(new AnnotationValueImpl(Array.get(value, i)));
            }
        }
        return annotationArrayValuesCache;
    }
}
