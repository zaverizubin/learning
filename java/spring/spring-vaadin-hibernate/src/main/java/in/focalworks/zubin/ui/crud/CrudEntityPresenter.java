/**
 *
 */
package in.focalworks.zubin.ui.crud;

import in.focalworks.zubin.backend.data.entity.AbstractEntity;
import in.focalworks.zubin.backend.data.entity.User;
import in.focalworks.zubin.backend.service.FilterableCrudService;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;

public class CrudEntityPresenter<T extends AbstractEntity> extends EntityPresenter<T, CrudView<T, ?>> {

	private FilterablePageableDataProvider<T, String> filteredDataProvider;


	public CrudEntityPresenter(FilterableCrudService<T> crudService, User currentUser) {
		super(crudService, currentUser);

		filteredDataProvider = new CrudEntityDataProvider<>(crudService);
	}

	@Override
	public void setView(CrudView<T, ?> view) {
		super.setView(view);
		view.getGrid().setDataProvider(filteredDataProvider);
	}

	public void filter(String filter) {
		filteredDataProvider.setFilter(filter);
	}

	public void cancel() {
		cancel(this::closeSilently, getView()::openDialog);
	}

	public void closeSilently() {
		close();
		getView().closeDialog();
	}

	@Override
	public T createNew() {
		return open(super.createNew());
	}

	public void loadEntity(Long id) {
		loadEntity(id, this::open);
	}

	private T open(T entity) {
		getView().getBinder().readBean(entity);
		getView().getForm().getButtons().setSaveDisabled(true);
		getView().getForm().getButtons().setDeleteDisabled(isNew());
		getView().updateTitle(isNew());
		getView().openDialog();
		return entity;
	}

	public void save() {
		if (writeEntity()) {
			super.save(e -> {
				if (isNew()) {
					getView().showCreatedNotification();
					filteredDataProvider.refreshAll();
				} else {
					getView().showUpdatedNotification();
					filteredDataProvider.refreshItem(e);
				}
				closeSilently();
			});
		}
	}

	public void delete() {
		super.delete(e -> {
			getView().showDeletedNotification();
			filteredDataProvider.refreshAll();
			closeSilently();
		});
	}

	public void onValueChange(boolean isDirty) {
		getView().getForm().getButtons().setSaveDisabled(!isDirty);
	}
}
