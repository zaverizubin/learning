package in.focalworks.zubin.ui.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.DebounceSettings;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.DebouncePhase;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("search-bar")
@HtmlImport("src/components/search-bar.html")
public class SearchBar extends PolymerTemplate<SearchBar.Model> {

	public interface Model extends TemplateModel {
		boolean isCheckboxChecked();

		void setCheckboxChecked(boolean checkboxChecked);

		void setCheckboxText(String checkboxText);

		void setButtonText(String actionText);
	}

	@Id("field")
	private TextField textField;

	@Id("clear")
	private Button clearButton;

	@Id("action")
	private Button actionButton;

	public SearchBar() {
		textField.setValueChangeMode(ValueChangeMode.EAGER);
		ComponentUtil.addListener(textField, SearchValueChanged.class,
				e -> fireEvent(new FilterChanged(this, false)));
		clearButton.addClickListener(e -> {
			textField.clear();
			getModel().setCheckboxChecked(false);
		});

		getElement().addPropertyChangeListener("checkboxChecked", e -> fireEvent(new FilterChanged(this, false)));
	}

	public String getFilter() {
		return textField.getValue();
	}

	public boolean isCheckboxChecked() {
		return getModel().isCheckboxChecked();
	}

	public void setPlaceHolder(String placeHolder) {
		textField.setPlaceholder(placeHolder);
	}

	public void setActionText(String actionText) {
		getModel().setButtonText(actionText);
	}

	public void setCheckboxText(String checkboxText) {
		getModel().setCheckboxText(checkboxText);
	}

	public void addFilterChangeListener(ComponentEventListener<FilterChanged> listener) {
		this.addListener(FilterChanged.class, listener);
	}

	public void addActionClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
		actionButton.addClickListener(listener);
	}

	@DomEvent(value = "value-changed", debounce = @DebounceSettings(timeout = 300, phases = DebouncePhase.TRAILING))
	public static class SearchValueChanged extends ComponentEvent<TextField> {
		public SearchValueChanged(TextField source, boolean fromClient) {
			super(source, fromClient);
		}
	}

	public static class FilterChanged extends ComponentEvent<SearchBar> {
		public FilterChanged(SearchBar source, boolean fromClient) {
			super(source, fromClient);
		}
	}
}
