package flarestar.mirror.mock.utils;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO
 */
public class MockMessager implements Messager {

    public static class Message {
        public final Diagnostic.Kind kind;
        public final CharSequence message;
        public final Element element;
        public final AnnotationMirror annotation;
        public final AnnotationValue annotationValue;

        public Message(Diagnostic.Kind kind, CharSequence message, Element element, AnnotationMirror annotation,
                       AnnotationValue annotationValue) {
            this.kind = kind;
            this.message = message;
            this.element = element;
            this.annotation = annotation;
            this.annotationValue = annotationValue;
        }
    }

    private List<Message> capturedMessages = new ArrayList<Message>();

    public void printMessage(Diagnostic.Kind kind, CharSequence message) {
        printMessage(kind, message, null);
    }

    public void printMessage(Diagnostic.Kind kind, CharSequence message, Element element) {
        printMessage(kind, message, element, null);
    }

    public void printMessage(Diagnostic.Kind kind, CharSequence message, Element element, AnnotationMirror annotation) {
        printMessage(kind, message, element, annotation, null);
    }

    public void printMessage(Diagnostic.Kind kind, CharSequence message, Element element, AnnotationMirror annotation,
                             AnnotationValue annotationValue) {
        capturedMessages.add(new Message(kind, message, element, annotation, annotationValue));
    }

    public List<Message> getCapturedMessages() {
        return Collections.unmodifiableList(capturedMessages);
    }
}
