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
    private List<TypeVariable> typeParameters = new ArrayList<TypeVariable>();
    private List<TypeMirror> parameters = new ArrayList<TypeMirror>();

    public ExecutableMirror(ExecutableElement element) {
        this.element = element;

        for (TypeParameterElement typeParameter : element.getTypeParameters()) {
            typeParameters.add((TypeVariable)typeParameter.asType());
        }

        for (VariableElement parameter : element.getParameters()) {
            parameters.add(parameter.asType());
        }
    }

    @Override
    public List<? extends TypeVariable> getTypeVariables() {
        return typeParameters;
    }

    @Override
    public TypeMirror getReturnType() {
        return element.getReturnType();
    }

    @Override
    public List<? extends TypeMirror> getParameterTypes() {
        return parameters;
    }

    @Override
    public List<? extends TypeMirror> getThrownTypes() {
        return element.getThrownTypes();
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.EXECUTABLE;
    }

    @Override
    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return typeVisitor.visitExecutable(this, p);
    }
}
