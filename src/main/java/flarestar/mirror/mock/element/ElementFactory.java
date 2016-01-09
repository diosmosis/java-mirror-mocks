package flarestar.mirror.mock.element;

import flarestar.mirror.mock.type.TypeMirrorFactory;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * TODO
 */
public class ElementFactory {
    private static Set<String> allLoadedTypes;
    private static Map<String, Element> elementCache = new HashMap<String, Element>();
    private static Map<String, AnnotationElement> annotationMirrorCache = new HashMap<String, AnnotationElement>();

    public static FieldElement make(Field field, ClassElement parentElement) {
        String elementId = getElementId(parentElement) + "." + field.getName();
        if (!elementCache.containsKey(elementId)) {
            elementCache.put(elementId, new FieldElement(field, parentElement));
        }
        return (FieldElement)elementCache.get(elementId);
    }

    public static MethodElement make(Method method, ClassElement parentElement) {
        String elementId = getElementId(parentElement) + "." + method.getName();
        if (!elementCache.containsKey(elementId)) {
            elementCache.put(elementId, new MethodElement(method, parentElement));
        }
        return (MethodElement)elementCache.get(elementId);
    }

    public static AnnotationElement make(Annotation annotation, Element parentElement) {
        String elementId = getElementId(parentElement) + ".@" + annotation.annotationType().getName();
        if (!annotationMirrorCache.containsKey(elementId)) {
            annotationMirrorCache.put(elementId, new AnnotationElement(annotation, annotation.annotationType()));
        }
        return annotationMirrorCache.get(elementId);
    }

    public static TypeParameterElement make(TypeVariable element, Element parentElement) {
        String elementId = getElementId(parentElement) + ".<" + element.getName();
        if (!elementCache.containsKey(elementId)) {
            elementCache.put(elementId, new TypeParameterElement(element, parentElement));
        }
        return (TypeParameterElement)elementCache.get(elementId);
    }

    public static ClassElement make(Class<?> klass) {
        String elementId = klass.getName();
        if (!elementCache.containsKey(elementId)) {
            elementCache.put(elementId, new ClassElement(klass));
        }
        return (ClassElement)elementCache.get(elementId);
    }

    public static PackageElement make(Package thePackage) {
        String elementId = thePackage.getName();
        if (!elementCache.containsKey(elementId)) {
            elementCache.put(elementId, new PackageElement(thePackage));
        }
        return (PackageElement)elementCache.get(elementId);
    }

    public static VariableElement makeParameter(Class<?> parameterType, int argIndex, MethodElement parentElement) {
        String elementId = getElementId(parentElement) + "(" + parameterType.getName();
        if (!elementCache.containsKey(elementId)) {
            elementCache.put(elementId, new ParameterElement(parameterType, argIndex, parentElement));
        }
        return (ParameterElement)elementCache.get(elementId);
    }

    public static VariableElement makeEnumConstant(Object value) {
        return new EnumConstantElement(value);
    }

    public static List<Element> getChildElementsForPackage(PackageElement packageElement) {
        String packageName = packageElement.getQualifiedName().toString();

        List<Element> result = new ArrayList<Element>();
        Set<String> processedChildPackages = new HashSet<String>();

        Set<String> allTypes = getAllLoadedTypes();
        for (String type : allTypes) {
            if (!type.startsWith(packageName)) {
                continue;
            }

            String trailing = type.substring(packageName.length() + 1);

            if (trailing.contains(".")) { // this is a class in the namespace
                Class<?> klass;
                try {
                    klass = Class.forName(type);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                if (klass.getDeclaringClass() == null) { // sanify check for inner classes
                    result.add(make(klass));
                }
            } else { // this is a type in a sub-package, only process the package
                String subPackageName = trailing.substring(0, trailing.indexOf('.'));
                if (!processedChildPackages.contains(subPackageName)) {
                    processedChildPackages.add(subPackageName);

                    Package subPackage = Package.getPackage(packageName + "." + subPackageName);
                    result.add(make(subPackage));
                }
            }
        }

        return result;
    }

    public static Set<String> getAllLoadedTypes() {
        if (allLoadedTypes == null) {
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                .addScanners(new SubTypesScanner(false)));
            allLoadedTypes = reflections.getAllTypes();
        }
        return allLoadedTypes;
    }

    private static String getElementId(Element element) {
        if (element instanceof ClassElement) {
            return ((ClassElement)element).getQualifiedName().toString();
        } else if (element instanceof FieldElement) {
            return getElementId(element.getEnclosingElement()) + "." + element.getSimpleName().toString();
        } else if (element instanceof MethodElement) {
            return getElementId(element.getEnclosingElement()) + "." + element.getSimpleName().toString();
        } else if (element instanceof PackageElement) {
            return ((PackageElement) element).getQualifiedName().toString();
        } else if (element instanceof ParameterElement) {
            return getElementId(element.getEnclosingElement()) + "(" + element.getSimpleName().toString();
        }

        throw new IllegalArgumentException("Not sure how to create unique id for " + element.toString());
    }

    public static TypeIntersectionElement makeIntersected(Type[] bounds, Element genericElement) { // TODO: should we have caching here?
        TypeMirror fakeSuperclass = TypeMirrorFactory.make(bounds[0], genericElement);

        List<TypeMirror> fakeInterfaces = new ArrayList<TypeMirror>();
        for (int i = 1; i < bounds.length; ++i) {
            fakeInterfaces.add(TypeMirrorFactory.make(bounds[i], genericElement));
        }
        return new TypeIntersectionElement(fakeSuperclass, fakeInterfaces, genericElement);
    }
}
