package flarestar.mirror.mock.element;

import flarestar.mirror.mock.type.TypeMirrorFactory;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO
 */
public class PackageElement implements javax.lang.model.element.PackageElement {
    private Package thePackage;

    // TODO: redundancy w/ ClassElement (there should be an AnnotatedElement common base)
    private List<AnnotationMirror> annotationMirrors = new ArrayList<AnnotationMirror>();
    private List<Element> enclosedElements = null;

    private Name qualifiedName;
    private Name simpleName;

    public PackageElement(Package thePackage) {
        this.thePackage = thePackage;

        qualifiedName = new Name(thePackage.getName());
        simpleName = new Name(getSimpleNameOf(thePackage));

        for (Annotation annotation : thePackage.getAnnotations()) {
            this.annotationMirrors.add(ElementFactory.make(annotation, this));
        }
    }

    @Override
    public Name getQualifiedName() {
        return qualifiedName;
    }

    @Override
    public TypeMirror asType() {
        return TypeMirrorFactory.make(this);
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.PACKAGE;
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return annotationMirrors;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> aClass) {
        return thePackage.getAnnotation(aClass);
    }

    @Override
    public Set<Modifier> getModifiers() {
        // TODO: don't know how to get modifiers for a package...
        return new HashSet<Modifier>();
    }

    @Override
    public Name getSimpleName() {
        return simpleName;
    }

    @Override
    public boolean isUnnamed() {
        return false; // TODO: not sure how to tell if a package is the default one
    }

    @Override
    public Element getEnclosingElement() {
        String parentPackage = getParentPackage();
        if (parentPackage == null) {
            return null;
        } else {
            return ElementFactory.make(Package.getPackage(parentPackage));
        }
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        if (enclosedElements == null) {
            enclosedElements = ElementFactory.getChildElementsForPackage(this);
        }
        return enclosedElements;
    }

    @Override
    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return elementVisitor.visitPackage(this, p);
    }

    private String getSimpleNameOf(Package thePackage) {
        String qualifiedName = thePackage.getName();
        return qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1);
    }

    private String getParentPackage() {
        String qualifiedName = thePackage.getName();

        int lastIndex = qualifiedName.lastIndexOf('.');
        if (lastIndex == -1) {
            return null;
        }

        return qualifiedName.substring(0, lastIndex);
    }
}