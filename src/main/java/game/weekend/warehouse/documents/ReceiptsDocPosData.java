package game.weekend.warehouse.documents;

import java.math.BigDecimal;
import java.util.ArrayList;

import game.weekend.framework.core.DocData;
import game.weekend.framework.core.IEditable;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.ListData;

public class ReceiptsDocPosData extends DocData {

	public ReceiptsDocPosData(int id, int mode, IntFrame frame, ArrayList<ListData> al) {
		super(frame);
		setValue("id", id);
		this.mode = mode;
		this.al = al;

		// Чтение документа
		if (id != 0 && mode != IEditable.ADD) {
			for (ListData ld : al)
				if ((int) ld.getValue("id") == id) {
					listData = ld;
					setValue("id", listData.getValue("id"));

					setValue("group_id", listData.getValue("group_id"));
					setValue("group_name", listData.getValue("group_name"));

					setValue("product_id", listData.getValue("product_id"));
					setValue("article", listData.getValue("article"));
					setValue("name", listData.getValue("name"));
					setValue("unit", listData.getValue("unit"));

					setValue("quantity", listData.getValue("quantity"));
					setValue("price", listData.getValue("price"));
					setValue("amount", listData.getValue("amount"));
					setValue("vat", listData.getValue("vat"));
					setValue("amount_vat", listData.getValue("amount_vat"));
					setValue("total", listData.getValue("total"));
					setValue("rest", listData.getValue("rest"));
					setValue("price_x", listData.getValue("price_x"));
					break;
				}
		}

		// listData не может быть null
		if (listData == null) {
			this.mode = IEditable.ADD;
			listData = new ListData(frame);
		}

		// Умолчания при добавлении
		if (mode == IEditable.ADD || mode == IEditable.ADD_COPY) {
			setValue("product_id", 2);
			setValue("quantity", BigDecimal.valueOf(1));
		}
	}

	@Override
	public boolean save() {

		// При добавлении
		if (mode != IEditable.EDIT) {

			// формируется новый id
			int newId = 0;
			for (ListData ld : al) {
				int id = (int) ld.getValue("id");
				if (newId > id)
					newId = id;
			}
			--newId;
			setValue("id", newId);

			// и добавляется новая запись.
			al.add(listData);
		}

		// Соханение всех необходимых данных
		listData.setValue("id", getValue("id"));

		listData.setValue("group_id", getValue("group_id"));
		listData.setValue("group_name", getValue("group_name"));

		listData.setValue("product_id", getValue("product_id"));
		listData.setValue("article", getValue("article"));
		listData.setValue("name", getValue("name"));
		listData.setValue("unit", getValue("unit"));

		listData.setValue("quantity", getValue("quantity"));
		listData.setValue("price", getValue("price"));
		listData.setValue("amount", getValue("amount"));
		listData.setValue("vat", getValue("vat"));
		listData.setValue("amount_vat", getValue("amount_vat"));
		listData.setValue("total", getValue("total"));
		listData.setValue("rest", getValue("rest"));
		listData.setValue("price_x", getValue("price_x"));

		return true;
	}

	@Override
	public boolean delete() {
		if ((int) listData.getValue("id") != 0) {
			al.remove(listData);
			return true;
		}
		return false;
	}

	private int mode = IEditable.ADD;
	private ArrayList<ListData> al;
	private ListData listData;
}
