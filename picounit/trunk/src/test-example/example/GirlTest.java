package example;

import picounit.Mocker;
import picounit.Test;

public class GirlTest implements Test {
	// Mocks
	private Boy boy;

	// Unit
	private Girl girl;

	public void mock(Boy boy) {
		this.boy = boy;

		this.girl = new Girl(boy);
	}

	public void testGirlKissesBoy(Mocker mocker) {
		boy.kiss();

	 	mocker.replay();

	 	girl.kiss();
	}
}