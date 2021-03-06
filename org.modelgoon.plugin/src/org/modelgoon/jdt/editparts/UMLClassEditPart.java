package org.modelgoon.jdt.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.jdt.core.IJavaElement;
import org.modelgoon.classes.editparts.ClassCompartmentModel;
import org.modelgoon.classes.figures.ClassFigure;
import org.modelgoon.core.ui.AbstractComponentEditPart;
import org.modelgoon.jdt.model.AssociationRelationShip;
import org.modelgoon.jdt.model.ExtensionRelationShip;
import org.modelgoon.jdt.model.UMLClass;

public class UMLClassEditPart extends AbstractComponentEditPart<UMLClass>
		implements JavaElementProvider {

	ClassFigure classFigure;

	private ChopboxAnchor anchor;

	public UMLClassEditPart() {
		setDeleteCommand(new Command() {
			@Override
			public void execute() {
				getModelElement().removeFromDiagram();
			}
		});
		addLinkCreationCommand(ExtensionRelationShip.class,
				new CreateInheritanceCommand());
		addLinkCreationCommand(AssociationRelationShip.class,
				new CreateAssociationCommand());
	}

	@Override
	protected IFigure createFigure() {
		this.classFigure = new ClassFigure();
		this.anchor = new ChopboxAnchor(this.classFigure);
		return this.classFigure;
	}

	@Override
	public IFigure getContentPane() {
		return this.classFigure.getFeaturesCompartment();
	}

	@Override
	protected void doRefreshVisuals(final UMLClass model) {
		String name = model.getQualifiedName();
		if (name.contains(".")) {
			name = name.substring(name.lastIndexOf(".") + 1);
		}
		this.classFigure.setClassName(name);
		this.classFigure.setLocation(model.getLocation());
		this.classFigure.setAbstract(model.isAbstract());
		this.classFigure.setInterface(model.isInterface());
		this.classFigure.setInternal(model.isInternal());

		this.classFigure.setSize(this.classFigure.getPreferredSize());
	}

	@Override
	protected List getModelChildren() {
		UMLClass model = getModelElement();
		List<Object> structuralFeatures = new ArrayList<Object>();
		structuralFeatures.add(new ClassCompartmentModel(model
				.getVisibleAttributes()));
		structuralFeatures.add(new ClassCompartmentModel(model
				.getVisibleMethods()));
		return structuralFeatures;
	}

	@Override
	public List getModelSourceConnections() {
		List outgoingConnexions = new ArrayList();
		outgoingConnexions.addAll(getModelElement()
				.getAssociationRelationships());
		outgoingConnexions
				.addAll(getModelElement().getExtensionRelationShips());
		return outgoingConnexions;
	}

	@Override
	public List getModelTargetConnections() {
		List incomingConnexion = new ArrayList();
		incomingConnexion.addAll(getModelElement()
				.getIncomingAssociationRelationShips());
		incomingConnexion.addAll(getModelElement()
				.getIncomingExtensionRelationship());
		return incomingConnexion;
	}

	public ConnectionAnchor getSourceConnectionAnchor(
			final ConnectionEditPart connection) {
		// TODO Auto-generated method stub
		return this.anchor;
	}

	public ConnectionAnchor getTargetConnectionAnchor(
			final ConnectionEditPart connection) {
		// TODO Auto-generated method stub
		return this.anchor;
	}

	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		// TODO Auto-generated method stub
		return this.anchor;
	}

	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		// TODO Auto-generated method stub
		return this.anchor;
	}

	public IJavaElement getJavaElement() {
		return getModelElement().getJavaType();
	}

}
