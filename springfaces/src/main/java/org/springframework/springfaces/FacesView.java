package org.springframework.springfaces;

import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class FacesView extends AbstractUrlBasedView {

	private static final ThreadLocal<FacesView> renderInstance = new NamedThreadLocal<FacesView>(
			"FacesView Render Instance");

	public FacesView() {
		super();
	}

	public FacesView(String url) {
		super(url);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		render(request, response);
	}

	void render(HttpServletRequest request, HttpServletResponse response) {
		renderInstance.set(this);
		try {
			Lifecycle lifecycle = FacesFactory.get(LifecycleFactory.class).getLifecycle(
					LifecycleFactory.DEFAULT_LIFECYCLE);
			FacesContext facesContext = FacesFactory.get(FacesContextFactory.class).getFacesContext(
					getServletContext(), request, response, lifecycle);
			try {

				lifecycle.execute(facesContext);
				lifecycle.render(facesContext);
			} finally {
				facesContext.release();
			}
		} finally {
			renderInstance.remove();
		}
	}

	@Override
	public boolean checkResource(Locale locale) throws Exception {
		//FIXME check if the resource exists
		return super.checkResource(locale);
	}

	public static FacesView getRenderInstance() {
		return renderInstance.get();
	}

}