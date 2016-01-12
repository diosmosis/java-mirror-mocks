package flarestar.mirror.mock.type;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class ExecutableMirror implements ExecutableType {

    private ExecutableElement element;
    private List<TypeVariable> typeParameters = null;
    private List<TypeMirror> parameters = null;

    public ExecutableMirror(ExecutableElement element) {
        this.element = element;
    }

    public List<? extends TypeVariable> getTypeVariables() {
        if (typeParameters == null) {
            typeParameters = new ArrayList<TypeVariable>();
            for (TypeParameterElement typeParameter : element.getTypeParameters()) {
                typeParameters.add((TypeVariable)typeParameter.asType());
            }
        }
        return typeParameters;
    }

    public TypeMirror getReturnType() {
        return element.getReturnType();
    }

    public List<? extends TypeMirror> getParameterTypes() {
        if (parameters == null) {
            parameters = new ArrayList<TypeMirror>();
            for (VariableElement parameter : element.getParameters()) {
                parameters.add(parameter.asType());
            }
        }
        return parameters;
    }

    public List<? extends TypeMirror> getThrownTypes() {
        return element.getThrownTypes();
    }

    public TypeKind getKind() {
        return TypeKind.EXECUTABLE;
    }

    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return typeVisitor.visitExecutable(this, p);
    }

    public Element asElement() {
        return element;
    }
}
