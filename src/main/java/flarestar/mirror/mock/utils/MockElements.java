package flarestar.mirror.mock.utils;

import flarestar.mirror.mock.element.*;

import javax.lang.model.element.*;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * TODO
 */
public class MockElements implements Elements {
    public PackageElement getPackageElement(CharSequence name) {
        Package thePackage = Package.getPackage(name.toString());
        if (thePackage == null) {
            return null;
        }

        return ElementFactory.make(thePackage);
    }

    public TypeElement getTypeElement(CharSequence name) {
        Class<?> klass;
        try {
            klass = Class.forName(name.toString());
        } catch (ClassNotFoundException e) {
            return null;
        }

        return ElementFactory.make(klass);
    }

    public Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValuesWithDefaults(AnnotationMirror annotationMirror) {
        return ((AnnotationElement)annotationMirror).getElementValuesWithDefaults();
    }

    public String getDocComment(Element element) {
        throw new UnsupportedOperationException("MockElements.getDocComment() is currently unsupported.");
    }

    public boolean isDeprecated(Element element) {
        return element.getAnnotation(Deprecated.class) != null;
    }

    public Name getBinaryName(TypeElement typeElement) {
        // TODO: https://docs.oracle.com/javase/specs/jls/se7/html/jls-13.html#jls-13.1
        throw new UnsupportedOperationException("MockElements.getBinaryName() is currently unsupported.");
    }

    public PackageElement getPackageOf(Element element) {
        while (element != null && !(element instanceof PackageElement)) {
            element = element.getEnclosingElement();
        }

        if (element == null) {
            return null;
        } else {
            return (PackageElement)element;
        }
    }

    public List<? extends Element> getAllMembers(TypeElement typeElement) {
        throw new UnsupportedOperationException("MockElements.getAllMembers() is currently unsupported.");
    }

    public List<? extends AnnotationMirror> getAllAnnotationMirrors(Element element) {
        throw new UnsupportedOperationException("MockElements.getAllAnnotationMirrors() is currently unsupported.");
    }

    public boolean hides(Element hider, Element hidden) {
        throw new UnsupportedOperationException("MockElements.hides() is currently unsupported.");
    }

    public boolean overrides(ExecutableElement overrider, ExecutableElement overridden, TypeElement type) {
        throw new UnsupportedOperationException("MockElements.overrides() is currently unsupported.");
    }

    public String getConstantExpression(Object o) {
        throw new UnsupportedOperationException("MockElements.getConstantExpression() is currently unsupported.");
    }

    public void printElements(Writer writer, Element... elements) {
        // no-op TODO: should we print something out here? not sure if it would be useful in tests
    }

    public Name getName(CharSequence charSequence) {
        return new flarestar.mirror.mock.element.Name(charSequence.toString());
    }
}
