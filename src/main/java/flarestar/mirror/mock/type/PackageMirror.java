package flarestar.mirror.mock.type;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class PackageMirror implements DeclaredType {
    private PackageElement packageElement;

    public PackageMirror(PackageElement packageElement) {
        this.packageElement = packageElement;
    }

    @Override
    public Element asElement() {
        return packageElement;
    }

    @Override
    public TypeMirror getEnclosingType() {
        Element enclosing = packageElement.getEnclosingElement();
        if (enclosing == null) {
            return null;
        } else {
            return enclosing.asType();
        }
    }

    @Override
    public List<? extends TypeMirror> getTypeArguments() {
        return new ArrayList<TypeMirror>();
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.PACKAGE;
    }

    @Override
    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return typeVisitor.visitDeclared(this, p);
    }
}
