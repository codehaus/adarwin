package example;

public class Girl {
	private final Boy boy;

	public Girl(Boy boy) {
		this.boy = boy;
	}

	public void kiss() {
		boy.kiss();
	}
}
