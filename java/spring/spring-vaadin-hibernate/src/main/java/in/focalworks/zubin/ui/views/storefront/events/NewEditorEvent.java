package in.focalworks.zubin.ui.views.storefront.events;

import com.vaadin.flow.component.ComponentEvent;
import in.focalworks.zubin.ui.views.orderedit.OrderItemsEditor;

public class NewEditorEvent extends ComponentEvent<OrderItemsEditor> {

	public NewEditorEvent(OrderItemsEditor component) {
		super(component, false);
	}
}