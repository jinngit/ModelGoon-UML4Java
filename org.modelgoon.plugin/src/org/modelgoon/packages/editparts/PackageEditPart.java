package org.modelgoon.packages.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.modelgoon.core.ui.AbstractComponentEditPart;
import org.modelgoon.packages.figures.PackageFigure;
import org.modelgoon.packages.model.PackageElement;

public class PackageEditPart extends AbstractComponentEditPart<PackageElement> {

	PackageFigure packageFigure;

	private ChopboxAnchor anchor;

	public PackageEditPart() {
		setDeleteCommand(new Command() {
			@Override
			public void execute() {
				getModelElement().removeFromDiagram();
			}
		});
	}

	@Override
	protected IFigure createFigure() {
		this.packageFigure = new PackageFigure();
		this.packageFigure.setLocation(new Point(200, 200));

		this.anchor = new ChopboxAnchor(this.packageFigure);
		return this.packageFigure;
	}

	@Override
	protected void doRefreshVisuals(final PackageElement model) {
		this.packageFigure.setPackageName(model.getQualifiedName());
		this.packageFigure.setLocation(model.getLocation());
	}

	@Override
	public List getModelSourceConnections() {
		List<Object> sources = new ArrayList<Object>();
		sources.addAll(getModelElement().getSourceLinks());
		return sources;
	}

	@Override
	public List getModelTargetConnections() {
		return getModelElement().getDestinationLinks();
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

}
