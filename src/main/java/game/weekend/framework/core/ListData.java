package game.weekend.framework.core;

public class ListData extends DocData {

	public ListData(IntFrame frame) {
		super(frame);
	}

	@Override
	public boolean save() {
		return false;
	}

	@Override
	public boolean delete() throws Exception {
		return false;
	}

}
