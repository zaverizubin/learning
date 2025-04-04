package in.focalworks.zubin.ui.views.dashboard;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.events.ChartLoadEvent;
import com.vaadin.flow.component.charts.model.Background;
import com.vaadin.flow.component.charts.model.BackgroundShape;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Pane;
import com.vaadin.flow.component.charts.model.PlotOptionsPie;
import com.vaadin.flow.component.charts.model.PlotOptionsSolidgauge;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import in.focalworks.zubin.backend.data.DashboardData;
import in.focalworks.zubin.backend.data.DeliveryStats;
import in.focalworks.zubin.backend.data.entity.Order;
import in.focalworks.zubin.backend.data.entity.OrderSummary;
import in.focalworks.zubin.backend.data.entity.Product;
import in.focalworks.zubin.backend.service.OrderService;
import in.focalworks.zubin.ui.MainView;
import in.focalworks.zubin.ui.dataproviders.OrdersGridDataProvider;
import in.focalworks.zubin.ui.utils.BakeryConst;
import in.focalworks.zubin.ui.utils.FormattingUtils;
import in.focalworks.zubin.ui.views.storefront.OrderCard;
import in.focalworks.zubin.ui.views.storefront.beans.OrdersCountDataWithChart;

@Tag("dashboard-view")
@HtmlImport("src/views/dashboard/dashboard-view.html")
@Route(value = BakeryConst.PAGE_DASHBOARD, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_DASHBOARD)
public class DashboardView extends PolymerTemplate<TemplateModel> {

	private static final String[] MONTH_LABELS = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
			"Aug", "Sep", "Oct", "Nov", "Dec"};

	private final OrderService orderService;

	@Id("todayCount")
	private DashboardCounterLabel todayCount;

	@Id("notAvailableCount")
	private DashboardCounterLabel notAvailableCount;

	@Id("newCount")
	private DashboardCounterLabel newCount;

	@Id("tomorrowCount")
	private DashboardCounterLabel tomorrowCount;

	@Id("deliveriesThisMonth")
	private Chart deliveriesThisMonthChart;

	@Id("deliveriesThisYear")
	private Chart deliveriesThisYearChart;

	@Id("yearlySalesGraph")
	private Chart yearlySalesGraph;

	@Id("ordersGrid")
	private Grid<Order> grid;

	@Id("monthlyProductSplit")
	private Chart monthlyProductSplit;

	@Id("todayCountChart")
	private Chart todayCountChart;

	@Autowired
	public DashboardView(OrderService orderService, OrdersGridDataProvider orderDataProvider) {
		this.orderService = orderService;

		grid.addColumn(OrderCard.getTemplate()
				.withProperty("orderCard", OrderCard::create)
				.withProperty("header", order -> null)
				.withEventHandler("cardClick",
						order -> UI.getCurrent().navigate(BakeryConst.PAGE_STOREFRONT + "/" + order.getId())));

		grid.setSelectionMode(Grid.SelectionMode.NONE);
		grid.setDataProvider(orderDataProvider);

		DashboardData data = orderService.getDashboardData(MonthDay.now().getMonthValue(), Year.now().getValue());
		populateYearlySalesChart(data);
		populateDeliveriesCharts(data);
		populateOrdersCounts(data.getDeliveryStats());
		initProductSplitMonthlyGraph(data.getProductDeliveries());

		measurePageLoadPerformance();
	}

	// This method is overridden to measure the page load performance and can be safely removed
	// if there is no need for that.
	private void measurePageLoadPerformance() {
		final int nTotal = 5; // the total number of charts on the page
		AtomicInteger nLoaded = new AtomicInteger();
		ComponentEventListener<ChartLoadEvent> chartLoadListener = (event) -> {
			nLoaded.addAndGet(1);
			if (nLoaded.get() == nTotal) {
				UI.getCurrent().getPage().executeJavaScript("$0._chartsLoadedResolve()", this);
			}
		};

		todayCountChart.addChartLoadListener(chartLoadListener);
		deliveriesThisMonthChart.addChartLoadListener(chartLoadListener);
		deliveriesThisYearChart.addChartLoadListener(chartLoadListener);
		yearlySalesGraph.addChartLoadListener(chartLoadListener);
		monthlyProductSplit.addChartLoadListener(chartLoadListener);
	}

	private void initProductSplitMonthlyGraph(Map<Product, Integer> productDeliveries) {

		LocalDate today = LocalDate.now();

		Configuration conf = monthlyProductSplit.getConfiguration();
		conf.getChart().setType(ChartType.PIE);
		conf.getChart().setBorderRadius(4);
		conf.setTitle("Products delivered in " + FormattingUtils.getFullMonthName(today));
		DataSeries deliveriesPerProductSeries = new DataSeries(productDeliveries.entrySet().stream()
				.map(e -> new DataSeriesItem(e.getKey().getName(), e.getValue())).collect(Collectors.toList()));
		PlotOptionsPie plotOptionsPie = new PlotOptionsPie();
		plotOptionsPie.setInnerSize("60%");
		plotOptionsPie.getDataLabels().setCrop(false);
		deliveriesPerProductSeries.setPlotOptions(plotOptionsPie);
		conf.addSeries(deliveriesPerProductSeries);
	}

	private void populateOrdersCounts(DeliveryStats deliveryStats) {
		List<OrderSummary> orders = orderService.findAnyMatchingStartingToday();

		OrdersCountDataWithChart todaysOrdersCountData = DashboardUtils
				.getTodaysOrdersCountData(deliveryStats, orders.iterator());
		todayCount.setOrdersCountData(todaysOrdersCountData);
		initTodayCountSolidgaugeChart(todaysOrdersCountData);
		notAvailableCount.setOrdersCountData(DashboardUtils.getNotAvailableOrdersCountData(deliveryStats));
		Order lastOrder = orderService.load(orders.get(orders.size() - 1).getId());
		newCount.setOrdersCountData(DashboardUtils.getNewOrdersCountData(deliveryStats, lastOrder));
		tomorrowCount.setOrdersCountData(DashboardUtils.getTomorrowOrdersCountData(deliveryStats, orders.iterator()));
	}


	private void initTodayCountSolidgaugeChart(OrdersCountDataWithChart data) {
		Configuration configuration = todayCountChart.getConfiguration();
		configuration.getChart().setType(ChartType.SOLIDGAUGE);
		configuration.setTitle("");
		configuration.getTooltip().setEnabled(false);

		configuration.getyAxis().setMin(0);
		configuration.getyAxis().setMax(data.getOverall());
		configuration.getyAxis().getLabels().setEnabled(false);

		PlotOptionsSolidgauge opt = new PlotOptionsSolidgauge();
		opt.getDataLabels().setEnabled(false);
		configuration.setPlotOptions(opt);

		DataSeriesItemWithRadius point = new DataSeriesItemWithRadius();
		point.setY(data.getCount());
		point.setInnerRadius("100%");
		point.setRadius("110%");
		configuration.setSeries(new DataSeries(point));

		Pane pane = configuration.getPane();
		pane.setStartAngle(0);
		pane.setEndAngle(360);

		Background background = new Background();
		background.setShape(BackgroundShape.ARC);
		background.setInnerRadius("100%");
		background.setOuterRadius("110%");
		pane.setBackground(background);
	}

	private void populateDeliveriesCharts(DashboardData data) {
		LocalDate today = LocalDate.now();

		// init the 'Deliveries in [this year]' chart
		Configuration yearConf = deliveriesThisYearChart.getConfiguration();
		configureColumnChart(yearConf);

		yearConf.setTitle("Deliveries in " + today.getYear());
		yearConf.getxAxis().setCategories(MONTH_LABELS);
		yearConf.addSeries(new ListSeries("per Month", data.getDeliveriesThisYear()));

		// init the 'Deliveries in [this month]' chart
		Configuration monthConf = deliveriesThisMonthChart.getConfiguration();
		configureColumnChart(monthConf);

		List<Number> deliveriesThisMonth = data.getDeliveriesThisMonth();
		String[] deliveriesThisMonthCategories = IntStream.rangeClosed(1, deliveriesThisMonth.size())
				.mapToObj(String::valueOf).toArray(String[]::new);

		monthConf.setTitle("Deliveries in " + FormattingUtils.getFullMonthName(today));
		monthConf.getxAxis().setCategories(deliveriesThisMonthCategories);
		monthConf.addSeries(new ListSeries("per Day", deliveriesThisMonth));
	}

	private void configureColumnChart(Configuration conf) {
		conf.getChart().setType(ChartType.COLUMN);
		conf.getChart().setBorderRadius(4);

		conf.getxAxis().setTickInterval(1);
		conf.getxAxis().setMinorTickLength(0);
		conf.getxAxis().setTickLength(0);

		conf.getyAxis().getTitle().setText(null);

		conf.getLegend().setEnabled(false);
	}

	private void populateYearlySalesChart(DashboardData data) {
		Configuration conf = yearlySalesGraph.getConfiguration();
		conf.getChart().setType(ChartType.AREASPLINE);
		conf.getChart().setBorderRadius(4);

		conf.setTitle("Sales last years");

		conf.getxAxis().setVisible(false);
		conf.getxAxis().setCategories(MONTH_LABELS);

		conf.getyAxis().getTitle().setText(null);

		int year = Year.now().getValue();
		for (int i = 0; i < 3; i++) {
			conf.addSeries(new ListSeries(Integer.toString(year - i), data.getSalesPerMonth(i)));
		}
	}
}
