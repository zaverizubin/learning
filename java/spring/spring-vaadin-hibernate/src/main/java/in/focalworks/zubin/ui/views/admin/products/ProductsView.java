package in.focalworks.zubin.ui.views.admin.products;

import static in.focalworks.zubin.ui.utils.BakeryConst.PAGE_PRODUCTS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import in.focalworks.zubin.backend.data.Role;
import in.focalworks.zubin.backend.data.entity.Product;
import in.focalworks.zubin.backend.data.entity.util.EntityUtil;
import in.focalworks.zubin.ui.MainView;
import in.focalworks.zubin.ui.components.SearchBar;
import in.focalworks.zubin.ui.crud.CrudEntityPresenter;
import in.focalworks.zubin.ui.crud.CrudView;
import in.focalworks.zubin.ui.utils.BakeryConst;
import in.focalworks.zubin.ui.utils.converters.CurrencyFormatter;

@Tag("products-view")
@HtmlImport("src/views/admin/products/products-view.html")
@Route(value = PAGE_PRODUCTS, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_PRODUCTS)
@Secured(Role.ADMIN)
public class ProductsView extends CrudView<Product, TemplateModel>  {

	@Id("search")
	private SearchBar search;

	@Id("grid")
	private Grid<Product> grid;

	private CrudEntityPresenter<Product> presenter;

	private final BeanValidationBinder<Product> binder = new BeanValidationBinder<>(Product.class);

	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();

	@Autowired
	public ProductsView(CrudEntityPresenter<Product> presenter, ProductForm form) {
		super(EntityUtil.getName(Product.class), form);
		this.presenter = presenter;
		form.setBinder(binder);

		setupEventListeners();
		setupGrid();
		presenter.setView(this);
	}

	private void setupGrid() {
		grid.addColumn(Product::getName).setHeader("Product Name").setFlexGrow(10);
		grid.addColumn(p -> currencyFormatter.encode(p.getPrice())).setHeader("Unit Price");
	}

	@Override
	public Grid<Product> getGrid() {
		return grid;
	}

	@Override
	protected CrudEntityPresenter<Product> getPresenter() {
		return presenter;
	}

	@Override
	protected String getBasePage() {
		return PAGE_PRODUCTS;
	}

	@Override
	protected BeanValidationBinder<Product> getBinder() {
		return binder;
	}

	@Override
	protected SearchBar getSearchBar() {
		return search;
	}
}
