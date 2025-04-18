package in.focalworks.zubin.ui.dataproviders;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.QuerySortOrderBuilder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import in.focalworks.zubin.backend.data.entity.Order;
import in.focalworks.zubin.backend.service.OrderService;
import in.focalworks.zubin.ui.utils.BakeryConst;

/**
 * A singleton pageable order data provider.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrdersGridDataProvider extends FilterablePageableDataProvider<Order, OrdersGridDataProvider.OrderFilter> {

	public static class OrderFilter implements Serializable {
		private String filter;
		private boolean showPrevious;

		public String getFilter() {
			return filter;
		}

		public boolean isShowPrevious() {
			return showPrevious;
		}

		public OrderFilter(String filter, boolean showPrevious) {
			this.filter = filter;
			this.showPrevious = showPrevious;
		}

		public static OrderFilter getEmptyFilter() {
			return new OrderFilter("", false);
		}
	}

	private final OrderService orderService;
	private List<QuerySortOrder> defaultSortOrders;
	private Consumer<Page<Order>> pageObserver;
	
	@Autowired
	public OrdersGridDataProvider(OrderService orderService) {
		this.orderService = orderService;
		setSortOrders(BakeryConst.DEFAULT_SORT_DIRECTION, BakeryConst.ORDER_SORT_FIELDS);
	}

	private void setSortOrders(Sort.Direction direction, String[] properties) {
		QuerySortOrderBuilder builder = new QuerySortOrderBuilder();
		for (String property : properties) {
			if (direction.isAscending()) {
				builder.thenAsc(property);
			} else {
				builder.thenDesc(property);
			}
		}
		defaultSortOrders = builder.build();
	}

	@Override
	protected Page<Order> fetchFromBackEnd(Query<Order, OrderFilter> query, Pageable pageable) {
		OrderFilter filter = query.getFilter().orElse(OrderFilter.getEmptyFilter());
		Page<Order> page = orderService.findAnyMatchingAfterDueDate(Optional.ofNullable(filter.getFilter()),
				getFilterDate(filter.isShowPrevious()), pageable);
		if (pageObserver != null) {
			pageObserver.accept(page);
		}
		return page;
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		return defaultSortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Order, OrderFilter> query) {
		OrderFilter filter = query.getFilter().orElse(OrderFilter.getEmptyFilter());
		return (int) orderService
				.countAnyMatchingAfterDueDate(Optional.ofNullable(filter.getFilter()), getFilterDate(filter.isShowPrevious()));
	}

	private Optional<LocalDate> getFilterDate(boolean showPrevious) {
		if (showPrevious) {
			return Optional.empty();
		}

		return Optional.of(LocalDate.now().minusDays(1));
	}

	public void setPageObserver(Consumer<Page<Order>> pageObserver) {
		this.pageObserver = pageObserver;
	}
	
	
}
