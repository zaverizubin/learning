package in.focalworks.zubin.ui.views.errors;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.RouteNotFoundError;
import com.vaadin.flow.router.RouterLink;
import in.focalworks.zubin.ui.MainView;
import in.focalworks.zubin.ui.utils.BakeryConst;

@ParentLayout(MainView.class)
@PageTitle(BakeryConst.TITLE_NOT_FOUND)
public class CustomRouteNotFoundError extends RouteNotFoundError {

	public CustomRouteNotFoundError() {
		RouterLink link = Component.from(
				ElementFactory.createRouterLink("", "Go to the front page."),
				RouterLink.class);
		getElement().appendChild(new Text("Oops you hit a 404. ").getElement(), link.getElement());
	}

	@Override
	public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
		return HttpServletResponse.SC_NOT_FOUND;
	}
}
